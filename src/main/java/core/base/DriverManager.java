package core.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import core.constants.TestGlobals;
import core.exceptions.CustomException;
import core.utils.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;

import static core.utils.LogUtils.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DriverManager {

	public static void createDriver(String browserName) throws CustomException {
		createDriver(browserName, "");
	}
	
	public static void createDriver(String browserName, String downloadPath) throws CustomException {

		try {
			switch (browserName.toUpperCase()) {
			case "CHROME":
				ChromeOptions options = new ChromeOptions();
				// Starts browser in maximized resolution
				options.addArguments("--start-maximized");
				
				if(downloadPath != null && !downloadPath.equals("")) {
					 Map<String, Object> prefs = new HashMap<>();
			            prefs.put("download.default_directory", downloadPath);
			            prefs.put("download.prompt_for_download", false);
			            prefs.put("download.directory_upgrade", true);
			            prefs.put("safebrowsing.enabled", true);
			            options.setExperimentalOption("prefs", prefs);
			            logInfo("Download option enabled in Driver Manager");
				}

				options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
				if (ConfigReader.getPropertyValue("RUN_ON_HEADLESS_BROWSER_MODE").equalsIgnoreCase("TRUE")) {
					options.addArguments("--headless=new");
				}

				WebDriverManager.chromedriver().setup();
				WebDriver driver = new ChromeDriver(options);
				TestGlobals.setDriver(driver);

			}

		} catch (Exception e) {
			String msg = String.format("Error while creating driver for browser -> {}", browserName);
			logErrorAndTrace(msg, e);
			throw new CustomException(msg);
		}

	}
	
	public static void quitDriver() {
		if(TestGlobals.getDriver() != null) {
			TestGlobals.getDriver().quit();
			TestGlobals.removeDriver();
		}
	
	}

}
