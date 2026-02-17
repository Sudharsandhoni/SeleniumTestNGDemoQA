package tests;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import com.aventstack.extentreports.ExtentTest;

import org.testng.ITestListener;
import org.testng.ITestResult;

import core.annotations.EnableDownload;
import core.base.DriverManager;
import core.constants.TestGlobals;
import core.context.TestContextManager;
import core.exceptions.CustomException;
import core.helper.download.DownloadUtils;
import core.helper.download.RequiresDownload;
import core.helpers.listeners.ExtentTestNGListener;
import core.helpers.reports.ExtentManager;
import core.helpers.reports.ExtentTestReportLogger;
import core.helpers.reports.ExtentTestManager;
import core.screenshots.ScreenshotHelper;
import core.screenshots.ScreenshotType;
import core.utils.ConfigReader;
import core.utils.LogUtils;
import core.utils.MouseActions;
import core.utils.SeleniumUtils;

@Listeners(ExtentTestNGListener.class)
public abstract class BaseTest implements ITestListener {
	 protected SeleniumUtils browser;
	    protected MouseActions pointer;
	    protected ExtentTestReportLogger testReporter;
	    protected String downloadPath = null;

	@BeforeMethod(alwaysRun = true)
	public void setup(Method method, ITestResult result) throws CustomException {

		// Create Log reference for current thread
		TestGlobals.setLogger(LogManager.getLogger(Thread.currentThread().getName()));

		// Driver Initialization
//		boolean needsDownload = 
//	            RequiresDownload.class.isAssignableFrom(method.getDeclaringClass());
		boolean enableDownload =
	            method.isAnnotationPresent(EnableDownload.class) ||
	            method.getDeclaringClass().isAnnotationPresent(EnableDownload.class);
		
		
		if(enableDownload) {			
			
			 downloadPath = new DownloadUtils().createUniqueDownloadDir(method.getName());
		}
		DriverManager.createDriver(ConfigReader.getPropertyValue("BROWSER"), downloadPath);
		

		browser = new SeleniumUtils(TestGlobals.getDriver(), TestGlobals.ELEMENTS_TIMEOUT);
		pointer = new MouseActions(TestGlobals.getDriver(), browser);
		testReporter = new ExtentTestReportLogger(browser);

		// Test Context
		boolean isRetry = TestGlobals.isRetryRun();
//        Boolean.parseBoolean(
//            result.getTestContext()
//                  .getSuite()
//                  .getParameter("isRetryRun"));

		String testName = result.getMethod().getMethodName();

		if (isRetry) {
			testName += " (RETRY)";
		}

		ExtentTest extentTest = ExtentManager.getInstance().createTest(testName);
		ExtentTestManager.setTest(extentTest);

		int retryCount = result.getMethod().getCurrentInvocationCount();

		TestContextManager.init(testName, retryCount);

	}

	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult result) {
		takeScreenshotOnFailure(result);
		cleanUp();
		
		
	}

	public void cleanUp() {
		ExtentTestManager.unload();
		TestContextManager.unload();
		DriverManager.quitDriver();
		TestGlobals.removeLogger();
	}

	public void takeScreenshotOnFailure(ITestResult result) {
		try {
			ScreenshotHelper.capture(browser, ScreenshotType.FAILURE, result.getThrowable().getMessage().split("\n")[0].trim());
		} catch (Exception e) {
			LogUtils.logStackTrace(e);
		}
	}
	
	protected String getDownloadPath() {
	    if (downloadPath == null) {
	        throw new IllegalStateException(
	            "Download path accessed in a test that does not enable downloads");
	    }
	    return downloadPath;
	}
	
	protected static void deleteDownloadTestFolder(Path downloadTestDir) {
	    if (downloadTestDir == null || !Files.exists(downloadTestDir)) return;
	    
	    // SAFETY: only allow deleting selenium-downloads
	    if (!downloadTestDir.getFileName().toString().equals("selenium-downloads")) {
	        throw new IllegalStateException("Refusing to delete unsafe directory: " + downloadTestDir);
	    }

	    try (Stream<Path> paths = Files.walk(downloadTestDir)) {
	        paths
	            .sorted(Comparator.reverseOrder()) // files → dir
	            .forEach(path -> {
	                try {
	                    Files.deleteIfExists(path);
	                } catch (IOException e) {
	                    throw new RuntimeException("Failed to delete: " + path, e);
	                }
	            });
	    } catch (IOException e) {
	        throw new RuntimeException("Failed to clean directory: " + downloadTestDir, e);
	    }
	}
	
	@AfterSuite
	public void suiteCleanUp() {
		deleteDownloadTestFolder(TestGlobals.DOWNLOADS_ROOT_DIR);
	}

}
