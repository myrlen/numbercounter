package org.numbercounter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

class StatisticsCalculatorTest {
  private final DataCapture dataCapture = new DataCapture();

  @Test
  void less()
  {
    StatisticsCalculator statisticsCalculator = dataCapture.build_stats();
    assertEquals(statisticsCalculator.less(0), 0);
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
