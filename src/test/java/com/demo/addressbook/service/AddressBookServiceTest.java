package com.demo.addressbook.service;

import com.demo.addressbook.entity.AddressBook;
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
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class AddressBookServiceTest {
    public static final String ID = "wXvCSd";
    public static final String BOOK_NAME = "IT-DEPT";
    @MockBean
    AddressBookRepository addressBookRepository;
    @Autowired
    AddressBookService addressBookService;

    @Test
    void createNewAddressBook() {
        // given
        AddressBook addressBook = new AddressBook();
        addressBook.setId(ID);
        addressBook.setName(BOOK_NAME);

        when(addressBookRepository.createByName(BOOK_NAME)).thenReturn(addressBook);

        //when
        AddressBook addressBookReturned = addressBookService.createNewAddressBook(BOOK_NAME);

        // then
        assertNotNull(addressBookReturned);
        assertEquals(ID, addressBookReturned.getId());
        assertEquals(BOOK_NAME, addressBookReturned.getName());
    }

    @Test
    void getAllAddressBooks() {
        //given
        List<AddressBook> addressBooks = Arrays.asList(new AddressBook(), new AddressBook());
        when(addressBookRepository.finaAll()).thenReturn(addressBooks);

        // when
        List<AddressBook> addressBookList = addressBookService.getAllAddressBooks();

        // then
        assertEquals(2, addressBookList.size());

    }

    @Test
    void getAddressBookById() {
        // given
        AddressBook addressBook = new AddressBook();
        addressBook.setId(ID);
        addressBook.setName(BOOK_NAME);

        when(addressBookRepository.findById(ID)).thenReturn(addressBook);

        //when
        AddressBook addressBookReturned = addressBookService.getAddressBookById(ID);

        // then
        assertNotNull(addressBookReturned);
        assertEquals(ID, addressBookReturned.getId());
        assertEquals(BOOK_NAME, addressBookReturned.getName());
    }
}