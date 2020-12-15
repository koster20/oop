package ru.sgu.oop;
public class Clients {
    private int ID;
    private String Name;
    private String Passport;

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {

        Name = name;
    }

    public void setPassport(String passport) {

        Passport = passport;
    }

    public String getPassport() {
        return Passport;
    }

    public String getName() {
        return Name;
    }
    public int getID()
    {
        return this.ID;
    }
    public void showInfo(){
        System.out.println("id: " + getID() + "Имя: " + getName() + "Паспорт: " + getPassport());
    }
//    Clients() {
//
//        Name = "Undefined";
//        Passport = "0000000000";
//
//    }
}
