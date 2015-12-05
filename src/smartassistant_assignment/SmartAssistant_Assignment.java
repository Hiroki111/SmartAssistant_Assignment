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
import java.util.ArrayList;
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
        String csvFile = "C:\\Users\\takaha\\Desktop\\SmartAssistant\\products.csv";
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile), "UTF8"));
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

        //See the content of the matrix of products.csv
        for (int i = 0; i < content.size(); i++) {
            ArrayList<String> temp = content.get(i);
            for (int l = 0; l < temp.size(); l++) {
                System.out.println("Row: " + i + " Col: " + l + " : " + temp.get(l));
            }            
        }
        
        
        //Applly the transforamtion and put the modified data into an ArrayList, not a matrix
        ArrayList<String> output = new ArrayList<>();
        final String columnNames ="'nane'|'offerurl'|'price'|'published'|'description'";        
        output.set(0, columnNames);
        
         ArrayList<String> temp = new ArrayList<>();
        
        //Iterate the rows, except the first row (i.e. column names)
        for (int i = 1; i < content.size(); i++) {
            temp = content.get(i);

            //Iterate the columns (i.e. each cell)
            for (int l = 0; l < temp.size(); l++) {
                
                if(l == 0){
                    
                }
                
                /*
                //Modify the price format
                if (i != 0 && l == 3) {
                    String price = changePriceFormat(temp.get(l));
                    //Set the resulting price in the expected format
                    temp.set(l, price);
                }
*/
                
                System.out.println("Row: " + i + " Col: " + l + " : " + temp.get(l));
            }

        }


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

    public String[] getDateAndFormat(String description) {
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

}
