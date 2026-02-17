package test.runners;

import static core.utils.LogUtils.logError;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import core.exceptions.CustomException;
import core.helpers.reports.ExtentTestReportLogger;
import core.utils.ConfigReader;
import core.utils.LogUtils;

public class RetryListener implements IAnnotationTransformer{
	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod){
		try {
			boolean immediateRetry = ConfigReader.getPropertyValue("RETRY_FAILED_TESTCASE_IMMEDIATELY").equalsIgnoreCase("true");
			if(immediateRetry) {

				annotation.setRetryAnalyzer(RetryAnalyzer.class);
				
			}
		} catch (Exception e) {
			logError(String.format("Retry failed -> %s", e.getMessage()));

		}
		
	}
}
