package com.demo.addressbook.service;

import com.demo.addressbook.model.AddressBook;
import com.demo.addressbook.model.Contact;
import com.demo.addressbook.repository.AddressBookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AddressBookServiceImplTest {
    public static final int ID_1 = 1;
    public static final String ADDRESS_BOOK_NAME_1 = "HR";
    public static final int ID_2 = 2;
    public static final String ADDRESS_BOOK_NAME_2 = "FINANCE";

    @MockBean
    AddressBookRepository addressBookRepository;
    @Autowired
    AddressBookServiceImpl addressBookService;

    @Test
    void testCreateNewAddressBook() {
        // given
        AddressBook addressBook = new AddressBook(ID_1, ADDRESS_BOOK_NAME_1);

        when(addressBookRepository.save(addressBook)).thenReturn(addressBook);

        //when
        AddressBook addressBookReturned = addressBookService.createNewAddressBook(addressBook);

        // then
        verify(addressBookRepository, times(1)).save(addressBook);
        assertNotNull(addressBookReturned);
        assertEquals(ID_1, addressBookReturned.getId());
        assertEquals(ADDRESS_BOOK_NAME_1, addressBookReturned.getName());
    }

    @Test
    void testListAllAddressBooks() {
        //given
        List<AddressBook> addressBooks = Arrays.asList(new AddressBook("AB-1"), new AddressBook("AB-2"));
        when(addressBookRepository.findAll()).thenReturn(addressBooks);

        // when
        List<AddressBook> addressBookList = addressBookService.listAllAddressBooks();

        // then
        assertEquals(2, addressBookList.size());

    }

    @Test
    void testFindAddressBook() {
        // given
        AddressBook addressBook = new AddressBook(ID_1, ADDRESS_BOOK_NAME_1);

        when(addressBookRepository.findById(ID_1)).thenReturn(addressBook);

        //when
        AddressBook addressBookReturned = addressBookService.findAddressBook(ID_1);

        // then
        verify(addressBookRepository, times(1)).findById(ID_1);
        assertNotNull(addressBookReturned);
        assertEquals(ID_1, addressBookReturned.getId());
        assertEquals(ADDRESS_BOOK_NAME_1, addressBookReturned.getName());
    }

    @Test
    void testDeleteAddressBook() {
        AddressBook addressBook = new AddressBook(ID_1, ADDRESS_BOOK_NAME_1);

        // given
        when(addressBookRepository.findById(ID_1)).thenReturn(addressBook);

        // when
        addressBookService.deleteAddressBook(ID_1);

        // then
        verify(addressBookRepository, times(1)).deleteById(ID_1);
    }

    @Test
    void testListAllContactsInAddressBook() {
        AddressBook addressBook = new AddressBook(ID_1, ADDRESS_BOOK_NAME_1);

        List<Contact> contactList = new ArrayList<>();
        Contact contact_1 = new Contact();
        contact_1.setFirstName("John");
        contact_1.setLastName("Moore");
        Contact contact_2 = new Contact();
        contact_2.setFirstName("Peter");
        contact_2.setLastName("Avola");
        contactList.add(contact_1);
        contactList.add(contact_2);

        // given
        when(addressBookRepository.findById(ID_1)).thenReturn(addressBook);
        when(addressBookRepository.listAddressBookContacts(addressBook)).thenReturn(contactList);

        // when
        List<Contact> returnedContacts = addressBookService.listAllContactsInAddressBook(ID_1);

        // then
        verify(addressBookRepository, times(1)).listAddressBookContacts(addressBook);
        assertThat(returnedContacts).hasSize(2);
    }

    @Test
    void testUpdateContactInAddressBook() {
        AddressBook addressBook = new AddressBook(ID_1, ADDRESS_BOOK_NAME_1);
        Contact contact = new Contact();
        contact.setFirstName("John");
        contact.setLastName("Moore");
        addressBook.addContact(contact);

        // given
        when(addressBookRepository.findById(ID_1)).thenReturn(addressBook);
        when(addressBookRepository.save(addressBook)).thenReturn(addressBook);

        // when
        addressBookService.updateContactInAddressBook(ID_1, contact);

        // then
        verify(addressBookRepository, times(1)).save(addressBook);
    }

    @Test
    void testRemoveContactFromAddressBook() {
        AddressBook addressBook = new AddressBook(ID_1, ADDRESS_BOOK_NAME_1);

        Contact contact_1 = new Contact();
        contact_1.setId(7);
        contact_1.setFirstName("John");
        contact_1.setLastName("Moore");
        addressBook.addContact(contact_1);

        // given
        when(addressBookRepository.findById(ID_1)).thenReturn(addressBook);
        when(addressBookRepository.save(addressBook)).thenReturn(addressBook);

        // when
        addressBookService.removeContactFromAddressBook(ID_1, 7);

        // then
        verify(addressBookRepository, times(1)).save(addressBook);
    }

    @Test
    void testListAllUniqueContacts() {
        List<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add("123456");
        phoneNumbers.add("7891011");
        AddressBook addressBook1 = new AddressBook(ID_1, ADDRESS_BOOK_NAME_1);
        Contact contact_1 = new Contact();
        contact_1.setFirstName("John");
        contact_1.setLastName("Moore");
        contact_1.setPhoneNumbers(phoneNumbers);
        addressBook1.addContact(contact_1);

        AddressBook addressBook2 = new AddressBook(ID_2, ADDRESS_BOOK_NAME_2);
        Contact contact_2 = new Contact();
        contact_2.setFirstName("John");
        contact_2.setLastName("Moore");
        contact_2.setPhoneNumbers(phoneNumbers);
        addressBook2.addContact(contact_2);

        List<AddressBook> addressBooks = Arrays.asList(addressBook1, addressBook2);
        // given
        when(addressBookRepository.findAll()).thenReturn(addressBooks);

        // when
        List<Contact> uniqueContacts = addressBookService.listAllUniqueContacts();

        // then
        assertThat(uniqueContacts).hasSize(1);
    }
}