package com.example.aitoolfinder.controller;

import com.example.aitoolfinder.dto.ToolRequestDto;
import com.example.aitoolfinder.model.Tool;
import com.example.aitoolfinder.service.ToolService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tools")
public class ToolController {

    @Autowired
    private ToolService toolService;

    /* ============================
       GET APIs
       ============================ */

    // GET /tools
    @GetMapping
    public ResponseEntity<List<Tool>> getAllTools() {
        return ResponseEntity.ok(toolService.getAllTools());
    }

    // GET /tools/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Tool> getToolById(@PathVariable Long id) {
        return ResponseEntity.ok(toolService.getToolById(id));
    }

    // GET /tools/filter
    // Example: /tools/filter?category=NLP&minRating=4&pricingType=FREE
    @GetMapping("/filter")
    public ResponseEntity<List<Tool>> getFilteredTools(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) String pricingType) {

        return ResponseEntity.ok(
                toolService.getFilteredTools(category, minRating, pricingType)
        );
    }

    /* ============================
       POST API
       ============================ */

    // POST /tools
    @PostMapping
    public ResponseEntity<Tool> createTool(
            @Valid @RequestBody ToolRequestDto toolRequestDto) {

        Tool createdTool = toolService.createTool(toolRequestDto);
        return new ResponseEntity<>(createdTool, HttpStatus.CREATED);
    }

    /* ============================
       PUT API (FULL UPDATE)
       ============================ */

    // PUT /tools/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Tool> updateTool(
            @PathVariable Long id,
            @Valid @RequestBody ToolRequestDto toolRequestDto) {

        Tool updatedTool = toolService.updateTool(id, toolRequestDto);
        return ResponseEntity.ok(updatedTool);
    }

    /* ============================
       PATCH API (PARTIAL UPDATE)
       ============================ */

    // PATCH /tools/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<Tool> patchTool(
            @PathVariable Long id,
            @RequestBody ToolRequestDto toolRequestDto) {

        Tool patchedTool = toolService.patchTool(id, toolRequestDto);
        return ResponseEntity.ok(patchedTool);
    }

    /* ============================
       DELETE API
       ============================ */

    // DELETE /tools/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTool(@PathVariable Long id) {
        toolService.deleteTool(id);
        return ResponseEntity.ok("Tool deleted successfully");
    }
}
