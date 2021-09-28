package com.github.igojeferson.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author tunatore
 */
public class MSAccessUtil {

    // UCanAccess 5.0.0 requires Java 8 or later to run
    public static Connection connectToAccess() {
        Connection connection = null;
        try {
            String dbConnectionString = "jdbc:ucanaccess://";

            String accessFileLocation = MSAccessUtil.class.getClassLoader().getResource("companydb.mdb").getPath();
            System.out.println("location of mdb file ==> " + accessFileLocation);
            String URL = dbConnectionString + accessFileLocation + ";memory=false";
            connection = DriverManager.getConnection(URL);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return connection;
    }

    public static void closeConnecion(Connection conn) {
        try {
            if (conn != null) {
                conn.commit();
                conn.close();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void runSELECTQueryOnAccess(Connection conn, String SQL) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                System.out.println(rs.getString("A") // Update the name of the talbes
                        + " " + rs.getString("B")
                        + " " + rs.getString("C"));
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void runINSERT_OR_DELETEQueryOnAccess(Connection conn, String SQL) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(SQL);
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}