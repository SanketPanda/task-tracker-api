package com.task.tracker.service.costcenter;

import com.task.tracker.dao.CostCenterRepository;
import com.task.tracker.dao.CustomerRepository;
import com.task.tracker.dao.ProjectRepository;
import com.task.tracker.dto.costcenter.CostCenterDTO;
import com.task.tracker.model.CostCenter;
import com.task.tracker.model.Customer;
import com.task.tracker.model.Project;
import com.task.tracker.service.costcenter.exception.CostCenterIdRequiredException;
import com.task.tracker.service.costcenter.exception.CostCenterLinkedException;
import com.task.tracker.service.costcenter.exception.CostCenterNotFoundException;
import com.task.tracker.service.costcenter.exception.DuplicateCostCenterException;
import com.task.tracker.service.customer.exception.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CostCenterService {

    @Autowired
    CostCenterRepository costCenterRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProjectRepository projectRepository;

    public CostCenterDTO getCostCenterById(final Long costCenterId){
        CostCenter costCenter = costCenterRepository.findById(costCenterId).orElseThrow(() -> new CostCenterNotFoundException("Cost-center not found with id: ", costCenterId));
        return CostCenterMapper.INSTANCE.toDTO(costCenter);
    }

    public List<CostCenterDTO> getCostCentersByCustomerId(final Long customerId){
        List<CostCenter> costCenter = costCenterRepository.findByCustomerId(customerId);
        return costCenter.stream().map(CostCenterMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    public List<CostCenterDTO> getCostCenterList(){
        List<CostCenter> costCenter = costCenterRepository.findAll();
        return costCenter.stream().map(CostCenterMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public CostCenterDTO addCostCenter(final CostCenterDTO costCenterDTO){
        final Customer customer = customerRepository.findById(costCenterDTO.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: ", costCenterDTO.getCustomerId()));
        if(isCostCenterExist(costCenterDTO)){
            throw new DuplicateCostCenterException(costCenterDTO.getTitle(),customer.getCompany());
        }
        CostCenter costCenter = CostCenterMapper.INSTANCE.toEntity(costCenterDTO);
        costCenter.setCustomer(customer);
        CostCenter newCostCenter = costCenterRepository.save(costCenter);
        return CostCenterMapper.INSTANCE.toDTO(newCostCenter);
    }

    @Transactional
    public void updateCostCenter(final CostCenterDTO costCenterDTO){
        Optional.ofNullable(costCenterDTO.getCostCenterId()).orElseThrow(CostCenterIdRequiredException::new);
        CostCenter costCenter = costCenterRepository.findById(costCenterDTO.getCostCenterId()).orElseThrow(() -> new CostCenterNotFoundException("Cost-center not found with id: ", costCenterDTO.getCostCenterId()));
        CostCenterMapper.toEntity(costCenterDTO, costCenter);
    }

    @Transactional
    public void deleteCostCenter(final CostCenterDTO costCenterDTO){
        CostCenter costCenter = costCenterRepository.findById(costCenterDTO.getCostCenterId()).orElseThrow(() -> new CostCenterNotFoundException("Cost-center not found with id: ", costCenterDTO.getCustomerId()));
        Optional<Project> linkedProject = projectRepository.findByCostCenter(costCenter.getCostCenterId());
        if(linkedProject.isPresent()){
            throw new CostCenterLinkedException(linkedProject.get().getTitle());
        }
        costCenterRepository.delete(costCenter);
    }
    private boolean isCostCenterExist(final CostCenterDTO costCenterDTO){
        return costCenterRepository.findByTitleAndCustomer(costCenterDTO.getTitle(), costCenterDTO.getCustomerId()).isPresent();
    }
}
