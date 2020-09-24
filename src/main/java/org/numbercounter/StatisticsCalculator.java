package org.numbercounter;

import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Class to be used for calculating statistics on the data points collected by DataCapture.
 */
public class StatisticsCalculator {
  private final NavigableMap<Integer, Long> lessStatistics = new TreeMap<>();
  private final NavigableMap<Integer, Long> greaterStatistics = new TreeMap<>();
  private final long totalPointCount;

  StatisticsCalculator(
      final NavigableMap<Integer, Long> pointCounts,
      final long totalPointCount)
  {
    this.totalPointCount = totalPointCount;

    long counter = 0L;
    int criticalPointLess = -1;
    for (final Map.Entry<Integer, Long> pointEntry : pointCounts.entrySet()) {
      lessStatistics.put(criticalPointLess, counter);
      criticalPointLess = pointEntry.getKey() + 1; //+1 because its "less than but not including".
      int criticalPointMore = pointEntry.getKey() - 1;
      greaterStatistics.put( criticalPointMore, totalPointCount - counter);

      counter += pointEntry.getValue();
    }
    lessStatistics.put(criticalPointLess, counter);
    //There will be one more entry than 'necessary' but it won't cause harm because it's a zero pair.
  }

  /**
   * Returns the number of points below the given input value, excluding that value.
   *
   * @param upper count the number of points below this value.
   * @return the number of points below upper
   */
  long less(int upper)
  {
    final int floorKey = lessStatistics.floorKey(upper);
    return lessStatistics.get(floorKey);
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
    final Entry<Integer, Long> ceilingEntry = greaterStatistics.ceilingEntry(lower);
    if (ceilingEntry == null)
      return 0; //The requested lower bound higher than the range of recorded values.
    else
      return ceilingEntry.getValue();
  }
}
