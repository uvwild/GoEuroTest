package test.goeuro.factories;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.QuoteMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import test.goeuro.MainProperties;

import java.io.IOException;

/**
 * Created by uv on 01/12/2015 for GoEuroTest
 * moved the configuration of the object mapper into its own factory class to be cleaner....
 */
@Configuration
public class CSVFormatFactory {

    private static CSVFormat csvFormat;

    /**
     * The CSV format is configured in this factory method.
     * CSV config could be further exposed into the mainProperties likewise the CSV_HEADERS.
     * out of habit we use a singleton in case this is reused... not really needed in this context.
     * @return the CSV format used for writing
     * @throws IOException
     */
    @Bean(name = "csvFormat")
    public CSVFormat createCSVFormat() throws IOException {
        if (csvFormat == null) {
            String csv_Headers = MainProperties.getMainProperty(MainProperties.CSV_HEADERS);
            String[] csvHeaderList = csv_Headers.split(",");
            csvFormat = CSVFormat.DEFAULT
                    .withHeader(csvHeaderList)
                    .withDelimiter(',')
                    .withRecordSeparator("\n")
                    .withQuoteMode(QuoteMode.NON_NUMERIC)
                    .withIgnoreEmptyLines();
        }
        return csvFormat;
    }
}
