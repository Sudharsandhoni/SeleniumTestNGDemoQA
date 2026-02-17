package test.pages.demoQA;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import core.exceptions.CustomException;
import core.exceptions.CustomTimeoutException;
import core.utils.ConfigReader;
import core.utils.SeleniumUtils;
import test.pages.BasePage;
import tests.data.demoQA.TextBoxForm;
public class TextBoxPage extends BasePage{

	private By pageTitle = By.xpath("//h1[text()='Text Box']");
	private final By fullName = By.id("userName");
	private final By emailAddress = By.id("userEmail");
	private final By currentAddress = By.id("currentAddress");
	private final By permanentAddress = By.id("permanentAddress");
	private final By output = By.id("output");
	private final By submit = By.id("submit");
	
	public TextBoxPage(SeleniumUtils browser){
		super(browser);
	}
	
	@Override
	protected By pageIdentifier() {
		return pageTitle;
	}
	
	public void lauch() {
		browser.get(String.format(ConfigReader.getPropertyValue("BASE_URL_DEMO_QA"), "/text-box"));
	}

	public void fillTheFormAndSubmit(TextBoxForm form) throws CustomException, CustomTimeoutException {
		browser.type(fullName, form.getFullName());
		browser.type(emailAddress, form.getEmail());
		browser.type(currentAddress, form.getCurrentAddress());
		browser.type(permanentAddress, form.getPermanentAddress());
		browser.clickTakescreenshotBeforeAction(submit);
		
	};
	
	
	public String getOutputText() throws CustomTimeoutException {
		return browser.getText(output).trim();
	}
	
	

}
