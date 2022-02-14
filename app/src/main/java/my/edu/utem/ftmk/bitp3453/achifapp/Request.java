package my.edu.utem.ftmk.bitp3453.achifapp;

public class Request {

    private String name;
    private String phonenumber;
    private String quantity;
    private String requestid;
    private String description;
    private double timestamp;
    private double latitude, longitude;

    public Request() {}

    public Request(String name, String phonenumber, String quantity, String description, double latitude, double longitude){
        this.name = name;
        this.phonenumber = phonenumber;
        this.quantity = quantity;
        this.requestid = requestid;
        this.description = description;
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double longitude) {
        this.latitude = latitude;
    }
}
