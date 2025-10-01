package TestCases;

import java.time.Duration;
import org.testng.Assert;
import org.testng.annotations.Test;
import Pages.BaseTest;
import Pages.CartPage;
import Pages.CheckoutPage;
import Pages.Loginpage;
import Utility.ReadExcelFile;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

@Test
public class CheckoutTestCase extends BaseTest {
    String filepath = "C:\\Users\\PC\\git\\repository2\\Testdata\\Book1.xlsx";

    public void checkoutcase() throws InterruptedException {
    	
    	private ExtentSparkReporter htmlReporter;
        private ExtentReports reports;
        private ExtentTest test;
        private WebDriver driver;  // Make sure WebDriver is properly passed.

        // Configuring the report
        public void configureReport1() {
        	htmlReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/ExtentListenerReportDemo2.html");

            reports = new ExtentReports();
            reports.attachReporter(htmlReporter);

            // Add system information/environment info to reports
            reports.setSystemInfo("Machine", "PC");
            reports.setSystemInfo("OS", "Windows 10");

            // Customize report details
            htmlReporter.config().setDocumentTitle("Extent Listener Report Demo");
            htmlReporter.config().setReportName("This is my secong Report");
            htmlReporter.config().setTheme(Theme.DARK);
        }
        configureReport1();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
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
                try {
                    cp.addtocart();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Assert.fail("Error while adding items to cart for user: " + username);
                }

                CheckoutPage cp1 = new CheckoutPage(driver);
                cp1.checkout();
                Assert.assertTrue(driver.getCurrentUrl().contains("checkout-complete"), "Checkout failed for user: " + username);

                System.out.println("Checkout completed successfully for: " + username);
                lp.logout();
                passedTests++;  // Increment passed test counter
                test.log(Status.PASS, MarkupHelper.createLabel("Test case passed: " + username, ExtentColor.GREEN));

            } catch (AssertionError e) {
                // Log the failure and continue with the next iteration
                System.out.println("Test failed for user: " + username + " -> " + e.getMessage());
                failedTests++;  // Increment failed test counter
                test.log(Status.FAIL, MarkupHelper.createLabel("Test case passed: " + username, ExtentColor.RED));
            }

            // Optional: Adding a small wait to avoid rapid successive logins
            Thread.sleep(1000);
        }

        // Log the summary after all tests are complete
        System.out.println("Test Summary: ");
        System.out.println("Total Tests: 4");
        System.out.println("Passed: " + passedTests);
        System.out.println("Failed: " + failedTests);
        reports.flush();
    }
}
