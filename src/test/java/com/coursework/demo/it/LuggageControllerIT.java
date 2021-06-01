package com.coursework.demo.it;


import com.coursework.demo.dto.LuggageDTO;
import com.coursework.demo.entity.Luggage;
import com.coursework.demo.repository.LuggageRepository;
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

import static com.coursework.demo.TestData.getLuggage;
import static com.coursework.demo.TestData.getLuggageRequest;
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
public class LuggageControllerIT {

    private static final String LUGGAGE_CONTROLLER_PATH = "/v1/luggage/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LuggageRepository luggageRepository;

    @Test
    @WithMockUser(roles = "USER")
    public void testRetrieveLuggageById() throws Exception {
        when(luggageRepository.findById(anyLong())).thenReturn(Optional.of(getLuggage()));

        mockMvc.perform(getRequest(LUGGAGE_CONTROLLER_PATH + "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(getLuggageRequest())));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testRetrieveLuggageList() throws Exception {
        final Luggage luggage = getLuggage();
        final List<Luggage> luggages = Collections.singletonList(luggage);
        final Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        final Page<Luggage> luggagePage = new PageImpl<>(luggages, pageable, 10);

        when(luggageRepository.findAll(pageable)).thenReturn(luggagePage);

        mockMvc.perform(getRequest(LUGGAGE_CONTROLLER_PATH))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(Collections.singletonList(getLuggageRequest()))));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testSaveLuggage() throws Exception {
        final Luggage luggage = getLuggage();
        final LuggageDTO request = getLuggageRequest();

        when(luggageRepository.save(any(Luggage.class))).thenReturn(luggage);

        mockMvc.perform(postRequest(LUGGAGE_CONTROLLER_PATH, request))
                .andExpect(status().isCreated())
                .andExpect(content().string(asJsonString(request)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateLuggage() throws Exception {
        final Luggage luggage = getLuggage();
        final LuggageDTO request = getLuggageRequest();

        when(luggageRepository.save(luggage)).thenReturn(luggage);

        mockMvc.perform(putRequest(LUGGAGE_CONTROLLER_PATH + "1", request))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(request)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateLuggageExpectedBadRequest() throws Exception {
        final Luggage luggage = getLuggage();
        final LuggageDTO request = getLuggageRequest();

        when(luggageRepository.save(luggage)).thenReturn(luggage);

        mockMvc.perform(putRequest(LUGGAGE_CONTROLLER_PATH + "2", request))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteLuggage() throws Exception {
        final Luggage luggage = getLuggage();

        when(luggageRepository.findById(anyLong())).thenReturn(Optional.of(luggage));
        doNothing().when(luggageRepository).delete(luggage);

        mockMvc.perform(deleteRequest(LUGGAGE_CONTROLLER_PATH + "1"))
                .andExpect(status().isNoContent());
    }
}
