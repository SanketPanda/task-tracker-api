package com.task.tracker.service.project;

import com.task.tracker.dao.CostCenterRepository;
import com.task.tracker.dao.CustomerRepository;
import com.task.tracker.dao.ProjectRepository;
import com.task.tracker.dto.project.ProjectDTO;
import com.task.tracker.model.CostCenter;
import com.task.tracker.model.Customer;
import com.task.tracker.model.Project;
import com.task.tracker.service.costcenter.exception.CostCenterNotFoundException;
import com.task.tracker.service.customer.exception.CustomerNotFoundException;
import com.task.tracker.service.project.exception.DuplicateProjectException;
import com.task.tracker.service.project.exception.ProjectIdRequiredException;
import com.task.tracker.service.project.exception.ProjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CostCenterRepository costCenterRepository;

    public ProjectDTO getProjectById(final Long projectId){
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id: ", projectId));
        return ProjectMapper.INSTANCE.toDTO(project);
    }

    public List<ProjectDTO> getProjectsByCustomerId(final Long customerId){
        return projectRepository.findByCustomer(customerId).stream().map(ProjectMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    public List<ProjectDTO> getAllProjects(){
        return projectRepository.findAll().stream().map(ProjectMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProjectDTO addProject(final ProjectDTO projectDTO){
        final Customer customer = customerRepository.findById(projectDTO.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: ", projectDTO.getCustomerId()));
        if(isProjectExist(projectDTO)){
            throw new DuplicateProjectException(projectDTO.getTitle(), customer.getCompany());
        }
        CostCenter costCenter = costCenterRepository.findById(projectDTO.getCostCenterId())
                .orElseThrow(() -> new CostCenterNotFoundException("Cost-center not found with id: ", projectDTO.getCustomerId()));
        Project project = ProjectMapper.INSTANCE.toEntity(projectDTO);
        project.setCustomer(customer);
        project.setCostCenter(costCenter);
        Project newProject = projectRepository.save(project);
        return ProjectMapper.INSTANCE.toDTO(newProject);
    }

    @Transactional
    public void updateProject(final ProjectDTO projectDTO){
        Optional.ofNullable(projectDTO.getProjectId()).orElseThrow(ProjectIdRequiredException::new);
        Project project = projectRepository.findById(projectDTO.getProjectId())
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id: ", projectDTO.getProjectId()));
        Customer customer = customerRepository.findById(projectDTO.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: ", projectDTO.getCustomerId()));
        CostCenter costCenter = costCenterRepository.findById(projectDTO.getCostCenterId())
                .orElseThrow(() -> new CostCenterNotFoundException("Cost-center not found with id: ", projectDTO.getCustomerId()));
        project.setCustomer(customer);
        project.setCostCenter(costCenter);
        ProjectMapper.toEntity(projectDTO, project);
    }

    @Transactional
    public void deleteProject(final ProjectDTO projectDTO){
        Optional.ofNullable(projectDTO.getProjectId()).orElseThrow(ProjectIdRequiredException::new);
        Project project = projectRepository.findById(projectDTO.getProjectId())
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id: ", projectDTO.getProjectId()));
        projectRepository.delete(project);
    }

    private boolean isProjectExist(final ProjectDTO projectDTO){
        return projectRepository.findByTitleAndCustomer(projectDTO.getTitle(), projectDTO.getCustomerId()).isPresent();
    }
}
