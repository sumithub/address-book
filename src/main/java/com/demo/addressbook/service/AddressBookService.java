package com.demo.addressbook.service;

import com.demo.addressbook.model.AddressBook;
import com.demo.addressbook.model.Contact;

import java.util.List;

public interface AddressBookService {
    AddressBook createNewAddressBook(AddressBook addressBook);

    List<AddressBook> listAllAddressBooks();

    AddressBook findAddressBook(int id);

    List<Contact> listAllContactsInAddressBook(int id);

    Contact addContactToAddressBook(int addressBookId, Contact contact);

    Contact updateContactInAddressBook(int addressBookId, Contact updatedContact);

    void removeContactFromAddressBook(int addressBookId, int contactId);

    List<Contact> listAllUniqueContacts();

    void deleteAddressBook(int id);
}
