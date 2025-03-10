package com.stringProcessingService.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.stringProcessingService.dto.ResponseObject;



@Service
public class WordAnalysisService {

	public ResponseObject analyzeString(String input) {
		String[] words = input.split("\\s+");
		int wordCount = words.length;

		List<String> palindromeWords = Arrays.stream(words).filter(word -> isPalindrome(word))
				.collect(Collectors.toList());

		boolean hasPalindrome = !palindromeWords.isEmpty();

		return new ResponseObject(UUID.randomUUID(), wordCount, hasPalindrome, palindromeWords);
	}

	private boolean isPalindrome(String word) {
		String cleaned = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
		return cleaned.length() > 1 && cleaned.equals(new StringBuilder(cleaned).reverse().toString());
	}
}
