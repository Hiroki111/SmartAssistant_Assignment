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
        //initialize B-reader
        String csvFile = "C:\\Users\\takaha\\Desktop\\SmartAssistant\\products.csv";
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile), "UTF8"));
        String line;
        int lineNumber = 0;
        String cvsSplitBy = ";";

        ArrayList<ArrayList<String>> content = new ArrayList<ArrayList<String>>();
        //content.add(new ArrayList<String>());

        while ((line = br.readLine()) != null) {
            String[] splittedLine = line.split(cvsSplitBy);

            ArrayList<String> temp = new ArrayList<String>();
            for (int i = 0; i < splittedLine.length; i++) {
                //System.out.println(lineNumber + " : " + i + " : " + splittedLine[i]);
                //content.get(lineNumber).add(splittedLine[i]); There may be an error in "content.get(lineNumber)"
                temp.add(splittedLine[i].replaceAll("\"", ""));
            }
            content.add(temp);
            lineNumber++;
        }

        //See the content
        for (int i = 0; i < content.size(); i++) {
            ArrayList<String> temp = content.get(i);

            for (int l = 0; l < temp.size(); l++) {

                //Modify the price format
                if (i != 0 && l == 3) {
                    //Erase non-numeric values other than "." and "," (e.g. USD or EUR)
                    temp.set(l, temp.get(l).replaceAll("[^0-9.,]", ""));

                    //Then, replace "," with "."
                    temp.set(l, temp.get(l).replaceAll(",", "."));

                    //Split the value by each dot
                    String tempArray[] = temp.get(l).split("\\.");
                    StringBuilder test = new StringBuilder();

                    //Remove all the unnecessary dots
                    if (tempArray.length >= 2) {
                        for (int x = 0; x < (tempArray.length - 1); x++) {
                            test.append(tempArray[x]);
                        }
                        test.append(".");
                    }
                    test.append(tempArray[tempArray.length - 1]);

                    //Set the resulting price in the expected format
                    temp.set(l, test.toString());
                }

                //Detect date
                if (i != 0 && l == 4) {
                    System.out.println("Line: " + i + " : " + l + " : " + temp.get(l));
                }

                //System.out.println("Line: " + i + " : " + l + " : " + temp.get(l));
            }

        }

        
    }

    public String[] getDateAndFormat(String description) {
        String[] date = {"",""};
        String DATE_REGEX1 = "([1-9]|0[1-9]|1[012])[-.](0[1-9]|[12][0-9]|3[01])[-.](19|20)\\d\\d";//mm/dd/yyyy
        String DATE_REGEX2 = "(0[1-9]|[12][0-9]|3[01])[-.]([1-9]|0[1-9]|1[012])[-.](19|20)\\d\\d";//dd/mm/yyyy

        Matcher m1 = Pattern.compile(DATE_REGEX1).matcher(description);
        Matcher m2 = Pattern.compile(DATE_REGEX2).matcher(description);

        if (m1.find()) {
            date[0] = m1.group();
            date[1] = "mm/dd/yyyy";
        }else if(m2.find()){
            date[0] = m2.group();
            date[1] = "dd/mm/yyyy";            
        }

        return date;
    }

}
