package com.demo.addressbook.repository;

import com.demo.addressbook.entity.AddressBook;

import java.util.List;

public interface AddressBookRepository {
    AddressBook createByName(String name);
    void deleteById(String id);
    List<AddressBook> finaAll();
    void save(AddressBook addressBook);
}