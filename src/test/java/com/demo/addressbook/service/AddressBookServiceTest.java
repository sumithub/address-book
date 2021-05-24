package com.demo.addressbook.service;

import com.demo.addressbook.entity.AddressBook;
import com.demo.addressbook.repository.AddressBookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class AddressBookServiceTest {

    @MockBean
    AddressBookRepository addressBookRepository;
    @MockBean
    AddressBookService addressBookService;

    @Test
    void createNewAddressBook() {
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
    }
}