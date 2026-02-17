package tests.demoQA;

import org.testng.Assert;
import org.testng.annotations.Test;

import core.constants.TestGlobals;
import core.exceptions.CustomException;
import core.exceptions.CustomTimeoutException;
import test.pages.demoQA.RadioButtonPage;
import tests.BaseTest;

public class RadioButtonTest extends BaseTest{

	
	@Test
	public void validateRadioButton() throws CustomException, CustomTimeoutException{
		RadioButtonPage radioBtnPage = new RadioButtonPage(browser);
		testReporter.step("Launch Radio Button Page",radioBtnPage::launch);
		
		testReporter.stepWithCapture("Click Yes Radio Button", () -> radioBtnPage.clickRadionButton("Yes"));
		testReporter.step("Validate Selected Message shows Yes", () -> Assert.assertEquals("Yes", radioBtnPage.getSuccessMessage()));
		radioBtnPage.clickRadionButton("Impressive");
		Assert.assertEquals("Impressive", radioBtnPage.getSuccessMessage());
		
	}
	
}
