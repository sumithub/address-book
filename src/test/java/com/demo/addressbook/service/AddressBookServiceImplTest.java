package com.demo.addressbook.service;

import com.demo.addressbook.model.AddressBook;
import com.demo.addressbook.model.Contact;
import com.demo.addressbook.repository.AddressBookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        AddressBook addressBook = new AddressBook(ADDRESS_BOOK_NAME_1);
        addressBook.setId(ID_1);

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
        AddressBook addressBook = new AddressBook(ADDRESS_BOOK_NAME_1);
        addressBook.setId(ID_1);

        when(addressBookRepository.findById(ID_1)).thenReturn(addressBook);

        //when
        AddressBook addressBookReturned = addressBookService.findAddressBook(ID_1);

        // then
        assertNotNull(addressBookReturned);
        assertEquals(ID_1, addressBookReturned.getId());
        assertEquals(ADDRESS_BOOK_NAME_1, addressBookReturned.getName());
    }

    @Test
    void testDeleteAddressBook() {
        AddressBook addressBook = new AddressBook(ADDRESS_BOOK_NAME_1);
        addressBook.setId(ID_1);

        // given
        when(addressBookRepository.findById(ID_1)).thenReturn(addressBook);

        // when
        addressBookService.deleteAddressBook(ID_1);

        // then
        verify(addressBookRepository, times(1)).deleteById(ID_1);
    }

    @Test
    void testAddContactToAddressBook() {
        AddressBook addressBook = new AddressBook(ADDRESS_BOOK_NAME_1);
        addressBook.setId(ID_1);
        Contact contact = new Contact();
        contact.setFirstName("John");
        contact.setLastName("Moore");
        addressBook.addContact(contact);

        // given
        when(addressBookRepository.findById(ID_1)).thenReturn(addressBook);
        when(addressBookRepository.save(addressBook)).thenReturn(addressBook);

        // when
        Contact savedContact = addressBookService.addContactToAddressBook(ID_1, contact);

        // then
        verify(addressBookRepository, times(1)).save(addressBook);
        assertNotNull(savedContact);
        assertEquals("John", savedContact.getFirstName());
        assertEquals("Moore", savedContact.getLastName());
    }

    @Test
    void testListAllContactsInAddressBook() {

    }
}