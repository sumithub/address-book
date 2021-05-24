package com.demo.addressbook.service;

import com.demo.addressbook.entity.AddressBook;
import com.demo.addressbook.repository.AddressBookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressBookService {
    private final AddressBookRepository addressBookRepository;

    public AddressBookService(AddressBookRepository addressBookRepository) {
        this.addressBookRepository = addressBookRepository;
    }

    public AddressBook createNewAddressBook(String name) {
        return addressBookRepository.createByName(name);
    }

    public List<AddressBook> getAllAddressBooks() {
        return addressBookRepository.finaAll();
    }

    public AddressBook getAddressBookById(String id) {
        return addressBookRepository.findById(id);
    }
}
