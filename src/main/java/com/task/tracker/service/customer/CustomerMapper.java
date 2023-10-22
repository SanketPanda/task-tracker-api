package com.task.tracker.service.customer;

import com.task.tracker.dto.customer.CustomerDTO;
import com.task.tracker.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDTO toDTO(Customer customer);

    @Mapping(target = "customerId", ignore = true)
    Customer toEntity(CustomerDTO customerDTO);

    static void toEntity(final CustomerDTO customerDTO, final Customer customer){
        customer.setCustomerId(customerDTO.getCustomerId());
        customer.setCompany(customerDTO.getCompany());
        customer.setContactName(customerDTO.getContactName());
        customer.setContactEmail(customerDTO.getContactEmail());
        customer.setContactPhone(customerDTO.getContactPhone());
        customer.setDescription(customerDTO.getDescription());
        customer.setAddress(customerDTO.getAddress());
    }
}
