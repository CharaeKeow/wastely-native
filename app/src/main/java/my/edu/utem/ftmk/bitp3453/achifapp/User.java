package my.edu.utem.ftmk.bitp3453.achifapp;

public class User {
    private String uid;
    private String userName;
    private String email;
    private String phoneNo;
    //password is not saved for security reason. Best to leave that to Firebase Auth

    public User(String uid, String userName, String email, String phoneNo) {
        this.uid = uid;
        this.userName = userName;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
