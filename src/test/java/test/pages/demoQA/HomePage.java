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
	private final By checkBox = By.xpath("//span[text()='Check Box']");
	private final By radioButton = By.xpath("//span[text()='Radio Button']");
	private final By webTables = By.xpath("//span[text()='Web Tables']");
	private final By buttons = By.xpath("//span[text()='Buttons']");
	private final By links = By.xpath("//span[text()='Links']");
	private final By brokenLinksImages = By.xpath("//span[text()='Broken Links - Images']");
	private final By uploadAndDownload = By.xpath("//span[text()='Upload and Download']");
	private final By dynamicProperties = By.xpath("//span[text()='Dynamic Properties']");
	
	
	public HomePage(SeleniumUtils browser){
		super(browser);
	}
		
	public void launch() {
		browser.get(ConfigReader.getPropertyValue("BASE_URL_DEMO_QA"));
	}
	
    @Override
    protected By pageIdentifier() {
        return By.cssSelector("img[class='banner-image']"); // unique Home element
    }

	public void clickElementsCard() {
		browser.clickTakescreenshotBeforeAction(elementsCard);
	}
	
	public void clickTextBoxText() {
		browser.click(textBox);
	}
	
	public void clickCheckBoxText() {
		browser.click(checkBox);
	}
	public void clickRadioButtonText() {
		browser.click(radioButton);
	}
	public void clickWebTablesText() {
		browser.click(webTables);
	}
	public void clickLinksText() {
		browser.click(links);
	}
	public void clickBrokenLinksImagesText() {
		browser.click(brokenLinksImages);
	}
	public void clickButtonsText() {
		browser.click(buttons);
	}
	public void clickUploadAndDownloadText() {
		browser.click(uploadAndDownload);
	}
	public void clickDynamicPropertiesText() {
		browser.click(dynamicProperties);
	}
}
