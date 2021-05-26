package com.demo.addressbook.controller;
import com.demo.addressbook.model.AddressBook;
import com.demo.addressbook.model.Contact;
import com.demo.addressbook.service.AddressBookService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
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
    public static final String BASE_URL = "/api/v1/address-books";

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
    }

    @Test
    void testGetListOfAddressBooks() throws Exception {
        when(addressBookService.listAllAddressBooks()).thenReturn(addressBookList);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("finance")));
    }

    @Test
    void testCreateNewAddressBook() throws Exception {
        AddressBook addressBook = new AddressBook("finance");
        addressBook.setId(1);
        when(addressBookService.createNewAddressBook(any())).thenReturn(addressBook);

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(addressBook)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo("finance")));
        verify(addressBookService, times(1)).createNewAddressBook(any());
    }

    @Test
    void testCreateContactInAddressBook() throws Exception {
        AddressBook addressBook = addressBookList.get(0);
        Contact contact = addressBook.getContacts().get(0);
        when(addressBookService.addContactToAddressBook(addressBook.getId(), contact)).thenReturn(contact);

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL+"/1/contacts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(contact)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", equalTo("John")));
        verify(addressBookService, times(1)).addContactToAddressBook(addressBook.getId(), contact);
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

    private static String toJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}