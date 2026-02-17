package test.pages.demoQA;

import org.openqa.selenium.By;

import core.utils.ConfigReader;
import core.utils.SeleniumUtils;
import test.pages.BasePage;

public class UploadAndDownloadPage extends BasePage{

	private By pageTitle = By.xpath("//h1[text()='Upload and Download']");
	private By downloadButton = By.id("downloadButton");
	private By uploadFileInput = By.id("uploadFile");
	private By uploadedFilePath = By.id("uploadedFilePath");
	
	public UploadAndDownloadPage(SeleniumUtils browser) {
		super(browser);
	}

	@Override
	protected By pageIdentifier() {
		return pageTitle;
	}
	

	public void launch() {
		browser.get(ConfigReader.getPropertyValue("BASE_URL_DEMO_QA") + "/upload-download");
	}
	
	public void clickDownloadButton() {
		browser.click(downloadButton);
	}
	
	public void uploadDocument(String filePath) {
		browser.type(uploadFileInput, filePath);
	}
	
	public String getUploadedFilepath() {
		return browser.getText(uploadedFilePath);
	}
	
	
	

}
