package com.stringProcessingService;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stringProcessingService.controller.StringProcessingServiceController;
import com.stringProcessingService.dto.ResponseObject;
import com.stringProcessingService.service.WordAnalysisService;

@WebMvcTest(StringProcessingServiceController.class)
@ExtendWith(MockitoExtension.class)
public class StringProcessingServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private WordAnalysisService wordAnalysisService;

    @InjectMocks
    private StringProcessingServiceController stringProcessingServiceController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void analyze_ShouldReturnResponse_WhenValidInputIsProvided() throws Exception {
        // Given
        String inputString = "hello world";
        
        ResponseObject mockResponse = new ResponseObject(UUID.randomUUID(), 3, true, List.of("madam", "racecar"));
        when(wordAnalysisService.analyzeString(inputString)).thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(post("/api/analyze")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Map.of("input", inputString))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Processed successfully"));

        verify(wordAnalysisService, times(1)).analyzeString(inputString);
    }

    @Test
    void analyze_ShouldReturnBadRequest_WhenInputIsMissing() throws Exception {
        // When & Then
        mockMvc.perform(post("/api/analyze")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Collections.emptyMap())))
                .andExpect(status().isBadRequest());
    }

    @Test
    void analyze_ShouldReturnBadRequest_WhenStringProcessorExceptionIsThrown() throws Exception {
        // Given
        String inputString = "duplicate";
        when(wordAnalysisService.analyzeString(inputString))
                .thenThrow(new Exception("String already processed"));

        // When & Then
        mockMvc.perform(post("/api/analyze")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Map.of("input", inputString))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("String already processed"));

        verify(wordAnalysisService, times(1)).analyzeString(inputString);
    }
}

