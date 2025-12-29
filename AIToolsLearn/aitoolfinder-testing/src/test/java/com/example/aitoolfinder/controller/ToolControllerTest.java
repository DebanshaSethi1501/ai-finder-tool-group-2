package com.example.aitoolfinder.controller;

import com.example.aitoolfinder.dto.ToolRequestDto;
import com.example.aitoolfinder.model.Tool;
import com.example.aitoolfinder.service.ToolService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ToolController.class)
class ToolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // ✅ NEW annotation (replaces @MockBean)
    @MockitoBean
    private ToolService toolService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateTool() throws Exception {

        Tool tool = new Tool();
        tool.setId(1L);
        tool.setName("ChatGPT");

        when(toolService.createTool(any(ToolRequestDto.class))).thenReturn(tool);

        ToolRequestDto dto = new ToolRequestDto();
        dto.setName("ChatGPT");
        dto.setUseCase("AI Assistant");
        dto.setCategory("NLP");
        dto.setPricingType("FREE");

        mockMvc.perform(post("/tools")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("ChatGPT"));
    }

    @Test
    void shouldGetAllTools() throws Exception {

        Tool tool = new Tool();
        tool.setId(1L);
        tool.setName("ChatGPT");

        when(toolService.getAllTools()).thenReturn(List.of(tool));

        mockMvc.perform(get("/tools"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("ChatGPT"));
    }
}
