package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage {
	public WebDriver driver;

	public CartPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(css = "div.inventory_item:nth-child(1) > div:nth-child(3) > button:nth-child(2)")
	WebElement element1;

	@FindBy(css = "div.inventory_item:nth-child(3) > div:nth-child(3) > button:nth-child(2)")
	WebElement element2;
	@FindBy(css = "svg[data-icon=\"shopping-cart\"]")
	WebElement cartbutton;
	@FindBy(css = "a.btn_action.checkout_button")
	WebElement checkout;

	public void addtocart() throws InterruptedException {
		
		element1.click();
		element2.click();
		element1.click();
		cartbutton.click();
		Actions action = new Actions(driver);
		action.scrollToElement(checkout);
		checkout.click();

	}

}
