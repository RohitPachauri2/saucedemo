package Utility;

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

public class ITestListnerClass implements ITestListener {

    private ExtentSparkReporter htmlReporter;
    private ExtentReports reports;
    private ExtentTest test;
    private WebDriver driver;  // Make sure WebDriver is properly passed.

    // Configuring the report
    public void configureReport() {
    	htmlReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/ExtentListenerReportDemo1.html");

        reports = new ExtentReports();
        reports.attachReporter(htmlReporter);

        // Add system information/environment info to reports
        reports.setSystemInfo("Machine", "PC");
        reports.setSystemInfo("OS", "Windows 10");

        // Customize report details
        htmlReporter.config().setDocumentTitle("Extent Listener Report Demo");
        htmlReporter.config().setReportName("This is my first Report");
        htmlReporter.config().setTheme(Theme.DARK);
    }

    @Override
    public void onStart(ITestContext context) {
        configureReport();
        System.out.println("On start method invoked.....");
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("On Finished method invoked.....");
        reports.flush(); // Make sure to flush the reports to save data
    }

    @Override
    public void onTestStart(ITestResult result) {
        // Get WebDriver instance from the test context
        driver = (WebDriver) result.getTestContext().getAttribute("driver");
        System.out.println("Test starting: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test passed: " + result.getName());
        test = reports.createTest(result.getName());
        test.log(Status.PASS, MarkupHelper.createLabel("Test case passed: " + result.getName(), ExtentColor.GREEN));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test failed: " + result.getName());
        
        // Create a test entry in the Extent report for failed test
        test = reports.createTest(result.getName());
        test.log(Status.FAIL, MarkupHelper.createLabel("Test case failed: " + result.getName(), ExtentColor.RED));

        // Handle screenshot capture when a test fails
        if (driver != null) {
        	String screenshotPath = System.getProperty("user.dir") + File.separator + "Screenshots" + File.separator + result.getName() + ".png";

            System.out.println("Screenshot Path: " + screenshotPath);

            File screenShotFile = new File(screenshotPath);
            File parentDir = new File(screenShotFile.getParent());

            if (!parentDir.exists()) {
                parentDir.mkdirs(); // Create directories if they don't exist
                System.out.println("Directory created: " + parentDir.getAbsolutePath());
            }

            try {
                // Capture the screenshot and save it to the specified location
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(screenshot, screenShotFile);
                System.out.println("Screenshot saved to: " + screenShotFile.getAbsolutePath());

                // Add the screenshot to the Extent report
                test.fail("Captured Screenshot: " + test.addScreenCaptureFromPath(screenshotPath));
            } catch (Exception e) {
                // Log error if screenshot capture fails
                test.fail("Screenshot could not be captured: " + e.getMessage());
                System.out.println("Error while saving screenshot: " + e.getMessage());
            }
        } else {
            System.out.println("WebDriver instance is null, unable to capture screenshot.");
        }
       
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Test skipped: " + result.getName());
        test = reports.createTest(result.getName());
        test.log(Status.SKIP, MarkupHelper.createLabel("Test case skipped: " + result.getName(), ExtentColor.YELLOW));
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Can be used to handle tests that fail but are within success percentage
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        onTestFailure(result); // Same behavior for timeout as failure
    }
}
