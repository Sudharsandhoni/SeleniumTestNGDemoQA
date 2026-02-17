package core.context;

import com.aventstack.extentreports.ExtentTest;

public class TestContext {
	private int stepCounter = 1;
	private int retryCount = 0;
	private String testName;
	private ExtentTest currentStepNode;

	public ExtentTest getCurrentStepNode() {
	    return currentStepNode;
	}

	public void setCurrentStepNode(ExtentTest node) {
	    this.currentStepNode = node;
	}
	
	public int nextStep() {
		return ++stepCounter;
	}
	
	public void resetSteps() {
		stepCounter = 1;
	}
	
	public int getRetryCount() {
		return retryCount;
	}
	
	public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
	}
	
	public String getTestName() {
        return testName;
    }
	
	public int getCurrentStepNumber() {
        return stepCounter;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }
}
