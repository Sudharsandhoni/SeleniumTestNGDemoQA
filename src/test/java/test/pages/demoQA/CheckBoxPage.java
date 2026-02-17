package test.pages.demoQA;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import core.exceptions.CustomException;
import core.exceptions.CustomTimeoutException;
import core.utils.ConfigReader;
import core.utils.SeleniumUtils;
import io.opentelemetry.exporter.logging.SystemOutLogRecordExporter;
import test.pages.BasePage;
import tests.data.demoQA.CheckBoxNode;
import tests.data.demoQA.CheckBoxNode.State;

public class CheckBoxPage extends BasePage{


	// uncheckedState = "rct-icon-uncheck";
	// checkedState = "rct-icon-check";
	// halfCheckedState = "rct-icon-half-check";
	// css selector to get attribute class -> input[id*='home'] +
	// span[class='rct-checkbox'] > svg
	// relevant xpath for same ->
	// //input[contains(@id,'home')]//following-sibling::span[@class='rct-checkbox']//*[local-name()='svg']
	private By pageTitle = By.xpath("//h1[text()='Check Box']");
	private By expandAll = By.cssSelector("button[class*='rct-option-expand-all']");
	private By collapseAll = By.cssSelector("button[class*='rct-option-collapse-all']");
	// private By checkBoxAll = By.xpath("//label[contains(@for, 'tree-node')]");
	// //ol//li//span[@class='rct-title']
	private By checkBoxList = By.cssSelector("div[id='tree-node'] > ol");
	// to be performed on checkBoxList
	// :scope not supported in cross browser testing and old selenium versions < 4
	private By checkBoxListChildElement = By.cssSelector(":scope > li");
	// to be performed on checkBoxListChildElement to check if any
	private By checkBoxChildList = By.cssSelector(":scope > ol");
	// to be performed on checkBoxListChildElement to check if any
	private By checkBoxName = By.cssSelector("span[class='rct-title']");
	// to be performed on checkBoxListChildElement to check if any
	// Hierarchical access
	private By checkBoxState = By.cssSelector(":scope > span > label > span[class='rct-checkbox'] > svg");
	// Relative access using String
	// private String checkBoxStateCssSelector = "input[id*='$CHECKBOX_NAME'] +
	// span[class='rct-checkbox'] > svg";

	public CheckBoxPage(SeleniumUtils browser) {
		super(browser);
	}
	
	@Override
	protected By pageIdentifier() {
		return pageTitle;
	}


	public void launch() {
		browser.get(ConfigReader.getPropertyValue("BASE_URL_DEMO_QA") + "/checkbox");
	}

	public void clickExpandAllButton() throws CustomException {
		browser.click(expandAll);
	}

	public void clickCollapseAllButton() throws CustomException, CustomTimeoutException {
		browser.click(collapseAll);
	}

	private State getCheckBoxState(String classValue) {
		// uncheckedState = "rct-icon-uncheck";
		// checkedState = "rct-icon-check";
		// halfCheckedState = "rct-icon-half-check";
		if (classValue.contains("rct-icon-uncheck")) {
			return State.UNCHECKED;
		} else if (classValue.contains("rct-icon-check")) {
			return State.CHECKED;
		} else if (classValue.contains("rct-icon-half-check")) {
			return State.INDETERMINATE;
		} else {
			return State.UNKNOWN;
		}
	}

	public CheckBoxNode readCheckBoxTree() throws CustomTimeoutException, CustomException {
//		browser.getAllElements();
		//clickExpandAllButton();
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//
//		}
		browser.waitForVisibility(checkBoxList);
		WebElement checkBoxParent = browser.find(checkBoxList);
		WebElement rootElement = checkBoxParent.findElement(checkBoxListChildElement);
		return parseCheckBoxElement(rootElement);

		// Explicitly locate the Home node
		//WebElement homeElement = checkBoxParent.findElement(By.xpath(".//li[.//span[normalize-space()='Home']]"));

		//return parseCheckBoxElement(homeElement);

	}

	private CheckBoxNode parseCheckBoxElement(WebElement checkboxElement) {
		try {
			String name = checkboxElement.findElement(checkBoxName).getText();
			State state = getCheckBoxState(checkboxElement.findElement(checkBoxState).getAttribute("class"));
			// System.out.println(name + " -->> " +state);
			List<CheckBoxNode> children = new ArrayList<>();
			List<WebElement> childContainers = checkboxElement.findElements(checkBoxChildList);
			for (WebElement container : childContainers) {
				List<WebElement> childCheckboxes = container.findElements(checkBoxListChildElement);
				for (WebElement childElement : childCheckboxes) {
					children.add(parseCheckBoxElement(childElement));
				}

			}
			return new CheckBoxNode(name, state, children);
		} catch (StaleElementReferenceException e) {
			throw new RuntimeException("Checkbox DOM changed while reading tree", e);
		}
	}

	public void click(String name) throws CustomException, CustomTimeoutException {
//	    WebElement checkbox = driver.findElement(
//	    		 By.xpath("//span[normalize-space()='" + name + "']/preceding-sibling::input[@type='checkbox']")
//	    );
//	    checkbox.click();
	    browser.click(By.xpath("//span[text()='" + name + "']"));
	    
	}


//
//	public void clickNode(String name) throws CustomTimoutException, CustomException {
//	    CheckBoxNode tree = readCheckBoxTree();
//	    List<String> path = tree.pathTo(name); // ["Home", "Desktop", "Notes"]
//
//	    for (int i = 0; i < path.size() - 1; i++) {
//	        expandIfCollapsed(path.get(i));
//	    }
//
//	    click(path.get(path.size() - 1));
//	}
//
//	
//
//	private void expandIfCollapsed(String nodeName) {
//	    WebElement li = findNodeLi(nodeName);
//
//	    // If it has children and is collapsed, expand it
//	    if (hasExpandToggle(li) && isCollapsed(li)) {
//	        WebElement toggle = li.findElement(By.xpath(".//button | .//span[contains(@class,'expand')]"));
//	        toggle.click();
//	        waitForChildrenToRender(li);
//	    }
//	}
//
//	private WebElement findNodeLi(String name) {
//	    return driver.findElement(
//	        By.xpath("//span[normalize-space()='" + name + "']/ancestor::li[1]")
//	    );
//	}
//
//	private boolean hasExpandToggle(WebElement li) {
//	    return !li.findElements(By.xpath(".//button | .//span[contains(@class,'expand')]")).isEmpty();
//	}
//
//	private boolean isCollapsed(WebElement li) {
//	    return li.getAttribute("class").contains("collapsed")
//	        || !li.findElements(By.xpath(".//ol | .//ul")).isEmpty()
//	           && !li.findElement(By.xpath(".//ol | .//ul")).isDisplayed();
//	}
//
//	private void waitForChildrenToRender(WebElement li) {
//	    By children = By.xpath(".//ol/li | .//ul/li");
//	    new WebDriverWait(driver, Duration.ofSeconds(5))
//	            .until(d -> !li.findElements(children).isEmpty());
//	}

}
