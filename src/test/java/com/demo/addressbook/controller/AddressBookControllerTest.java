package com.demo.addressbook.controller;
import com.demo.addressbook.model.AddressBook;
import com.demo.addressbook.model.Contact;
import com.demo.addressbook.service.AddressBookService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AddressBookControllerTest {

    @MockBean
    private AddressBookService addressBookService;

    @Autowired
    private MockMvc mockMvc;

    private static List<AddressBook> addressBookList;

    @BeforeAll
    public static void init() {
        AddressBook addressBook = new AddressBook("finance");
        addressBook.setId(1);
        Contact contact_1 = new Contact();
        List<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add("123456");
        phoneNumbers.add("7891011");
        contact_1.setFirstName("John");
        contact_1.setLastName("Moore");
        contact_1.setPhoneNumbers(phoneNumbers);
        addressBook.addContact(contact_1);
        addressBookList = new ArrayList<>();
        addressBookList.add(addressBook);
        System.out.println(addressBookList.size());
    }

    @Test
    void testGetListOfAddressBooks() throws Exception {
        when(addressBookService.listAllAddressBooks()).thenReturn(addressBookList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/address-books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("finance")));
    }

    @Test
    void testCreateNewAddressBook() {
    }

    @Test
    void testCreateContactInAddressBook() {
    }

    @Test
    void testUpdateContactInAddressBook() {
    }

    @Test
    void testGetAllContactsInAddressBook() {
    }

    @Test
    void testGetAllUniqueContacts() {
    }

    @Test
    void testDeleteAddressBook() {
    }

    @Test
    void testDeleteContactFromAddressBook() {
    }
}