package Pages;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import Utility.Browserfactory;
import Utility.dataprovider;

public class BaseTest {
    public WebDriver driver;
    public dataprovider dp = new dataprovider();

    @BeforeClass
    public void setup(ITestContext context) {
        driver = Browserfactory.startApplication(driver, dp.getbrowser(), dp.geturl());

        // ✅ Set the driver in the context so the listener can access it
        context.setAttribute("driver", driver);
    }

    @AfterClass
    public void close() {
        if (driver != null) {
            Browserfactory.quitapplication(driver);
        } else {
            System.out.println("Driver is null, skipping quit.");
        }
    }
}
