/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartassistant_assignment;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author takaha
 */
public class SmartAssistant_Assignment {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
        String inputCSV = "C:\\Users\\takaha\\Desktop\\SmartAssistant\\products.csv";
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputCSV), "UTF8"));
        String line;
        String cvsSplitBy = ";";

        ArrayList<ArrayList<String>> content = new ArrayList<>();

        //Create a matrix, which contains all the date from products.csv
        while ((line = br.readLine()) != null) {
            String[] splittedLine = line.split(cvsSplitBy);

            ArrayList<String> temp = new ArrayList<>();
            for (int i = 0; i < splittedLine.length; i++) {
                temp.add(splittedLine[i].replaceAll("\"", ""));
            }
            content.add(temp);
        }

        /*
        //See the content of the matrix of products.csv
        for (int i = 0; i < content.size(); i++) {
            ArrayList<String> temp = content.get(i);
            for (int l = 0; l < temp.size(); l++) {
                System.out.println("Row: " + i + " Col: " + l + " : " + temp.get(l));
            }
        }*/

        //Applly the transforamtion and put the modified data into an ArrayList, not a matrix
        String[] output = new String[content.size()];
        //The column names are fixed, so the first row is hard-coded.
        final String columnNames = "'nane'|'offerurl'|'price'|'published'|'description'";
        output[0] = columnNames;

        ArrayList<String> row = new ArrayList<>();

        //Iterate the rows of the matrix, except the first row (i.e. column names)
        for (int i = 1; i < content.size(); i++) {
            //temp contains the whole information of one single product
            row = content.get(i);
            String product = "";
            //The current row will transform into one product
            product = applyTransformation(product, row);
            output[i] = product;
            System.out.println("DONE " + i + " : " + output[i]);
        }

        String outputCSV ="C:\\Users\\takaha\\Desktop\\SmartAssistant\\test.csv";
        
    }

    public static String changePriceFormat(String price) {
        //Erase non-numeric values other than "." and "," (e.g. USD or EUR)
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

    public static String[] getDateAndFormat(String description) {
        String[] date = {"", ""};
        String DATE_REGEX1 = "([1-9]|0[1-9]|1[012])[-.](0[1-9]|[12][0-9]|3[01])[-.](19|20)\\d\\d";//mm/dd/yyyy
        String DATE_REGEX2 = "(0[1-9]|[12][0-9]|3[01])[-.]([1-9]|0[1-9]|1[012])[-.](19|20)\\d\\d";//dd/mm/yyyy

        Matcher m1 = Pattern.compile(DATE_REGEX1).matcher(description);
        Matcher m2 = Pattern.compile(DATE_REGEX2).matcher(description);

        if (m1.find()) {
            date[0] = m1.group();
            date[1] = "mm/dd/yyyy";
        } else if (m2.find()) {
            date[0] = m2.group();
            date[1] = "dd/mm/yyyy";
        }

        return date;
    }

    public static String changeDateFormat(String date) throws ParseException {
        List<SimpleDateFormat> knownPatterns = new ArrayList<SimpleDateFormat>();
        knownPatterns.add(new SimpleDateFormat("mm/dd/yyyy"));
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

    public static String reviseDateFormat(String date) {
        List<SimpleDateFormat> knownPatterns = new ArrayList<SimpleDateFormat>();
        knownPatterns.add(new SimpleDateFormat("dd/mm/yyyy"));
        knownPatterns.add(new SimpleDateFormat("dd-mm-yyyy"));
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

    public static String applyTransformation(String product, ArrayList<String> row) throws ParseException {
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
                        //This is when both "published" will be empty
                        product = product + "||'" + row.get(l) + "'";
                    } else if (dateAndFormat[1] == "mm/dd/yyyy") {
                        //The format needs to be changed from "mm/dd/yyyy" to "dd/mm/yyyy"
                        product = product + "|'" + changeDateFormat(dateAndFormat[0]) + "'|'" + row.get(l) + "'";
                    } else {
                        //The format is revised since it could d/mm/yyyy, instead of dd/mm/yyyy
                        product = product + "|'" + reviseDateFormat(dateAndFormat[0]) + "'|'" + row.get(l) + "'";
                    }
                    break;
                default:
                    product = "invalid information was detected";
                    break;
            }
        }
        return product;

    }

    public static void writeCSV(String[] output, String FileUrl){
    
        
    }
}
