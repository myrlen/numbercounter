package org.numbercounter;

import java.util.LinkedList;
import java.util.List;

public class DataCapture {
  final List<Integer> data = new LinkedList<>();

  public void add(int datapoint) {
    data.add(datapoint);
  }

  public StatisticsCalculator build_stats() {
    return new StatisticsCalculator(data);
  }
}
