package api.user;

public class User {
    String userName;
    String email;
    String password;
    String passwordConfirm;
    String baseUrl = "https://inctagram.work";

    public User() {

    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getConfirmPassword() {
        return passwordConfirm;
    }
    public User withUserName(String userName) {
        this.userName = userName;
        return this;
    }
    public User withEmail(String email) {
        this.email = email;
        return this;
    }

    public User withPassword(String password) {
        this.password = password;
        return this;
    }
    public User withConfirmPassword(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", passwordConfirm='" + passwordConfirm + '\'' +
                ", baseUrl='" + baseUrl + '\'' +
                '}';
    }

}
