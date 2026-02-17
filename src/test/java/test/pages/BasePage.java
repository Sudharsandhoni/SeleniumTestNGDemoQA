package test.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import core.constants.TestGlobals;
import core.utils.MouseActions;
import core.utils.SeleniumUtils;

public abstract class BasePage {

	protected final SeleniumUtils browser;
	protected MouseActions pointer;
	private final By allLinks = By.tagName("a");
	private final By allImages = By.tagName("img");

	protected abstract By pageIdentifier();

	protected BasePage(SeleniumUtils browser) {
		this.browser = browser;

	}

	/** Lazy accessor for MouseActions */
	protected MouseActions getPointer() {
		if (pointer == null) {
			pointer = new MouseActions(TestGlobals.getDriver(), browser);
		}
		return pointer;
	}

	public void waitForPageToLoad() {
		browser.find(pageIdentifier());
	}

	public boolean isLoaded() {
		try {
			return browser.isElementDisplayed(pageIdentifier());
		} catch (Exception e) {
			return false;
		}
	}

	public List<WebElement> getAllLinks() {
		return browser.findAll(allLinks);
	}

	public List<WebElement> getAllImages() {
		return browser.findAll(allImages);
	}

}
