package com.task.tracker.service.project;

import com.task.tracker.dto.costcenter.CostCenterDTO;
import com.task.tracker.dto.customer.CustomerDTO;
import com.task.tracker.dto.project.ProjectDTO;
import com.task.tracker.model.CostCenter;
import com.task.tracker.model.Customer;
import com.task.tracker.model.Project;
import com.task.tracker.service.costcenter.CostCenterMapper;
import com.task.tracker.service.customer.CustomerMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProjectMapper {

    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    @Mapping(source = "customer.customerId", target = "customerId")
    @Mapping(source = "costCenter", target = "costCenterDTO", qualifiedByName = "setCostCenterDto")
    @Mapping(source = "costCenter.costCenterId", target = "costCenterId")
    @Mapping(source = "customer", target = "customerDTO", qualifiedByName = "setCustomerDto")
    ProjectDTO toDTO(Project project);

    @Mapping(target = "projectId", ignore = true)
    @Mapping(target = "costCenter", ignore = true)
    Project toEntity(ProjectDTO projectDTO);

    static void toEntity(final ProjectDTO projectDTO, final Project project){
        project.setTitle(projectDTO.getTitle());
        project.setDescription(projectDTO.getDescription());
    }

    @Named("setCostCenterDto")
    default CostCenterDTO setCostCenterDto(CostCenter costCenter){
        return CostCenterMapper.INSTANCE.toDTO(costCenter);
    }

    @Named("setCustomerDto")
    default CustomerDTO setCustomerDto(Customer customer){
        return CustomerMapper.INSTANCE.toDTO(customer);
    }
}
