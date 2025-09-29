package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage {
	public WebDriver driver;

	public CheckoutPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//input[@id='first-name']")
	WebElement address1;
	@FindBy(xpath = "//input[@id='last-name']")
	WebElement address2;
	@FindBy(css = "input[id='postal-code']")
	WebElement postcode;
	@FindBy(xpath = "//input[@class='btn_primary cart_button']")
	WebElement cutbutton;
	@FindBy(css = "a[class='btn_action cart_button']")
	WebElement finishbut;

	public void checkout() {
		address1.sendKeys("this is sample address1");
		address2.sendKeys("this is for sample address2");
		postcode.sendKeys("201789");
		cutbutton.click();
		finishbut.click();
	}

}
