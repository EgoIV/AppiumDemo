package com.synergix.mobilesalesapp.defs;

import com.google.inject.Inject;
import com.synergix.module.mobilesalesapp.PageProvider;
import com.synergix.module.mobilesalesapp.pages.LoginPage;

import cucumber.api.java.en.Given;

/**
 * @Author Charlie
 * @Date Nov 22, 2016
 *
 */

public class SubmitSalesSpotDefs {

	@Inject
	private PageProvider pageProvider;

	// @Given("^I want to open app on device$")
	// public void i_want_to_open_app_on_device() throws Throwable {
	// common.openApp();
	// }

	@Given("^I want to login to app$")
	public void i_want_to_login_to_app() throws Throwable {
		// login.loginMobileApp("172.18.0.21", "9080", "guest", "taskhub",
		// "AUTO3");
		System.out.println(1);
		LoginPage page = pageProvider.get(LoginPage.class);
		page.setupServer("172.18.0.21", "9080");
		page.login("guest", "taskhub", "AUTO3");
		// scenario.write(msg);
	}

}
