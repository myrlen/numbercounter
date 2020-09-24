Number Counter
==============================================================
 
numbercounter counts a list of numbers and makes it possible to generate basic statistics about 
that list of numbers 

* **Less** provides the number of datapoints less than a given data point (excluding the given 
data point itself)
* **Greater** provides the number of datapoints greater than a given data point (excluding the given 
              data point itself)
* **Between** provides the number of datapoints between two given data points (including the data 
points themselves)

To use the library you can add a maven dependecy to your project or call it on a set of CSV files.

You will need to have at least Java 8 installed.  Either the Oracle implementation or the OpenJDK implementation will do.

Using as a library
------------------
Build locally using gradle:
```
./gradlew build
```

Include in your project:
```xml
<dependency>
  <groupId>org.numbercounter</groupId>
  <artifactId>numbercounter</artifactId>
  <versionId>1.0-SNAPSHOT</version>
</dependency>
```

Then import and use:
```java
import org.numbercounter.DataCollector;
import org.numbercounter.StatisticsCalculator;

class Example {
  public static void main(final String [] args)
  {
    DataCapture capture = DataCapture();        
    capture.add(3);
    capture.add(9);
    capture.add(3);
    capture.add(4);
    capture.add(6);        

    StatisticsCalculator stats = capture.build_stats();        

    System.out.println(stats.less(4)); // should return 2 (only two values 3, 3 are less than 4)        
    System.out.println(stats.between(3, 6)); // should return 4 (3, 3, 4 and 6 are between 3 and 6)        
    System.out.println(stats.greater(4)); // should return 2 (6 and 9 are the only two values greater than 4)
  }
}
```

Using as a runtime
------------------
Under [src/test/resources/testData1.csv](src/test/resources/testData1.csv) you can find an example datapoints file.

Under [src/test/resources/testRequests1.csv](src/test/resources/testRequests1.csv) you can find an example requests file.

Build using gradle:
```
./gradlew build
```

Then call the program at the command line:
```
java -jar "./build/libs/numbercounter-1.0-SNAPSHOT.jar" --inputCSVFile "./src/test/resources/testData1.csv" --requestsCSVFile "./src/test/resources/testRequests1.csv" --outputDirectory "./build/test-results/"
```

License
------------------
This library is licenced under the [Apache 2 Licence](LICENSE.txt).

[![License](http://img.shields.io/badge/license-Apache2-red.svg)](http://opensource.org/licenses/apache-2.0) 
