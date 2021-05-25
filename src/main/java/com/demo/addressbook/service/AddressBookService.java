package com.demo.addressbook.service;

import com.demo.addressbook.entity.AddressBook;
import com.demo.addressbook.entity.Contact;
import com.demo.addressbook.repository.AddressBookRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AddressBookService {
    private final AddressBookRepository addressBookRepository;

    public AddressBookService(AddressBookRepository addressBookRepository) {
        this.addressBookRepository = addressBookRepository;
    }

    public AddressBook createNewAddressBook(AddressBook addressBook) {
        return addressBookRepository.save(addressBook);
    }

    public List<AddressBook> getAllAddressBooks() {
        return addressBookRepository.findAll();
    }

    public AddressBook getAddressBookById(int id) {
        return addressBookRepository.findById(id);
    }

    public List<Contact> getAllContactsInAddressBook(int id) {
        AddressBook addressBook = addressBookRepository.findById(id);
        if (addressBook != null)
            return addressBook.getContacts();
        return new ArrayList<>();
    }


    public Contact addContactToAddressBook(int addressBookId, Contact contact) {
        AddressBook addressBook = addressBookRepository.findById(addressBookId);

        if (addressBook != null) {
            contact.setId(Math.abs(contact.hashCode()));
            addressBook.addContact(contact);
            addressBookRepository.save(addressBook);
        }
        return contact;
    }

    public Contact updateContactInAddressBook(int addressBookId, Contact updatedContact) {
        AddressBook addressBook = addressBookRepository.findById(addressBookId);

        if (addressBook != null) {
            Optional<Contact> oldContact = getContactById(addressBook, updatedContact.getId());
            if (oldContact.isPresent()) {
                addressBook.addContact(updatedContact);
                addressBookRepository.save(addressBook);
            }
        }
        return updatedContact;
    }

    public void removeContactFromAddressBook(int addressBookId, int contactId) {
        AddressBook addressBook = addressBookRepository.findById(addressBookId);

        if (addressBook != null) {
            Optional<Contact> contactToBeRemoved = getContactById(addressBook, contactId);
            if (contactToBeRemoved.isPresent())
                    addressBook.removeContact(contactToBeRemoved.get());
                addressBookRepository.save(addressBook);
            }
        }

    private Optional<Contact> getContactById(AddressBook addressBook, int contactId) {
        Optional<Contact> contact = addressBook
                .getContacts()
                .stream()
                .filter(c -> c.getId() == contactId)
                .findFirst();
        return contact;
    }

    public List<Contact> findAllUniqueContacts() {
        List<Contact> contacts = new ArrayList<>();
        List<AddressBook> addressBooks = addressBookRepository.findAll();
        for (AddressBook addressBook: addressBooks)
            contacts.addAll(addressBook.getContacts());
        return contacts
                .stream()
                .distinct()
                .collect(Collectors.toList());
    }

    public void deleteAddressBookById(int id) {
         addressBookRepository.deleteById(id);
    }
}
