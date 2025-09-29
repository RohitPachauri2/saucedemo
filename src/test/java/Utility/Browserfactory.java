package Utility;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Browserfactory {

    public static WebDriver startApplication(WebDriver driver, String browser, String url) {

        if (browser.equalsIgnoreCase("Chrome")) {
            WebDriverManager.chromedriver().setup();

            // Disable Chrome password manager via preferences
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);

            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("prefs", prefs);
            options.addArguments("--disable-notifications");
            options.addArguments("--start-maximized");

            // Optional: Start with clean Chrome profile to avoid saved logins
            // options.addArguments("user-data-dir=/tmp/chrome-profile-" + System.currentTimeMillis());

            driver = new ChromeDriver(options);

        } else if (browser.equalsIgnoreCase("Firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();

        } else if (browser.equalsIgnoreCase("Edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();

        } else {
            System.out.println("Browser not supported!");
            return null;
        }

        if (driver == null) {
            System.out.println("Failed to initialize driver.");
            return null;
        }

        driver.get(url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver;
    }

    public static void quitapplication(WebDriver driver) {
        if (driver != null) {
            driver.quit();
        }
    }
}
