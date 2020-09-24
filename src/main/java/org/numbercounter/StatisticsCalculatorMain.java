package org.numbercounter;

import java.io.IOException;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

public class StatisticsCalculatorMain {

  public static final String INPUT_CSV_FILE = "inputCSVFile";
  public static final String REQUESTS_CSV_FILE = "requestsCSVFile";
  public static final String OUTPUT_DIRECTORY = "outputDirectory";

  public static void main(final String [] args)
  {
    final ArgumentParser parser = ArgumentParsers.newFor("StatisticsCalculator").build()
        .defaultHelp(true)
        .description("calculate requested statistics on inputted numbers.");
    parser.addArgument("-i", "--" + INPUT_CSV_FILE)
        .help("CSV File containing the data to calculate the results on");
    parser.addArgument("-r", "--" + REQUESTS_CSV_FILE)
        .help("CSV File containing the requests for statistics to be calculated");
    parser.addArgument("-o", "--" + OUTPUT_DIRECTORY)
        .help("The directory to accept output of results");

    Namespace ns = null;

    try {
      ns = parser.parseArgs(args);
    } catch (ArgumentParserException e) {
      parser.handleError(e);
      System.exit(1);
    }


    final String dataPathname = ns.getString(INPUT_CSV_FILE);
    final DataCapture dataCapture = new DataCapture();
    try {
      CSVReader.readData(dataPathname, dataCapture);
    } catch (final IOException e) {
      System.err.println("The file '" + dataPathname + "' couldn't be read.");
    }


    final String requestsPathname = ns.getString(REQUESTS_CSV_FILE);
    final AnswerCapture answerCapture = new AnswerCapture();
    try {
      final StatisticsCalculator statisticsCalculator = dataCapture.build_stats();
      CSVReader.readRequests(requestsPathname, statisticsCalculator, answerCapture);
    } catch (final IOException e) {
      System.err.println("The file '" + requestsPathname + "' couldn't be read.");
    }


    final String outputPathname = ns.getString(OUTPUT_DIRECTORY);
  }
}
