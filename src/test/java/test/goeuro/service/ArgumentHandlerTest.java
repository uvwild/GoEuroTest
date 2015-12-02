package test.goeuro.service;

import org.junit.Test;
import test.goeuro.common.BaseTest;

import static org.mockito.Mockito.*;

/**
 * Created by uv on 27/11/2015 for GoEuroTest
 * This Test does not need the spring context because we can instantiate our bean with mocks
 */
@SuppressWarnings("unused")
public class ArgumentHandlerTest extends BaseTest {

    private final ArgumentHandlerImpl argumentHandler = new ArgumentHandlerImpl();

    @Test
    public void testHandleArg() throws Exception {
        argumentHandler.remoteQueryService = mock(RemoteQueryService.class);
        argumentHandler.resultWriter = mock(ResultWriter.class);
        when(argumentHandler.remoteQueryService.queryRemoteApiAsJson(testArg)).thenReturn(jsonResponse);
        argumentHandler.handleArg(testArg);
        verify(argumentHandler.resultWriter, times(1)).writeResponseAsJson(jsonResponse);
    }
}
