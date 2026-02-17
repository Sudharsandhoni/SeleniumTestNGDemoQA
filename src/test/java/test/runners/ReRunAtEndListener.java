package test.runners;

import static core.utils.LogUtils.logError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.IExecutionListener;
import org.testng.ITestResult;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import core.constants.TestGlobals;
import core.exceptions.CustomException;
import core.helpers.listeners.ExtentTestNGListener;
import core.helpers.listeners.SuiteListener;
import core.helpers.reports.ExtentTestReportLogger;
import core.utils.ConfigReader;
import core.utils.LogUtils;

public class ReRunAtEndListener implements IExecutionListener{
	
	@Override
	public void onExecutionFinish() {
		try {
			boolean retryAtEnd = ConfigReader.getPropertyValue("RETRY_FAILED_TESTCASES_AT_END").equalsIgnoreCase("true");
			if(retryAtEnd) {
				
				if(TestGlobals.getFailedTests().isEmpty()) {
					return;
				}
				TestGlobals.markRetryRun();
				LogUtils.logInfo("Retrying all failed test cases");
				
				TestNG testng = new TestNG();
				XmlSuite suite = new XmlSuite();
				suite.setName("FailedTestSuite");
//				Map<String, String> params = new HashMap<>();
//				params.put("isRetryRun", "true");
//				suite.setParameters(params);
				XmlTest test = new XmlTest(suite);
				test.setName("Rerun Failed Tests");
				
				List<XmlClass> classes = new ArrayList<>();
				
				for(ITestResult result: TestGlobals.getFailedTests()) {
					classes.add(new XmlClass(result.getTestClass().getName()));
				}
				
				test.setClasses(classes);
				testng.setXmlSuites(List.of(suite));
				testng.addListener(new ExtentTestNGListener());
				testng.addListener(new SuiteListener());
				testng.addListener(new RetryListener());
				testng.run();
			}
		} catch (Exception e) {
			logError(String.format("Retry failed -> %s", e.getMessage()));
		}
	}
}
