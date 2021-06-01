package com.coursework.demo.service;

import com.coursework.demo.entity.Luggage;
import com.coursework.demo.repository.LuggageRepository;
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

import static com.coursework.demo.TestData.getLuggage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class LuggageServiceImplTest {

    @MockBean
    private LuggageRepository luggageRepository;

    @Autowired
    private LuggageService luggageService;

    @Test
    public void testGetById() {
        final Luggage luggage = getLuggage();

        when(luggageRepository.findById(anyLong())).thenReturn(Optional.of(luggage));

        final Luggage result = luggageService.getById(1L);

        assertEquals(luggage, result);
        verify(luggageRepository).findById(anyLong());
    }

    @Test
    public void testGetAll() {
        final Luggage luggage = getLuggage();
        final List<Luggage> luggages = Collections.singletonList(luggage);
        final Pageable pageable = PageRequest.of(0, 5);
        final Page<Luggage> luggagePage = new PageImpl<>(luggages, pageable, 5);

        when(luggageRepository.findAll(pageable)).thenReturn(luggagePage);

        final List<Luggage> result = luggageService.getAll(pageable);

        assertEquals(luggages, result);
        verify(luggageRepository).findAll(pageable);
    }

    @Test
    public void testSave() {
        final Luggage luggage = getLuggage();

        when(luggageRepository.save(luggage)).thenReturn(luggage);

        final Luggage result = luggageService.save(luggage);

        assertEquals(luggage, result);
        verify(luggageRepository).save(luggage);
    }

    @Test
    public void testDelete() {
        final Luggage luggage = getLuggage();

        doNothing().when(luggageRepository).delete(luggage);

        final Luggage result = luggageService.delete(luggage);

        assertEquals(luggage, result);
        verify(luggageRepository).delete(luggage);
    }
}
