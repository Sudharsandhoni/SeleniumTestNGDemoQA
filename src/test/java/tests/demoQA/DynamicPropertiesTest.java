package tests.demoQA;

import org.testng.Assert;
import org.testng.annotations.Test;

import test.pages.demoQA.DynamicPropertiesPage;
import test.pages.demoQA.HomePage;
import tests.BaseTest;

public class DynamicPropertiesTest extends BaseTest{
	
	@Test
	public void ValidateDynamicProperties() {
		HomePage homePage = new HomePage(browser);
		testReporter.step("Launch HomePage", homePage::launch);
		testReporter.step("Click Elements Card", homePage::clickElementsCard);
		testReporter.step("Click Dyanmic Propertis Text/Link", homePage::clickDynamicPropertiesText);
		DynamicPropertiesPage dynamicPropertiesPage = new DynamicPropertiesPage(browser);
		String dynamicIdText = testReporter.step("Get dynamic Id changing text value", () -> dynamicPropertiesPage.getDynamicIDText());
		testReporter.step("Validate Dyanamic Text", () -> Assert.assertEquals(dynamicIdText, "This text has random Id"));
		testReporter.step("Click Dynamically enabled Button", dynamicPropertiesPage::clickDynamicallyEnabledButton);
		testReporter.step("Wait For Dynamically color changing button to change its color", dynamicPropertiesPage::waitForButtonColorChange);
		testReporter.step("Click Dynamically visible Button", dynamicPropertiesPage::clickDynamicallyVisibleButton);

		
	}

}
