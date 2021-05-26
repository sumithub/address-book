package com.demo.addressbook.repository;

import com.demo.addressbook.model.AddressBook;
import com.demo.addressbook.model.Contact;

import java.util.List;

public interface AddressBookRepository {
    AddressBook findById(int id);
    void deleteById(int id);
    List<AddressBook> findAll();
    List<Contact> listAddressBookContacts(AddressBook addressBook);
    AddressBook save(AddressBook addressBook);
}
