package com.task.tracker.controller.ticket;

import com.task.tracker.dto.ticket.TicketDTO;
import com.task.tracker.dto.ticket.TicketSearchCriteriaDTO;
import com.task.tracker.service.ticket.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/ticket")
public class TicketController {

    @Autowired
    TicketService ticketService;

    @GetMapping("search")
    private List<TicketDTO> getClosedTicketsBetweenDates(final TicketSearchCriteriaDTO searchCriteriaDTO){
        return ticketService.getClosedTicketsBetweenDates(searchCriteriaDTO);
    }

    @GetMapping("ticket-id/{ticketId}")
    private TicketDTO getTicketById(@PathVariable("ticketId") final Long ticketId){
        return ticketService.getTicketById(ticketId);
    }

    @GetMapping("project-id/{projectId}")
    private List<TicketDTO> getProjectById(@PathVariable("projectId") final Long projectId,
        @RequestParam(value = "status", required = false) final String ticketStatus){
        return ticketService.getTicketsByProjectId(projectId, ticketStatus);
    }

    @GetMapping("un-assigned/list")
    private List<TicketDTO> getUnAssignedTicketList(){
        return ticketService.getUnAssignedTicketList();
    }

    @GetMapping("list")
    private List<TicketDTO> getTicketList(@RequestParam(value = "status", required = false) final String ticketStatus){
        return ticketService.getAllTicketList(ticketStatus);
    }

    @GetMapping("user-id/{userId}/list")
    private List<TicketDTO> getUnCompletedTicketListByUser(@PathVariable("userId") final String userId){
        return ticketService.getUnCompletedTicketListByUser(userId);
    }

    @PostMapping("")
    private ResponseEntity<TicketDTO> addTicket(@RequestBody @Valid final TicketDTO ticketDTO){
        TicketDTO newTicketDTO = ticketService.addTicket(ticketDTO);
        return new ResponseEntity<>(newTicketDTO, HttpStatus.CREATED);
    }

    @PutMapping("")
    private ResponseEntity<Void> updateTicket(@RequestBody @Valid final TicketDTO ticketDTO){
        ticketService.updateTicket(ticketDTO);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("")
    private ResponseEntity<Void> deleteTicket(@RequestBody @Valid final TicketDTO ticketDTO){
        ticketService.deleteTicket(ticketDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
