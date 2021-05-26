package com.demo.addressbook.repository;

import com.demo.addressbook.model.AddressBook;
import java.util.List;

public interface AddressBookRepository {
    AddressBook findById(int id);
    void deleteById(int id);
    List<AddressBook> findAll();
    AddressBook save(AddressBook addressBook);
}
