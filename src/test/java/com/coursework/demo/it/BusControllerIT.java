package com.coursework.demo.it;

import com.coursework.demo.dto.BusDTO;
import com.coursework.demo.entity.Bus;
import com.coursework.demo.repository.BusRepository;
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

import static com.coursework.demo.TestData.getBus;
import static com.coursework.demo.TestData.getBusRequest;
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
public class BusControllerIT {

    private static final String BUS_CONTROLLER_PATH = "/v1/buses/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BusRepository busRepository;

    @Test
    @WithMockUser(roles = "USER")
    public void testRetrieveBusById() throws Exception {
        when(busRepository.findById(anyLong())).thenReturn(Optional.of(getBus()));

        mockMvc.perform(getRequest(BUS_CONTROLLER_PATH + "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(getBusRequest())));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testRetrieveBusesList() throws Exception {
        final Bus bus = getBus();
        final List<Bus> buses = Collections.singletonList(bus);
        final Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        final Page<Bus> busPage = new PageImpl<>(buses, pageable, 10);

        when(busRepository.findAll(pageable)).thenReturn(busPage);

        mockMvc.perform(getRequest(BUS_CONTROLLER_PATH))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(Collections.singletonList(getBusRequest()))));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testSaveBus() throws Exception {
        final Bus bus = getBus();
        final BusDTO request = getBusRequest();

        when(busRepository.save(any(Bus.class))).thenReturn(bus);

        mockMvc.perform(postRequest(BUS_CONTROLLER_PATH, request))
                .andExpect(status().isCreated())
                .andExpect(content().string(asJsonString(request)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateBus() throws Exception {
        final Bus bus = getBus();
        final BusDTO request = getBusRequest();

        when(busRepository.save(bus)).thenReturn(bus);

        mockMvc.perform(putRequest(BUS_CONTROLLER_PATH + "1", request))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(request)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateBusExpectedBadRequest() throws Exception {
        final Bus bus = getBus();
        final BusDTO request = getBusRequest();

        when(busRepository.save(bus)).thenReturn(bus);

        mockMvc.perform(putRequest(BUS_CONTROLLER_PATH + "2", request))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteBus() throws Exception {
        final Bus bus = getBus();

        when(busRepository.findById(anyLong())).thenReturn(Optional.of(bus));
        doNothing().when(busRepository).delete(bus);

        mockMvc.perform(deleteRequest(BUS_CONTROLLER_PATH + "1"))
                .andExpect(status().isNoContent());
    }
}
