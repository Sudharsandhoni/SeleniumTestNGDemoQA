package test.runners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import core.constants.TestGlobals;
import core.utils.LogUtils;


public class RetryAnalyzer implements IRetryAnalyzer{
	private static final int MAX_RETRY  = TestGlobals.RETRY_COUNT;
	private int retryCount = 1;
	@Override
	public boolean retry(ITestResult result) {
	
		if (retryCount <= MAX_RETRY) {
			LogUtils.logInfo(String.format("Retrying failed test case -> %d", retryCount));
            retryCount++;
            return true;
        }
		
		return false;
	}

}
