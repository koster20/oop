package ru.sgu.oop;

import java.util.ArrayList;
import java.util.List;

public class Data {
    ArrayList<ru.sgu.oop.Clients> Clients = new ArrayList<>();
    ArrayList<Sellings> Sellings= new ArrayList<>();
    ArrayList<ru.sgu.oop.Rooms> Rooms = new ArrayList<>();



    public List<ru.sgu.oop.Sellings> getSelling() {
        return Sellings;
    }
    public void setSell(ru.sgu.oop.Sellings sell) {
        Sellings.add(sell);
    }

    public void setClients(ru.sgu.oop.Clients cl) {
        Clients.add(cl);
    }
    public List<ru.sgu.oop.Clients> getClients() {
        return Clients;
    }
    public List<ru.sgu.oop.Rooms> getRooms() {
        return Rooms;
    }

    public void setRooms(ru.sgu.oop.Rooms r) {
        Rooms.add(r);
    }
}
