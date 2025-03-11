package com.stringProcessingService.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.stringProcessingService.dto.ResponseObject;



@Service
public class WordAnalysisService {

	public ResponseObject analyzeString(String input) {
		String[] words = input.split("\\s+");
		int wordCount = words.length;

		List<String> palindromeWords = Arrays.stream(words).filter(this::isPalindrome).toList();

		boolean hasPalindrome = !palindromeWords.isEmpty();

		return new ResponseObject(UUID.randomUUID(), wordCount, hasPalindrome, palindromeWords);
	}
	@Cacheable(value = "palindromeCache", key = "#word")
	private boolean isPalindrome(String word) {
		String cleaned = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
		return cleaned.length() > 1 && cleaned.contentEquals(new StringBuilder(cleaned).reverse().toString());
	}
}
