package cariteman.hans.datamodel;

/**
 * Created by hans.sagita on 12/10/2017.
 */

public class Member {
    private String email;
    private String address;
    private String fullName;
    private String phoneNumber;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Member(String email, String address, String fullName, String phoneNumber) {
        this.email = email;
        this.address = address;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    public Member() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
