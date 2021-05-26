package com.demo.addressbook.repository;

import com.demo.addressbook.model.AddressBook;
import com.demo.addressbook.model.Contact;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AddressBookRepositoryIntegrationTest {
    @Autowired
    private AddressBookRepository addressBookRepository;
    private AddressBook addressBook;

    @BeforeEach
    public void setUp() {
        addressBook = new AddressBook("finance");
        addressBook.setId(1);
        Contact contact_1 = new Contact();
        List<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add("123456");
        phoneNumbers.add("7891011");
        contact_1.setFirstName("John");
        contact_1.setLastName("Moore");
        contact_1.setPhoneNumbers(phoneNumbers);
        addressBook.addContact(contact_1);
    }

    @AfterEach
    public void tearDown() {
        addressBook = null;
    }

    @Test
    void findById() {
        // given
        addressBookRepository.save(addressBook);
        // when
        AddressBook returnedAddressBook = addressBookRepository.findById(addressBook.getId());
        // then
        assertEquals(1, returnedAddressBook.getId());
    }

    @Test
    void deleteById() {
        addressBookRepository.save(addressBook);
        addressBookRepository.deleteById(addressBook.getId());
        AddressBook returnedBook = addressBookRepository.findById(addressBook.getId());
        assertNull(returnedBook);
    }

    @Test
    void findAll() {
        AddressBook addressBook1 = new AddressBook(1,"b1");
        AddressBook addressBook2 = new AddressBook(2,"b2");
        addressBookRepository.save(addressBook1);
        addressBookRepository.save(addressBook2);
        List<AddressBook> addressBookList = addressBookRepository.findAll();
        assertEquals("b2", addressBookList.get(1).getName());
    }

    @Test
    void listAddressBookContacts() {
        addressBookRepository.save(addressBook);
        List<Contact> contactList = addressBookRepository.listAddressBookContacts(addressBook);
        assertThat(contactList).hasSize(1);
        assertEquals("John", contactList.get(0).getFirstName());
    }

    @Test
    void save() {
        AddressBook savedAddressBook = addressBookRepository.save(addressBook);
        assertNotNull(savedAddressBook);
        assertEquals(1, savedAddressBook.getId());
    }
}