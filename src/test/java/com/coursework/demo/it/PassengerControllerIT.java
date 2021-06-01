package com.coursework.demo.it;

import com.coursework.demo.dto.PassengerDTO;
import com.coursework.demo.entity.Passenger;
import com.coursework.demo.repository.PassengerRepository;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.coursework.demo.TestData.getExpectedPassengersDTOList;
import static com.coursework.demo.TestData.getExpectedPassengersList;
import static com.coursework.demo.TestData.getPassenger;
import static com.coursework.demo.TestData.getPassengerRequest;
import static com.coursework.demo.it.TestUtils.asJsonString;
import static com.coursework.demo.it.TestUtils.deleteRequest;
import static com.coursework.demo.it.TestUtils.getRequest;
import static com.coursework.demo.it.TestUtils.postRequest;
import static com.coursework.demo.it.TestUtils.putRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PassengerControllerIT {

    private static final String PASSENGER_CONTROLLER_PATH = "/v1/passengers/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PassengerRepository passengerRepository;

    @Test
    @WithMockUser(roles = "USER")
    public void testRetrievePassengerById() throws Exception {
        when(passengerRepository.findById(anyLong())).thenReturn(Optional.of(getPassenger()));

        mockMvc.perform(getRequest(PASSENGER_CONTROLLER_PATH + "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(getPassengerRequest())));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testRetrievePassengersListByRouteId() throws Exception {
        when(passengerRepository.getPassengersByRoute(anyLong())).thenReturn((getExpectedPassengersList()));

        mockMvc.perform(getRequest(PASSENGER_CONTROLLER_PATH + "routes/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(getExpectedPassengersDTOList())));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testRetrievePassengerList() throws Exception {
        final Passenger passenger = getPassenger();
        final List<Passenger> passengers = Collections.singletonList(passenger);
        final Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        final Page<Passenger> passengerPage = new PageImpl<>(passengers, pageable, 10);

        when(passengerRepository.findAll(pageable)).thenReturn(passengerPage);

        mockMvc.perform(getRequest(PASSENGER_CONTROLLER_PATH))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(Collections.singletonList(getPassengerRequest()))));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void testSavePassenger() throws Exception {
        final Passenger passenger = getPassenger();
        final PassengerDTO request = getPassengerRequest();

        when(passengerRepository.save(any(Passenger.class))).thenReturn(passenger);

        mockMvc.perform(postRequest(PASSENGER_CONTROLLER_PATH, request))
                .andExpect(status().isCreated())
                .andExpect(content().string(asJsonString(request)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdatePassenger() throws Exception {
        final Passenger passenger = getPassenger();
        final PassengerDTO request = getPassengerRequest();

        when(passengerRepository.save(passenger)).thenReturn(passenger);

        mockMvc.perform(putRequest(PASSENGER_CONTROLLER_PATH + "1", request))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(request)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdatePassengerExpectedBadRequest() throws Exception {
        final Passenger passenger = getPassenger();
        final PassengerDTO request = getPassengerRequest();

        when(passengerRepository.save(passenger)).thenReturn(passenger);

        mockMvc.perform(putRequest(PASSENGER_CONTROLLER_PATH + "2", request))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeletePassenger() throws Exception {
        final Passenger passenger = getPassenger();

        when(passengerRepository.findById(anyLong())).thenReturn(Optional.of(passenger));
        doNothing().when(passengerRepository).delete(passenger);

        mockMvc.perform(deleteRequest(PASSENGER_CONTROLLER_PATH + "1"))
                .andExpect(status().isNoContent());
    }
}
