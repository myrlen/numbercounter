package org.numbercounter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class StatisticsCalculatorTest {

  /**
   * Helper class to create test cases for 'greater' and 'less' functions in the
   * StatisticsCalculator class.
   */
  private static class SingleInputTestCase {
    //Adding a function name and description makes the output easier to read when a test fails.
    String function;
    String description;

    final DataCapture dataCapture = new DataCapture();
    int testValue;
    long expectedValue;

    SingleInputTestCase(final String function, final String description) {
      this.function = function;
      this.description = description;
    }

    SingleInputTestCase data(int ... data) {
      Arrays.stream(data).forEach(dataCapture::add);
      return this;
    }

    SingleInputTestCase testValue(int testValue) {
      this.testValue = testValue;
      return this;
    }

    SingleInputTestCase expectedValue(long expectedValue) {
      this.expectedValue = expectedValue;
      return this;
    }

    @Override
    public String toString() {
      return function + "{'" + description + "'}";
    }
  }

  /**
   * Helper class to create test cases for 'between' function in the
   * StatisticsCalculator class.
   */
  private static class BetweenTestCase {
    //Adding a function name and description makes the output easier to read when a test fails.
    String description;

    final DataCapture dataCapture = new DataCapture();
    int lowerValue;
    int upperValue;
    long expectedValue;

    BetweenTestCase(final String description) {
      this.description = description;
    }

    BetweenTestCase data(int ... data) {
      Arrays.stream(data).forEach(dataCapture::add);
      return this;
    }

    BetweenTestCase lowerValue(int lowerValue) {
      this.lowerValue = lowerValue;
      return this;
    }

    BetweenTestCase upperValue(int upperValue) {
      this.upperValue = upperValue;
      return this;
    }

    BetweenTestCase expectedValue(long expectedValue) {
      this.expectedValue = expectedValue;
      return this;
    }

    @Override
    public String toString() {
      return "between{'" + description + "'}";
    }
  }

  @ParameterizedTest
  @MethodSource("lessTestCases")
  void less(final SingleInputTestCase lessTestCase)
  {
    final StatisticsCalculator statisticsCalculator
        = lessTestCase.dataCapture.build_stats();
    assertEquals(
        lessTestCase.expectedValue,
        statisticsCalculator.less(lessTestCase.testValue),
        lessTestCase.toString());
  }

  static Stream<SingleInputTestCase> lessTestCases()
  {
    final String function = "less";
    return Stream.of(
        new SingleInputTestCase(function, "Stephen's case")
            .data(3, 9, 3, 4, 6)
            .testValue(4)
            .expectedValue(2),
        new SingleInputTestCase(function, "zero case")
            .data(1, 2, 2, 3, 4, 5, 6)
            .testValue(0)
            .expectedValue(0),
        new SingleInputTestCase(function, "all but one case")
            .data(1, 2, 2, 3, 4, 5, 6)
            .testValue(6)
            .expectedValue(6)
    );
  }

  @ParameterizedTest
  @MethodSource("betweenTestCases")
  void between(final BetweenTestCase betweenTestCase)
  {
    final StatisticsCalculator statisticsCalculator
        = betweenTestCase.dataCapture.build_stats();
    assertEquals(
        betweenTestCase.expectedValue,
        statisticsCalculator.between(betweenTestCase.lowerValue, betweenTestCase.upperValue),
        betweenTestCase.toString());
  }

  static Stream<BetweenTestCase> betweenTestCases()
  {
    return Stream.of(
        new BetweenTestCase("Stephen's case")
            .data(3, 9, 3, 4, 6)
            .lowerValue(3)
            .upperValue(6)
            .expectedValue(4),
        new BetweenTestCase("single point case")
            .data(1, 2, 2, 3, 4, 5, 6)
            .lowerValue(2)
            .upperValue(2)
            .expectedValue(2),
        new BetweenTestCase("empty set  case")
            .data(3, 9, 3, 4, 6)
            .lowerValue(7)
            .upperValue(8)
            .expectedValue(0),
        new BetweenTestCase("all case")
            .data(1, 2, 2, 3, 4, 5, 6)
            .lowerValue(1)
            .upperValue(6)
            .expectedValue(7),
        new BetweenTestCase("last value case")
            .data(1, 2, 2, 3, 4, 5, 6)
            .lowerValue(6)
            .upperValue(9)
            .expectedValue(1),
        new BetweenTestCase("first value case")
            .data(1, 2, 2, 3, 4, 5, 6)
            .lowerValue(0)
            .upperValue(1)
            .expectedValue(1)
    );
  }

  @ParameterizedTest
  @MethodSource("greaterTestCases")
  void greater(final SingleInputTestCase greaterTestCase)
  {
    final StatisticsCalculator statisticsCalculator
        = greaterTestCase.dataCapture.build_stats();
    assertEquals(
        greaterTestCase.expectedValue,
        statisticsCalculator.greater(greaterTestCase.testValue),
        greaterTestCase.toString());
  }

  static Stream<SingleInputTestCase> greaterTestCases()
  {
    final String function = "greater";
    return Stream.of(
        new SingleInputTestCase(function,"Stephen's case")
            .data(3, 9, 3, 4, 6)
            .testValue(4)
            .expectedValue(2),
        new SingleInputTestCase(function, "all case")
            .data(1, 2, 2, 3, 4, 5, 6)
            .testValue(0)
            .expectedValue(7),
        new SingleInputTestCase(function, "none case")
            .data(1, 2, 2, 3, 4, 5, 6)
            .testValue(6)
            .expectedValue(0)
    );
  }
}
