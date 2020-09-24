package org.numbercounter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CSVReader {
  private static final String OPERATION_COLUMN = "operation";
  private static final String LOWER_COLUMN = "lower";
  private static final String UPPER_COLUMN = "upper";

  public static void readData(
      final String dataPathname,
      final DataCapture dataCapture) throws IOException
  {
    final File csvFileData = new File(dataPathname);
    try (final FileReader csvFileReaderData = new FileReader(csvFileData)) {
      final CSVParser inputRecords = CSVFormat.DEFAULT.parse(csvFileReaderData);
      inputRecords.forEach(x -> {
        final String number = x.get(0);
        dataCapture.add(Integer.parseInt(number));
      });
    }
  }

  public static Optional<Integer> getOptionalInput(
      final CSVRecord record,
      final String columnName) {
    final String stringValue = record.get(columnName);
    if (stringValue.isEmpty())
      return Optional.empty();
    else
      return Optional.of(Integer.parseInt(stringValue));
  }

  public static void readRequests(
      final String requestsPathname,
      final StatisticsCalculator statisticsCalculator,
      final AnswerCapture answerCapture) throws IOException, MissingNumberException {
    final File csvFileData = new File(requestsPathname);
    try (final FileReader csvFileReaderData = new FileReader(csvFileData)) {
      final CSVParser inputRecords = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(csvFileReaderData);
      inputRecords.forEach(record -> {
        final String operationString = record.get(OPERATION_COLUMN);
        final Operation operation = Operation.valueOf(operationString);
        final Optional<Integer> lower = getOptionalInput(record, LOWER_COLUMN);
        final Optional<Integer> upper = getOptionalInput(record, UPPER_COLUMN);

        Integer z;
        Integer w;
        long result;
        switch (operation) {
          case Less:
            z = upper.orElseThrow(MissingNumberException::new);
            result = statisticsCalculator.less(z);
            break;
          case Greater:
            w = lower.orElseThrow(MissingNumberException::new);
            result = statisticsCalculator.greater(w);
            break;
          case Between:
            w = lower.orElseThrow(MissingNumberException::new);
            z = upper.orElseThrow(MissingNumberException::new);
            result = statisticsCalculator.between(w, z);
            break;
          default:
            throw new IllegalStateException("Unexpected value: " + operation);
        }
        answerCapture.add(
            new Answer(
                operation,
                lower,
                upper,
                result));
      });
    }
  }
}
