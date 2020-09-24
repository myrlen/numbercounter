package org.numbercounter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

public class CSVReader {
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
}
