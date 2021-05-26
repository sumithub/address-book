package com.demo.addressbook.repository;

import com.demo.addressbook.model.AddressBook;
import com.demo.addressbook.model.Contact;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AddressBookRepositoryImpl implements AddressBookRepository {
    private final Map<Integer, AddressBook> addressBookMap = new HashMap<>();

    /**
     * Finds AddressBook by ID
     * @param id
     * @return AddressBook
     */
    @Override
    public AddressBook findById(int id) {
        return addressBookMap.get(id);
    }

    /**
     * Deletes AddressBook By ID
     * @param id
     */
    @Override
    public void deleteById(int id) {
        removeAddressFromMap(id);
    }

    /**
     * Finds all Address Books
     * @return List<AddressBook>
     */
    @Override
    public List<AddressBook> findAll() {
        return new ArrayList<>(addressBookMap.values());
    }

    /**
     * List of Contacts in Address Book
     * @param addressBook
     * @return List<Contact>
     */
    @Override
    public List<Contact> listAddressBookContacts(AddressBook addressBook) {
        return addressBook.getContacts();
    }

    /**
     * Saves Address Book
     * @param addressBook
     * @return AddressBook
     */
    @Override
    public AddressBook save(AddressBook addressBook) {
        if (addressBook.getId() <= 0)
            addressBook.setId(generateID(addressBook.getName()));
        this.updateAddressMap(addressBook);
        return addressBookMap.get(addressBook.getId());
    }

    /**
     * Updates Address in map
     * @param addressBook
     */
    private void updateAddressMap(AddressBook addressBook) {
        if (addressBookMap.containsKey(addressBook.getId()))
            addressBookMap.replace(addressBook.getId(), addressBook);
        else
            addressBookMap.put(addressBook.getId(), addressBook);
    }

    /**
     * Removes Address From Map
     * @param id
     */
    private void removeAddressFromMap(int id) {
        this.addressBookMap.remove(id);
    }

    /**
     * Generates unique address id
     * @param name
     * @return
     */
    public int generateID(String name) {
        return Math.abs(name.toLowerCase().hashCode());
    }
}
