package org.numbercounter;

import java.util.LinkedList;
import java.util.List;

public class AnswerCapture {
  final List<Answer> answers = new LinkedList<>();
  public void add(final Answer answer) {
    answers.add(answer);
  }
}
