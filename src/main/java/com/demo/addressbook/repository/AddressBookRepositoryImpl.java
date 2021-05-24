package com.demo.addressbook.repository;

import com.demo.addressbook.entity.AddressBook;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AddressBookRepositoryImpl implements AddressBookRepository{
    @Override
    public AddressBook createByName(String name) {
        return null;
    }

    @Override
    public AddressBook findById(String id) {
        return null;
    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public List<AddressBook> finaAll() {
        return null;
    }

    @Override
    public void save(AddressBook addressBook) {

    }
}
