package org.numbercounter;

import java.util.List;
import java.util.stream.Collectors;

public class StatisticsCalculator {
  private final List<Long> data;

  public StatisticsCalculator(List<Long> data) {
    this.data = data;
  }

  long less(long upper)
  {
    return data.stream().filter(x -> x < upper).count();
  }

  int between(int lower, int upper)
  {
    return 0;
  }

  int greater(int lower)
  {
    return 0;
  }
}
