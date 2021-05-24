package com.demo.addressbook.service;

import com.demo.addressbook.entity.AddressBook;
import com.demo.addressbook.repository.AddressBookRepository;

import java.util.List;

public class AddressBookService {
    private final AddressBookRepository addressBookRepository;

    public AddressBookService(AddressBookRepository addressBookRepository) {
        this.addressBookRepository = addressBookRepository;
    }

    public AddressBook createNewAddressBook(String name) {
        return null;
    }

    public List<AddressBook> getAllAddressBooks() {
        return null;
    }

    public AddressBook getAddressBookById(String id) {
        return null;
    }
}
