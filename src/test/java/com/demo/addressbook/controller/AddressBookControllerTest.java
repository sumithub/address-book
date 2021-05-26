package com.demo.addressbook.controller;

import com.demo.addressbook.model.AddressBook;
import com.demo.addressbook.model.Contact;
import com.demo.addressbook.service.AddressBookService;
import com.demo.addressbook.service.ResourceNotFoundException;
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
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
        contact_1.setId(99);
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
    void testUpdateContactInAddressBook() throws Exception {
        AddressBook addressBook = addressBookList.get(0);
        Contact contact = addressBook.getContacts().get(0);
        when(addressBookService.updateContactInAddressBook(addressBook.getId(), contact)).thenReturn(contact);

        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL+"/1/contacts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(contact)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("John")))
                .andExpect(jsonPath("$.lastName", equalTo("Moore")));
        verify(addressBookService, times(1)).updateContactInAddressBook(addressBook.getId(), contact);
    }

    @Test
    void testGetAllContactsInAddressBook() throws Exception {
        AddressBook addressBook = addressBookList.get(0);
        List<Contact> contactList = addressBook.getContacts();
        when(addressBookService.listAllContactsInAddressBook(addressBook.getId())).thenReturn(contactList);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL+"/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", equalTo("John")));
        verify(addressBookService, times(1)).listAllContactsInAddressBook(addressBook.getId());
    }

    @Test
    void testGetAllUniqueContacts() throws Exception {
        AddressBook addressBook2 = new AddressBook("hr phone");
        addressBook2.setId(2);
        Contact contact_1 = new Contact();
        List<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add("123456");
        phoneNumbers.add("7891011");
        contact_1.setFirstName("John");
        contact_1.setLastName("Moore");
        contact_1.setPhoneNumbers(phoneNumbers);
        addressBook2.addContact(contact_1);
        List<AddressBook> list_2 = new ArrayList<>(addressBookList);
        list_2.add(addressBook2);

        when(addressBookService.listAllUniqueContacts()).thenReturn(Collections.singletonList(contact_1));

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL+"/contacts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", equalTo("John")))
                .andExpect(jsonPath("$[0].lastName", equalTo("Moore")))
                .andExpect(jsonPath("$[0].phoneNumbers", hasSize(2)))
                .andExpect(jsonPath("$[0].phoneNumbers[0]", equalTo("123456")))
                .andExpect(jsonPath("$[0].phoneNumbers[1]", equalTo("7891011")));
        verify(addressBookService, times(1)).listAllUniqueContacts();
    }

    @Test
        void testDeleteAddressBook() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(addressBookService, times(1)).deleteAddressBook(anyInt());
    }

    @Test
    void testDeleteContactFromAddressBook() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/1/contact/99")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(addressBookService, times(1)).removeContactFromAddressBook(anyInt(), anyInt());
    }

    @Test
    public void testResourceNotFoundException() throws Exception {
        when(addressBookService.listAllContactsInAddressBook(anyInt())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/34")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private static String toJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}