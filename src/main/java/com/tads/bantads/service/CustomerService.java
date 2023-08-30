package com.tads.bantads.service;

import com.tads.bantads.dto.CustomerDTO;
import com.tads.bantads.dto.UpdateCustomerDTO;
import com.tads.bantads.mapper.CustomerMapper;
import com.tads.bantads.model.Customer;
import com.tads.bantads.rabbit.Orchestrator;
import com.tads.bantads.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private AddressService addressService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private Orchestrator orchestrator;

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.DTOToCustomer(customerDTO);
        addressService.saveAddress(customer.getAddress());
        CustomerDTO customerCreated = customerMapper.customerToDTO(customerRepository.saveAndFlush(customer));
        orchestrator.sendCustomerCreatedToQueue(customerCreated);
        return customerCreated;
    }

    public CustomerDTO getCustomer(UUID id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        return customerMapper.customerToDTO(customer);
    }

    public Page<CustomerDTO> getCustomer(int page, int size) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "name");
        return new PageImpl<>(
                customerRepository.findAll().stream().map(customerMapper::customerToDTO).collect(Collectors.toList()),
                pageRequest, size);
    }

    public CustomerDTO updateCustomer(UUID id, UpdateCustomerDTO updateCustomerDTO){
        Optional<Customer> customerOptional = customerRepository.findById(id);
        Customer customer = customerMapper.updateCustomer(customerOptional.orElseThrow(), updateCustomerDTO);
        addressService.saveAddress(customer.getAddress());
        return customerMapper.customerToDTO(customerRepository.saveAndFlush(customer));
    }

    public void deleteCustomer(UUID id) {
        customerRepository.deleteById(id);
    }
}
