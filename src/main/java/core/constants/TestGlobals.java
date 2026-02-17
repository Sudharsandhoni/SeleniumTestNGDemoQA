package core.constants;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.Logger;

import core.context.TestContextManager;
import core.utils.LogUtils;

public class TestGlobals {
	
	public static final int ELEMENTS_TIMEOUT = 30;
	public static final int RETRY_COUNT = 2;
	private static final ThreadLocal<Boolean> retryRun =
            ThreadLocal.withInitial(() -> false);
	
	public static Path DOWNLOADS_ROOT_DIR;
	
	public static synchronized Path getDownloadsRootDir() {
        if (DOWNLOADS_ROOT_DIR == null) {
            try {
                DOWNLOADS_ROOT_DIR = Paths.get(
                    System.getProperty("user.dir"),
                    "selenium-downloads"
                ).toAbsolutePath();

                Files.createDirectories(DOWNLOADS_ROOT_DIR);
            } catch (IOException e) {
                throw new RuntimeException("Failed to create downloads root directory", e);
            }
        }
        return DOWNLOADS_ROOT_DIR;
    }

    public static void markRetryRun() {
        retryRun.set(true);
    }

    public static boolean isRetryRun() {
        return retryRun.get();
    }


	// Driver
	private static ThreadLocal<WebDriver> tldriver = new ThreadLocal<>();

	public static void setDriver(WebDriver driver) {
		tldriver.set(driver);
	}

	public static WebDriver getDriver() {
		return tldriver.get();
	}

	public static void removeDriver() {
		tldriver.remove();
	}
	
	// Logger
	private static final ThreadLocal<Logger> tlLogger = new ThreadLocal<>();

	private TestGlobals() {
		// prevent instantiation
	}

	public static void setLogger(Logger logger) {
		tlLogger.set(logger);
	}

	public static Logger getLogger() {
		return tlLogger.get();
	}

	public static void removeLogger() {
		tlLogger.remove();
	}
	
	// Base URL
	private static ThreadLocal<String> tlBaseUrl = new ThreadLocal();
	
	public static void setBaseUrl(String baseUrl) {
		tlBaseUrl.set(baseUrl);
	}
	
	public static String getBaseUrl() {
		return tlBaseUrl.get();
	}
	
	public static void removeBaseUrl() {
		tlBaseUrl.remove();
	}
	
	// Failed test collector
	private static Set<ITestResult> failedTests = ConcurrentHashMap.newKeySet(); 
	
	public static void addFailedTests(ITestResult test){
		failedTests.add(test);
	}
	
	public static Set<ITestResult> getFailedTests(){
		return failedTests;
	}
	
	public static void clearFailedTests() {
		 failedTests.clear();
	}
	
	//
	public static int nextStep() {
		return TestContextManager.get().nextStep();
	}
}
