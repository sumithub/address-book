package com.demo.addressbook.entity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressBook {
    private int id;
    @NotEmpty(message = "Name couldn't be empty")
    @NotNull(message = "Required Name value is null")
    private String name;
    private final Map<Integer, Contact> contactMap = new HashMap<>();

    public AddressBook(String name) {
        this.name = name;
    }

    public AddressBook() {}

    public void addContact(Contact contact) {
        this.contactMap.put(contact.getId(), contact);
    }

    public void removeContact(Contact contact) {
        this.contactMap.remove(contact.getId());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Contact> getContacts() {
        return new ArrayList<>(this.contactMap.values());
    }
}
