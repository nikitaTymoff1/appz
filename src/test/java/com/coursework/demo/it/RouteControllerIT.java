package com.coursework.demo.it;

import com.coursework.demo.dto.RouteDTO;
import com.coursework.demo.entity.Route;
import com.coursework.demo.repository.RouteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.coursework.demo.TestData.getRoute;
import static com.coursework.demo.TestData.getRouteRequest;
import static com.coursework.demo.it.TestUtils.asJsonString;
import static com.coursework.demo.it.TestUtils.deleteRequest;
import static com.coursework.demo.it.TestUtils.getRequest;
import static com.coursework.demo.it.TestUtils.postRequest;
import static com.coursework.demo.it.TestUtils.putRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RouteControllerIT {

    private static final String ROUTE_CONTROLLER_PATH = "/v1/routes/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RouteRepository routeRepository;

    @Test
    @WithMockUser(roles = "USER")
    public void testRetrieveRouteById() throws Exception {
        when(routeRepository.findById(anyLong())).thenReturn(Optional.of(getRoute()));

        mockMvc.perform(getRequest(ROUTE_CONTROLLER_PATH + "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(getRouteRequest())));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testRetrieveRouteList() throws Exception {
        final Route route = getRoute();
        final List<Route> routes = Collections.singletonList(route);
        final Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        final Page<Route> routePage = new PageImpl<>(routes, pageable, 10);

        when(routeRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(routePage);

        mockMvc.perform(getRequest(ROUTE_CONTROLLER_PATH))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(Collections.singletonList(getRouteRequest()))));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testSaveRoute() throws Exception {
        final Route route = getRoute();
        final RouteDTO request = getRouteRequest();

        when(routeRepository.save(any(Route.class))).thenReturn(route);

        mockMvc.perform(postRequest(ROUTE_CONTROLLER_PATH, request))
                .andExpect(status().isCreated())
                .andExpect(content().string(asJsonString(request)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateRoute() throws Exception {
        final Route route = getRoute();
        final RouteDTO request = getRouteRequest();

        when(routeRepository.save(route)).thenReturn(route);

        mockMvc.perform(putRequest(ROUTE_CONTROLLER_PATH + "1", request))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(request)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateRouteExpectedBadRequest() throws Exception {
        final Route route = getRoute();
        final RouteDTO request = getRouteRequest();

        when(routeRepository.save(route)).thenReturn(route);

        mockMvc.perform(putRequest(ROUTE_CONTROLLER_PATH + "2", request))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteRoute() throws Exception {
        final Route route = getRoute();

        when(routeRepository.findById(anyLong())).thenReturn(Optional.of(route));
        doNothing().when(routeRepository).delete(route);

        mockMvc.perform(deleteRequest(ROUTE_CONTROLLER_PATH + "1"))
                .andExpect(status().isNoContent());
    }
}
