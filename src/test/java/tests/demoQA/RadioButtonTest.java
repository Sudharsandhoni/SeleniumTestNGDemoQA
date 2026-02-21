package tests.demoQA;

import org.testng.Assert;
import org.testng.annotations.Test;

import core.constants.TestGlobals;
import core.exceptions.CustomException;
import core.exceptions.CustomTimeoutException;
import test.pages.demoQA.CheckBoxPage;
import test.pages.demoQA.HomePage;
import test.pages.demoQA.RadioButtonPage;
import tests.BaseTest;

public class RadioButtonTest extends BaseTest{

	
	@Test
	public void validateRadioButton() throws CustomException, CustomTimeoutException{
		HomePage homePage = new HomePage(browser);
		testReporter.step("Launch HomePage", homePage::launch);
		testReporter.step("Click Elements Card", homePage::clickElementsCard);
		testReporter.step("Click Radio Button Text/Link", homePage::clickRadioButtonText);

		RadioButtonPage radioBtnPage = new RadioButtonPage(browser);
		radioBtnPage.waitForPageToLoad();
		//testReporter.step("Launch Radio Button Page",radioBtnPage::launch);
		
		testReporter.stepWithCapture("Click Yes Radio Button", () -> radioBtnPage.clickRadionButton("Yes"));
		testReporter.step("Validate Selected Message shows Yes", () -> Assert.assertEquals("Yes", radioBtnPage.getSuccessMessage()));
		radioBtnPage.clickRadionButton("Impressive");
		Assert.assertEquals("Impressive", radioBtnPage.getSuccessMessage());
		
	}
	
}
