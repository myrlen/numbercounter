package org.numbercounter;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class StatisticsCalculator {
  private final TreeMap<Integer, Long> lessStatistics = new TreeMap<>();
  private final TreeMap<Integer, Long> greaterStatistics = new TreeMap<>();

  public StatisticsCalculator(final List<Integer> data) {
    //This is O(n(log(m))) where
    // n is the number of elements in the list, and
    // m is the maximum number of *unique* elements in the list.
    // The maximum number of unique elements in the list is 1000, which is a constant,
    // therefore this is O(n(log(c))) -> O(n)
    final TreeMap<Integer, Long> pointCounts = new TreeMap<>();
    data.forEach(dataPoint -> {
      final Long pointCount = pointCounts.computeIfAbsent(dataPoint, x -> 0L)+1;
      pointCounts.put(dataPoint, pointCount);
    });

    long counter = 0L;
    int criticalPointLess = -1;
    long totalCount = data.size();
    for (final Map.Entry<Integer, Long> pointEntry : pointCounts.entrySet()) {
      lessStatistics.put(criticalPointLess, counter);
      criticalPointLess = pointEntry.getKey() + 1; //+1 because its "less than but not including".
      int criticalPointMore = pointEntry.getKey() - 1;
      greaterStatistics.put( criticalPointMore, totalCount - counter);

      counter += pointEntry.getValue();
    }
    lessStatistics.put(criticalPointLess, counter);
    //There will be one more entry than 'necessary' but it won't cause harm because it's a zero pair.
  }

  long less(int upper)
  {
    final int floorKey = lessStatistics.floorKey(upper);
    return lessStatistics.get(floorKey);
  }

  int between(int lower, int upper)
  {
    return 0;
  }

  long greater(int lower)
  {
    final Entry<Integer, Long> ceilingEntry = greaterStatistics.ceilingEntry(lower);
    if (ceilingEntry == null)
      return 0; //The requested lower bound higher than the range of recorded values.
    else
      return ceilingEntry.getValue();
  }
}
