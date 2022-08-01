package com.BusOnlineTicket.QA.utils;

import com.BusOnlineTicket.QA.base.BaseTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class Listerners extends BaseTest implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        Reporter.log("Method name is: "+result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Reporter.log("Status of execution is: "+result.getStatus());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ITestListener.super.onTestFailure(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ITestListener.super.onTestSkipped(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        ITestListener.super.onTestFailedWithTimeout(result);
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("on startt");
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("on finish");
    }
}
