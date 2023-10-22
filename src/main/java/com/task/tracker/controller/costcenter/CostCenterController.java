package com.task.tracker.controller.costcenter;

import com.task.tracker.dto.costcenter.CostCenterDTO;
import com.task.tracker.service.costcenter.CostCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/cost-center")
public class CostCenterController {

    @Autowired
    CostCenterService costCenterService;



    @GetMapping("{costCenterId}")
    private CostCenterDTO getCostCenterById(@PathVariable("costCenterId") final Long costCenterId){
        return costCenterService.getCostCenterById(costCenterId);
    }

    @GetMapping("customer/{customerId}")
    private List<CostCenterDTO> getCostCenterByCustomerId(@PathVariable("customerId") final Long customerId){
        return costCenterService.getCostCentersByCustomerId(customerId);
    }

    @GetMapping("list")
    private List<CostCenterDTO> getCostCenterList(){
        return costCenterService.getCostCenterList();
    }

    @PostMapping("")
    private ResponseEntity<CostCenterDTO> addCostCenter(@RequestBody @Valid final CostCenterDTO costCenterDTO){
        CostCenterDTO newCostCenterDTO = costCenterService.addCostCenter(costCenterDTO);
        return new ResponseEntity<>(newCostCenterDTO, HttpStatus.CREATED);
    }

    @PutMapping("")
    private ResponseEntity<Void> updateCostCenter(@RequestBody @Valid final CostCenterDTO costCenterDTO){
        costCenterService.updateCostCenter(costCenterDTO);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("")
    private ResponseEntity<Void> deleteCostCenter(@RequestBody @Valid final CostCenterDTO costCenterDTO){
        costCenterService.deleteCostCenter(costCenterDTO);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
