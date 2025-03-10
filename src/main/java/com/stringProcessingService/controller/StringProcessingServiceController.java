package com.stringProcessingService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stringProcessingService.dto.ResponseObject;
import com.stringProcessingService.service.WordAnalysisService;


@RestController
@RequestMapping("/api/analyze")
public class StringProcessingServiceController {

	@Autowired
	private WordAnalysisService wordAnalysisService;

	@PostMapping
	public ResponseObject analyze(@RequestBody String input) {
		return wordAnalysisService.analyzeString(input);
	}

}
