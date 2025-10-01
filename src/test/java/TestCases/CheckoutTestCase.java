package TestCases;

import java.time.Duration;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import Pages.BaseTest;
import Pages.CartPage;
import Pages.CheckoutPage;
import Pages.Loginpage;
import Utility.ReadExcelFile;
import org.openqa.selenium.WebDriver;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class CheckoutTestCase extends BaseTest {
    String filepath = "C:\\Users\\PC\\git\\repository2\\Testdata\\Book1.xlsx";
    private ExtentReports reports;
    private ExtentTest test;
    private WebDriver driver;

    // Initialize the Extent Reports and WebDriver
    @BeforeClass
    public void setup() {
        // Configure Extent Report
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/ExtentListenerReportDemo2.html");
        reports = new ExtentReports();
        reports.attachReporter(htmlReporter);
        reports.setSystemInfo("Machine", "PC");
        reports.setSystemInfo("OS", "Windows 10");
        htmlReporter.config().setDocumentTitle("Extent Listener Report Demo");
        htmlReporter.config().setReportName("Checkout Test Report");
        htmlReporter.config().setTheme(Theme.DARK);
        
        // Initialize WebDriver (Assuming driver is initialized in BaseTest)
        driver = getDriver(); // Ensure this method is available in BaseTest
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
    }

    @Test
    public void checkoutcase() throws InterruptedException {
        Loginpage lp = new Loginpage(driver);
        int passedTests = 0;   // Counter for passed tests
        int failedTests = 0;   // Counter for failed tests

        for (int i = 0; i < 4; i++) {
            String username = ReadExcelFile.getcellvalue(filepath, "Logindata", i, 0);
            String password = ReadExcelFile.getcellvalue(filepath, "Logindata", i, 1);

            try {
                // Try to login
                boolean loginSuccessful = lp.login(username, password);
                Assert.assertTrue(loginSuccessful, "Login failed for user: " + username);

                // Proceed if login is successful
                CartPage cp = new CartPage(driver);
                cp.addtocart();

                CheckoutPage cp1 = new CheckoutPage(driver);
                cp1.checkout();
                Assert.assertTrue(driver.getCurrentUrl().contains("checkout-complete"), "Checkout failed for user: " + username);

                System.out.println("Checkout completed successfully for: " + username);
                lp.logout();

                passedTests++;
                test.log(Status.PASS, MarkupHelper.createLabel("Test case passed for user: " + username, ExtentColor.GREEN));

            } catch (AssertionError e) {
                failedTests++;
                test.log(Status.FAIL, MarkupHelper.createLabel("Test case failed for user: " + username, ExtentColor.RED));
            }

            Thread.sleep(1000); // Small wait to avoid rapid logins
        }

        // Log the summary after all tests are complete
        System.out.println("Test Summary: ");
        System.out.println("Total Tests: 4");
        System.out.println("Passed: " + passedTests);
        System.out.println("Failed: " + failedTests);
    }

    @AfterClass
    public void tearDown() {
        // Finalize Extent Report
        reports.flush();
        // Close WebDriver if needed
        if (driver != null) {
            driver.quit();
        }
    }
}
