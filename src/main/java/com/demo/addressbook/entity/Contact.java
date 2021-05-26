package com.demo.addressbook.entity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

public class Contact {

    private int id;
    @NotEmpty(message = "First Name couldn't be empty")
    @NotNull(message = "Required First Name value is null")
    private String firstName;

    @NotEmpty(message = "Last Name couldn't be empty")
    @NotNull(message = "Required Last Name value is null")
    private String lastName;

    private List<String> phoneNumbers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Contact)) {
            return false;
        }
        Contact contact = (Contact) obj;
        return Objects.equals(firstName.toLowerCase(), contact.getFirstName().toLowerCase())
                && Objects.equals(lastName.toLowerCase(), contact.getLastName().toLowerCase())
                && phoneNumbers.containsAll(contact.getPhoneNumbers())
                && contact.getPhoneNumbers().containsAll(phoneNumbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName.toLowerCase(), lastName.toLowerCase(), phoneNumbers);
    }
}
