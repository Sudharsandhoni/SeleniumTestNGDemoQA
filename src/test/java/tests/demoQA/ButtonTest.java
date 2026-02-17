package tests.demoQA;

import org.testng.Assert;
import org.testng.annotations.Test;

import test.pages.demoQA.ButtonsPage;
import tests.BaseTest;

public class ButtonTest extends BaseTest{
	
	@Test
	public void validateButtons() {
		ButtonsPage buttonsPage = new ButtonsPage(browser);
		testReporter.step("Launch Buttons Page", buttonsPage::launch);
		testReporter.step("Double click on button",  buttonsPage::doubleClickButton);
		testReporter.step("validate double clicked message", () -> Assert.assertTrue(buttonsPage.isDoubleClickedMessageDisplayed()));
		testReporter.step("Right click on button",  buttonsPage::rightClickButton);
		testReporter.step("validate Right clicked message", () -> Assert.assertTrue(buttonsPage.isRightClickedMessageDisplayed()));
		testReporter.step("Click on dynamic id button",  buttonsPage::clickDynamicButton);
		testReporter.step("validate dynamic button clicked message", () -> Assert.assertTrue(buttonsPage.isDyanmicButtonClickedMessageDisplayed()));
	}

}
