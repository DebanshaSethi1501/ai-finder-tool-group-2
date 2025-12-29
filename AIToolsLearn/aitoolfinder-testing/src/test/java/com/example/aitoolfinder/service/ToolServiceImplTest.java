package com.example.aitoolfinder.service;

import com.example.aitoolfinder.dto.ToolRequestDto;
import com.example.aitoolfinder.model.Tool;
import com.example.aitoolfinder.repository.ToolRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ToolServiceImplTest {

    @Mock
    private ToolRepository toolRepository;

    @InjectMocks
    private ToolServiceImpl toolService;

    @Test
    void shouldCreateToolSuccessfully() {
        // given
        ToolRequestDto dto = new ToolRequestDto();
        dto.setName("ChatGPT");
        dto.setUseCase("AI Assistant");
        dto.setCategory("NLP");
        dto.setPricingType("FREE");

        Tool savedTool = new Tool();
        savedTool.setId(1L);
        savedTool.setName("ChatGPT");
        savedTool.setAverageRating(0.0);

        when(toolRepository.save(any(Tool.class))).thenReturn(savedTool);

        // when
        Tool result = toolService.createTool(dto);

        // then
        assertNotNull(result);
        assertEquals("ChatGPT", result.getName());
        verify(toolRepository, times(1)).save(any(Tool.class));
    }

    @Test
    void shouldGetToolById() {
        Tool tool = new Tool();
        tool.setId(1L);
        tool.setName("ChatGPT");

        when(toolRepository.findById(1L)).thenReturn(Optional.of(tool));

        Tool result = toolService.getToolById(1L);

        assertEquals(1L, result.getId());
        assertEquals("ChatGPT", result.getName());
    }
}
