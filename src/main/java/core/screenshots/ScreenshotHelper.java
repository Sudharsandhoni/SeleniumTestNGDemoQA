package core.screenshots;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import core.constants.TestGlobals;
import core.context.TestContext;
import core.context.TestContextManager;
import core.exceptions.CustomException;
import core.helpers.reports.ExtentTestManager;
import core.utils.ConfigReader;
import core.utils.FileUtils;
import core.utils.LogUtils;
import core.utils.SeleniumUtils;

public class ScreenshotHelper {
	private static final String DIR = "target\\screenshots";
	private static final boolean saveToDIR = ConfigReader
			.getPropertyValue("SAVE_SCREENSHOTS_TO_TARGET_SCREENSHOT_DIRECTORY").equalsIgnoreCase("true");

	private ScreenshotHelper() {
	}

	public static void capture(SeleniumUtils browser, ScreenshotType type, String description) {
		TestContext ctx = TestContextManager.get();
		int step = ctx.getCurrentStepNumber();
		ExtentTest stepNode = ctx.getCurrentStepNode();

	    if (stepNode == null) {
	        // Fallback: log at test level if not inside a step
	        stepNode = ExtentTestManager.getTest();
	    }
	    
		String base64 = browser.takeScreenshotBase64();

		String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH-mm-ss"));

		String logicalName = String.format("%s_S%02d_%s_%s_Retry%d_%s", ctx.getTestName(), step, sanitize(description),
				type.name(), ctx.getRetryCount(), time);

//		 String name = String.format(
//	                "%02d | %s | %s | Retry:%d | %s",
//	                step,
//	                type,
//	                ctx.getTestName(),
//	                ctx.getRetryCount(),
//	                description
//	        );

		// Extent attachment
		stepNode.info(buildStepHtml(step, description, type),
				MediaEntityBuilder.createScreenCaptureFromBase64String(base64, logicalName).build());

//		 ExtentTestManager.getTest()
//         .addScreenCaptureFromBase64String(base64, name);

//		 if(saveToDIR) {
//			 try {
//				String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd__HH_mm_ss_SSS"));
//				FileUtils.saveBase64ImgToPngFile(base64, DIR, timeStamp+ "_"+name);
//			 } catch (CustomException e) {
//				LogUtils.logStackTrace(e);
//			 }
//		 }

		if (saveToDIR) {
			try {
				FileUtils.saveBase64ImgToPngFile(base64, DIR, logicalName);
			} catch (CustomException e) {
				LogUtils.logStackTrace(e);
			}
		}

	}

	private static String sanitize(String s) {
		return s.replaceAll("[^a-zA-Z0-9]", "_");
	}

	private static String buildStepHtml(int step, String desc, ScreenshotType type) {

		StringBuilder sb = new StringBuilder();
	    sb.append(String.format("<b>Action:</b> %s<br/>", desc));
	    sb.append(String.format("<b>Type:</b> %s", type));

	    if (type == ScreenshotType.FAILURE
	            || type == ScreenshotType.ASSERTION_FAILURE
	            || type == ScreenshotType.NAVIGATION
	            || type == ScreenshotType.PAGE_LOAD) {

	        sb.append(String.format("<br/><b>URL:</b> %s",
	                TestGlobals.getDriver().getCurrentUrl()));
	    }

	    return sb.toString();
	}

}
