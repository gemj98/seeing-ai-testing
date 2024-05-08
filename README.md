# AI Test Automation - SeeingAi Mobile App

This script runs automated test cases on SeeingAi to check the app's AI
feature to recognize people and their emotions. Selenium and Appium 
frameworks are used to interact  with the device and extract information.

## Features

- Automated testing of intelligent feature.
- Simple summary of pass rate.
- Application tested on physical Android device.

## High level algorithm

1. The test cases and their respective expected output 
(which is a list of words) are defined in a `.json` file. 
2. The script loads the test cases and then opens the Files application in 
a physical Android device. Then, it navigates to the folder containing the 
test input images. 
3. Each image is shared to the SeeingAi application to be analyzed and 
the resulting output for the Person section is extracted. 
4. If the actual output contains the words in the expected output in the 
correct amount, then the test is considered to pass.

## Expected output file format

The expected output for the test cases are defined in the 
`expected_output.json` file. 

```
{
  "test_cases": [
    {
      "test_number": "01",
      "words": ["man", "happy"]
    },
    {
      "test_number": "02",
      "words": ["man", "sad"]
    },
    ...
  ]
}
```

## Comparing extracted output

The output for the tested image is extracted from SeeingAi application and 
compared with the list of expected words. In the following sample test case
we expect both the words 'man' and 'happy' to appear once. This is true for
the actual output, so the test passed.

```
Test case: tc-he-01
Expected output: [man, happy]
Actual output: 22 years old man with Brown hair looking happy.
PASS
```

## Script image demo

*insert image here*

# Script video demo

*insert video here*