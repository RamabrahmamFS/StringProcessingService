package com.stringProcessingService;
import org.junit.jupiter.api.Test;

import com.stringProcessingService.dto.ResponseObject;
import com.stringProcessingService.service.WordAnalysisService;

import static org.junit.jupiter.api.Assertions.*;

public class WordAnalysisServiceTest {
	  private final WordAnalysisService service = new WordAnalysisService();

	    @Test
	    void testAnalyzeString() {
	        String input = "madam racecar hello";
	        ResponseObject response = service.analyzeString(input);

	        assertEquals(3, response.wordCount());
	        assertTrue(response.hasPalindrome());
	        assertTrue(response.palindromeWords().contains("madam"));
	        assertTrue(response.palindromeWords().contains("racecar"));
	    }

	    @Test
	    void testAnalyzeString_NoPalindrome() {
	        String input = "hello world";
	        ResponseObject response = service.analyzeString(input);

	        assertEquals(2, response.wordCount());
	        assertFalse(response.hasPalindrome());
	    }

}
