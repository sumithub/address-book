package com.demo.addressbook.controller;

import com.demo.addressbook.model.AddressBook;
import com.demo.addressbook.model.Contact;
import com.demo.addressbook.service.AddressBookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(AddressBookController.BASE_URL)
public class AddressBookController {

    public static final String BASE_URL = "/api/v1/address-books";
    private final AddressBookService addressBookService;

    public AddressBookController(AddressBookService addressBookService) {
        this.addressBookService = addressBookService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AddressBook> getListOfAddressBooks() {
        return addressBookService.listAllAddressBooks();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AddressBook createNewAddressBook(@Valid @RequestBody AddressBook addressBook) {
        return addressBookService.createNewAddressBook(addressBook);
    }

    @PostMapping("/{addressBookId}/contacts")
    @ResponseStatus(HttpStatus.CREATED)
    public Contact createContactInAddressBook(@PathVariable int addressBookId, @Valid @RequestBody Contact contact) {
        return addressBookService.addContactToAddressBook(addressBookId, contact);
    }

    @PutMapping("/{addressBookId}/contacts")
    @ResponseStatus(HttpStatus.OK)
    public Contact updateContactInAddressBook(@PathVariable int addressBookId, @Valid @RequestBody Contact contact) {
        return addressBookService.updateContactInAddressBook(addressBookId, contact);
    }

    @GetMapping({"/{addressBookId}"})
    @ResponseStatus(HttpStatus.OK)
    public List<Contact> getAllContactsInAddressBook(@PathVariable int addressBookId) {
        return addressBookService.listAllContactsInAddressBook(addressBookId);
    }

    @GetMapping({"/contacts"})
    @ResponseStatus(HttpStatus.OK)
    public List<Contact> getAllUniqueContacts() {
        return addressBookService.listAllUniqueContacts();
    }

    @DeleteMapping({"/{addressBookId}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteAddressBook(@PathVariable int addressBookId) {
        addressBookService.deleteAddressBook(addressBookId);
    }

    @DeleteMapping({"/{addressBookId}/contact/{contactId}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteContactFromAddressBook(@PathVariable int addressBookId, @PathVariable int contactId) {
        addressBookService.removeContactFromAddressBook(addressBookId, contactId);
    }
}
