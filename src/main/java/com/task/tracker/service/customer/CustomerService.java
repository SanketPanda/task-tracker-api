package com.task.tracker.service.customer;

import com.task.tracker.dao.CustomerRepository;
import com.task.tracker.dto.customer.CustomerDTO;
import com.task.tracker.model.Customer;
import com.task.tracker.service.customer.exception.CustomerIdRequiredException;
import com.task.tracker.service.customer.exception.CustomerNotFoundException;
import com.task.tracker.service.customer.exception.DuplicateCustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public CustomerDTO getCustomerById(final Long customerId){
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: ", customerId));
        return CustomerMapper.INSTANCE.toDTO(customer);
    }

    public CustomerDTO getCustomerByName(final String customerName){
        Customer customer = customerRepository.findByCompanyName(customerName).orElseThrow(() -> new CustomerNotFoundException(customerName));
        return CustomerMapper.INSTANCE.toDTO(customer);
    }

    public List<CustomerDTO> getCustomerList(){
        return customerRepository.findAll().stream().map(CustomerMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public CustomerDTO addCustomer(final CustomerDTO customerDTO){
        if(isCustomerExist(customerDTO)) throw new DuplicateCustomerException(customerDTO.getCompany());
        Customer customer = CustomerMapper.INSTANCE.toEntity(customerDTO);
        Customer newCustomer = customerRepository.save(customer);
        return CustomerMapper.INSTANCE.toDTO(newCustomer);
    }

    @Transactional
    public void updateCustomer(final CustomerDTO customerDTO){
        Optional.ofNullable(customerDTO.getCustomerId()).orElseThrow(CustomerIdRequiredException::new);
        Customer customer = customerRepository.findById(customerDTO.getCustomerId()).orElseThrow(() -> new CustomerNotFoundException("Customer not found with id", customerDTO.getCustomerId()));
        CustomerMapper.toEntity(customerDTO, customer);
    }

    @Transactional
    public void deleteCustomer(final CustomerDTO customerDTO){
        Optional.ofNullable(customerDTO.getCustomerId()).orElseThrow(CustomerIdRequiredException::new);
        Customer customer = customerRepository.findById(customerDTO.getCustomerId()).orElseThrow(() -> new CustomerNotFoundException("Customer not found with id", customerDTO.getCustomerId()));
        customerRepository.delete(customer);
    }

    public boolean isCustomerExist(final CustomerDTO customerDTO){
        return customerRepository.findByCompanyName(customerDTO.getCompany()).isPresent();
    }

    public boolean isCustomerExist(final Long customerId){
        return customerRepository.findById(customerId).isPresent();
    }
}
