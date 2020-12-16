package ru.sgu.oop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;

public class oopapp {




    public static Connection connect(String url) {
        Connection conn = null;
        try {
            // db parameters

            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e);
        }
        return conn;
    }

    //"jdbc:sqlite:Resource/sqlite/Sellings.db"
    public static Data selectAll(String url) {
        String sql = "SELECT * FROM Sellings";
        Connection conn = connect(url);
        Data data = new Data();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);


            // loop through the result set
            while (rs.next()) {


                Sellings sell = new Sellings();
                Clients client = new Clients();
                Rooms room = new Rooms();
                sell.setID(rs.getInt("ID"));
                sell.setCheckInDate(rs.getString("checkInDate"));
                sell.setReleaseData(rs.getString("releaseDate"));

                int Clientid = rs.getInt("clientId");
                try (Statement stci = conn.createStatement();
                     ResultSet ci = stci.executeQuery("SELECT * from Clients WHERE ID=" + Clientid)) {
                    client.setID(ci.getInt("ID"));
                    client.setName(ci.getString("Name"));
                    client.setPassport(ci.getString("Passport"));
                    sell.setCustomer(client);
                } catch (SQLException e) {
                    System.out.println(e + " in 51-59");
                }


                int RoomId = rs.getInt("roomId");
                try (Statement stri = conn.createStatement();
                     ResultSet ri = stri.executeQuery("SELECT * from Rooms WHERE Id=" + RoomId)) {
                    room.setID(ri.getInt("ID"));
                    room.setNumber(ri.getInt("Number"));
                    room.setCapacity(ri.getInt("Capacity"));
                    room.setPrice(ri.getInt("Price"));
                    room.setType(ri.getString("Type"));
                    sell.setSettlement(room);
                } catch (SQLException e) {
                    System.out.println(e + " in 64-71");
                }


                data.setSell(sell);
            }

        }catch (SQLException e) {
            System.out.println(e);
        }

        sql = "SELECT * FROM Clients";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Clients clients = new Clients();
                clients.setID(rs.getInt("Id"));
                clients.setName(rs.getString("Name"));
                clients.setPassport(rs.getString("Passport"));

                data.setClient(clients);
            }


        } catch (SQLException e) {
            System.out.println(e);
        }
        sql = "SELECT * FROM rooms";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Rooms rooms = new Rooms();
                rooms.setID(rs.getInt("Id"));
                rooms.setCapacity(rs.getInt("Capacity"));
                rooms.setNumber(rs.getInt("Number"));
                rooms.setPrice(rs.getInt("Price"));
                rooms.setType(rs.getString("Type"));
                data.setRoom(rooms);
            }
            return data;

        } catch (SQLException e) {
            System.out.println(e);
        }
        return data;
    }

    //"jdbc:sqlite:Resource/sqlite/out.db"
    public static void writeSQL(Data data, String url) {
        Connection conn = connect(url);
        try {


            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS Clients ( ID INTEGER PRIMARY KEY UNIQUE, Name TEXT NOT NULL, Passport TEXT NOT NULL)");
            stmt.execute("CREATE TABLE IF NOT EXISTS Rooms (ID INTEGER PRIMARY KEY UNIQUE, Number INTEGER NOT NULL, Capacity INTEGER NOT NULL, Type TEXT NOT NULL, PRICE INTEGER NOT NULL)");
            stmt.execute("CREATE TABLE IF NOT EXISTS Sellings (ID INTEGER PRIMARY KEY UNIQUE, RoomID INTEGER NOT NULL, ClientID INTEGER NOT NULL, CheckInDate TEXT NOT NULL, ReleaseDate TEXT NOT NULL, FOREIGN KEY (RoomID) REFERENCES Rooms(ID),FOREIGN KEY (ClientID) REFERENCES Client(ID))");
            ArrayList<ru.sgu.oop.Clients> clients = data.getClients();


            for (Clients cl : clients) {


                stmt.execute("REPLACE INTO Clients VALUES(" + cl.getID() + "," + "\"" + cl.getName() + "\"" + "," + "\"" + cl.getPassport() + "\"" + ")");

            }
            ArrayList<Rooms> rooms = data.getRooms();
            for (Rooms r : rooms) {

                stmt.execute("REPLACE INTO Rooms VALUES(" + r.getID() + "," + r.getNumber() + "," + r.getCapacity() + "," + "\"" + r.getType() + "\"" + "," + r.getPrice() + ")");

            }
            for (Sellings sl : data.getSellings()) {
                stmt.execute("REPLACE INTO Sellings VALUES (" + sl.getID() + "," + sl.getSettlement().getID() + "," + sl.getCustomer().getID() + "," + "\"" + sl.getCheckInDate() + "\"" + "," + "\"" + sl.getReleaseData() + "\"" + ")");
            }

        } catch (SQLException e) {
            System.out.print(e + " in 132 - 157");
        }
    }

    public static Data deserializeFromXML() {
        Data deserializeSells = new Data();
        try {
            XmlMapper xmlMapper = new XmlMapper();
            String readSells = new String(Files.readAllBytes(Paths.get("Resource/Sellings.xml")));

            deserializeSells = xmlMapper.readValue(readSells, Data.class);

            serializeToXML(deserializeSells);


        } catch (IOException e) {
            // handle the exception
            System.out.println(e);
        }
        return deserializeSells;
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

        Data data = deserializeFromXML();
        data.ShowInfo();
        writeSQL(data, "jdbc:sqlite:Resource/sqlite/Sellings.db");
        data = selectAll("jdbc:sqlite:Resource/sqlite/Sellings.db");
        data.ShowInfo();
//        writeSQL(data, "jdbc:sqlite:Resource/sqlite/out.db");
//        data = selectAll("jdbc:sqlite:Resource/sqlite/out.db");
//        data.ShowInfo();
    }
}
