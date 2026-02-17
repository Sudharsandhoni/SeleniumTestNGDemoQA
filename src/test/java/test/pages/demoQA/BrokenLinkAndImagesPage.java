package test.pages.demoQA;

import org.openqa.selenium.By;

import core.utils.ConfigReader;
import core.utils.SeleniumUtils;
import test.pages.BasePage;

public class BrokenLinkAndImagesPage extends BasePage {
	
	private By pageTitle = By.xpath("//h1[text()='Broken Links - Images']");
	
	public BrokenLinkAndImagesPage(SeleniumUtils browser) {
		super(browser);
	}

	public void launch() {
		browser.get(ConfigReader.getPropertyValue("BASE_URL_DEMO_QA") + "/broken");
	}

	@Override
	protected By pageIdentifier() {
		return pageTitle;
	}

}
