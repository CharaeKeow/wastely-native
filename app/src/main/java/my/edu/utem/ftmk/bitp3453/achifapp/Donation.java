package my.edu.utem.ftmk.bitp3453.achifapp;

public class Donation {
    private String donationTitle;
    private String phoneNo;
    private String description;
    private String pickUpTime;
    private String quantity;
    //private int[] coordinate = {12, 133};
    //private String userID; //id of person who created this
    //private boolean isPickUp;

    public Donation() {}

    public Donation(String donationTitle, String phoneNo, String description, String pickUpTime, String quantity) {
        this.donationTitle = donationTitle;
        this.phoneNo = phoneNo;
        this.description = description;
        this.pickUpTime = pickUpTime;
        this.quantity = quantity;
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
    //private String requesterID; //id of person who requested this
}
