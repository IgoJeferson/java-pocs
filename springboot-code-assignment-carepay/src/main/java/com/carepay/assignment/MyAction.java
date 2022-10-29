package com.carepay.assignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class MyAction {
    public boolean debug = true;

    @Autowired
    public DataSource ds;

    public Collection getCustomers(String firstName, String lastName, String address, String zipCode, String city) throws SQLException {
        Connection conn = ds.getConnection();
        String query = new String("SELECT * FROM customers where 1=1");
        if (firstName != null) {
            query = query + " and first_name = '" + firstName + "'";
        }
        if (firstName != null) {
            query = query + " and last_name = '" + firstName + "'";
        }
        if (firstName != null) {
            query = query + " and address = '" + address + "'";
        }
        if (firstName != null) {
            query = query + " and zip_code = '" + zipCode + "'";
        }
        if (firstName != null) {
            query = query + " and city = '" + city + "'";
        }
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        List customers = new ArrayList();
        while (rs.next()) {
            Object[] objects = new Object[] { rs.getString(1), rs.getString(2) };
            if (debug) print(objects, 4);
            customers.add(new Customer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
        }
        return customers;
    }

    public void print(Object[] s, int indent) {
        for (int i=0; i<=indent; i++) System.out.print(' ');
        printUpper(s);
    }

    public static void printUpper(Object [] words){
        words.length
        int i = 0;
        try {
            while (true){
                if (words[i].getClass() == String.class) {
                    String so = (String)words[i];;
                    so = so.toUpperCase();
                    System.out.println(so);
                }
                i++;
            }
        } catch (IndexOutOfBoundsException e) {
            //iteration complete
        }
    }
}