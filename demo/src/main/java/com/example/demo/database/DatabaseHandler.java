package com.example.demo.database;


import com.example.demo.controller.CharacterController;
import com.example.demo.controller.CookieController;
import com.example.demo.model.Character;
import com.example.demo.view.LoginView;
import org.springframework.aop.scope.ScopedObject;

import javax.annotation.ManagedBean;
import javax.inject.Named;
import java.sql.*;

@Named
public class DatabaseHandler {

    private static final String DB_USERNAME = "3TPwwoisvN";
    private static final String DB_DBNAME = "3TPwwoisvN";
    private static final String DB_PASSWORD = "8vHjTGL9Kc";
    private static final String DB_SERVERNAME = "remotemysql.com";
    private static final Integer DB_PORT = 3306;
    private static Statement statement = null;
    private static Connection connection = connect();



    private static String username;
    private static String password;
    private static String redirectSite;

    public static String redirect() {
        System.out.println("Redirecting to " + redirectSite + "....");
        return redirectSite;
    }


    public static Connection connect() {
        Connection conn = null;


        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Couldn't find Driver class...");
        }

        //System.out.println("Trying to connect to the database...");
        try {
            conn = DriverManager.getConnection("jdbc:mysql://" + DB_SERVERNAME + "/" + DB_DBNAME, DB_USERNAME, DB_PASSWORD);
            //System.out.println("Successfully connected to the database..");
            return conn;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to the database...");
            return null;
        }
    }

    private static void insertBasicDataIntoNewUser(Connection conn, String username) {
        try {
            statement = conn.createStatement();
        } catch (SQLException e) {
            System.out.println("Couldn't create a new statement to insert basic date into a new user's table.");
            e.printStackTrace();
        }

        String sql = "INSERT INTO " + username + " VALUES(" +
                "'" + username + "'," +
                "'null'," +
                "'null'," +
                "'100'," +
                "'0'," +
                "'0'," +
                "'10'," +
                "'10'," +
                "'10'," +
                "'10'," +
                "'-1'," +
                "'-1'," +
                "'-1'," +
                "'-1'," +
                "'-1'," +
                "'-1'," +
                "'-1'," +
                "'-1'," +
                "'-1'," +
                "'-1'," +
                "'-1'," +
                "'-1'," +
                "'-1'," +
                "'-1')";


        try {
            statement.executeUpdate(sql);
            System.out.println("Successfully inserted basic data into + " + username);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Something went wrong with inserting basic data into " + username);
        }
    }

    public static void createNewUser(Connection conn, String username) {
        try {
            statement = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Something went wrong while creating statement to make a new USER table");
        }
        String sql = "CREATE TABLE IF NOT EXISTS " + username + "(" +
                "name VARCHAR(15) PRIMARY KEY NOT NULL DEFAULT " + "'" + username + "'"+ ","+
                "race VARCHAR(30) NOT NULL," +
                "img VARCHAR(100) NOT NULL," +
                "energy INT NOT NULL," +
                "exp INT NOT NULL DEFAULT 0," +
                "cash INT NOT NULL DEFAULT 0, " +
                "str INT NOT NULL DEFAULT 10," +
                "intel INT NOT NULL DEFAULT 10," +
                "con INT NOT NULL DEFAULT 10," +
                "dex INT NOT NULL DEFAULT 10," +
                "helmet INT," +
                "gloves INT," +
                "boots INT," +
                "outfit INT," +
                "weapon INT," +
                "jewellery INT," +
                "inv1 INT," +
                "inv2 INT," +
                "inv3 INT," +
                "inv4 INT," +
                "inv5 INT," +
                "inv6 INT," +
                "inv7 INT," +
                "inv8 INT)";

        try {
            statement.executeUpdate(sql);
            System.out.println("Succesfully created new " + username + " table");
            insertBasicDataIntoNewUser(conn, username);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Couldn't create new " + username + " table. Check if " + username + " table already exists or not!");
        }


    }

    public static void createUsersTable(Connection conn) {


        System.out.println("Trying to create new USERS table..");
        try {
            statement = conn.createStatement();
        } catch (SQLException e) {
            System.out.println("Something went wrong while creating the statement for making a new USERS table.");
        }

        String sql = "CREATE TABLE IF NOT EXISTS USERS (" +
                "username VARCHAR(15) PRIMARY KEY NOT NULL," +
                "password VARCHAR(20) NOT NULL" +
                ")";

        try {
            statement.executeUpdate(sql);
            System.out.println("Succesfully created new USERS table");
        } catch (SQLException e) {
            System.out.println("User already exists!");
        }
    }

    public static void updateUser(String username, String whatToUpdate, Object newValue) {

        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Something went wrong while trying to create statement to update the user..");
        }

        String sql = "UPDATE " + username + " SET " + whatToUpdate + " = " + newValue;
        System.out.println("Trying to update " + username + " TABLE's " + whatToUpdate + " field to " + newValue);
        try {
            statement.executeUpdate(sql);
            System.out.println("Successfully updated " + username + " TABLE's " + whatToUpdate + " field to " + newValue);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Couldn't update " + username + " TABLE's " + whatToUpdate + " field to " + newValue);
        }


    }

    public static void dropTable(Connection conn, String tableName) {
        try {
            statement = conn.createStatement();
        } catch (SQLException e) {
            System.out.println("Something wente wrong while creating a statement to drop the table");
        }

        String sql = "DROP TABLE " + tableName;
        try {
            statement.executeUpdate(sql);
            System.out.println("Successfully dropped table " + tableName);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Something went wrong whle dropping table " + tableName);
        }


    }

    public static void saveUserData(String username) {
        Connection conn = DatabaseHandler.connect();
        ResultSet resultSet;
        Statement statement;
        try {
            statement = conn.createStatement();
            String sql = "SELECT * FROM " + username;
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                System.out.println("Setting index " + CharacterController.getLoggedInUsersCounter() + " to " + resultSet.getString("name"));
                CharacterController.getLoggedInUsers().get(CharacterController.getLoggedInUsersCounter()).setName(resultSet.getString("name"));
                CharacterController.getLoggedInUsers().get(CharacterController.getLoggedInUsersCounter()).setRace(resultSet.getString("race"));
                CharacterController.getLoggedInUsers().get(CharacterController.getLoggedInUsersCounter()).setImg(resultSet.getString("img"));
                CharacterController.getLoggedInUsers().get(CharacterController.getLoggedInUsersCounter()).setExp(resultSet.getInt("exp"));
                CharacterController.getLoggedInUsers().get(CharacterController.getLoggedInUsersCounter()).setCash(resultSet.getInt("cash"));
                CharacterController.getLoggedInUsers().get(CharacterController.getLoggedInUsersCounter()).setStr(resultSet.getInt("str"));
                CharacterController.getLoggedInUsers().get(CharacterController.getLoggedInUsersCounter()).setIntel(resultSet.getInt("intel"));
                CharacterController.getLoggedInUsers().get(CharacterController.getLoggedInUsersCounter()).setCon(resultSet.getInt("con"));
                CharacterController.getLoggedInUsers().get(CharacterController.getLoggedInUsersCounter()).setDex(resultSet.getInt("dex"));
                CharacterController.getLoggedInUsers().get(CharacterController.getLoggedInUsersCounter()).setHelmet(resultSet.getInt("helmet"));
                CharacterController.getLoggedInUsers().get(CharacterController.getLoggedInUsersCounter()).setGloves(resultSet.getInt("gloves"));
                CharacterController.getLoggedInUsers().get(CharacterController.getLoggedInUsersCounter()).setBoots(resultSet.getInt("boots"));
                CharacterController.getLoggedInUsers().get(CharacterController.getLoggedInUsersCounter()).setOutfit(resultSet.getInt("outfit"));
                CharacterController.getLoggedInUsers().get(CharacterController.getLoggedInUsersCounter()).setWeapon(resultSet.getInt("weapon"));
                CharacterController.getLoggedInUsers().get(CharacterController.getLoggedInUsersCounter()).setJewellry(resultSet.getInt("jewellery"));
                CharacterController.getLoggedInUsers().get(CharacterController.getLoggedInUsersCounter()).setInv1(resultSet.getInt("inv1"));
                CharacterController.getLoggedInUsers().get(CharacterController.getLoggedInUsersCounter()).setInv2(resultSet.getInt("inv2"));
                CharacterController.getLoggedInUsers().get(CharacterController.getLoggedInUsersCounter()).setInv3(resultSet.getInt("inv3"));
                CharacterController.getLoggedInUsers().get(CharacterController.getLoggedInUsersCounter()).setInv4(resultSet.getInt("inv4"));
                CharacterController.getLoggedInUsers().get(CharacterController.getLoggedInUsersCounter()).setInv5(resultSet.getInt("inv5"));
                CharacterController.getLoggedInUsers().get(CharacterController.getLoggedInUsersCounter()).setInv6(resultSet.getInt("inv6"));
                CharacterController.getLoggedInUsers().get(CharacterController.getLoggedInUsersCounter()).setInv7(resultSet.getInt("inv7"));
                CharacterController.getLoggedInUsers().get(CharacterController.getLoggedInUsersCounter()).setInv8(resultSet.getInt("inv8"));
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        Connection connection = connect();
        createUsersTable(connection);



    }


}

