package com.tads.bantads.mapper;

import com.tads.bantads.dto.CustomerDTO;
import com.tads.bantads.dto.UpdateCustomerDTO;
import com.tads.bantads.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    @Autowired
    private AddressMapper addressMapper;

    public CustomerDTO customerToDTO(Customer customer) {
        return new CustomerDTO(customer.getId(), customer.getName(), customer.getEmail(), customer.getCpf(), addressMapper.addressToDTO(customer.getAddress()));
    }

    public Customer DTOToCustomer(CustomerDTO customerDTO) {
        return Customer.builder()
                .id(customerDTO.id())
                .name(customerDTO.name())
                .email(customerDTO.email())
                .cpf(customerDTO.cpf())
                .address(addressMapper.DTOToAddress(customerDTO.address()))
                .build();
    }

    public Customer updateCustomer(Customer customer, UpdateCustomerDTO updateCustomerDTO) {
        customer.setName(updateCustomerDTO.name());
        customer.setEmail(updateCustomerDTO.email());
        customer.setAddress(addressMapper.DTOToAddress(updateCustomerDTO.address()));
        return customer;
    }
}
