/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartassistant_assignment;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

        
        DecimalFormat dec = new DecimalFormat();
        DecimalFormatSymbols decFS = new DecimalFormatSymbols();
        
        decFS.setDecimalSeparator('.');
        
        //dec.setGroupingUsed(false);
        dec.setDecimalFormatSymbols(decFS);
        
        NumberFormat format = NumberFormat.getInstance();
        Number number;

        //See the content
        for (int i = 0; i < content.size(); i++) {
            ArrayList<String> temp = content.get(i);

            for (int l = 0; l < temp.size(); l++) {

                if (i != 0 && l == 3) {
                    //DecimalFormat dec = new DecimalFormat(temp.get(l), decFS);
                    //System.out.println("Line: " + i + " : " + l + " : " + temp.get(l));
                    //change the decimal mark here, not by replaceAll(",", ".")
                    
                    //then, erase non-numeric values
                    temp.set(l,temp.get(l).replaceAll("[^0-9.,]", ""));
                    
                    //Erase grouping marks, set the decimal marks as dot
                    
                    
                    number = format.parse(temp.get(l));
                    Double d = number.doubleValue();
                    temp.set(l,dec.format(d));
                    
                    System.out.println(temp.get(l));
                    
                    
                    //System.out.println("Line: " + i + " : " + l + " : " + temp.get(l).replaceAll("[^0-9.,]", ""));
                } else {
                    //System.out.println("Line: " + i + " : " + l + " : " + temp.get(l));
                }
                //System.out.println("Line: " + i + " : " + l + " : " + temp.get(l));

            }

        }

        /*
         while((line = br.readLine()) != null) {
         String[] temp = line.split(cvsSplitBy);
         System.out.println(temp[0] + " ; " + temp[1] + " ; " + temp[2] + " ; " + temp[3] + " ; " + temp[4]);
         }
         */
    }

}
