package com.demo.addressbook.repository;

import com.demo.addressbook.model.AddressBook;
import com.demo.addressbook.model.Contact;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AddressBookRepositoryImpl implements AddressBookRepository {
    private final Map<Integer, AddressBook> addressBookMap = new HashMap<>();

    @Override
    public AddressBook findById(int id) {
        return addressBookMap.get(id);
    }

    @Override
    public void deleteById(int id) {
        removeAddressFromMap(id);
    }

    @Override
    public List<AddressBook> findAll() {
        return new ArrayList<>(addressBookMap.values());
    }

    @Override
    public List<Contact> listAddressBookContacts(AddressBook addressBook) {
        return addressBook.getContacts();
    }

    @Override
    public AddressBook save(AddressBook addressBook) {
        if (addressBook.getId() <= 0)
            addressBook.setId(generateID(addressBook.getName()));
        this.updateAddressMap(addressBook);
        return addressBookMap.get(addressBook.getId());
    }

    private void updateAddressMap(AddressBook addressBook) {
        if (addressBookMap.containsKey(addressBook.getId()))
            addressBookMap.replace(addressBook.getId(), addressBook);
        else
            addressBookMap.put(addressBook.getId(), addressBook);
    }

    private void removeAddressFromMap(int id) {
        this.addressBookMap.remove(id);
    }

    public int generateID(String name) {
        return Math.abs(name.toLowerCase().hashCode());
    }
}
