package org.numbercounter;

import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Class to be used for calculating statistics on the data points collected by DataCapture.
 */
public class StatisticsCalculator {
  private final NavigableMap<Integer, Long> pointCountStatistics = new TreeMap<>();
  private final long totalPointCount;

  StatisticsCalculator(
      final NavigableMap<Integer, Long> pointCounts,
      final long totalPointCount)
  {
    this.totalPointCount = totalPointCount;

    long counter = 0L;
    for (final Map.Entry<Integer, Long> pointEntry : pointCounts.entrySet()) {
      pointCountStatistics.put(pointEntry.getKey(), counter);
      counter += pointEntry.getValue();
    }
  }

  /**
   * Returns the number of points below the given input value, excluding that value.
   *
   * @param upper count the number of points below this value.
   * @return the number of points below upper
   */
  long less(int upper)
  {
    return totalPointCount - (greater(upper-1));
  }

  /**
   * Returns the number of points between the given input values, including those values.
   *
   * @param lower count the number of points above this value.
   * @param upper count the number of points above this value.
   * @return the number of points between lower and upper including those values.
   */
  long between(int lower, int upper)
  {
    return totalPointCount - (less(lower) + greater(upper));
  }


  /**
   * Returns the number of points above the given input value, excluding that value.
   *
   * @param lower count the number of points above this value.
   * @return the number of points above lower
   */
  long greater(int lower)
  {
    final Entry<Integer, Long> ceilingEntry = pointCountStatistics.higherEntry(lower);
    if (ceilingEntry == null)
      return 0; //The requested lower bound higher than the range of recorded values.
    else
      return totalPointCount - ceilingEntry.getValue();
  }
}
