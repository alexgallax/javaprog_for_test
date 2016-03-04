package addressbook;

public class ContactData {
    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final String address;
    private final String mobile;
    private final String email;
    private final String notes;

    public ContactData(String firstName, String middleName, String lastName,
                       String address, String mobile,
                       String email,
                       String notes) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.address = address;
        this.mobile = mobile;
        this.email = email;
        this.notes = notes;
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

    public String getAddress() {
        return address;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getNotes() {
        return notes;
    }
}
