package cs636.music.presentation;

/**
 * Created by pavithra on 10/17/17.
 */

import java.sql.*;

import static cs636.music.dao.DBConstants.*;


public class Register {
    private static Connection connection;
    public static void main(String[] args){
        String dataBaseUrl = null;
        String userName = null;
        String userPassword = null;
        if (args.length == 0 || args.length == 1) {  // no args: leave dbUrl null, for HSQLDB
//            inFile = "test.dat";
        } else if (args.length == 3) {
            dataBaseUrl = args[0];
            userName = args[1];
            userPassword = args[2];
        } else {
            System.out
                    .println("usage:java <dataBaseUrl> <userName> <userPassword> ");
            return;
        }

        try {
            String driverName = createDBConnection (dataBaseUrl, userName, userPassword);
            System.out.println("Connection established with:" + driverName );

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            addUserToDB();
            System.out.println("User added to DataBase");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }



    private static String  createDBConnection(String dataBaseUrl, String userName, String userPassword) throws SQLException {
        String driver = null;

        if (dataBaseUrl == null)
            dataBaseUrl = HSQLDB_URL;  // default to HSQLDB
        if (dataBaseUrl.contains("oracle")) {
            driver = ORACLE_DRIVER;
        } else if (dataBaseUrl.contains("mysql")) {
            driver = MYSQL_DRIVER;
        } else if (dataBaseUrl.contains("hsqldb")) {
            driver = HSQLDB_DRIVER;
            userName = "sa";
            userPassword = "";
        } else throw new SQLException("Unknown DB URL pattern in DbDAO constructor");
        System.out.println("Connecting using driver "+ driver+ ", DB URL " + dataBaseUrl);
        try {
            Class.forName(driver);
        } catch (Exception e) {
            throw new SQLException("Problem with loading driver: " + e);
        }
        connection = DriverManager.getConnection(dataBaseUrl, userName, userPassword);
        return driver;
    }

    private static void addUserToDB() throws SQLException{
        Statement stmt = connection.createStatement();

        stmt.execute("insert into  site_user (user_id, firstname, lastname, email_address) VALUES " +
                  "(4 , 'jimmy', 'fallon', 'jfallon@gmail.com')");

        try {
            ResultSet result = stmt.executeQuery("select * from  site_user");
            while(result.next()){
                System.out.println(result.getInt(1));


            }


//
        } finally {
            stmt.close();
        }
    }




}
