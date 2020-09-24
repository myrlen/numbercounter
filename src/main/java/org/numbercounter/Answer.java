package org.numbercounter;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
class Answer {

  private final Operation operation;
  private final Optional<Integer> lower;
  private final Optional<Integer> upper;
  private final Long result;

  public Answer(
      final Operation operation,
      final Optional<Integer> lower,
      final Optional<Integer> upper,
      final Long result) {
    this.operation = operation;
    this.lower = lower;
    this.upper = upper;
    this.result = result;
  }

  public Operation getOperation() {
    return operation;
  }

  public Optional<Integer> getLower() {
    return lower;
  }

  public Optional<Integer> getUpper() {
    return upper;
  }

  public Long getResult() {
    return result;
  }
}
