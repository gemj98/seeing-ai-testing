package org.example.script;

import io.appium.java_client.android.AndroidDriver;
import org.example.utils.AndroidDriverManager;
import org.example.utils.WebElements;
import org.example.model.TestCase;
import org.example.utils.FingerGestureUtils;
import org.example.utils.TestManager;

import java.util.List;

public class TestDemo {

    private AndroidDriver driver;
    private WebElements elements;
    private FingerGestureUtils gestures;
    private TestManager testManager;
    private final int INITIAL_TEST_CASE_NUMBER = 1;
    private final int TEST_CASES_NUMBER = 22;

    public void runTest() {

        var androidDriverManager = new AndroidDriverManager();
        driver = androidDriverManager.getDriver();
        elements = new WebElements(driver);
        gestures = new FingerGestureUtils(driver);
        testManager = new TestManager("expected_output.json");

        initialScreen();
        int testPassNumber = testImages(
                "tc-he-",
                INITIAL_TEST_CASE_NUMBER,
                TEST_CASES_NUMBER
        );
        float passPercentage = (float) testPassNumber / (float) TEST_CASES_NUMBER * 100;
        System.out.printf("Test Passed: %d/%d\n",testPassNumber,TEST_CASES_NUMBER);
        System.out.printf("Pass %%: %.1f\n", passPercentage);
    }

    private void initialScreen() {
        elements.agreeButton().click();
        gestures.swipe(FingerGestureUtils.Direction.UP, 99);
        elements.internalStorageButton().click();
        elements.picturesFolder().click();
        elements.inputImagesFolder().click();
    }

    private int testImages(String baseFileName, int startNumber, int endNumber) {
        int passCounter = 0;
        for (int i = startNumber; i <= endNumber; i++) {
            var imageFileName = String.format("%s%02d",baseFileName,i);

            TestCase testCase = testManager.getTestCaseByNumber(i);
            List<String> expectedOutput = testCase.getWords();
            System.out.printf("Test case: %s\n",imageFileName);
            System.out.printf("Expected output: %s\n", expectedOutput);

            var actualOutput = testImage(imageFileName);
            var outputAsList = List.of(actualOutput.toLowerCase().split("[ .]"));
            System.out.printf("Actual output: %s\n", actualOutput);
            if (testManager.compareOutputs(testCase.getWords(), outputAsList)) {
                passCounter++;
                System.out.println("PASS");
            } else {
                System.out.println("FAIL");
            }
            System.out.println("--------------------------------------");
        }
        return passCounter;
    }

    private String testImage(String imageFileName) {
        elements.testFileName(imageFileName).click();
        elements.shareButton().click();
        elements.seeingAiButton().click();
        elements.waitForProcessing();
        String actualOutput = elements.getTestOutput();
        elements.backToSeeingAi();
        return actualOutput;
    }
}