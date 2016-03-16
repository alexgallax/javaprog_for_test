package addressbook.model;

public class ContactData {
    private final int id;
    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final String group;
    private final String address;
    private final String mobile;
    private final String email;
    private final String notes;

    public ContactData(int id, String firstName, String middleName, String lastName,
                       String group,
                       String address, String mobile,
                       String email,
                       String notes) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.group = group;
        this.address = address;
        this.mobile = mobile;
        this.email = email;
        this.notes = notes;
    }

    public ContactData(String firstName, String middleName, String lastName,
                       String group,
                       String address, String mobile,
                       String email,
                       String notes) {
        this.id = Integer.MAX_VALUE;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.group = group;
        this.address = address;
        this.mobile = mobile;
        this.email = email;
        this.notes = notes;
    }

    public int getId() {
        return id;
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

    public String getGroup() {
        return group;
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

    @Override
    public String toString() {
        return "ContactData{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactData that = (ContactData) o;

        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        return lastName != null ? lastName.equals(that.lastName) : that.lastName == null;

    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }
}
