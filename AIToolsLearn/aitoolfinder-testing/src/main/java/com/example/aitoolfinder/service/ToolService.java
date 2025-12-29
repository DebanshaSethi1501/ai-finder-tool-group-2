package com.example.aitoolfinder.service;

import com.example.aitoolfinder.dto.ToolRequestDto;
import com.example.aitoolfinder.model.Tool;

import java.util.List;

public interface ToolService {

    List<Tool> getAllTools();

    Tool getToolById(Long id);

    Tool createTool(ToolRequestDto toolRequestDto);

    Tool updateTool(Long id, ToolRequestDto toolRequestDto);

    Tool patchTool(Long id, ToolRequestDto toolRequestDto);

    void deleteTool(Long id);

    List<Tool> getFilteredTools(String category, Double minRating, String pricingType);
}
