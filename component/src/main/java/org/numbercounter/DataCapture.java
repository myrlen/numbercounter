package org.numbercounter;

import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Class to be used for capturing data points and calculating statistics on those data points.
 */
public class DataCapture {
  final NavigableMap<Integer, Long> pointCounts = new TreeMap<>();
  long totalPointCount = 0;

  /**
   * Add a data point to the data capture.  This function records a count of each data
   * point it has observed, and a total count.  It can record as many as Long.MAX_VALUE
   * instances of each data point
   *
   * Time complexity is O(m) where m is the number of unique elements and will not exceed 1000.
   * Memory complexity is also O(m).
   *
   * @param dataPoint a number between [0, 1000]
   */
  public void add(int dataPoint) {
    //This is O(log(m)) where
    // m is the maximum number of *unique* elements.
    // The maximum number of unique elements in the list is given in the problem as 1000
    // therefore this is O(log(1000)) -> O(1)
    final Long pointCount = pointCounts.computeIfAbsent(dataPoint, x -> 0L)+1;
    pointCounts.put(dataPoint, pointCount);
    totalPointCount++;
  }

  /**
   * Create an object capable of efficiently calculating statistics on the observed data points.
   * If you continue adding data points after calling build_stats, the StatisticsCalculator this
   * generates will operate on the set of points captured at the time build_stats was called.
   *
   * Implementation is not thread safe.
   *
   * @return StatisticsCalculator
   */
  public StatisticsCalculator build_stats() {
    return new StatisticsCalculator(pointCounts, totalPointCount);
  }
}
