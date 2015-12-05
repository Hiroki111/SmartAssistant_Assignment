/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import java.text.ParseException;
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

    @Test
    public void TestGetDateAndFormat() {
        SmartAssistant_Assignment tester = new SmartAssistant_Assignment();
        String[] dummy = {"dummyDate", "dummyFormat"};

        dummy = tester.getDateAndFormat("aaa");
        assertEquals(dummy[0], "");
        assertEquals(dummy[1], "");
        System.out.println("Date: " + dummy[0] + " Format: " + dummy[1]);

        dummy = tester.getDateAndFormat("Here a date: 20.09.2014");
        assertEquals(dummy[0], "20.09.2014");
        assertEquals(dummy[1], "dd/mm/yyyy");
        System.out.println("Date: " + dummy[0] + " Format: " + dummy[1]);

        dummy = tester.getDateAndFormat("Another date: 31.4.1999");
        assertEquals(dummy[0], "31.4.1999");
        assertEquals(dummy[1], "dd/mm/yyyy");
        System.out.println("Date: " + dummy[0] + " Format: " + dummy[1]);

        dummy = tester.getDateAndFormat("American date: 12-23-2003");
        assertEquals(dummy[0], "12-23-2003");
        assertEquals(dummy[1], "mm/dd/yyyy");
        System.out.println("Date: " + dummy[0] + " Format: " + dummy[1]);

        dummy = tester.getDateAndFormat("One more date: 1.10.2014");
        assertEquals(dummy[0], "1.10.2014");
        assertEquals(dummy[1], "mm/dd/yyyy");
        System.out.println("Date: " + dummy[0] + " Format: " + dummy[1]);
    }

    @Test
    public void TestChangePriceFormat() {
        SmartAssistant_Assignment tester = new SmartAssistant_Assignment();

        String dummy = "";

        dummy = tester.changePriceFormat("USD 1,232.99");
        assertEquals(dummy, "1232.99");
        System.out.println(dummy);

        dummy = tester.changePriceFormat("USD 8654.56");
        assertEquals(dummy, "8654.56");
        System.out.println(dummy);

        dummy = tester.changePriceFormat("345,99 [euro symbole]");
        assertEquals(dummy, "345.99");
        System.out.println(dummy);

        dummy = tester.changePriceFormat("EUR 1255,59");
        assertEquals(dummy, "1255.59");
        System.out.println(dummy);

        dummy = tester.changePriceFormat("$34.00");
        assertEquals(dummy, "34.00");
        System.out.println(dummy);

        dummy = tester.changePriceFormat("431.333,0 EUR");
        assertEquals(dummy, "431333.0");
        System.out.println(dummy);

        dummy = tester.changePriceFormat("34,99");
        assertEquals(dummy, "34.99");
        System.out.println(dummy);

        dummy = tester.changePriceFormat("234.99");
        assertEquals(dummy, "234.99");
        System.out.println(dummy);

        dummy = tester.changePriceFormat("42");
        assertEquals(dummy, "42");
        System.out.println(dummy);
    }

    @Test
    public void TestChangeDateFormat() throws ParseException {
        SmartAssistant_Assignment tester = new SmartAssistant_Assignment();

        String dummy = "";
        
        dummy = tester.reviseDateFormat("20.09.2014");
        System.out.println(dummy);
        assertEquals(dummy, "20.09.2014");

        dummy = tester.reviseDateFormat("31.4.1999");
        System.out.println(dummy);
        assertEquals(dummy, "31.04.1999");

        dummy = tester.changeDateFormat("12-23-2003");
        System.out.println(dummy);
        assertEquals(dummy, "23.12.2003");

        dummy = tester.changeDateFormat("1.10.2014");
        System.out.println(dummy);
        assertEquals(dummy, "10.01.2014");

    }
}
