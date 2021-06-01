package com.coursework.demo.controller;

import com.coursework.demo.dto.AddTicketDTO;
import com.coursework.demo.dto.TicketDTO;
import com.coursework.demo.entity.Ticket;
import com.coursework.demo.mapper.TicketMapper;
import com.coursework.demo.service.TicketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@Api(tags = "Ticket API")
@RequestMapping("v1/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final TicketMapper ticketMapper;

    @Autowired
    public TicketController(TicketService ticketService, TicketMapper ticketMapper) {
        this.ticketService = ticketService;
        this.ticketMapper = ticketMapper;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN, USER')")
    @ApiOperation(value = "Get ticket info by id")
    public ResponseEntity<TicketDTO> get(@PathVariable("id") long id){
        Ticket ticket = ticketService.getById(id);
        return ResponseEntity.status(OK).body(ticketMapper.convertToDto(ticket));
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN, USER')")
    @ApiOperation(value = "Get the list of all tickets")
    public ResponseEntity<List<TicketDTO>> list(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok().body(ticketMapper.convertToDtoList(ticketService.getAll(pageable)));
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Create new ticket")
    public ResponseEntity<TicketDTO> save(@RequestBody AddTicketDTO addTicketDTO) {
        Ticket ticket = ticketService.save(ticketMapper.convertToEntity(addTicketDTO));
        return ResponseEntity.status(CREATED).body(ticketMapper.convertToDto(ticket));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Update existing ticket by id")
    public ResponseEntity<TicketDTO> update(@PathVariable("id") long id, @RequestBody TicketDTO ticketDTO) {
        if (id == ticketDTO.getId()) {
            Ticket ticket = ticketService.save(ticketMapper.convertToEntity(ticketDTO));
            return ResponseEntity.status(OK).body(ticketMapper.convertToDto(ticket));
        } else {
            return ResponseEntity.status(BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Delete ticket by id")
    public ResponseEntity delete(@PathVariable("id") long id){
        Ticket ticket = ticketService.getById(id);
        ticketService.delete(ticket);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
