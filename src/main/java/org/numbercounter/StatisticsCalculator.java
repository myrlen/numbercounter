package org.numbercounter;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

public class StatisticsCalculator {
  private final NavigableMap<Integer, Long> lessStatistics = new TreeMap<>();
  private final NavigableMap<Integer, Long> greaterStatistics = new TreeMap<>();
  private final long totalPointCount;

  public StatisticsCalculator(final List<Integer> data) {
     totalPointCount = data.size();
    //This is O(n(log(m))) where
    // n is the number of elements in the list, and
    // m is the maximum number of *unique* elements in the list.
    // The maximum number of unique elements in the list is 1000, which is a constant,
    // therefore this is O(n(log(c))) -> O(n)
    final NavigableMap<Integer, Long> pointCounts = new TreeMap<>();
    data.forEach(dataPoint -> {
      final Long pointCount = pointCounts.computeIfAbsent(dataPoint, x -> 0L)+1;
      pointCounts.put(dataPoint, pointCount);
    });

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

  long less(int upper)
  {
    final int floorKey = lessStatistics.floorKey(upper);
    return lessStatistics.get(floorKey);
  }

  long between(int lower, int upper)
  {
    return totalPointCount - (less(lower) + greater(upper));
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
