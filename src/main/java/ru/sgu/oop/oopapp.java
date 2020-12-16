package ru.sgu.oop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import functionalTests.component.migration.X;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public class oopapp {

    public static Connection connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:Resource/sqlite/Sellings.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e);
        }
        return conn;
    }

    public static void selectAll() {
        String sql = "SELECT * FROM Sellings";
        Connection conn = connect();
        Data data = new Data();
        System.out.println("Check in date \t Release date \t Client name \t Client passport \t Room number \t Room capacity \t Room type \t Room price");
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);


            // loop through the result set
            while (rs.next()) {


                Sellings sell = new Sellings();
                Clients client = new Clients();
                Rooms room = new Rooms();

                sell.setCheckInDate(rs.getString("checkInDate"));
                sell.setReleaseData(rs.getString("releaseDate"));

                int Clientid = rs.getInt("clientId");
                try (Statement stci = conn.createStatement();
                     ResultSet ci = stci.executeQuery("SELECT * from Client WHERE Id=" + Clientid)) {
                    client.setName(ci.getString("name"));
                    client.setPassport(ci.getString("passport"));
                    sell.setCustomer(client);
                } catch (SQLException e) {
                    System.out.println(e + " in 51-59");
                }


                int RoomId = rs.getInt("roomId");
                try (Statement stri = conn.createStatement();
                     ResultSet ri = stri.executeQuery("SELECT * from Rooms WHERE Id=" + RoomId)) {
                    room.setNumber(ri.getInt("Number"));
                    room.setCapacity(ri.getInt("Capacity"));
                    room.setPrice(ri.getInt("Price"));
                    room.setType(ri.getString("Type"));
                    sell.setSettlement(room);
                } catch (SQLException e) {
                    System.out.println(e + " in 64-71");
                }
                System.out.println(sell.getCheckInDate() + "\t" + "\t " + sell.getReleaseData() + "\t" + "\t" + "\t" + sell.CustomerForSQL() + "\t" + sell.SettlementForSQL());


                data.setSell(sell);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        sql = "SELECT * FROM Сlients";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){
                Clients clients = new Clients();
                clients.setID(rs.getInt("Id"));
                clients.setName(rs.getString("Name"));
                clients.setPassport(rs.getString("Passport"));

                data.setClient(clients);
            }

        }catch (SQLException e) {
            System.out.println(e);
        }
    }

//    public static void writeSQL(Data data) {
//        Connection conn = connect();
//        try {
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery("CREATE TABLE IF NOT  EXISTS Clients");
//            rs = stmt.executeQuery("CREATE TABLE IF NOT  EXISTS Rooms");
//            rs = stmt.executeQuery("CREATE TABLE IF NOT  EXISTS Sells");
//
//
//        } catch (SQLException e) {
//            System.out.print(e);
//        }
//    }

    public static void deserializeFromXML() {

        try {
            XmlMapper xmlMapper = new XmlMapper();
            String readSells = new String(Files.readAllBytes(Paths.get("Resource/Sellings.xml")));

            Data deserializeSells = xmlMapper.readValue(readSells, Data.class);

            serializeToXML(deserializeSells);

        } catch (IOException e) {
            // handle the exception
            System.out.println(e);
        }
    }

    //Client client, Room room,
    public static void serializeToXML(Data sell) {

        try {
            XmlMapper xmlMapper = new XmlMapper();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            xmlMapper.writeValue(byteArrayOutputStream, sell);
            File write = new File("Resource/out/Sellings.xml");
            FileWriter fileWriter = new FileWriter(write);
            fileWriter.write(byteArrayOutputStream.toString());
            fileWriter.close();



        } catch (JsonProcessingException e) {
            // handle exception
            System.out.println(e);
        } catch (IOException e) {
            // handle exception
            System.out.println(e);
        }
    }


    public static void main(String[] args) {

        deserializeFromXML();
        selectAll();

    }
}
