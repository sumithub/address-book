package com.demo.addressbook.service;

import com.demo.addressbook.model.AddressBook;
import com.demo.addressbook.model.Contact;
import com.demo.addressbook.repository.AddressBookRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AddressBookServiceImpl implements AddressBookService {
    private final AddressBookRepository addressBookRepository;

    public AddressBookServiceImpl(AddressBookRepository addressBookRepository) {
        this.addressBookRepository = addressBookRepository;
    }

    @Override
    public AddressBook createNewAddressBook(AddressBook addressBook) {
        return addressBookRepository.save(addressBook);
    }

    @Override
    public List<AddressBook> listAllAddressBooks() {
        return addressBookRepository.findAll();
    }

    @Override
    public AddressBook findAddressBook(int id) {
        AddressBook addressBook = addressBookRepository.findById(id);
        if (addressBook == null)
            throw new ResourceNotFoundException("Address Book with given ID doesn't exist");

        return addressBook;
    }

    @Override
    public void deleteAddressBook(int id) {
        addressBookRepository.deleteById(id);
    }

    @Override
    public Contact addContactToAddressBook(int addressBookId, Contact contact) {
        AddressBook addressBook = addressBookRepository.findById(addressBookId);
        if (addressBook == null)
            throw new ResourceNotFoundException("Address Book with given ID doesn't exist");

        contact.setId(Math.abs(contact.hashCode()));
        addressBook.addContact(contact);
        addressBookRepository.save(addressBook);
        return contact;
    }


    @Override
    public List<Contact> listAllContactsInAddressBook(int id) {
        AddressBook addressBook = addressBookRepository.findById(id);
        if (addressBook == null)
            throw new ResourceNotFoundException("Address Book with given ID doesn't exist");

        return addressBookRepository.listAddressBookContacts(addressBook);
    }

    @Override
    public Contact updateContactInAddressBook(int addressBookId, Contact updatedContact) {
        AddressBook addressBook = addressBookRepository.findById(addressBookId);
        if (addressBook == null)
            throw new ResourceNotFoundException("Address Book with given ID doesn't exist");

            Optional<Contact> oldContact = getContactById(addressBook, updatedContact.getId());
            if (oldContact.isPresent()) {
                addressBook.addContact(updatedContact);
                addressBookRepository.save(addressBook);
        }
            else
                throw new ResourceNotFoundException("Given Contact doesn't exist");
        return updatedContact;
    }

    @Override
    public void removeContactFromAddressBook(int addressBookId, int contactId) {
        AddressBook addressBook = addressBookRepository.findById(addressBookId);
        if (addressBook == null)
            throw new ResourceNotFoundException("Address Book with given ID doesn't exist");

            Optional<Contact> contactToBeRemoved = getContactById(addressBook, contactId);
            if (contactToBeRemoved.isPresent()) {
                addressBook.removeContact(contactToBeRemoved.get());
                addressBookRepository.save(addressBook);
            }
            else
                throw new ResourceNotFoundException("Contact with given ID doesn't exist");
        }

    private Optional<Contact> getContactById(AddressBook addressBook, int contactId) {
        return addressBook
                .getContacts()
                .stream()
                .filter(c -> c.getId() == contactId)
                .findFirst();
    }

    @Override
    public List<Contact> listAllUniqueContacts() {
        List<Contact> contacts = new ArrayList<>();
        List<AddressBook> addressBooks = addressBookRepository.findAll();
        for (AddressBook addressBook: addressBooks)
            contacts.addAll(addressBook.getContacts());
        return contacts
                .stream()
                .distinct()
                .collect(Collectors.toList());
    }
}
