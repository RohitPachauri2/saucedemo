package TestCases;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Pages.Loginpage;
import Utility.ReadExcelFile;

import Pages.BaseTest;
import Pages.Loginpage;

public class Logintestcase extends BaseTest{
	@DataProvider(name = "loginData")
    public Object[][] logindataprovider() throws Exception {
        String filename = "C:\\Users\\PC\\eclipse-workspace\\saucedemo\\Testdata\\Book1.xlsx";
        String sheetname = "Logindata";
        
        // Ensure the row count is valid
        int rowCount = ReadExcelFile.getrowcount(filename, sheetname);
        
        if (rowCount < 0) {
            throw new IOException("No rows found in the sheet");
        }

        // Get the number of columns (fields in the sheet)
        int columnCount = ReadExcelFile.getcolumncount(filename, sheetname);
        
        if (columnCount <0) {
            throw new IOException("No columns found in the sheet");
        }

        // Create the data array
        Object[][] loginData = new Object[rowCount][columnCount];

        // Populate the array with data from the Excel sheet
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                loginData[row][col] = ReadExcelFile.getcellvalue(filename, sheetname, row, col);
            }
        }
        
        return loginData;
    }

    @Test(priority=1,dataProvider = "loginData")
    public void verifylogin(String username, String password) throws InterruptedException {
   
    	Loginpage lp=new Loginpage(driver);
    	lp.login(username, password);
        // Your test code for login verification using username and password
    	
    	
        System.out.println("Username: " + username + " | Password: " + password);
        lp.logout();
        
        // Implement actual login logic here
    }
}
