package com.coursework.demo.service;

import com.coursework.demo.entity.Ticket;
import com.coursework.demo.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.coursework.demo.TestData.getTicket;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TicketServiceImplTest {

    @MockBean
    private TicketRepository ticketRepository;

    @Autowired
    private TicketService ticketService;

    @Test
    public void testGetById() {
        final Ticket ticket = getTicket();

        when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(ticket));

        final Ticket result = ticketService.getById(1L);

        assertEquals(ticket, result);
        verify(ticketRepository).findById(anyLong());
    }

    @Test
    public void testGetAll() {
        final Ticket ticket = getTicket();
        final List<Ticket> tickets = Collections.singletonList(ticket);
        final Pageable pageable = PageRequest.of(0, 5);
        final Page<Ticket> ticketPage = new PageImpl<>(tickets, pageable, 5);

        when(ticketRepository.findAll(pageable)).thenReturn(ticketPage);

        final List<Ticket> result = ticketService.getAll(pageable);

        assertEquals(tickets, result);
        verify(ticketRepository).findAll(pageable);
    }

    @Test
    public void testSave() {
        final Ticket ticket = getTicket();

        when(ticketRepository.save(ticket)).thenReturn(ticket);

        final Ticket result = ticketService.save(ticket);

        assertEquals(ticket, result);
        verify(ticketRepository).save(ticket);
    }

    @Test
    public void testDelete() {
        final Ticket ticket = getTicket();

        doNothing().when(ticketRepository).delete(ticket);

        final Ticket result = ticketService.delete(ticket);

        assertEquals(ticket, result);
        verify(ticketRepository).delete(ticket);
    }

}
