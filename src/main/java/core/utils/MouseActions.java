package core.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class MouseActions {

	private WebDriver driver;
	private SeleniumUtils browser;
    private final Actions pointer;
	
	public MouseActions(WebDriver driver, SeleniumUtils browser) {
		this.driver = driver;
		this.browser = browser;
		this.pointer = new Actions(this.driver);
		
	}
	
	public void doubleClick(By locator) {
		WebElement element = browser.find(locator);
		//LogUtils.logInfo(element.getLocation().toString());
		pointer.doubleClick(element).build().perform();;
	}
	
	public void rightClick(By locator) {
		pointer.contextClick(browser.find(locator)).build().perform();
	}
	
	
	
}
