package basic.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Try to rerun test 3 times using TestNG annotation.
 * If test fails and retry max count is not reached,
 * then it increases current retry count,
 * sets test result as failed and rerun the test.
 */
public class Retry implements IRetryAnalyzer {
    int retryCount = 0;
    int maxRetryCount = 2;

    public boolean retry(ITestResult result) {
        if(!result.isSuccess()) {
            if(retryCount < maxRetryCount) {
                retryCount++;
                result.setStatus(ITestResult.FAILURE);
                return true;
            } else {
                result.setStatus(ITestResult.FAILURE);
            }
        }else {
            result.setStatus(ITestResult.SUCCESS);
        }
        return false;
    }
}
