package test.goeuro.service;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;
import test.goeuro.MainProperties;
import test.goeuro.data.Location;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by uv on 27/11/2015 for GoEuroTest
 * Like a controller but dealing only with cmdline args
 */
@Component
class ArgumentHandlerImpl implements ArgumentHandler {
    private static final Logger LOG = Logger.getLogger(ArgumentHandlerImpl.class.getSimpleName());

    @Autowired(required = false)
    ApplicationArguments applicationArguments;

    @Autowired
    RemoteQueryService remoteQueryService;

    @Autowired
    ResultWriter resultWriter;

    @Override
    public void run(String[] args) throws IOException {
        LOG.finer( "running with " + applicationArguments.getOptionNames());

        for (String arg : args) {   // handling additional arguments is extra
            handleArg(arg);
        }
    }

    @PreDestroy
    public void sayBye() {
        LOG.log(Level.INFO, "GoodBye");
    }


    /**
     * class local visibility for easier unit testing
     */
    void handleArg(String arg) {
        try {
            if (MainProperties.PARMTYPE.JSON.equals(MainProperties.getTypeProperty(MainProperties.PARM_TYPE))) {
                useJson(arg);
            } else {
                useObjects(arg);
            }
        } catch (IOException ioe) {
            LOG.severe("Caught Exception " + ioe.getMessage());

        }
    }

    private void useJson(String arg) throws IOException {
        LOG.finer("handeArgJson: " + arg);
        JSONArray response = remoteQueryService.queryRemoteApiAsJson(arg);
        resultWriter.writeResponseAsJson(response);
    }

    private void useObjects(String arg) throws IOException {
        LOG.finer("handeArg: " + arg);
        Location[] locations = remoteQueryService.queryRemoteApi(arg);
        resultWriter.writeResponse(locations);
    }

}
