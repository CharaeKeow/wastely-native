package my.edu.utem.ftmk.bitp3453.achifapp;

public class Donation {
    private String donationTitle;
    private String phoneNo;
    private String description;
    private String pickUpTime;
    private String quantity;
    private double latitude;
    private double longitude;
    private String donorID;
    //private boolean isPickUp;

    public Donation() {}

    public Donation(String donationTitle, String phoneNo, String description, String pickUpTime, String quantity, double latitude, double longitude, String donorID) {
        this.donationTitle = donationTitle;
        this.phoneNo = phoneNo;
        this.description = description;
        this.pickUpTime = pickUpTime;
        this.quantity = quantity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.donorID = donorID;
    }

    public String getDonationTitle() {
        return donationTitle;
    }

    public void setDonationTitle(String donationTitle) {
        this.donationTitle = donationTitle;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(String pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDonorID() {
        return donorID;
    }

    public void setDonorID(String donorID) {
        this.donorID = donorID;
    }
    //private String requesterID; //id of person who requested this
}
