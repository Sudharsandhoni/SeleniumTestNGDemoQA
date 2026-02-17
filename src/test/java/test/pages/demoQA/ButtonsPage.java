package test.pages.demoQA;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import core.exceptions.CustomException;
import core.exceptions.CustomTimeoutException;
import core.utils.ConfigReader;
import core.utils.SeleniumUtils;
import test.pages.BasePage;

public class ButtonsPage extends BasePage{

	private By pageTitle = By.xpath("//h1[text()='Buttons']");
	private By btnDoubleClick = By.id("doubleClickBtn");
	private By btnRightClick = By.id("rightClickBtn");
	private By btntClickDynamicId = By.xpath("//button[normalize-space()='Click Me']");
	private By doubleClickedMessage = By.xpath("//p[normalize-space()='You have done a double click']");
	private By rightClickedMessage = By.xpath("//p[normalize-space()='You have done a right click']");
	private By dynamicButtonClickedMessage = By.xpath("//p[normalize-space()='You have done a dynamic click']");
	

	public ButtonsPage(SeleniumUtils browser) {
		super(browser);
	}
	
	@Override
	protected By pageIdentifier() {
		return pageTitle;
	}

	public void launch() throws CustomException {
		browser.get(ConfigReader.getPropertyValue("BASE_URL_DEMO_QA") + "/buttons");
		
	}

	public void clickDynamicButton() throws CustomException, CustomTimeoutException {
		browser.click(btntClickDynamicId);
	}

	public void doubleClickButton() {
		getPointer().doubleClick(btnDoubleClick);
	}

	public void rightClickButton() {
		getPointer().rightClick(btnRightClick);
	}
	
	public boolean isDoubleClickedMessageDisplayed() {
		return browser.isElementDisplayed(doubleClickedMessage);
	}
	
	public boolean isRightClickedMessageDisplayed() {
		return browser.isElementDisplayed(rightClickedMessage);
	}
	
	public boolean isDyanmicButtonClickedMessageDisplayed() {
		return browser.isElementDisplayed(dynamicButtonClickedMessage);
	}


}
