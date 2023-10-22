package com.task.tracker.controller.project;

import com.task.tracker.dto.project.ProjectDTO;
import com.task.tracker.service.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/project")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @GetMapping("project-id/{projectId}")
    private ProjectDTO getProjectById(@PathVariable("projectId") final Long projectId){
        return projectService.getProjectById(projectId);
    }

    @GetMapping("customer-id/{customerId}")
    private List<ProjectDTO> getProjectByCustomerId(@PathVariable("customerId") final Long customerId){
        return projectService.getProjectsByCustomerId(customerId);
    }

    @GetMapping("list")
    private List<ProjectDTO> getAllProjects(){
        return projectService.getAllProjects();
    }

    @PostMapping("")
    private ResponseEntity<ProjectDTO> addProject(@RequestBody @Valid final ProjectDTO projectDTO){
        final ProjectDTO newProjectDTO = projectService.addProject(projectDTO);
        return new ResponseEntity<>(newProjectDTO, HttpStatus.CREATED);
    }

    @PutMapping("")
    private ResponseEntity<Void> updateProject(@RequestBody @Valid final ProjectDTO projectDTO){
        projectService.updateProject(projectDTO);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("")
    private ResponseEntity<Void> deleteProject(@RequestBody @Valid final ProjectDTO projectDTO){
        projectService.deleteProject(projectDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
