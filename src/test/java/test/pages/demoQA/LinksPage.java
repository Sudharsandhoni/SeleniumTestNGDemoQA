package test.pages.demoQA;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import core.exceptions.CustomException;
import core.utils.ConfigReader;
import core.utils.SeleniumUtils;
import test.pages.BasePage;

public class LinksPage extends BasePage {

	public LinksPage(SeleniumUtils browser) {
		super(browser);
	}

	public void launch() {
		browser.get(ConfigReader.getPropertyValue("BASE_URL_DEMO_QA") + "/links");
	}

	private By homeLink = By.linkText("Home");
	private By homeLink2 = By.id("dynamicLink");
	private By pageTitle = By.xpath("//h1[text()='Links']");
	private final By createdLink = By.id("created");
	private final By responseText = By.id("linkResponse");
	private final By noContentLink = By.id("no-content");
	private final By movedLink = By.id("moved");
	private final By badRequestLink = By.id("bad-request");
	private final By unauthorizedLink = By.id("unauthorized");
	private final By forbiddenLink = By.id("forbidden");
	private final By notFoundLink = By.id("invalid-url");

	@Override
	protected By pageIdentifier() {
		return pageTitle;
	}

	public record ApiLink(String name, Runnable clickAction, int status, String statusText) {
	}

	public void clickOnHomeLink() {
		browser.click(homeLink);
	}

	public void clickOnHomeLink2() {
		browser.click(homeLink2);
	}

	public void clickCreatedLink() {
		browser.click(createdLink);
	}

	public void clickNoContentLink() {
		browser.click(noContentLink);
	}

	public void clickMovedLink() {
		browser.click(movedLink);
	}

	public void clickBadRequestLink() {
		browser.click(badRequestLink);
	}

	public void clickUnauthorizedLink() {
		browser.click(unauthorizedLink);
	}

	public void clickForbiddenLink() {
		browser.click(forbiddenLink);
	}

	public void clickNotFoundLink() {
		browser.click(notFoundLink);
	}

	public WebElement getLinkReponseElement() {
		return browser.waitForVisibility(responseText);
	}

	public String waitForTextChaneAndGetText(WebElement element) {
		browser.waitForTextChanged(element);
		return element.getText();

	}

	public String waitForTextChaneAndGetText(WebElement element, String oldText) {
		browser.waitForTextChanged(element, oldText);
		return element.getText();

	}

	public void validateHomeLinkNavigation() {
		openAndValidateHome(() -> clickOnHomeLink());
	}

	public void validateHomeLink2Navigation() {
		openAndValidateHome(() -> clickOnHomeLink2());
	}

	private void openAndValidateHome(Runnable clickAction) {
		String parentWindow = browser.getWindowHandle();

		clickAction.run();
		browser.switchToNewWindow(parentWindow);

		HomePage home = new HomePage(browser);
		Assert.assertTrue(home.isLoaded(), "Home page not loaded");
		Assert.assertEquals(browser.getUrl(), "https://demoqa.com/");

		browser.closeCurrentWindow();
		browser.switchToWindow(parentWindow);
	}

	public List<ApiLink> getApiLinks() {
		return List.of(new ApiLink("Created", this::clickCreatedLink, 201, "Created"),
				new ApiLink("No Content", this::clickNoContentLink, 204, "No Content"),
				new ApiLink("Moved Permanently", this::clickMovedLink, 301, "Moved Permanently"),
				new ApiLink("Bad Request", this::clickBadRequestLink, 400, "Bad Request"),
				new ApiLink("Unauthorized", this::clickUnauthorizedLink, 401, "Unauthorized"),
				new ApiLink("Forbidden", this::clickForbiddenLink, 403, "Forbidden"),
				new ApiLink("Not Found", this::clickNotFoundLink, 404, "Not Found"));
	}

	public void validateApiLink(ApiLink link) {

	    boolean responseAlreadyVisible = browser.isElementDisplayed(responseText, 3);

	    String oldText = null;
	    WebElement responseElement = null;

	    if (responseAlreadyVisible) {
	        responseElement = browser.waitForVisibility(responseText);
	        oldText = responseElement.getText();
	    }

	    // Click API link
	    link.clickAction().run();

	    // Handle first appearance vs text change
	    if (!responseAlreadyVisible) {
	        responseElement = browser.waitForVisibility(responseText);
	    } else {
	        browser.waitForTextChanged(responseElement, oldText);
	    }

	    String expectedMessage = String.format(
	            "Link has responded with staus %d and status text %s",
	            link.status(),
	            link.statusText()
	    );

	    Assert.assertEquals(responseElement.getText(), expectedMessage);
	}
}
