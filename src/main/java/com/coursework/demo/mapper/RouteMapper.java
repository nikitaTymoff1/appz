package com.coursework.demo.mapper;

import com.coursework.demo.dto.AddRouteDTO;
import com.coursework.demo.dto.RouteDTO;
import com.coursework.demo.entity.Route;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RouteMapper {

    RouteDTO convertToDto(Route route);

    Route convertToEntity(RouteDTO routeDTO);

    Route convertToEntity(AddRouteDTO routeDTO);

    List<RouteDTO> convertToDtoList(List<Route> routes);

}
