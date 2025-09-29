package Pages;


import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Loginpage {

	public WebDriver driver;
	public Loginpage(WebDriver driver) {
		
		this.driver=driver;
		PageFactory.initElements(driver, this);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		
	}
	
	@FindBy(xpath="//input[@id=\"user-name\"]") WebElement username;
	@FindBy(css="input[id='password']")WebElement pass;
	@FindBy(id="login-button") WebElement submit;
	@FindBy(css="div.bm-burger-button>button") WebElement menu;
	@FindBy(css="a[id=\"logout_sidebar_link\"]") WebElement logout;
	
	public boolean login(String t_user, String t_pass) throws InterruptedException {
	    username.sendKeys(t_user);
	    pass.sendKeys(t_pass);
	    submit.click();
	    
	    // Wait for login to complete, could be a better waiting condition based on the app
	    Thread.sleep(2000);
	    
	    // Check if the login is successful by verifying the presence of an element in the home page
	    try {
	        // Add a condition that checks for a successful login, like an element that's present after login.
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        wait.until(ExpectedConditions.urlContains("inventory"));
	        return true;
	    } catch (Exception e) {
	    	username.clear();
	    	pass.clear();
	        return false;
	    }
	}

	public void logout() {
		
		menu.click();
		logout.click();
		try {
			Thread.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
