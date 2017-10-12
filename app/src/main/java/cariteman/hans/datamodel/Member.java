package cariteman.hans.datamodel;

/**
 * Created by hans.sagita on 12/10/2017.
 */

public class Member {
    private String email;

    public Member(String email) {
        this.email = email;
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
