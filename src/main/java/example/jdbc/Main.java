package example.jdbc;

import java.sql.*;

public class Main {
    static String driverName = "org.postgresql.Driver";
    static String postgresURI = "jdbc:postgresql://localhost:5432/postgres";

    public static void main(String[] args) {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        query();
        insert();
        query();
    }

    public static void query(){
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
    }

    public static void insert(){
        String name = "Nuovo nome";
        Connection con = null;
        try {
            con = DriverManager.getConnection(postgresURI, "postgres", "root");
            String generatedColumns[] = { "id" };
            PreparedStatement pstm = con.prepareStatement("insert into miatabella(name) values (?)",generatedColumns);
            pstm.setString(1, name);

            int affectedRows = pstm.executeUpdate();
            if(affectedRows>0){
                ResultSet generatedKeys = pstm.getGeneratedKeys();
                if (generatedKeys.next()){
                    System.out.println("Generata riga con id"+generatedKeys.getInt(1));
                }
            }else{
                System.out.println(affectedRows);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}