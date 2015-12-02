package test.goeuro.common;

import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 * this enables the spring context for tests using it by inheriting from this class.
 * Created by uv on 27/11/2015 for GoEuroTest
 */
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class})
@EnableAutoConfiguration
public abstract class SpringBaseTest extends BaseTest {

    @SuppressWarnings({"WeakerAccess", "unused"})   // with weaker access the spring configuration seems to break
    @ComponentScan(basePackages = { "test.goeuro.service", "test.goeuro.factories", "test.goeuro.common"})
    @Configuration
    public static class TestConfiguration  {
    }
}
