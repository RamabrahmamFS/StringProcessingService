package com.stringProcessingService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stringProcessingService.dto.ResponseObject;
import com.stringProcessingService.service.WordAnalysisService;

import java.util.Map;

@RestController
@RequestMapping("/api/analyze")
public class StringProcessingServiceController {

	@Autowired
	private WordAnalysisService wordAnalysisService;

	@PostMapping
	public ResponseObject analyze(@RequestBody Map<String, String> request) {
		return wordAnalysisService.analyzeString(request.get("input"));
	}

}
