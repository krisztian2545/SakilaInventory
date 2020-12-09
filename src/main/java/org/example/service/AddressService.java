package org.example.service;

import org.example.exceptions.UnknownAddressException;
import org.example.exceptions.UnknownCountryException;
import org.example.model.Address;

import java.util.Collection;

public interface AddressService {

    Collection<Address> getAllAddress();
    Collection<Address> getAddressInCity(String city);

    void recordAddress(Address address) throws UnknownCountryException;
    void deleteAddress(Address address) throws UnknownAddressException;
}