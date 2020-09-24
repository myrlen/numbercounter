package org.numbercounter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class StatisticsCalculatorTest {

  private static class SingleInputTestCase {
    final DataCapture dataCapture = new DataCapture();
    String function;
    String description;
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

  private final DataCapture dataCapture = new DataCapture();

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

  @Test
  void between()
  {
    StatisticsCalculator statisticsCalculator = dataCapture.build_stats();
    assertEquals(statisticsCalculator.between(0, 0), 0);
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
