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
    public static void main(String[] args) throws FileNotFoundException, IOException {
    //initialize B-reader
        String csvFile ="C:\\Users\\takaha\\Desktop\\SmartAssistant\\products.csv";
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile),"UTF8"));
        String line;
        int lineNumber = 0;
        String cvsSplitBy =";";
        
        ArrayList<ArrayList<String>> content = new ArrayList<ArrayList<String>>();
        //content.add(new ArrayList<String>());
        
        while((line = br.readLine()) != null){
            String[] splittedLine = line.split(cvsSplitBy);
            
            ArrayList<String> temp = new ArrayList<String>();
            for(int i =0; i < splittedLine.length ; i++){
                System.out.println(lineNumber + " : " + i + " : " + splittedLine[i]);
                //content.get(lineNumber).add(splittedLine[i]); There may be an error in "content.get(lineNumber)"
                temp.add(splittedLine[i]);
            }
            content.add(temp);
            lineNumber++;
        }

        
        /*
        while((line = br.readLine()) != null) {
            String[] temp = line.split(cvsSplitBy);
            System.out.println(temp[0] + " ; " + temp[1] + " ; " + temp[2] + " ; " + temp[3] + " ; " + temp[4]);
        }
        */
    
    }
    
}
