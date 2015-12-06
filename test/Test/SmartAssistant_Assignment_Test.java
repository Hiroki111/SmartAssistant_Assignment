/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;
import smartassistant_assignment.SmartAssistant_Assignment;

/**
 *
 * @author Hiroki Takahashi
 */
public class SmartAssistant_Assignment_Test {

    @Test
    public void TestGetDateAndFormat() {
        SmartAssistant_Assignment tester = new SmartAssistant_Assignment();
        String[] dummy = {"dummyDate", "dummyFormat"};

        //Description with no date
        dummy = tester.getDateAndFormat("aaa");
        assertEquals(dummy[0], "");
        assertEquals(dummy[1], "");
        System.out.println("Date: " + dummy[0] + " Format: " + dummy[1]);

        //Description with dd.mm.yyyy date
        dummy = tester.getDateAndFormat("Here a date: 20.09.2014");
        assertEquals(dummy[0], "20.09.2014");
        assertEquals(dummy[1], "dd/mm/yyyy");
        System.out.println("Date: " + dummy[0] + " Format: " + dummy[1]);

        //Description with dd.m.yyyy date
        dummy = tester.getDateAndFormat("Another date: 31.4.1999");
        assertEquals(dummy[0], "31.4.1999");
        assertEquals(dummy[1], "dd/mm/yyyy");
        System.out.println("Date: " + dummy[0] + " Format: " + dummy[1]);

        //Description with mm-dd-yyyy date
        dummy = tester.getDateAndFormat("American date: 12-23-2003");
        assertEquals(dummy[0], "12-23-2003");
        assertEquals(dummy[1], "mm/dd/yyyy");
        System.out.println("Date: " + dummy[0] + " Format: " + dummy[1]);

        //Description with m/dd/yyyy date
        dummy = tester.getDateAndFormat("One more date: 1.10.2014");
        assertEquals(dummy[0], "1.10.2014");
        assertEquals(dummy[1], "mm/dd/yyyy");
        System.out.println("Date: " + dummy[0] + " Format: " + dummy[1]);
    }

    @Test
    public void TestChangePriceFormat() {
        SmartAssistant_Assignment tester = new SmartAssistant_Assignment();

        String dummy = "";

        //Remove the currency name and comma
        dummy = tester.changePriceFormat("USD 1,232.99");
        assertEquals(dummy, "1232.99");
        System.out.println(dummy);

        //Remove the currency name
        dummy = tester.changePriceFormat("USD 8654.56");
        assertEquals(dummy, "8654.56");
        System.out.println(dummy);

        //Remove the currency symbol and replace the comma with a dot
        dummy = tester.changePriceFormat("345,99 €");
        assertEquals(dummy, "345.99");
        System.out.println(dummy);

        //Remove the currency name and replace the comma with a dot
        dummy = tester.changePriceFormat("EUR 1255,59");
        assertEquals(dummy, "1255.59");
        System.out.println(dummy);

        //Remove the currency name
        dummy = tester.changePriceFormat("$34.00");
        assertEquals(dummy, "34.00");
        System.out.println(dummy);

        //Remove the currency name, remove the unnecessary dot, and replace the comma with a dot 
        dummy = tester.changePriceFormat("431.333,0 EUR");
        assertEquals(dummy, "431333.0");
        System.out.println(dummy);

        //Replace the comma with a dot 
        dummy = tester.changePriceFormat("34,99");
        assertEquals(dummy, "34.99");
        System.out.println(dummy);

        //No change
        dummy = tester.changePriceFormat("234.99");
        assertEquals(dummy, "234.99");
        System.out.println(dummy);

        //No change
        dummy = tester.changePriceFormat("42");
        assertEquals(dummy, "42");
        System.out.println(dummy);
    }

    @Test
    public void TestChangeDateFormat() throws ParseException {
        SmartAssistant_Assignment tester = new SmartAssistant_Assignment();

        String dummy = "";

        //No change
        dummy = tester.reviseDateFormat("20.09.2014");
        System.out.println(dummy);
        assertEquals(dummy, "20.09.2014");

        //There needs to be one more digits in the month
        dummy = tester.reviseDateFormat("31.4.1999");
        System.out.println(dummy);
        assertEquals(dummy, "31.04.1999");

        //Change the order and separators
        dummy = tester.changeDateFormat("12-23-2003");
        System.out.println(dummy);
        assertEquals(dummy, "23.12.2003");

        //Change the order and add one more "0" in the month
        dummy = tester.changeDateFormat("1.10.2014");
        System.out.println(dummy);
        assertEquals(dummy, "10.01.2014");

    }

    @Test
    public void TestTransformation() throws ParseException {
        SmartAssistant_Assignment tester = new SmartAssistant_Assignment();
        String dummy = "";
        ArrayList<String> dummyRow = new ArrayList<>();

        //All the information will be concatenated into one string
        String[] dummyList1 = new String[]{"Product 1", "http://dummy.com", "sku", "1234.0", "description with no date"};
        dummyRow.addAll(Arrays.asList(dummyList1));
        dummy = tester.applyTransformation(dummyRow);
        System.out.println(dummy);
        assertEquals(dummy, "'Product 1'|'http://dummy.com?id=sku'|'1234.0'||'description with no date'");

        dummyRow.clear();

        //All the information will be concatenated into one string
        String[] dummyList2 = new String[]{"Product 2", "http://dummy.com", "sku159", "$10", "'Here a date: 20.09.2014"};
        dummyRow.addAll(Arrays.asList(dummyList2));
        dummy = tester.applyTransformation(dummyRow);
        System.out.println(dummy);
        assertEquals(dummy, "'Product 2'|'http://dummy.com?id=sku159'|'10'|'20.09.2014'|''Here a date: 20.09.2014'");

        dummyRow.clear();

        //All the information will be concatenated into one string
        String[] dummyList3 = new String[]{"Product 3", "http://dummy.com", "SKU", "123,456.78 EUR"};
        dummyRow.addAll(Arrays.asList(dummyList3));
        dummy = tester.applyTransformation(dummyRow);
        System.out.println(dummy);
        assertEquals(dummy, "'Product 3'|'http://dummy.com?id=SKU'|'123456.78'");
    }
}
