package test.goeuro.service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import test.goeuro.MainProperties;
import test.goeuro.common.BaseTest;
import test.goeuro.factories.CSVFormatFactory;
import test.goeuro.factories.ObjectMapperFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by uv on 01/12/2015 for GoEuroTest
 */
@SuppressWarnings("unused")
public class ResultWriterCSVImplTest extends BaseTest {

    //  not using spring Autowire for unit test speed
    @SuppressWarnings("FieldCanBeLocal")
    private ResultWriter resultWriter;


    @Test
    public void testCreateResponse() throws IOException {
        // we need to manually wire the beans
        resultWriter = new ResultWriterCSVImpl(new CSVFormatFactory().createCSVFormat(),new ObjectMapperFactory().createObjectMapper());
        resultWriter.writeResponseAsJson(jsonResponse);
        File resultFile = new File(MainProperties.getMainProperty(MainProperties.RESULT_FILENAME));

        try {
            assertTrue(resultFile.exists());
            @SuppressWarnings("unchecked") List<String> fileContent = FileUtils.readLines(resultFile);
            testCsvSize(fileContent);
            testCsvHeader(fileContent);
            testFirstDataLine(fileContent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // remove testFile
            resultFile.deleteOnExit();
        }
    }

    private void testFirstDataLine(List<String> fileContent) {
        String firstDataLine = fileContent.get(1);
        assertEquals(csvFirstDataLine, firstDataLine);
    }


    private void testCsvSize(List<String> fileContent) throws Exception {
        // result size plus header
        assertEquals(fileContent.size(), jsonResponse.length() + 1);
    }

    private void testCsvHeader(List<String> fileContent) throws Exception {
        // check header Line
        String header = fileContent.get(0);
        // header = removeQuotes(header);
        assertEquals(csvHeaderLine, header);
    }

    // to undo quoting when needed
    private String removeQuotes(String header) {
        String[] fieldList = header.split(",");
        String[] unquotedList = new String[fieldList.length];
        int i = 0;
        for (String field : fieldList) {
            if (field.startsWith("\"") && field.endsWith("\"")) {
                int length = field.length();
                unquotedList[i++] = field.substring(1, length - 1);  // remove quotes
            }
        }
        return StringUtils.join(unquotedList, ",");
    }

}
