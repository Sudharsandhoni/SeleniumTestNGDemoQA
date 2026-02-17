package core.helpers.reports;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;

import core.exceptions.CustomException;
import core.utils.ConfigReader;
import core.utils.LogUtils;

public class ExtentManager {
	private ExtentManager() {
	}

	private static ExtentReports extent;

	public static synchronized ExtentReports getInstance() {

		if (extent == null) {
			extent = new ExtentReports();
			ExtentSparkReporter reporter = new ExtentSparkReporter("target\\reports\\ExtentReport.html");
			reporter.viewConfigurer().viewOrder().as(new ViewName[] { ViewName.DASHBOARD, ViewName.TEST,
					ViewName.CATEGORY, ViewName.AUTHOR, ViewName.DEVICE, ViewName.EXCEPTION, ViewName.LOG }).apply();
			reporter.config().setJs("document.addEventListener('DOMContentLoaded', function() {"
					+ "  const normalize = () => {"
					+ "    const walker = document.createTreeWalker(document.body, NodeFilter.SHOW_TEXT, null, false);"
					+ "    let node;" + "    while ((node = walker.nextNode())) {" + "      let v = node.nodeValue;"
					+ "      if (!v) continue;" + "      // Replace any non-ASCII char before am/pm"
					+ "      v = v.replace(/[^\\x00-\\x7F]+(?=(am|pm))/gi, ' ');" + "      node.nodeValue = v;"
					+ "    }" + "  };" + "  normalize();" + "  setInterval(normalize, 500);" + "});");
			reporter.config().setEncoding("utf-8");
			reporter.config().setDocumentTitle("Automation Execution Reports");
			reporter.config().setReportName("UI Automation Report");
			// reporter.config().setTheme(Theme.STANDARD);
			// Locale.setDefault(Locale.US);
			// reporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, HH:mm '('zzz')'");
			try {
				reporter.loadJSONConfig(new File("src\\test\\resources\\config\\spark-config.json"));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

			extent.attachReporter(reporter);
			extent.setSystemInfo("OS", System.getProperty("os.name"));
			extent.setSystemInfo("Executed By", "Automation Framework");
			try {
				extent.setSystemInfo("Browser", ConfigReader.getPropertyValue("BROWSER"));
			} catch (Exception e) {
				LogUtils.logStackTrace(e);
				throw new RuntimeException("Error while creating/retrieving the Extent Report instance");
			}
		}

		return extent;
	}

}
