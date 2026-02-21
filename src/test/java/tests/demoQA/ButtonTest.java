package tests.demoQA;

import org.testng.Assert;
import org.testng.annotations.Test;

import test.pages.demoQA.ButtonsPage;
import test.pages.demoQA.HomePage;
import tests.BaseTest;

public class ButtonTest extends BaseTest{
	
	@Test(enabled = false)// pages has issues after recent update
	public void validateButtons() {
		HomePage homePage = new HomePage(browser);
		testReporter.step("Launch HomePage", homePage::launch);
		testReporter.step("Click Elements Card", homePage::clickElementsCard);
		testReporter.step("Click Buttons Text/Link", homePage::clickButtonsText);
		ButtonsPage buttonsPage = new ButtonsPage(browser);
		buttonsPage.waitForPageToLoad();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		testReporter.step("Double click on button",  buttonsPage::doubleClickButton);
		testReporter.step("validate double clicked message", () -> Assert.assertTrue(buttonsPage.isDoubleClickedMessageDisplayed()));
		testReporter.step("Right click on button",  buttonsPage::rightClickButton);
		testReporter.step("validate Right clicked message", () -> Assert.assertTrue(buttonsPage.isRightClickedMessageDisplayed()));
		testReporter.step("Click on dynamic id button",  buttonsPage::clickDynamicButton);
		testReporter.step("validate dynamic button clicked message", () -> Assert.assertTrue(buttonsPage.isDyanmicButtonClickedMessageDisplayed()));
	}

}
