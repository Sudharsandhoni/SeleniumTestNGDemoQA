package core.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ImageValidator {

	private JavascriptExecutor js;

	public ImageValidator(WebDriver driver) {
		this.js = (JavascriptExecutor) driver;
	}

	public boolean isValidSrc(String src) {
		return src != null && !src.isEmpty();
	}

	public boolean isImageLoaded(WebElement image) {
		return (Boolean) js.executeScript("return arguments[0].complete === true && "
				+ "arguments[0].naturalWidth > 0 && " + "arguments[0].naturalHeight > 0;", image);
	}
	
	public boolean isImageLoadedAndDisplayed(WebElement image) {
		return image.isDisplayed() && (Boolean) js.executeScript("return arguments[0].complete === true && "
				+ "arguments[0].naturalWidth > 0 && " + "arguments[0].naturalHeight > 0;", image);
	}

}
