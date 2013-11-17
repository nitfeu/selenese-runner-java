package jp.vmi.selenium.selenese;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.thoughtworks.selenium.SeleniumException;

import jp.vmi.selenium.selenese.result.Error;
import jp.vmi.selenium.selenese.result.Success;
import jp.vmi.selenium.testutils.TestCaseTestBase;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Driver independent test.
 */
@SuppressWarnings("javadoc")
public class DriverIndependentTest extends TestCaseTestBase {

    protected final FilenameFilter pngFilter = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(".png");
        }
    };

    @Before
    public void initialize() {
        driver = new HtmlUnitDriver(true);
    }

    @Test
    public void testFlowControl() throws IllegalArgumentException {
        execute("flowControl");
        assertThat(xmlResult, containsString("[Finished with x = 15 and y = 14]"));
    }

    @Test
    public void testForEach() throws IllegalArgumentException {
        execute("forEach");
        assertThat(xmlResult, containsString("[chedder]"));
        assertThat(xmlResult, containsString("[edam]"));
        assertThat(xmlResult, containsString("[swiss]"));
    }

    @Test
    public void noCommandSelenese() throws IllegalArgumentException {
        execute("noCommand");
        assertThat(result, is(instanceOf(Success.class)));
    }

    @Test
    public void verifyNotText() throws IllegalArgumentException {
        execute("VerifyNotText");
        assertThat(result, is(instanceOf(Success.class)));
    }

    @Test(expected = SeleniumException.class)
    public void invalidCommandInHtml() throws IllegalArgumentException {
        execute("invalidCommand");
    }

    @Test
    public void pauseCommand() throws IllegalArgumentException {
        StopWatch sw = new StopWatch();
        sw.start();
        execute("pause");
        sw.stop();
        assertThat(result, is(instanceOf(Success.class)));
        assertThat(sw.getTime(), is(greaterThanOrEqualTo(2900L)));
    }

    @Test
    public void setSpeed() throws IllegalArgumentException {
        StopWatch sw = new StopWatch();
        sw.start();
        execute("setSpeed");
        sw.stop();
        assertThat(result, is(instanceOf(Success.class)));
        assertThat(sw.getTime(), is(greaterThanOrEqualTo(2900L)));
    }

    @Test
    public void emptyFile() throws IOException {
        execute("empty");
        assertThat(result, is(instanceOf(Error.class)));
        assertThat(result.getMessage(), containsString("Not selenese script."));
    }

    /**
     * Return error result if test-case is not found in test-suite.
     * (Don't throw Exception) 
     *
     * @throws FileNotFoundException
     */
    @Test
    public void issue75() throws FileNotFoundException {
        execute("issue75");
        assertThat(result, is(instanceOf(Error.class)));
        assertThat(result.getMessage(), containsString("notFound.html"));
    }
}
