package core.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.OutputType;
import core.constants.TestGlobals;
import core.exceptions.CustomException;
import core.exceptions.CustomTimeoutException;
import core.helpers.reports.ExtentTestReportLogger;
import core.helpers.reports.ExtentTestManager;
import core.screenshots.ScreenshotHelper;
import core.screenshots.ScreenshotType;

public class SeleniumUtils {

	private static final boolean HIGHLIGHT_ELEMENT = true;
	private static final int RETRY_COUNT = 2;
	private final WebDriver driver;
	private final WebDriverWait defaultWait;
	private final int defaultTimeoutSeconds;

	public SeleniumUtils(WebDriver driver, int timeoutInSeconds) {
		this.driver = driver;
		this.defaultTimeoutSeconds = timeoutInSeconds;
		this.defaultWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
	}

	public void get(String url) {
		driver.get(url);
	}

	// 1
	public WebElement waitForVisibility(By locator) throws CustomTimeoutException {

		try {
			return this.defaultWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (TimeoutException e) {
			throw new CustomTimeoutException(
					"Element not visible within " + this.defaultTimeoutSeconds + "s: " + locator, e);
		}
	}

	public WebElement waitForVisibility(By locator, int waitTime) throws CustomTimeoutException {
		if (waitTime == defaultTimeoutSeconds) {
			return waitForVisibility(locator);
		}
		try {
			WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(waitTime));
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (TimeoutException e) {
			throw new CustomTimeoutException("Element not visible within " + waitTime + "s: " + locator, e);
		}
	}

	// 2
	public void scrollIntoView(WebElement element) {

		JavascriptExecutor jsExecutor = (JavascriptExecutor) this.driver;
		jsExecutor.executeScript("arguments[0].scrollIntoView({block: 'center'})", element);

	}

	// 3
	public void highlightElement(WebElement element) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) this.driver;
		jsExecutor.executeScript("arguments[0].style.border='3px solid red'", element);
	}

	public void waitForClickable(WebElement element) {

		try {
			defaultWait.until(ExpectedConditions.elementToBeClickable(element));
		} catch (TimeoutException e) {
			throw new CustomTimeoutException(
					"Element not visible within " + this.defaultTimeoutSeconds + "s: " + element, e);
		}
	}

	public void waitForClickable(WebElement element, int waitTime) {
		if (waitTime == defaultTimeoutSeconds) {
			waitForClickable(element);
			return;
		}
		try {
			WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(waitTime));
			wait.until(ExpectedConditions.elementToBeClickable(element));
		} catch (TimeoutException e) {
			throw new CustomTimeoutException("Element not visible within " + waitTime + "s: " + element, e);
		}
	}

	public void waitForTextChanged(WebElement element) {
		waitForTextChanged(element, defaultTimeoutSeconds);
	}
	
	public void waitForTextChanged(WebElement element, String oldText) {
		waitForTextChanged(element, oldText, defaultTimeoutSeconds);
	}

	public void waitForTextChanged(WebElement element, int waitTime) {

		
		try {
			if (waitTime == defaultTimeoutSeconds) {
				// WebElement element = waitForVisibility(locator);
				String oldText = element.getText();
				defaultWait.until(_ -> !element.getText().equals(oldText));
				return;
			}
			// WebElement element = waitForVisibility(locator, waitTime);
			String oldText = element.getText();
			WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(waitTime));
			wait.until(_ -> !element.getText().equals(oldText));

		} catch (TimeoutException e) {
			throw new CustomTimeoutException("Element text is not changed within " + waitTime + "s: " + element, e);
		}
	}

	public void waitForTextChanged(WebElement element, String oldText, int waitTime) {

	
		try {
			if (waitTime == defaultTimeoutSeconds) {
				// WebElement element = waitForVisibility(locator);

				defaultWait.until(_ -> !element.getText().equals(oldText));
				return;
			}
			// WebElement element = waitForVisibility(locator, waitTime);

			WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(waitTime));
			wait.until(_ -> !element.getText().equals(oldText));

		} catch (TimeoutException e) {
			throw new CustomTimeoutException("Element text is not changed within " + waitTime + "s: " + element + "\nOld Text -> "+ oldText + "\nNew Text -> " + element.getText(), e);
		}
	}
	
	
	
	public void waitForAttributeChanged(By locator, String attributeName, String attributeValueToBeContains, int waitTime) {
		try {
			if(waitTime == defaultTimeoutSeconds) {
				defaultWait.until(ExpectedConditions.attributeContains(locator, attributeName.toLowerCase().trim(), attributeValueToBeContains));
				return;
			}
			WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(waitTime));
			wait.until(ExpectedConditions.attributeContains(locator, attributeName.toLowerCase().trim(), attributeValueToBeContains));
		}
		catch (TimeoutException e) {
			throw new CustomTimeoutException("Element attribute value is not changed as expected within " + waitTime + "s: " 
					+ locator.toString() + "\nAttribute name: "+attributeName 
					+ "\nAttributeValue: " + attributeValueToBeContains
					+ "\nCurrentAttributeValue" + getAttributeValue(locator, attributeName), e);
		}
		
	}
	
	public void waitForAttributeChanged(By locator, String attributeName, String attributeValueToBeContains) {
		waitForAttributeChanged(locator, attributeName, attributeValueToBeContains, defaultTimeoutSeconds);
	}

	public boolean isElementDisplayed(By locator) {
		try {
			waitForVisibility(locator);
			return true;
		} catch (CustomTimeoutException e) {
			return false;
		}
	}

	public boolean isElementDisplayed(By locator, int waitTime) {
		try {
			waitForVisibility(locator, waitTime);
			return true;
		} catch (CustomTimeoutException e) {
			return false;
		}
	}

	public WebElement findCustom(By locator, int retries, boolean highlightElement, int waitTime) {
		for (int attempts = 1; attempts <= retries; attempts++) {
			try {
				WebElement element = waitForVisibility(locator, waitTime);
				//LogUtils.logInfo(element.getLocation().toString());
				scrollIntoView(element);
				//LogUtils.logInfo(element.getLocation().toString());
				if (highlightElement) {
					highlightElement(element);
				}
				return element;

			} catch (StaleElementReferenceException e) {
				attempts++;
			}
		}

		throw new RuntimeException(String.format("Unable to find element %s after retries: %d", locator, retries));

	}

	public WebElement find(By locator) {
		return findCustom(locator, RETRY_COUNT, HIGHLIGHT_ELEMENT, this.defaultTimeoutSeconds);
	}

	public List<WebElement> findAllCustom(By locator, int retries, boolean highlightElements, int waitTime)
			throws CustomTimeoutException {
		for (int attempts = 1; attempts <= retries; attempts++) {
			try {
				// wait until at least one element is visible
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
				List<WebElement> elements = wait.until(driver -> {
					List<WebElement> list = driver.findElements(locator);
					list.removeIf(e -> !e.isDisplayed());
					return list.isEmpty() ? null : list;
				});

				if (highlightElements) {
					for (WebElement element : elements) {
						scrollIntoView(element);
						highlightElement(element);
					}
				}

				return elements;

			} catch (StaleElementReferenceException e) {
				// retry
			}
		}

		throw new RuntimeException(String.format("Unable to find elements %s after retries: %d", locator, retries));
	}

	public List<WebElement> findAll(By locator) {
		// Since multiple elements not highlighting each one
		return findAllCustom(locator, RETRY_COUNT, false, this.defaultTimeoutSeconds);
	}

	public String getUrl() {
		return driver.getCurrentUrl();
	}

	public String getWindowHandle() {
		return driver.getWindowHandle();
	}

	public Set<String> getWindowHandles() {
		return driver.getWindowHandles();
	}

	public void switchToWindow(String window) {
		driver.switchTo().window(window);
	}
	
	public String switchToNewWindow(String parentWindow) {
	    return driver.getWindowHandles().stream()
	            .filter(handle -> !handle.equals(parentWindow))
	            .findFirst()
	            .map(handle -> {
	                driver.switchTo().window(handle);
	                return handle;
	            })
	            .orElseThrow(() ->
	                new AssertionError("No new window found"));
	}

	public void closeCurrentWindow() {
		driver.close();
	}

	public void click(By locator) {
		clickCustom(locator, RETRY_COUNT, HIGHLIGHT_ELEMENT, false, this.defaultTimeoutSeconds);
	}

	public void clickTakescreenshotBeforeAction(By locator) throws CustomException {
		clickCustom(locator, RETRY_COUNT, HIGHLIGHT_ELEMENT, true, this.defaultTimeoutSeconds);
	}

	public void clickCustom(By locator, int retries, boolean highLightElement, boolean takeScreenshotBeforeAction,
			int waitTime) {
		for (int attempts = 1; attempts <= retries; attempts++) {
			try {
				WebElement element = waitForVisibility(locator, waitTime);
				scrollIntoView(element);
				if (highLightElement) {
					highlightElement(element);
				}
				waitForClickable(element, waitTime);
				if (takeScreenshotBeforeAction) {
					// ExtentReportLogger.takeScreenshotAndAttachToReport("Before Click");
					// ScreenshotHelper.capture(this, ScreenshotType.BEFORE_ACTION,
					// locator.toString());
					ScreenshotHelper.capture(this, ScreenshotType.BEFORE_ACTION, "Click " + locator);
				}
				element.click();
				return;
			} catch (StaleElementReferenceException e) {
				attempts++;
			}

		}
		throw new RuntimeException(String.format("Unable to click element %s after retries: %d", locator, retries));

	}

	public void type(By locator, String text) throws CustomException {
		typeCustom(locator, text, false, RETRY_COUNT, HIGHLIGHT_ELEMENT, this.defaultTimeoutSeconds);
	}

	public void type_ClearTextBeforetype(By locator, String text) throws CustomException {
		typeCustom(locator, text, true, RETRY_COUNT, HIGHLIGHT_ELEMENT, this.defaultTimeoutSeconds);
	}

	public void typeCustom(By locator, String text, boolean clearTextBeforeEnterText, int retries,
			boolean highLightElement, int waitTime) throws CustomException {
		for (int attempts = 1; attempts <= retries; attempts++) {
			try {
				WebElement element = waitForVisibility(locator, waitTime);
				scrollIntoView(element);
				if (highLightElement) {
					highlightElement(element);
				}
				if (!element.isEnabled()) {
					throw new RuntimeException("Input field is not enabled for typing");
				}
				if (clearTextBeforeEnterText) {
					element.clear();
				}
				element.sendKeys(text);
				return;
			} catch (StaleElementReferenceException e) {
				attempts++;
			}

		}
		throw new RuntimeException(String.format("Unable to type into element %s after retries: %d", locator, retries));

	}

	public String getTextCustom(By locator, int retries, boolean highLightElement, int waitTime)
			throws CustomTimeoutException {

		for (int attempts = 1; attempts <= retries; attempts++) {
			try {
				WebElement element = waitForVisibility(locator, waitTime);
				scrollIntoView(element);
				if (highLightElement) {
					highlightElement(element);
				}
				return element.getText();
			} catch (StaleElementReferenceException e) {
				attempts++;
			}

		}
		throw new RuntimeException(
				String.format("Unable to get text for element %s after retries: %d", locator, retries));

	}

	public String getText(By locator) {
		return getTextCustom(locator, RETRY_COUNT, HIGHLIGHT_ELEMENT, this.defaultTimeoutSeconds);
	}
	
	public String getAttributeValue(By locator, String attributeName) {
		WebElement element = find(locator);
		return element.getAttribute(attributeName.trim());
	}
	
	public String getAttributeValue(WebElement element, String attributeName) {
		return element.getAttribute(attributeName.trim());
	}

	public String takeScreenshot(String action) throws CustomException {
		try {
			File src = ((TakesScreenshot) this.driver).getScreenshotAs(OutputType.FILE);
			String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd__HH_mm_ss_SSS"));
			String filName = String.format("target\\screenshots\\screenshot_%s.png", timeStamp);
			FileHandler.createDir(new File("target\\screenshots"));
			FileHandler.copy(src, new File(filName));

			return filName;

		} catch (Exception e) {
			throw new CustomException(
					String.format("Error while taking screenshot for action %s. Error -> %s", action, e.getMessage()));
		}

	}

	public String takeScreenshotBase64() {
		String base64 = ((TakesScreenshot) this.driver).getScreenshotAs(OutputType.BASE64);

		return base64;
	}

}
