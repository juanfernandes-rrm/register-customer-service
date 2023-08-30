package com.tads.bantads.controller;

import com.tads.bantads.dto.CustomerDTO;
import com.tads.bantads.dto.UpdateCustomerDTO;
import com.tads.bantads.service.CustomerService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private static final String messageGet = "Get customer";
    private static final String messageUpdate = "Update customer";
    private static final String messageDelete = "Delete customer";

    @Autowired
    private CustomerService customerService;

    @PostMapping
    @Transactional
    public ResponseEntity<EntityModel<CustomerDTO>> createCustomer(@RequestBody @Valid CustomerDTO customerDTO) {
        CustomerDTO createdCustomer = customerService.createCustomer(customerDTO);

        EntityModel<CustomerDTO> customerResource = EntityModel.of(createdCustomer);
        customerResource.add(linkTo(methodOn(CustomerController.class).getCustomer(createdCustomer.id())).withSelfRel().withTitle(messageGet));
        customerResource.add(linkTo(methodOn(CustomerController.class).updateCustomer(createdCustomer.id(),
                new UpdateCustomerDTO(createdCustomer.name(), createdCustomer.email(), createdCustomer.address()))).withSelfRel().withTitle(messageUpdate));
        customerResource.add(linkTo(methodOn(CustomerController.class).deleteCustomer(createdCustomer.id())).withSelfRel().withTitle(messageDelete));

        return ResponseEntity.created(URI.create(customerResource.getRequiredLink(IanaLinkRelations.SELF).getHref())).body(customerResource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CustomerDTO>> getCustomer(@PathVariable UUID id) {
        CustomerDTO customer = customerService.getCustomer(id);

        EntityModel<CustomerDTO> customerResource = EntityModel.of(customer);
        customerResource.add(linkTo(methodOn(CustomerController.class).updateCustomer(customer.id(),
                new UpdateCustomerDTO(customer.name(), customer.email(), customer.address()))).withSelfRel().withTitle(messageUpdate));
        customerResource.add(linkTo(methodOn(CustomerController.class).deleteCustomer(customer.id())).withSelfRel().withTitle(messageDelete));

        return ResponseEntity.ok(customerResource);
    }

    @GetMapping
    public ResponseEntity<Page<CustomerDTO>> getCustomer(@RequestParam(name = "page", defaultValue = "0") int page,
                                                         @RequestParam(name = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok().body(customerService.getCustomer(page, size));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<EntityModel<CustomerDTO>> updateCustomer(@PathVariable UUID id, @RequestBody @Valid UpdateCustomerDTO updateCustomerDTO) {
        CustomerDTO updatedCustomer = customerService.updateCustomer(id, updateCustomerDTO);
        EntityModel<CustomerDTO> customerResource = EntityModel.of(updatedCustomer);
        customerResource.add(linkTo(methodOn(CustomerController.class).getCustomer(updatedCustomer.id())).withSelfRel().withTitle(messageGet));
        customerResource.add(linkTo(methodOn(CustomerController.class).deleteCustomer(updatedCustomer.id())).withSelfRel().withTitle(messageDelete));

        return ResponseEntity.ok(customerResource);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id){
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
