package com.coursework.demo.controller;

import com.coursework.demo.dto.AddBusDTO;
import com.coursework.demo.dto.BusDTO;
import com.coursework.demo.entity.Bus;
import com.coursework.demo.mapper.BusMapper;
import com.coursework.demo.service.BusService;
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
@Api(tags = "Bus API")
@RequestMapping("v1/buses")
public class BusController {

    private final BusService busService;
    private final BusMapper busMapper;

    @Autowired
    public BusController(BusService busService, BusMapper busMapper) {
        this.busService = busService;
        this.busMapper = busMapper;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN, USER')")
    @ApiOperation(value = "Get bus info by id")
    public ResponseEntity<BusDTO> get(@PathVariable("id") long id) {
        Bus bus = busService.getById(id);
        return ResponseEntity.status(OK).body(busMapper.convertToDto(bus));
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN, USER')")
    @ApiOperation(value = "Get the list of all buses")
    public ResponseEntity<List<BusDTO>> list(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok().body(busMapper.convertToDtoList(busService.getAll(pageable)));
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Create new bus")
    public ResponseEntity<BusDTO> save(@RequestBody AddBusDTO addBusDTO) {
        Bus bus = busService.save(busMapper.convertToEntity(addBusDTO));
        return ResponseEntity.status(CREATED).body(busMapper.convertToDto(bus));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Update existing bus by id")
    public ResponseEntity<BusDTO> update(@PathVariable("id") long id, @RequestBody BusDTO busDTO) {
        if (id == busDTO.getId()) {
            Bus bus = busService.save(busMapper.convertToEntity(busDTO));
            return ResponseEntity.status(OK).body(busMapper.convertToDto(bus));
        } else {
            return ResponseEntity.status(BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Delete bus by id")
    public ResponseEntity delete(@PathVariable("id") long id) {
        Bus bus = busService.getById(id);
        busService.delete(bus);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
