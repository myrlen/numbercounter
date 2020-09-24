package org.numbercounter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class StatisticsCalculatorTest {
  private final DataCapture dataCapture = new DataCapture();

  @ParameterizedTest
  @MethodSource("lessTestCases")
  void less(final LessTestCase lessTestCase)
  {
    final StatisticsCalculator statisticsCalculator
        = lessTestCase.dataCapture.build_stats();
    assertEquals(
        lessTestCase.expectedValue,
        statisticsCalculator.less(lessTestCase.testValue),
        lessTestCase.toString());
  }

  static Stream<LessTestCase> lessTestCases()
  {
    return Stream.of(
        new LessTestCase("zero case")
            .data(1, 2, 2, 3, 4, 5, 6)
            .testValue(0)
            .expectedValue(0),
        new LessTestCase("all but one case")
            .data(1, 2, 2, 3, 4, 5, 6)
            .testValue(6)
            .expectedValue(6)
    );
  }

  private static class LessTestCase {
    final DataCapture dataCapture = new DataCapture();
    String description;
    int testValue;
    int expectedValue;

    LessTestCase(final String description) {
      this.description = description;
    }

    LessTestCase data(long ... data) {
      Arrays.stream(data).forEach(dataCapture::add);
      return this;
    }

    LessTestCase testValue(int testValue) {
      this.testValue = testValue;
      return this;
    }

    LessTestCase expectedValue(int expectedValue) {
      this.expectedValue = expectedValue;
      return this;
    }

    @Override
    public String toString() {
      return "LessTestCase{" + description + '}';
    }
  }

  @Test
  void between()
  {
    StatisticsCalculator statisticsCalculator = dataCapture.build_stats();
    assertEquals(statisticsCalculator.between(0, 0), 0);
  }

  @Test
  void greater()
  {
    StatisticsCalculator statisticsCalculator = dataCapture.build_stats();
    assertEquals(statisticsCalculator.greater(0), 0);
  }
}
