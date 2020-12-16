package ru.sgu.oop;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.ArrayList;
import java.util.List;

public class Data {
    @JacksonXmlElementWrapper(localName = "Clients")
    ArrayList<Clients> Client = new ArrayList<>();

    @JacksonXmlElementWrapper(localName = "Sellings")
    ArrayList<Sellings> Selling = new ArrayList<>();

    @JacksonXmlElementWrapper(localName = "Rooms")
    ArrayList<Rooms> Room = new ArrayList<>();



    public void setSell(Sellings sell) {
        Selling.add(sell);
    }

    public void setClient(Clients cl) {
        Client.add(cl);
    }



    public void setRoom(Rooms r) {
        Room.add(r);
    }
    @JsonIgnore
    public ArrayList<Rooms> getRooms() {
        return Room;
    }
    @JsonIgnore
    public ArrayList<Clients> getClients() {
        return Client;
    }

    @JsonIgnore
    public ArrayList<Sellings> getSellings() {
        return Selling;
    }
    @JsonIgnore
    public void ShowInfo() {
        for (Clients cl : Client) {
            System.out.println("cl");
            cl.showInfo();
        }
        for (Rooms r : Room) {
            System.out.println("R");
            r.ShowInfo();
        }
        for (Sellings sl : Selling) {
            System.out.println("sl");
            sl.ShowInfo();
        }
    }
}
