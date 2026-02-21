package tests.demoQA;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.testng.annotations.Test;

import core.constants.TestGlobals;
import core.exceptions.CustomException;
import core.exceptions.CustomTimeoutException;
import core.utils.LogUtils;
import test.pages.demoQA.ButtonsPage;
import test.pages.demoQA.CheckBoxPage;
import test.pages.demoQA.HomePage;
import tests.BaseTest;
import tests.data.demoQA.CheckBoxNode;
import tests.utils.TreeAssertions;

public class CheckBoxTest extends BaseTest {

	@Test(enabled = false)// pages has issues after recent update
	public void validateCheckBox() throws CustomException, CustomTimeoutException {
		HomePage homePage = new HomePage(browser);
		testReporter.step("Launch HomePage", homePage::launch);
		testReporter.step("Click Elements Card", homePage::clickElementsCard);
		testReporter.step("Click Checkbox Text/Link", homePage::clickCheckBoxText);
		CheckBoxPage checkBoxPage = new CheckBoxPage(browser);
		checkBoxPage.waitForPageToLoad();
		//testReporter.step("Launch Checkbox Page", checkBoxPage::launch);
		testReporter.step("Click Expand All Button", checkBoxPage::clickExpandAllButton);
		CheckBoxNode before = testReporter.step("Read CheckBox Tree", checkBoxPage::readCheckBoxTree);

		String elementToClick = testReporter.step("Pick random checkbox", () -> {
			List<String> names = before.getAllCheckBoxNames();
			return names.get(ThreadLocalRandom.current().nextInt(names.size()));
		});

		testReporter.step("Click checkbox: " + elementToClick, () -> checkBoxPage.click(elementToClick));

		CheckBoxNode expected = testReporter.step("Build expected tree", () -> before.toggle(elementToClick));

		CheckBoxNode actual = testReporter.step("Read checkbox tree (actual state)", checkBoxPage::readCheckBoxTree);

		testReporter.step("Verify tree matches expected", () -> TreeAssertions.assertEquals(expected, actual));

	}

}
