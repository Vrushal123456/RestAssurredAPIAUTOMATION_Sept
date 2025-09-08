package com.bookapi.report;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import org.testng.ITestListener;
import org.testng.ITestResult;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;

public class AllureFactory implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("=============== TEST CASE FAILED ================");
        Throwable throwable = result.getThrowable();

        if (throwable != null) {
            String message = throwable.getMessage();
            String stackTrace = org.testng.internal.Utils.longStackTrace(throwable, true);

            Allure.addAttachment("Failure Message",
                    new ByteArrayInputStream(message.getBytes(StandardCharsets.UTF_8)));
            Allure.addAttachment("Stacktrace",
                    new ByteArrayInputStream(stackTrace.getBytes(StandardCharsets.UTF_8)));
        }

        Allure.getLifecycle().updateTestCase(tc -> tc.setStatus(Status.FAILED));
    }
}
