package test.goeuro.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.goeuro.common.SpringBaseTest;

import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Created by uv on 27/11/2015 for GoEuroTest
 * This Test uses the spring context to use autowire the bean!
 */
@SuppressWarnings("unused")
public class ArgumentHandlerAutowiredTest extends SpringBaseTest {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    ArgumentHandlerImpl argumentHandler;

    @Test
    public void testHandleArg() throws Exception {
        assertNotNull(argumentHandler);
        argumentHandler.remoteQueryService = mock(RemoteQueryService.class);
        argumentHandler.resultWriter = mock(ResultWriter.class);
        when(argumentHandler.remoteQueryService.queryRemoteApiAsJson(testArg)).thenReturn(jsonResponse);
        argumentHandler.handleArg(testArg);
        verify(argumentHandler.resultWriter, times(1)).writeResponseAsJson(jsonResponse);
    }
}
