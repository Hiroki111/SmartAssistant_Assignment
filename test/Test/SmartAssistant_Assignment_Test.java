/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import smartassistant_assignment.SmartAssistant_Assignment;

/**
 *
 * @author takaha
 */
public class SmartAssistant_Assignment_Test {
    
    public SmartAssistant_Assignment_Test() {
    }
    
   
   
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void SmartA() {
        SmartAssistant_Assignment tester = new SmartAssistant_Assignment();
        String[] dummy = {"dummyDate","dummyFormat"};
        
        dummy = tester.getDateAndFormat("aaa");
        assertEquals(dummy[0],"");
        assertEquals(dummy[1],"");
        System.out.println("Date: " + dummy[0] + " Format: " + dummy[1]);
        
        dummy = tester.getDateAndFormat("Here a date: 20.09.2014");
        assertEquals(dummy[0],"20.09.2014");
        assertEquals(dummy[1],"dd/mm/yyyy");
        System.out.println("Date: " + dummy[0] + " Format: " + dummy[1]);
    
        dummy = tester.getDateAndFormat("Another date: 31.4.1999");
        assertEquals(dummy[0],"31.4.1999");
        assertEquals(dummy[1],"dd/mm/yyyy");
        System.out.println("Date: " + dummy[0] + " Format: " + dummy[1]);
        
        dummy = tester.getDateAndFormat("American date: 12-23-2003");
        assertEquals(dummy[0],"12-23-2003");
        assertEquals(dummy[1],"mm/dd/yyyy");
        System.out.println("Date: " + dummy[0] + " Format: " + dummy[1]);
        
        dummy = tester.getDateAndFormat("One more date: 1.10.2014");
        assertEquals(dummy[0],"1.10.2014");
        assertEquals(dummy[1],"mm/dd/yyyy");
        System.out.println("Date: " + dummy[0] + " Format: " + dummy[1]);
    }
    
    
}
