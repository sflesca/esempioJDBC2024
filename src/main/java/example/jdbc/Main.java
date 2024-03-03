package example.jdbc;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String driverName = "org.postgresql.Driver";
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String postgresURI = "jdbc:postgresql://localhost:5432/postgres";
        Connection con = null;
        try {
            con = DriverManager.getConnection(postgresURI,"postgres","root");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select id, name from miatabella");
            while (rs.next())
                System.out.println("ID: "+rs.getInt(1)+" NAME:"+rs.getString(2));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (con!=null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        System.out.println("Hello world!");
    }
}