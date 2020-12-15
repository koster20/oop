package ru.sgu.oop;

class Sellings {
    private int ID;
    private String CheckInDate;
    private String ReleaseData;
    private Clients Customer = new Clients();
    private Rooms Settlement = new Rooms();

    public void setCheckInDate(String checkInDate) {
        CheckInDate = checkInDate;
    }

    public void setCustomer(Clients customer){ Customer=customer; }

    public void setReleaseData(String releaseData) {
        ReleaseData = releaseData;
    }

    public void setSettlement(Rooms settlement) {
        Settlement = settlement;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public Clients getCustomer() {
        return Customer;
    }

    public Rooms getSettlement() {
        return Settlement;
    }



    public String getCheckInDate() {
        return CheckInDate;
    }

    public String getReleaseData() {
        return ReleaseData;
    }
    public String CustomerForSQL() {
        return Customer.getName()+"\t"+"\t" + "\t" + Customer.getPassport();
    }

    public String SettlementForSQL() {
        return "\t" + "\t" + Settlement.getNumber() + "\t" + "\t" + "\t" + "\t" + Settlement.getCapacity()+"\t"+"\t" + "\t  "  + Settlement.getType()+"\t" + "\t"+Settlement.getPrice();
    }

}
