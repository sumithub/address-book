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

    /**
     * Lists all address books
     * @return AddressBooks
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AddressBook> getListOfAddressBooks() {
        return addressBookService.listAllAddressBooks();
    }

    /**
     * Creates new Address Book
     * @param addressBook payload
     * @return Address Book
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AddressBook createNewAddressBook(@Valid @RequestBody AddressBook addressBook) {
        return addressBookService.createNewAddressBook(addressBook);
    }

    /**
     * Creates a new Contact in Address Book
     * @param addressBookId
     * @param contact
     * @return Contact
     */
    @PostMapping("/{addressBookId}/contacts")
    @ResponseStatus(HttpStatus.CREATED)
    public Contact createContactInAddressBook(@PathVariable int addressBookId, @Valid @RequestBody Contact contact) {
        return addressBookService.addContactToAddressBook(addressBookId, contact);
    }

    /**
     * Updates Contact in Address Book
     * @param addressBookId
     * @param contact
     * @return Contact
     */
    @PutMapping("/{addressBookId}/contacts")
    @ResponseStatus(HttpStatus.OK)
    public Contact updateContactInAddressBook(@PathVariable int addressBookId, @Valid @RequestBody Contact contact) {
        return addressBookService.updateContactInAddressBook(addressBookId, contact);
    }

    /**
     * Returns all contacts in Address Book
     * @param addressBookId
     * @return List<Contact>
     */
    @GetMapping({"/{addressBookId}/contacts"})
    @ResponseStatus(HttpStatus.OK)
    public List<Contact> getAllContactsInAddressBook(@PathVariable int addressBookId) {
        return addressBookService.listAllContactsInAddressBook(addressBookId);
    }

    /**
     * Returns all unique contacts in all Address Books
     * @return List<Contact>
     */
    @GetMapping({"/unique-contacts"})
    @ResponseStatus(HttpStatus.OK)
    public List<Contact> getAllUniqueContacts() {
        return addressBookService.listAllUniqueContacts();
    }

    /**
     * Deletes Address Book by Id
     * @param addressBookId
     */
    @DeleteMapping({"/{addressBookId}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteAddressBook(@PathVariable int addressBookId) {
        addressBookService.deleteAddressBook(addressBookId);
    }

    /**
     * Deletes a contact in AddressBook
     * @param addressBookId
     * @param contactId
     */
    @DeleteMapping({"/{addressBookId}/contact/{contactId}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteContactFromAddressBook(@PathVariable int addressBookId, @PathVariable int contactId) {
        addressBookService.removeContactFromAddressBook(addressBookId, contactId);
    }
}
