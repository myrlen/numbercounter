package org.numbercounter;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

public class StatisticsCalculatorMain {
  public static void main(final String [] args)
  {
    final ArgumentParser parser = ArgumentParsers.newFor("StatisticsCalculator").build()
        .defaultHelp(true)
        .description("calculate requested statistics on inputted numbers.");
    parser.addArgument("-i", "--inputCSVFile")
        .help("CSV File containing the data to calculate the results on");
    parser.addArgument("-r", "--requestsCSVFile")
        .help("CSV File containing the requests for statistics to be calculated");
    parser.addArgument("-o", "--outputDirectory")
        .help("The directory to accept output of results");

    Namespace ns = null;

    try {
      ns = parser.parseArgs(args);
    } catch (ArgumentParserException e) {
      parser.handleError(e);
      System.exit(1);
    }

    final String dataPathname = ns.getString("dataCSVFile");
    final String requestsPathname = ns.getString("requestsCSVFile");
    final String outputPathname = ns.getString("outputDirectory");
  }
}
