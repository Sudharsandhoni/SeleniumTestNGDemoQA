package tests.demoQA;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import core.constants.TestGlobals;
import core.utils.ImageValidator;
import core.utils.LinkValidator;
import test.pages.demoQA.BrokenLinkAndImagesPage;
import tests.BaseTest;

public class BrokenLinksAndImagesTest extends BaseTest {

	@Test
	public void validateNavigableLinks() {
		BrokenLinkAndImagesPage brokenLinkAndImagesPage = new BrokenLinkAndImagesPage(browser);

		testReporter.step("Launch Broken Links And Images Page", brokenLinkAndImagesPage::launch);

		List<WebElement> links = testReporter.step("Get All Links from Broken Links And Images Page",
				brokenLinkAndImagesPage::getAllLinks);

		SoftAssert softAssert = new SoftAssert();
		LinkValidator linkValidator = new LinkValidator();
		testReporter.step("Validate Links", () -> {
			int index = 0;
			for (WebElement link : links) {
				index++;
				String href = link.getAttribute("href");
				if (!linkValidator.isValidLink(href)) {
					continue;
				}
				int statusCode = linkValidator.getStatusCode(href);
				if(!(statusCode < 400)) {
					softAssert.fail("Broken Link: " + linkValidator.describeLink(link, index) + "\nStatus: " + statusCode);
				}
				
			}
			softAssert.assertAll();

		});
	}

	@Test
	public void validateValidHTMLLinks() {
		BrokenLinkAndImagesPage brokenLinkAndImagesPage = new BrokenLinkAndImagesPage(browser);

		testReporter.step("Launch Broken Links And Images Page", brokenLinkAndImagesPage::launch);

		List<WebElement> links = testReporter.step("Get All Links from Broken Links And Images Page",
				brokenLinkAndImagesPage::getAllLinks);

		SoftAssert softAssert = new SoftAssert();
		LinkValidator linkValidator = new LinkValidator();
		testReporter.step("Validate Links", () -> {
			int index = 0;
			for (WebElement link : links) {
				index++;
				String href = link.getAttribute("href");
				if(!linkValidator.isValidLink(href)) {
					softAssert.fail("Invalid href found for link: " + linkValidator.describeLink(link, index));
				}
				
			}
			softAssert.assertAll();

		});
	}

	@Test
	public void validateImages() {
		BrokenLinkAndImagesPage brokenLinkAndImagesPage = new BrokenLinkAndImagesPage(browser);

		testReporter.step("Launch Broken Links And Images Page", brokenLinkAndImagesPage::launch);

		List<WebElement> images = testReporter.step("Get All Images from Broken Links And Images Page",
				brokenLinkAndImagesPage::getAllImages);

		SoftAssert softAssert = new SoftAssert();

		ImageValidator imageValidator = new ImageValidator(TestGlobals.getDriver());
		testReporter.step("Validate Images", () -> {
			for (WebElement image : images) {
				String src = image.getAttribute("src");
				if (!imageValidator.isValidSrc(src)) {
					continue;
				}
				boolean loaded = imageValidator.isImageLoaded(image);
				softAssert.assertTrue(loaded, "Broken Image: " + src);
			}
			softAssert.assertAll();

		});
		
	}
	
	@Test
	public void validateHTMLImages() {
		BrokenLinkAndImagesPage brokenLinkAndImagesPage = new BrokenLinkAndImagesPage(browser);

		testReporter.step("Launch Broken Links And Images Page", brokenLinkAndImagesPage::launch);

		List<WebElement> images = testReporter.step("Get All Images from Broken Links And Images Page",
				brokenLinkAndImagesPage::getAllImages);

		SoftAssert softAssert = new SoftAssert();

		ImageValidator imageValidator = new ImageValidator(TestGlobals.getDriver());
		testReporter.step("Validate Images", () -> {
			for (WebElement image : images) {
				String src = image.getAttribute("src");
				softAssert.assertTrue(imageValidator.isValidSrc(src), "Broken Image: " + src);

			}
			softAssert.assertAll();

		});
		
	}
}
