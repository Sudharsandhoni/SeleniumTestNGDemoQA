package test.pages.demoQA;

import org.openqa.selenium.By;
import core.exceptions.CustomException;
import core.exceptions.CustomTimeoutException;
import core.utils.ConfigReader;
import core.utils.SeleniumUtils;
import test.pages.BasePage;


public class HomePage extends BasePage{

	private final By elementsCard = By.xpath("//h5[text()='Elements']");
	private final By textBox = By.xpath("//span[text()='Text Box']");
	
	public HomePage(SeleniumUtils browser){
		super(browser);
	}
		
	public void launch() throws CustomException {
		browser.get(ConfigReader.getPropertyValue("BASE_URL_DEMO_QA"));
	}
	
    @Override
    protected By pageIdentifier() {
        return By.cssSelector("img[class='banner-image']"); // unique Home element
    }

	public void clickElementsCard() throws CustomException, CustomTimeoutException {
		browser.clickTakescreenshotBeforeAction(elementsCard);
	}
	
	public void clickTextBoxText() throws CustomException, CustomTimeoutException {
		browser.click(textBox);
	}
	
}
