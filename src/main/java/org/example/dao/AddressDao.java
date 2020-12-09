package org.example.dao;

import org.example.exceptions.UnknownAddressException;
import org.example.exceptions.UnknownCountryException;
import org.example.model.Address;

import java.util.Collection;

/**
 * DAO = Data Access Object
 *
 * CRUD Methods:
 *  - Create
 *  - Read
 *  - Update
 *  - Delete
 */
public interface AddressDao {

    void createAddress(Address address) throws UnknownCountryException;
    Collection<Address> readAll();

    void deleteAddress(Address address) throws UnknownAddressException;
}