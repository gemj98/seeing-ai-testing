package org.example.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.TestCase;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestManager {
    private List<TestCase> testCases;

    public TestManager(String testCasesFile) {
        loadTestCases(testCasesFile);
    }

    private void loadTestCases(String testCasesFile) {

        List<TestCase> testCases;

        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(getClass().getClassLoader().getResource(testCasesFile).getFile());
        try {
            Map<String, List<TestCase>> testCasesObject = objectMapper.readValue(file, new TypeReference<>(){});
            testCases = testCasesObject.get("test_cases");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.testCases = testCases;
    }

    public TestCase getTestCaseByNumber(int testNumber) {
        String testNumberString = String.format("%02d", testNumber);
        return testCases.stream().filter(tc -> tc.getTestNumber().equals(testNumberString)).findFirst().orElse(null);
    }

    public boolean compareOutputs(List<String> expectedOutput, List<String> actualOutput) {
        Map<String, Integer> expectedCounts = getWordCounts(expectedOutput);
        Map<String, Integer> actualCounts = getWordCounts(actualOutput);
        for (String word : expectedCounts.keySet()) {
            if (!actualCounts.getOrDefault(word, 0).equals(expectedCounts.get(word))) {
                return false;
            }
        }

        return true;
    }

    private Map<String, Integer> getWordCounts(List<String> words) {
        Map<String, Integer> wordCounts = new HashMap<>();
        for (String word : words) {
            wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
        }
        return wordCounts;
    }
}
