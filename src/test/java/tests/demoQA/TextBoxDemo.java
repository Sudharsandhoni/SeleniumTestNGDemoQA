package tests.demoQA;
import org.testng.Assert;
import org.testng.annotations.Test;
import core.constants.TestGlobals;
import core.exceptions.CustomException;
import core.exceptions.CustomTimeoutException;
import core.helpers.reports.ExtentTestReportLogger;
import test.pages.demoQA.HomePage;
import test.pages.demoQA.TextBoxPage;
import tests.BaseTest;
import tests.data.demoQA.TextBoxForm;

public class TextBoxDemo extends BaseTest{
	
	
	@Test
	public void validateTextBox() throws CustomException, InterruptedException, CustomTimeoutException {
		HomePage homePage = new HomePage(browser);
		homePage.launch();		
		homePage.clickElementsCard();
		testReporter.info("Clicked Elements Card");
		homePage.clickTextBoxText();
		TextBoxPage textBoxPage = new TextBoxPage(browser);
		TextBoxForm formData = new TextBoxForm();
		formData.setFullName("Larson Corney");
		formData.setEmail("larson.corney@yopmail.com");
		formData.setCurrentAddress("123 13th Floor, Horror Apartments, Hollywood city, Manchester, USA - 100009");
		formData.setPermanentAddress("123 13th Floor, Horror Apartments, Hollywood city, Manchester, USA - 100009");
		textBoxPage.fillTheFormAndSubmit(formData);
		Assert.assertEquals(formData.toString().trim(), textBoxPage.getOutputText());
		//Thread.sleep(5000);	
		
	}
}
