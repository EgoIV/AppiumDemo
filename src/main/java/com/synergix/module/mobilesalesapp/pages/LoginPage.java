package com.synergix.module.mobilesalesapp.pages;

import java.io.IOException;

import org.openqa.selenium.By;

import com.synergix.module.mobilesalesapp.ObjectRepositoryProvider;

import io.appium.java_client.android.AndroidDriver;

/**
 * @Author Charlie
 * @Date Nov 25, 2016 $
 */

public class LoginPage extends AbstractPage {

	private final ObjectRepositoryProvider objRepositoryProvider;
	
	public LoginPage(AndroidDriver driver, ObjectRepositoryProvider objRepoProvider) throws IOException {
		super(driver, objRepoProvider.get("elements.properties"));
		this.objRepositoryProvider = objRepoProvider;
	}

	public void setupServer(String host, String port) {
		click(By.xpath("//*[@resource-id='synergix.android:id/settingMenuItem']"));
		type(By.xpath("//android.widget.EditText[@resource-id='synergix.android:id/host']"), host, true);
		type(By.xpath("//*[@resource-id='synergix.android:id/port']"), port, false);
		click(By.xpath("//*[@resource-id='synergix.android:id/masterSettingConfirmButton']"));
	}

	public void login(String user, String password, String company) {
		type(By.xpath("//*[@resource-id='synergix.android:id/textUserName']"), user, false);
		type(By.xpath("//*[@resource-id='synergix.android:id/textPassword']"), password, false);
		click(By.xpath("//*[@resource-id='synergix.android:id/mainDb']"));
		click(By.xpath("//*[contains(@text,'" + company + "')]"));
		click(By.xpath("//*[@resource-id='synergix.android:id/btnLogin']"));
		waitForAndGetElement(By.xpath("//*[@resource-id='android:id/up']"), 120000);
	}
}
