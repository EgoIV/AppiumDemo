/**
 *
 */
package com.synergix.module.mobilesalesapp;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.google.inject.Singleton;
import com.synergix.core.Utils;

import io.appium.java_client.android.AndroidDriver;

@Singleton
public class DriverProvider {
	private static AndroidDriver wd;

	protected DriverProvider() throws MalformedURLException {
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("device", "Nexus 7");
		caps.setCapability("deviceName", "B8692513DC41E30C");
		wd = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
		Utils.pause(5000);
	}

	public AndroidDriver get() {
		return wd;
	}

	/**
	 * @return Current driver
	 */
	public static AndroidDriver getInstance() {
		return wd;
	}
}
