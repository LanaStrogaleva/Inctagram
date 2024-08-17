package api.profile;

public class Profile {
    String username;
    String firstName;
    String lastName;
    String dateOfBirth;
    String country;
    String city;
    String aboutMe;

    public Profile() {

    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public Profile withUsername(String username) {
        this.username = username;
        return  this;
    }

    public Profile withFirstName(String firstName) {
        this.firstName = firstName;
        return  this;
    }

    public Profile withLastName(String lastName) {
        this.lastName = lastName;
        return  this;
    }

    public Profile withDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return  this;
    }

    public Profile withCountry(String country) {
        this.country = country;
        return  this;
    }
    public Profile withCity(String city) {
        this.city = city;
        return  this;
    }
    public Profile withAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
        return  this;
    }
}
