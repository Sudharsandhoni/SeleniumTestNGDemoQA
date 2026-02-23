package test.pages.demoQA;

import org.openqa.selenium.By;

import core.utils.SeleniumUtils;
import test.pages.BasePage;

public class DynamicPropertiesPage extends BasePage{
	
	private By title = By.xpath("//h1[normalize-space()='Dynamic Properties']");
	private By dynamicIDText = By.xpath("//div//p");
	private By dynamicallyEnabledButton = By.id("enableAfter");
	private By dynamicColorChangeButton = By.id("colorChange");
	private By dynamicVisibleButton = By.id("visibleAfter");
	
	

	public DynamicPropertiesPage(SeleniumUtils browser) {
		super(browser);
	}

	@Override
	protected By pageIdentifier() {
		return title;
	}
	
	public String getDynamicIDText() {
		return browser.getText(dynamicIDText);
	}
	
	public void clickDynamicallyEnabledButton() {
		browser.click(dynamicallyEnabledButton);
	} 

	public void clickDynamicallyVisibleButton() {
		browser.click(dynamicVisibleButton);
	}
	public void waitForButtonColorChange() {
		browser.waitForAttributeChanged(dynamicColorChangeButton, "class", "danger", 10);
	}
}
