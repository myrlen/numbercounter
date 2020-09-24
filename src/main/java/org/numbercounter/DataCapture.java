package org.numbercounter;

import java.util.NavigableMap;
import java.util.TreeMap;

public class DataCapture {
  final NavigableMap<Integer, Long> pointCounts = new TreeMap<>();
  long totalPointCount = 0;

  public void add(int dataPoint) {
    //This is O((log(m)) where
    // m is the maximum number of *unique* elements.
    // The maximum number of unique elements in the list is given in the problem as 1000
    // therefore this is O(log(1000)) -> O(1)
    final Long pointCount = pointCounts.computeIfAbsent(dataPoint, x -> 0L)+1;
    pointCounts.put(dataPoint, pointCount);
    totalPointCount++;
  }

  public StatisticsCalculator build_stats() {
    return new StatisticsCalculator(pointCounts, totalPointCount);
  }
}
