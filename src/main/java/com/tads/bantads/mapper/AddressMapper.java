package com.tads.bantads.mapper;

import com.tads.bantads.dto.AddressDTO;
import com.tads.bantads.model.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public AddressDTO addressToDTO(Address address) {
        return new AddressDTO(address.getCep(), address.getStreet(), address.getNumber(), address.getNeighborhood(), address.getCity(), address.getState(), address.getComplement());
    }

    public Address DTOToAddress(AddressDTO addressDTO){
        return Address.builder().cep(addressDTO.cep())
                                .street(addressDTO.street())
                                .number(addressDTO.number())
                                .neighborhood(addressDTO.neighborhood())
                                .city(addressDTO.city())
                                .state(addressDTO.state())
                                .complement(addressDTO.complement())
                                .build();
    }
}
