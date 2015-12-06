/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartassistant_assignment;

import com.opencsv.CSVWriter;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The whole process can be described in the following way. First of all, the
 * input data is going to be extracted and put into a matrix, which is made
 * ArrayList<String>. Then, the required transformation will be made on the each
 * product inside of the matrix. Once a product has the transformation, the
 * product will be stored in an array of String. Finally, the array, which will
 * contain all the products with required transformation, will be written into
 * the CSV file.
 *
 * @author Hiroki Takahashi
 */
public class SmartAssistant_Assignment {

    //The following two variables have to be modified, based on the directories of the input and output files.
    static String inputFile = "C:\\Users\\takaha\\Desktop\\SmartAssistant\\products.csv";
    static String outputFile = "C:\\Users\\takaha\\Desktop\\SmartAssistant\\result.csv";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF8"));
        String line;
        String cvsSplitBy = ";";

        //This matrix will contain all the input data from products.csv
        ArrayList<ArrayList<String>> inputMatrix = new ArrayList<>();

        //Fill in the matrix of the input data
        while ((line = br.readLine()) != null) {
            String[] splittedLine = line.split(cvsSplitBy);
            ArrayList<String> temp = new ArrayList<>();

            for (int i = 0; i < splittedLine.length; i++) {
                //The quote character is removed here, so that the transoformation would be easy to make later
                temp.add(splittedLine[i].replaceAll("\"", ""));
            }
            inputMatrix.add(temp);
        }

        //All the data have been obtained, so the next step is to make the transformation on them
        //and fill in this output array with the data
        String[] outputArray = new String[inputMatrix.size()];

        //The column names are fixed, so the first row is hard-coded.
        final String columnNames = "'nane'|'offerurl'|'price'|'published'|'description'";
        outputArray[0] = columnNames;

        //Iterate each row of the matrix, except the first row (i.e. column names)
        ArrayList<String> row = new ArrayList<>();
        for (int i = 1; i < inputMatrix.size(); i++) {
            //From here, "row" will contain the whole information of one single product
            row = inputMatrix.get(i);
            String product = "";
            //The current row will transform into one product
            product = applyTransformation(row);
            //Now, "product" contains all the information of one single product.
            outputArray[i] = product;
            //The following line can be used to see the detail of the product
            //System.out.println("Transformation is done " + i + " : " + outputArray[i]);
        }

        writeCSV(outputArray, outputFile);
    }

   /**
    * This will change a string of price and return it in the required format.
    * @param Price which may contain non-meric values and commas
    * @return Price which is either in "___.__" or "__" format
    */
    public static String changePriceFormat(String price) {
        //Remove non-numeric values other than "." and "," (e.g. USD or EUR)
        price = price.replaceAll("[^0-9.,]", "");

        //Then, replace "," with "."
        price = price.replaceAll(",", ".");

        //Split the value by each dot
        String[] priceArray = price.split("\\.");

        StringBuilder stringBuilder = new StringBuilder();
        //Remove all the unnecessary dots
        if (priceArray.length >= 2) {
            for (int x = 0; x < (priceArray.length - 1); x++) {
                stringBuilder.append(priceArray[x]);
            }
            stringBuilder.append(".");
        }
        stringBuilder.append(priceArray[priceArray.length - 1]);

        //Assign and return the price in the expected format
        price = stringBuilder.toString();
        return price;
    }

    /**
     * This will get a string of description and return an array of strings,
     * which contains the date and its format information
     * (The process to change the format is separated)
     * @param Description from the input file
     * @return An array which contains the date in day/month/year order and its format information
     */
    public static String[] getDateAndFormat(String description) {
        String[] date = {"", ""};
        String DATE_REGEX1 = "([1-9]|0[1-9]|1[012])[-.](0[1-9]|[12][0-9]|3[01])[-.](19|20)\\d\\d";//mm/dd/yyyy
        String DATE_REGEX2 = "(0[1-9]|[12][0-9]|3[01])[-.]([1-9]|0[1-9]|1[012])[-.](19|20)\\d\\d";//dd/mm/yyyy

        Matcher mm_dd_yyyy = Pattern.compile(DATE_REGEX1).matcher(description);
        Matcher dd_mm_yyyy = Pattern.compile(DATE_REGEX2).matcher(description);

        if (mm_dd_yyyy.find()) {
            date[0] = mm_dd_yyyy.group();
            date[1] = "mm/dd/yyyy";
        } else if (dd_mm_yyyy.find()) {
            date[0] = dd_mm_yyyy.group();
            date[1] = "dd/mm/yyyy";
        }

        return date;
    }

    /**
     * This will change a date which in month/day/year format into
     * dd.mm.yyyy format and return it 
     * @param date in month/day/year order
     * @return date in dd.mm.yyyy format
     * @throws ParseException 
     */
    public static String changeDateFormat(String date) throws ParseException {
        List<SimpleDateFormat> knownPatterns = new ArrayList<SimpleDateFormat>();
        knownPatterns.add(new SimpleDateFormat("mm-dd-yyyy"));
        knownPatterns.add(new SimpleDateFormat("mm.dd.yyyy"));
        final String NEW_FORMAT = "dd.mm.yyyy";

        for (SimpleDateFormat pattern : knownPatterns) {
            try {
                // Take a try
                Date d = pattern.parse(date);
                pattern.applyPattern(NEW_FORMAT);
                return date = pattern.format(d);

            } catch (ParseException pe) {
                // Loop on
            }
        }
        return date;
    }

    /**
     * This will revise a date which is in day/month/year order, since it sometimes has
     * only one digit in its day. Also, sometimes a hyphen is used instead of a comma.
     * If any of them is identified in the input date, it will be fixed (e.g., 1-12-2015 will be 01.12.2015)
     * and then returned.
     * @param Date in day/month/year order
     * @return Date in dd.mm.yyyy format
     */
    public static String reviseDateFormat(String date) {
        List<SimpleDateFormat> knownPatterns = new ArrayList<SimpleDateFormat>();
        knownPatterns.add(new SimpleDateFormat("dd-mm-yyyy"));//This is not used for this particular task.
        knownPatterns.add(new SimpleDateFormat("dd.mm.yyyy"));
        final String NEW_FORMAT = "dd.mm.yyyy";

        for (SimpleDateFormat pattern : knownPatterns) {
            try {
                // Take a try
                Date d = pattern.parse(date);
                pattern.applyPattern(NEW_FORMAT);
                return date = pattern.format(d);

            } catch (ParseException pe) {
                // Loop on
            }
        }
        return date;
    }

    /**
     * This will apply the required transformation on the input, which is an array list
     * containing information of one product from the input file.
     * Then, the information will be returned in String.
     * @param Row which contains information of one product from the input file
     * @return String which contains information of one product with required transformation
     * @throws ParseException 
     */
    public static String applyTransformation(ArrayList<String> row) throws ParseException {
        String product = "";

        for (int l = 0; l < row.size(); l++) {
            switch (l) {
                case 0://Name
                    product = product + "'" + row.get(l) + "'";
                    break;
                case 1://Link
                    product = product + "|'" + row.get(l) + "?id=";
                    break;
                case 2://SKU
                    product = product + "" + row.get(l) + "'";
                    break;
                case 3://Selling-Price
                    product = product + "|'" + changePriceFormat(row.get(l)) + "'";
                    break;
                case 4://description
                    String[] dateAndFormat = getDateAndFormat(row.get(l));
                    if (dateAndFormat[0] == "") {
                        //This is when "description" is empty
                        product = product + "||'" + row.get(l) + "'";
                    } else if (dateAndFormat[1] == "mm/dd/yyyy") {
                        //The format needs to be changed from "mm/dd/yyyy" to "dd/mm/yyyy"
                        product = product + "|'" + changeDateFormat(dateAndFormat[0]) + "'|'" + row.get(l) + "'";
                    } else {
                        //The format is revised since it could d/mm/yyyy, instead of dd/mm/yyyy
                        product = product + "|'" + reviseDateFormat(dateAndFormat[0]) + "'|'" + row.get(l) + "'";
                    }
                    break;
                default://This would be invoked if there are 6 or more columns in the input CSV
                    product = "The input data is invalid";
                    break;
            }
        }
        return product;
    }

    /**
     * This will write information all the products into the designated directory, where there is supposed
     * to be a CSV file.
     * All the information of the products are in an array of String.
     * @param output
     * @param FileUrl
     * @throws IOException 
     */
    public static void writeCSV(String[] output, String FileUrl) throws IOException {
        CSVWriter writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream(FileUrl), "ISO-8859-1"), '\n');

        writer.writeNext(output);
        writer.close();
    }
}
