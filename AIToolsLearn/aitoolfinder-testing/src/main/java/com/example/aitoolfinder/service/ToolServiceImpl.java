package com.example.aitoolfinder.service;

import com.example.aitoolfinder.dto.ToolRequestDto;
import com.example.aitoolfinder.model.Tool;
import com.example.aitoolfinder.repository.ToolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToolServiceImpl implements ToolService {

    @Autowired
    private ToolRepository toolRepository;

    @Override
    public List<Tool> getAllTools() {
        return toolRepository.findAll();
    }

    @Override
    public Tool getToolById(Long id) {
        return toolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tool not found with id: " + id));
    }

    @Override
    public Tool createTool(ToolRequestDto dto) {
        Tool tool = mapToEntity(dto);
        tool.setAverageRating(0.0);
        return toolRepository.save(tool);
    }

    @Override
    public Tool updateTool(Long id, ToolRequestDto dto) {
        Tool existingTool = getToolById(id);

        existingTool.setName(dto.getName());
        existingTool.setUseCase(dto.getUseCase());
        existingTool.setCategory(dto.getCategory());
        existingTool.setPricingType(dto.getPricingType());

        return toolRepository.save(existingTool);
    }

    @Override
    public Tool patchTool(Long id, ToolRequestDto dto) {
        Tool existingTool = getToolById(id);

        if (dto.getName() != null)
            existingTool.setName(dto.getName());

        if (dto.getUseCase() != null)
            existingTool.setUseCase(dto.getUseCase());

        if (dto.getCategory() != null)
            existingTool.setCategory(dto.getCategory());

        if (dto.getPricingType() != null)
            existingTool.setPricingType(dto.getPricingType());

        return toolRepository.save(existingTool);
    }

    @Override
    public void deleteTool(Long id) {
        Tool tool = getToolById(id);
        toolRepository.delete(tool);
    }

    @Override
    public List<Tool> getFilteredTools(String category, Double minRating, String pricingType) {
        return toolRepository.findAll()
                .stream()
                .filter(t -> category == null || t.getCategory().equalsIgnoreCase(category))
                .filter(t -> minRating == null || t.getAverageRating() >= minRating)
                .filter(t -> pricingType == null || t.getPricingType().equalsIgnoreCase(pricingType))
                .collect(Collectors.toList());
    }

    /* ==========================
       DTO → ENTITY MAPPER
       ========================== */
    private Tool mapToEntity(ToolRequestDto dto) {
        Tool tool = new Tool();
        tool.setName(dto.getName());
        tool.setUseCase(dto.getUseCase());
        tool.setCategory(dto.getCategory());
        tool.setPricingType(dto.getPricingType());
        return tool;
    }
}
