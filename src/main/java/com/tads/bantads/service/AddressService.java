package com.tads.bantads.service;

import com.tads.bantads.model.Address;
import com.tads.bantads.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public Address saveAddress(Address address){
        return addressRepository.saveAndFlush(address);
    }
}
