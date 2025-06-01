package ge.tbc.testautomation.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;


public class RetryAnalyzer implements IRetryAnalyzer {
    // ინახავს რამდენჯერ გაეშვა ტესტი თავიდან
    private int retryCount = 0;

    @Override
    public boolean retry(ITestResult result) {
        // იღებს Retry ანოტაციას ტესტ მეთოდიდან
        Retry retry = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Retry.class);

        // თუ ანოტაცია არ არის, ტესტი არ გაეშვება თავიდან
        if (retry == null) {
            return false;
        }

        // ანოტაციიდან იღებს მაქსიმალური რაოდენობის მნიშვნელობას
        int maxRetryCount = retry.maxRetries();

        // თუ მაქსიმალური რაოდენობა არ არის მიღწეული
        if (retryCount < maxRetryCount) {
            System.out.println("ტესტის თავიდან გაშვება: " + result.getName() + ", მცდელობა: " + (retryCount + 1));
            retryCount++;
            return true;
        }

        // აღადგენს მთვლელს შემდეგი ტესტისთვის
        retryCount = 0;
        return false;
    }
}