package org.numbercounter;

import java.util.LinkedList;
import java.util.List;

public class DataCapture {
  final List<Long> data = new LinkedList<Long>();

  public void add(long datapoint) {
    data.add(datapoint);
  }

  public StatisticsCalculator build_stats() {
    return new StatisticsCalculator(data);
  }
}
