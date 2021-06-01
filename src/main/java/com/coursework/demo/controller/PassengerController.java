package com.coursework.demo.controller;

import com.coursework.demo.dto.AddPassengerDTO;
import com.coursework.demo.dto.PassengerDTO;
import com.coursework.demo.entity.Passenger;
import com.coursework.demo.mapper.PassengerMapper;
import com.coursework.demo.service.PassengerService;
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
@Api(tags = "Passenger API")
@RequestMapping("v1/passengers")
public class PassengerController {

    private final PassengerService passengerService;
    private final PassengerMapper passengerMapper;

    @Autowired
    public PassengerController(PassengerService passengerService, PassengerMapper passengerMapper) {
        this.passengerService = passengerService;
        this.passengerMapper = passengerMapper;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN, USER')")
    @ApiOperation(value = "Get passenger info by id")
    public ResponseEntity<PassengerDTO> get(@PathVariable("id") long id) {
        Passenger passenger = passengerService.getById(id);
        return ResponseEntity.status(OK).body(passengerMapper.convertToDto(passenger));
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN, USER')")
    @ApiOperation(value = "Get the list of all passengers")
    public ResponseEntity<List<PassengerDTO>> list(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok().body(passengerMapper.convertToDtoList(passengerService.getAll(pageable)));
    }

    @GetMapping("/routes/{id}")
    @PreAuthorize("hasAnyRole('ADMIN, USER')")
    @ApiOperation(value = "Get the list of all passengers in specific route")
    public ResponseEntity<List<PassengerDTO>> list(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(passengerMapper.convertToDtoList(passengerService.getPassengersByRoute(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Create new passenger")
    public ResponseEntity<PassengerDTO> save(@RequestBody AddPassengerDTO addPassengerDTO) {
        Passenger passenger = passengerService.save(passengerMapper.convertToEntity(addPassengerDTO));
        return ResponseEntity.status(CREATED).body(passengerMapper.convertToDto(passenger));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Update existing passenger by id")
    public ResponseEntity<PassengerDTO> update(@PathVariable("id") long id, @RequestBody PassengerDTO passengerDTO) {
        if (id == passengerDTO.getId()) {
            Passenger passenger = passengerService.save(passengerMapper.convertToEntity(passengerDTO));
            return ResponseEntity.status(OK).body(passengerMapper.convertToDto(passenger));
        } else {
            return ResponseEntity.status(BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Delete passenger by id")
    public ResponseEntity delete(@PathVariable("id") long id) {
        Passenger passenger = passengerService.getById(id);
        passengerService.delete(passenger);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
