package tests.demoQA;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import core.constants.TestGlobals;
import core.exceptions.CustomException;
import core.exceptions.CustomTimeoutException;
import test.pages.demoQA.HomePage;
import test.pages.demoQA.UploadAndDownloadPage;
import test.pages.demoQA.WebTablesPage;
import tests.BaseTest;
import tests.data.demoQA.WebTableRegistrationFormData;

public class WebTablesTest extends BaseTest {

	@Test(enabled = false)// pages has issues after recent update
	public void validateWebTable() throws CustomException, CustomTimeoutException {
		HomePage homePage = new HomePage(browser);
		testReporter.step("Launch HomePage", homePage::launch);
		testReporter.step("Click Elements Card", homePage::clickElementsCard);
		testReporter.step("Click Web Tables Text/Link", homePage::clickWebTablesText);

		WebTablesPage webTablesPage = new WebTablesPage(browser);
		webTablesPage.waitForPageToLoad();
		

		//webTablesPage.launch();
		webTablesPage.clickAddButton();
		Assert.assertTrue(webTablesPage.isRegistrationFormDisplayed());
		String firstName = "Danny";
		String lastName = "Morrison";
		String email = "danny.morrison@yopmail.com";
		int age = 32;
		String salary = "150000";
		String department = "Information Technology";

		WebTableRegistrationFormData form = new WebTableRegistrationFormData(firstName, lastName, email, age, salary,
				department);

		webTablesPage.submitRegistrationForm(form);
		webTablesPage.searchData(firstName);
		List<WebTableRegistrationFormData> datas = webTablesPage.getTableData();
		//System.out.println(form.getFirstName() + " " +  form.getLastName() + " "  + form.getEmail() + " " + form.getAge() + " " + form.getSalary() + " " + form.getDepartment());

		Assert.assertTrue(datas.stream().anyMatch(n -> {
			//System.out.println(n.getFirstName() + " " +  n.getLastName() + " "  + n.getEmail() + " " + n.getAge() + " " + n.getSalary() + " " + n.getDepartment());
			
			if (!n.getFirstName().trim().equals(form.getFirstName())) {
				
				return false;
			} else if (n.getAge() == form.getAge() && n.getLastName().trim().equals(form.getLastName())
					&& n.getSalary().trim().equals(form.getSalary()) && n.getEmail().trim().equals(form.getEmail())
					&& n.getDepartment().trim().equals(form.getDepartment())) {
				return true;
			} else {
				return false;
			}
		}));
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}
}
