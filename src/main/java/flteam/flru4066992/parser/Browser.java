package flteam.flru4066992.parser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Browser {

    private static WebDriver webDriver = new ChromeDriver(getChromeOptions());

    private static ChromeOptions getChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        return chromeOptions;
    }

    public static synchronized WebDriver instance() {
        return webDriver;
    }

    /**
     * Mandatory invoke this before closing app!
     */
    public synchronized static void close() {
        webDriver.quit();
    }

    private Browser() {
    }

}
