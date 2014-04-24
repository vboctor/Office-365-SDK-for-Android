package com.microsoft.office365.test.integration;

import java.util.concurrent.Future;

import com.microsoft.office365.Logger;
import com.microsoft.office365.exchange.ContactClient;
import com.microsoft.office365.exchange.MailClient;
import com.microsoft.office365.test.integration.framework.TestCase;
import com.microsoft.office365.test.integration.framework.TestExecutionCallback;

public interface TestPlatformContext {

	String getServerUrl();

	String getClientId();

	String getRedirectUrl();

	String getTestListName();

	String getSiteRelativeUrl();

	MailClient getMailClient();

	// CalendarClient getCalendarClient();

	ContactClient getContactClient();

	Future<Void> showMessage(String message);

	void executeTest(TestCase testCase, TestExecutionCallback callback);

	void sleep(int seconds) throws Exception;

	Logger getLogger();

	String getAuthenticationMethod();

}
