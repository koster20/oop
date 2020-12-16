package ru.sgu.oop;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.ArrayList;
import java.util.List;

public class Data {
    @JacksonXmlElementWrapper(localName = "Clients")
    ArrayList<ru.sgu.oop.Clients> Client = new ArrayList<>();

    @JacksonXmlElementWrapper(localName = "Sellings")
    ArrayList<Sellings> Selling = new ArrayList<>();

    @JacksonXmlElementWrapper(localName = "Rooms")
    ArrayList<ru.sgu.oop.Rooms> Room = new ArrayList<>();



    public void setSell(ru.sgu.oop.Sellings sell) {
        Selling.add(sell);
    }

    public void setClient(ru.sgu.oop.Clients cl) {
        Client.add(cl);
    }



    public void setRoom(ru.sgu.oop.Rooms r) {
        Room.add(r);
    }
    @JsonIgnore
    public List<ru.sgu.oop.Rooms> getRoom() {
        return Room;
    }
    @JsonIgnore
    public List<ru.sgu.oop.Clients> getClient() {
        return Client;
    }

    @JsonIgnore
    public List<ru.sgu.oop.Sellings> getSelling() {
        return Selling;
    }

}
