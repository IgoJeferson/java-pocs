package com.github.igojeferson.example;

import java.sql.Connection;

public class MSAccessJavaApplication {

    public static void main(String[] args) {

        Connection conn = MSAccessUtil.connectToAccess();
        /*MSAccessUtil.runINSERT_OR_DELETEQueryOnAccess(conn, "DELETE FROM Amber");*/
        /*for (int i = 0; i < 10; i++) {
            String SQL ="INSERT INTO Amber VALUES('" + i + "','" + i + "','" + i + "')";
            MSAccessUtil.runINSERT_OR_DELETEQueryOnAccess(conn, SQL);
        }*/
        MSAccessUtil.runSELECTQueryOnAccess(conn, "SELECT * FROM Amber");
        MSAccessUtil.closeConnecion(conn);

    }
}