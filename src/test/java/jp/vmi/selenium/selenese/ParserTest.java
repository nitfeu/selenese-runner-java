package jp.vmi.selenium.selenese;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

import jp.vmi.selenium.testutils.TestBase;
import jp.vmi.selenium.webdriver.DriverOptions;
import jp.vmi.selenium.webdriver.WebDriverManager;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@SuppressWarnings("javadoc")
public class ParserTest extends TestBase {

    private static final String WITHOUT_BASE_URL = "/selenese/withoutBaseURL.html";
    private static final String TEST_SUITE = "/selenese/testSuite.html";

    @Before
    public void setup() {
        setWebDriverFactory(WebDriverManager.HTMLUNIT, new DriverOptions());
    }

    @Test
    public void parseTestCaseWithoutBaseURL() {
        Runner runner = new Runner();
        runner.setDriver(manager.get());
        try (InputStream is = getClass().getResourceAsStream(WITHOUT_BASE_URL)) {
            Selenese selenese = Parser.parse(WITHOUT_BASE_URL, is, runner.getCommandFactory());
            assertThat(selenese, is(instanceOf(TestCase.class)));
        } catch (IOException e) {
            // no operation.
        }
    }

    @Test
    public void parseTestSuite() {
        Runner runner = new Runner();
        runner.setDriver(manager.get());
        try (InputStream is = getClass().getResourceAsStream(TEST_SUITE)) {
            Selenese selenese = Parser.parse(TEST_SUITE, is, runner.getCommandFactory());
            assertThat(selenese, is(instanceOf(TestSuite.class)));
        } catch (IOException e) {
            // no operation.
        }
    }
}
