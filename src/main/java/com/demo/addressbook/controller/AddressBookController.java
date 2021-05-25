package com.demo.addressbook.controller;

import com.demo.addressbook.entity.AddressBook;
import com.demo.addressbook.entity.Contact;
import com.demo.addressbook.service.AddressBookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AddressBookController.BASE_URL)
public class AddressBookController {

    public static final String BASE_URL = "/api/v1/address-book";

    private final AddressBookService addressBookService;

    public AddressBookController(AddressBookService addressBookService) {
        this.addressBookService = addressBookService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AddressBook> getListOfAddressBooks() {
        return addressBookService.getAllAddressBooks();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AddressBook createNewAddressBook(@RequestBody AddressBook addressBook) {
        return addressBookService.createNewAddressBook(addressBook);
    }

    @PostMapping("/{addressBookId}/contacts")
    @ResponseStatus(HttpStatus.CREATED)
    public Contact createContactInAddressBook(@PathVariable int addressBookId, @RequestBody Contact contact) {
        return addressBookService.addContactToAddressBook(addressBookId, contact);
    }

    @PutMapping("/{addressBookId}/contacts")
    @ResponseStatus(HttpStatus.OK)
    public Contact updateContactInAddressBook(@PathVariable int addressBookId, @RequestBody Contact contact) {
        return addressBookService.updateContactInAddressBook(addressBookId, contact);
    }

    @GetMapping({"/{addressBookId}"})
    @ResponseStatus(HttpStatus.OK)
    public List<Contact> getAllContactsInAddressBook(@PathVariable int addressBookId) {
        return addressBookService.getAllContactsInAddressBook(addressBookId);
    }

    @GetMapping({"/contacts"})
    @ResponseStatus(HttpStatus.OK)
    public List<Contact> getAllUniqueContacts() {
        return addressBookService.findAllUniqueContacts();
    }

    @DeleteMapping({"/{addressBookId}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteAddressBook(@PathVariable int addressBookId) {
        addressBookService.deleteAddressBookById(addressBookId);
    }

    @DeleteMapping({"/{addressBookId}/contact/{contactId}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteContactFromAddressBook(@PathVariable int addressBookId, @PathVariable int contactId) {
        addressBookService.removeContactFromAddressBook(addressBookId, contactId);
    }
}
