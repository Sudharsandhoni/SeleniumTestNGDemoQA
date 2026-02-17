package test.pages.demoQA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import core.exceptions.CustomException;
import core.exceptions.CustomTimeoutException;
import core.utils.ConfigReader;
import core.utils.SeleniumUtils;
import test.pages.BasePage;
import tests.data.demoQA.WebTableRegistrationFormData;

public class WebTablesPage extends BasePage {

	private By pageTitle = By.xpath("//h1[text()='Web Tables']");
	private By addNewRecordButton = By.id("addNewRecordButton");
	private By registrationForm = By.id("registration-form-modal");
	private By form_firstName = By.id("firstName");
	private By form_lastName = By.id("lastName");
	private By form_email = By.id("userEmail");
	private By form_age = By.id("age");
	private By form_salary = By.id("salary");
	private By form_department = By.id("department");
	private By form_submitBtn = By.id("submit");
	private By searchBox = By.id("searchBox");
	private By headerNames = By.xpath("//div[contains(@class, 'header-content')]");
	private By tableRows = By.xpath("//div[@role= 'row']");
	private By columnDatas = By.xpath("//div[@class='rt-td']");

	public WebTablesPage(SeleniumUtils browser) {
		super(browser);
	}
	
	@Override
	protected By pageIdentifier() {
		return pageTitle;
	}

	public void launch() throws CustomException {
		browser.get(ConfigReader.getPropertyValue("BASE_URL_DEMO_QA") + "/webtables");
	}

	public void clickAddButton() throws CustomException, CustomTimeoutException {
		browser.click(addNewRecordButton);
	}

	public boolean isRegistrationFormDisplayed() {
		return browser.isElementDisplayed(registrationForm);
	}

	public void submitRegistrationForm(WebTableRegistrationFormData form)
			throws CustomException, CustomTimeoutException {
		browser.type(form_firstName, form.getFirstName());
		browser.type(form_lastName, form.getLastName());
		browser.type(form_email, form.getEmail());
		browser.type(form_age, Integer.toString(form.getAge()));
		browser.type(form_salary, form.getSalary());
		browser.type(form_department, form.getDepartment());
		browser.click(form_submitBtn);

	}

	public void searchData(String data) throws CustomException, CustomTimeoutException {
		browser.type(searchBox, data);
	}

	public List<WebTableRegistrationFormData> getTableData() throws CustomTimeoutException {
		List<WebTableRegistrationFormData> tableData = new ArrayList<>();
		browser.waitForVisibility(tableRows);
		List<WebElement> webTableRows = browser.findAll(tableRows);
		Map<String, Integer> headerMap = new HashMap<>();
		List<WebElement> headers = browser.findAll(headerNames);
		int index = 0;
		for (WebElement header : headers) {
			headerMap.put(header.getText().trim().toLowerCase().replaceAll("\\s+", ""), index);
			index++;
		}
		for (int row = 1; row < webTableRows.size(); row++) {
			List<WebElement> webColumnDatas = webTableRows.get(row).findElements(columnDatas);
			if(webColumnDatas.get(0).getText().trim() == "") {
				return tableData;
			}
			tableData.add(new WebTableRegistrationFormData(webColumnDatas.get(headerMap.get("firstname")).getText(),
					webColumnDatas.get(headerMap.get("lastname")).getText(),
					webColumnDatas.get(headerMap.get("email")).getText(),
					Integer.parseInt(webColumnDatas.get(headerMap.get("age")).getText()),
					webColumnDatas.get(headerMap.get("salary")).getText(),
					webColumnDatas.get(headerMap.get("department")).getText()));

		}

		return tableData;
	}
	
	
	

}
