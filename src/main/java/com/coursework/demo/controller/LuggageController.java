package com.coursework.demo.controller;

import com.coursework.demo.dto.AddLuggageDTO;
import com.coursework.demo.dto.LuggageDTO;
import com.coursework.demo.entity.Luggage;
import com.coursework.demo.mapper.LuggageMapper;
import com.coursework.demo.service.LuggageService;
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
@Api(tags = "Luggage API")
@RequestMapping("v1/luggage")
public class LuggageController {

    private final LuggageService luggageService;
    private final LuggageMapper luggageMapper;

    @Autowired
    public LuggageController(LuggageService luggageService, LuggageMapper luggageMapper) {
        this.luggageService = luggageService;
        this.luggageMapper = luggageMapper;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN, USER')")
    @ApiOperation(value = "Get luggage info by id")
    public ResponseEntity<LuggageDTO> get(@PathVariable("id") long id) {
        Luggage luggage = luggageService.getById(id);
        return ResponseEntity.status(OK).body(luggageMapper.convertToDto(luggage));
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN, USER')")
    @ApiOperation(value = "Get the list of all luggage")
    public ResponseEntity<List<LuggageDTO>> list(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok().body(luggageMapper.convertToDtoList(luggageService.getAll(pageable)));
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Create new luggage")
    public ResponseEntity<LuggageDTO> save(@RequestBody AddLuggageDTO addLuggageDTO) {
        Luggage luggage = luggageService.save(luggageMapper.convertToEntity(addLuggageDTO));
        return ResponseEntity.status(CREATED).body(luggageMapper.convertToDto(luggage));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Update existing luggage by id")
    public ResponseEntity<LuggageDTO> update(@PathVariable("id") long id, @RequestBody LuggageDTO luggageDTO) {
        if (id == luggageDTO.getId()) {
            Luggage luggage = luggageService.save(luggageMapper.convertToEntity(luggageDTO));
            return ResponseEntity.status(OK).body(luggageMapper.convertToDto(luggage));
        } else {
            return ResponseEntity.status(BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Delete luggage by id")
    public ResponseEntity delete(@PathVariable("id") long id) {
        Luggage luggage = luggageService.getById(id);
        luggageService.delete(luggage);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
