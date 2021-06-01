package com.coursework.demo.it;


import com.coursework.demo.dto.TicketDTO;
import com.coursework.demo.entity.Ticket;
import com.coursework.demo.repository.TicketRepository;
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

import static com.coursework.demo.TestData.getTicket;
import static com.coursework.demo.TestData.getTicketRequest;
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
public class TicketControllerIT {

    private static final String TICKET_CONTROLLER_PATH = "/v1/tickets/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketRepository ticketRepository;

    @Test
    @WithMockUser(roles = "USER")
    public void testRetrieveTicketById() throws Exception {
        when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(getTicket()));

        mockMvc.perform(getRequest(TICKET_CONTROLLER_PATH + "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(getTicketRequest())));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testRetrieveTicketList() throws Exception {
        final Ticket ticket = getTicket();
        final List<Ticket> tickets = Collections.singletonList(ticket);
        final Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        final Page<Ticket> ticketPage = new PageImpl<>(tickets, pageable, 10);

        when(ticketRepository.findAll(pageable)).thenReturn(ticketPage);

        mockMvc.perform(getRequest(TICKET_CONTROLLER_PATH))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(Collections.singletonList(getTicketRequest()))));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testSaveTicket() throws Exception {
        final Ticket ticket = getTicket();
        final TicketDTO request = getTicketRequest();

        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        mockMvc.perform(postRequest(TICKET_CONTROLLER_PATH, request))
                .andExpect(status().isCreated())
                .andExpect(content().string(asJsonString(request)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateTicket() throws Exception {
        final Ticket ticket = getTicket();
        final TicketDTO request = getTicketRequest();

        when(ticketRepository.save(ticket)).thenReturn(ticket);

        mockMvc.perform(putRequest(TICKET_CONTROLLER_PATH + "1", request))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(request)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateTicketExpectedBadRequest() throws Exception {
        final Ticket ticket = getTicket();
        final TicketDTO request = getTicketRequest();

        when(ticketRepository.save(ticket)).thenReturn(ticket);

        mockMvc.perform(putRequest(TICKET_CONTROLLER_PATH + "2", request))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteTicket() throws Exception {
        final Ticket ticket = getTicket();

        when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(ticket));
        doNothing().when(ticketRepository).delete(ticket);

        mockMvc.perform(deleteRequest(TICKET_CONTROLLER_PATH + "1"))
                .andExpect(status().isNoContent());
    }
}
