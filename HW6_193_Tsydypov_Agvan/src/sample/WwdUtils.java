/*
     Derby - WwdUtils.java - utilitity methods used by WwdEmbedded.java

        Licensed to the Apache Software Foundation (ASF) under one
           or more contributor license agreements.  See the NOTICE file
           distributed with this work for additional information
           regarding copyright ownership.  The ASF licenses this file
           to you under the Apache License, Version 2.0 (the
           "License"); you may not use this file except in compliance
           with the License.  You may obtain a copy of the License at

             http://www.apache.org/licenses/LICENSE-2.0

           Unless required by applicable law or agreed to in writing,
           software distributed under the License is distributed on an
           "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
           KIND, either express or implied.  See the License for the
           specific language governing permissions and limitations
           under the License.

*/

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class WwdUtils {

    /*****************
     **  Asks user to enter a wish list item or 'exit' to exit the loop - returns
     **       the string entered - loop should exit when the string 'exit' is returned
     ******************/
    public static String getWishItem() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String ans = "";
        try {
            while (ans.length() == 0) {
                System.out.println("Enter wish-list item (enter exit to end): ");
                ans = br.readLine();
                if (ans.length() == 0)
                    System.out.print("Nothing entered: ");
            }
        } catch (java.io.IOException e) {
            System.out.println("Could not read response from stdin");
        }
        return ans;
    }  /**  END  getWishItem  ***/

    /***      Check for  WISH_LIST table    ****/
    public static boolean wwdChk4Table(Connection conTst) throws SQLException {
        try {
            Statement s = conTst.createStatement();
            s.execute("SELECT * FROM PHONE_BOOK");
        } catch (SQLException sqle) {
            String theError = (sqle).getSQLState();
            if (theError.equals("42X05")) {
                return false;
            }
        }
        return true;

    }
}