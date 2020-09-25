package org.numbercounter.application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

class CSVWriter {
  private static final String OPERATION_COLUMN = "operation";
  private static final String LOWER_COLUMN = "lower";
  private static final String UPPER_COLUMN = "upper";
  private static final String RESULTS_COLUMN = "result";

  static File writeCSVFile(
      final String pathname,
      final List<Answer> answers)
      throws IOException {
    final File file = File.createTempFile("answersList", "", new File(pathname));
    final CSVFormat csvFormat = CSVFormat.RFC4180.withHeader(
        OPERATION_COLUMN,
        LOWER_COLUMN,
        UPPER_COLUMN,
        RESULTS_COLUMN);
    try (final CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(file), csvFormat)) {
      answers.
          forEach(answer -> {
            try {
              csvPrinter.printRecord(
                  answer.getOperation(),
                  answer.getLower().map(Object::toString).orElse(""),
                  answer.getUpper().map(Object::toString).orElse(""),
                  answer.getResult());
            } catch (IOException e) {
              e.printStackTrace();
            }
          });
    }
    return file;
  }
}
