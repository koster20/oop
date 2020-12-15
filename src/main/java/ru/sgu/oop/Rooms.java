package ru.sgu.oop;

public class Rooms {
    private int ID;
    private int Number;
    private int Capacity;
    private String Type;
    private int Price;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCapacity() {
        return Capacity;
    }

    public int getNumber() {
        return Number;
    }

    public int getPrice() {
        return Price;
    }

    public String getType() {
        return Type;
    }

    public void setCapacity(int capacity) {
        Capacity = capacity;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public void setType(String type) {
        Type = type;
    }

    public void ShowInfo(){
        System.out.println("ID: " + getID()+"\nНомер: "+getNumber()+"\nВместимость: " + getCapacity()+"\nЦена" + getPrice()+ "\nТип: " +getType());
    }

//    Rooms(int number, int capacity, String type, int price){
//        Number= number;
//        Capacity = capacity;
//        Type = type;
//        Price = price;
//    }
//    Rooms()
//    {
//        Number = 0;
//        Capacity =0;
//        Type = "undefined";
//        Price = 0;
//    }
}
