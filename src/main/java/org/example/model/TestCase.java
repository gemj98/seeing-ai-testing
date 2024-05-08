package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TestCase {
    public static final String NO_HUMAN_TEXT = "no person detected";

    @JsonProperty("test_number")
    String testNumber;
    List<String> words;
};


