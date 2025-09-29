package TestCases;

import java.time.Duration;

import org.testng.Assert;
import org.testng.annotations.Test;

import Pages.BaseTest;
import Pages.CartPage;
import Pages.Loginpage;
import Utility.ReadExcelFile;
@Test
public class CartTestCase extends BaseTest{
	String filename=System.getProperty("user.dir")+"\\Testdata\\Book1.xlsx";
	public void carttestcase() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		Loginpage lp=new Loginpage(driver);
		String username=ReadExcelFile.getcellvalue(filename, "Logindata", 0, 0);
		String password=ReadExcelFile.getcellvalue(filename, "Logindata", 0, 1);
		
		try {
			lp.login(username, password);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CartPage cp=new CartPage(driver);
		try {
			cp.addtocart();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;
		lp.logout();
		
		
	}

}
