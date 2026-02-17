package test.pages.demoQA;

import org.openqa.selenium.By;

import core.exceptions.CustomException;
import core.exceptions.CustomTimeoutException;
import core.utils.ConfigReader;
import core.utils.SeleniumUtils;
import test.pages.BasePage;

public class RadioButtonPage extends BasePage{
	
	private By pageTitle = By.xpath("//h1[text()='Radio Button']");
	private String radioBtnXpathString = "//label[normalize-space()='%s']";
	private By successMessage = By.xpath("//span[@class='text-success']");
	
	
	public RadioButtonPage(SeleniumUtils browser) {
		super(browser);
	}
	@Override
	protected By pageIdentifier() {
		return pageTitle;
	}
	
	public void launch() throws CustomException {
		browser.get(ConfigReader.getPropertyValue("BASE_URL_DEMO_QA") + "/radio-button");
	}
	
	
	public void clickRadionButton(String name) throws CustomException, CustomTimeoutException {
		if(name != null && name != "") {
			browser.click(By.xpath(String.format(radioBtnXpathString, name)));
		}
	}
	
	public String getSuccessMessage() throws CustomTimeoutException {
		return browser.getText(successMessage);
	}
	
	
	
	

}
