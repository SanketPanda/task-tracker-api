package com.task.tracker.service.costcenter;

import com.task.tracker.dto.costcenter.CostCenterDTO;
import com.task.tracker.dto.customer.CustomerDTO;
import com.task.tracker.model.CostCenter;
import com.task.tracker.model.Customer;
import com.task.tracker.service.customer.CustomerMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CostCenterMapper {
    CostCenterMapper INSTANCE = Mappers.getMapper(CostCenterMapper.class);

    @Mapping(source = "customer.customerId", target = "customerId")
    @Mapping(source = "customer", target = "customerDTO", qualifiedByName = "setCustomerDto")
    CostCenterDTO toDTO(CostCenter costCenter);

    @Mapping(target = "costCenterId", ignore = true)
    CostCenter toEntity(CostCenterDTO costCenterDTO);

    static void toEntity(CostCenterDTO costCenterDTO, CostCenter costCenter){
        costCenter.setCostCenterId(costCenter.getCostCenterId());
        costCenter.setTitle(costCenterDTO.getTitle());
    }

    @Named("setCustomerDto")
    default CustomerDTO setCustomerDto(Customer customer){
        return CustomerMapper.INSTANCE.toDTO(customer);
    }
}
