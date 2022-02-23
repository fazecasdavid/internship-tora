package cubs.training.data;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class Person implements Serializable {

    private static final long serialVersionUID = 42L;
    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final String id;
    private final String email;

    public Person(String firstName, String middleName, String lastName, String id, String email) {
        this.firstName = StringUtils.capitalize(firstName);
        this.middleName = StringUtils.capitalize(middleName);
        this.lastName = StringUtils.capitalize(lastName);
        this.id = id;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", id='" + id + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
