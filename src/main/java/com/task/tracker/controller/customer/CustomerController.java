package com.task.tracker.controller.customer;

import com.task.tracker.dto.customer.CustomerDTO;
import com.task.tracker.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("customer-id/{customerId}")
    private CustomerDTO getCustomerById(@PathVariable("customerId") Long customerId){
        return customerService.getCustomerById(customerId);
    }

    @GetMapping("customer-name/{customerName}")
    private CustomerDTO getCustomerByName(@PathVariable("customerName") String customerName){
        return customerService.getCustomerByName(customerName);
    }

    @GetMapping("list")
    private List<CustomerDTO> getCustomerList(){
        return customerService.getCustomerList();
    }

    @PostMapping("")
    private ResponseEntity<CustomerDTO> addCustomer(@RequestBody @Valid final CustomerDTO customerDTO){
        CustomerDTO newCustomerDTO = customerService.addCustomer(customerDTO);
        return new ResponseEntity<>(newCustomerDTO, HttpStatus.CREATED);
    }

    @PutMapping("")
    private ResponseEntity<Void> updateCustomer(@RequestBody @Valid final CustomerDTO customerDTO){
        customerService.updateCustomer(customerDTO);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("")
    private ResponseEntity<Void> deleteCustomer(@RequestBody @Valid final CustomerDTO customerDTO){
        customerService.deleteCustomer(customerDTO);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
