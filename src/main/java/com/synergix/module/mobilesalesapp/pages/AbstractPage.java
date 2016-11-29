package com.synergix.module.mobilesalesapp.pages;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.synergix.core.Constants;
import com.synergix.core.Utils;
import com.synergix.module.mobilesalesapp.ObjectRepositoryProvider;

import io.appium.java_client.android.AndroidDriver;

/**
 * @author <a href="mailto:haint21@fsoft.com.vn">Nguyen Thanh Hai</a>
 *
 *         Oct 28, 2016
 */
public abstract class AbstractPage {

	protected final AndroidDriver driver;
	
	protected final Properties repo;

	protected int loopCount;
	protected int timesToRepeat;
	protected static boolean firstTimeLogin;

	protected String baseUrl;
	protected int defaultTimeOut;
	protected int waitInterval;
	protected boolean ieFlag;
	protected Properties config;
	protected Properties elements;

	public AbstractPage(AndroidDriver driver, Properties repo) throws IOException {
		this.driver = driver;
		this.repo = repo;

		config = new ObjectRepositoryProvider().getProperties(Constants.CONFIGS);

		loopCount = Integer.parseInt(config.getProperty("TESTBASE_LOOP_COUNT"));
		timesToRepeat = Integer.parseInt(config.getProperty("TESTBASE_ACTION_REPEAT"));
		firstTimeLogin = Boolean.parseBoolean(config.getProperty("TESTBASE_FIRST_TIME_LOGIN"));
		defaultTimeOut = Integer.parseInt(config.getProperty("TESTBASE_DEFAULT_TIMEOUT"));
		waitInterval = Integer.parseInt(config.getProperty("TESTBASE_WAIT_INTERVAL"));
		ieFlag = Boolean.parseBoolean(config.getProperty("TESTBASE_IEFLAG"));
	}

	public WebElement getElement(Object locator) {
		By by = locator instanceof By ? (By) locator : By.xpath(locator.toString());
		WebElement elem = null;

		try {
			elem = driver.findElement(by);
		} catch (NoSuchElementException e) {
			System.out.println(e.getMessage());
		}

		return elem;
	}

	public void checkCycling(Exception e, int loopCountAllowed) {
		if (loopCount > loopCountAllowed) {
			System.out.println("Cycled: " + e.getMessage());
		}
		loopCount++;
	}

	public boolean isDisplay(Object locator) {
		boolean bool = false;
		WebElement e = getElement(locator);
		try {
			if (e != null)
				bool = e.isDisplayed();
		} catch (StaleElementReferenceException ex) {
			checkCycling(ex, 10);
			Utils.pause(waitInterval);
			isDisplay(locator);
		} finally {
			loopCount = 0;
		}
		return bool;
	}

	public WebElement getDisplayedElement(Object locator, Object... opParams) {
		By by = locator instanceof By ? (By) locator : By.xpath(locator.toString());
		WebElement e = null;
		try {
			if (by != null)
				e = driver.findElement(by);
			if (e != null) {
				if (isDisplay(by))
					return e;
			}
		} catch (NoSuchElementException ex) {
			System.out.println(ex.getMessage());
		} catch (StaleElementReferenceException ex) {
			checkCycling(ex, 10);
			Utils.pause(waitInterval);
			getDisplayedElement(locator);
		} finally {
			loopCount = 0;
		}

		return null;
	}

	public boolean isElementPresent(Object locator) {
		return getElement(locator) != null;
	}

	public boolean isElementNotPresent(Object locator) {
		return !isElementPresent(locator);
	}

	/*
	 * @opPram[0]: timeout
	 * 
	 * @opPram[1]: 0,1 0: No Assert 1: Assert
	 */
	public WebElement waitForAndGetElement(Object locator, int... opParams) {
		WebElement elem = null;
		int timeout = opParams.length > 0 ? opParams[0] : defaultTimeOut;
		int isAssert = opParams.length > 1 ? opParams[1] : 1;
		int notDisplayE = opParams.length > 2 ? opParams[2] : 0;
		for (int tick = 0; tick < timeout / waitInterval; tick++) {
			if (notDisplayE == 2) {
				elem = getElement(locator);
				// elem = getDisplayedElement(locator);
			} else {
				elem = getDisplayedElement(locator);
			}
			if (null != elem)
				return elem;
			Utils.pause(waitInterval);
		}
		if (isAssert == 1)
			assert false : ("Timeout after " + timeout + "ms waiting for element present: " + locator);
		System.out.println("cannot find element after " + timeout / 1000 + "s.");
		return null;
	}

	public List<WebElement> getElements(Object locator) {
		By by = locator instanceof By ? (By) locator : By.xpath(locator.toString());
		List<WebElement> elements = null;
		try {
			elements = driver.findElements(by);
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		return elements;
	}

	/**
	 * 
	 * @param locator
	 * @param opParams
	 * 			@opPram[0]: timeout @opPram[1]: 0,1 0: No Assert 1: Assert
	 * @return
	 */
	public List<WebElement> waitForAndGetElements(By locator, int... opParams) {
		waitForAndGetElement(locator, opParams);
		return getElements(locator);
	}

	/*
	 * @opPram[0]: timeout
	 * 
	 * @opPram[1]: 0,1 0: No Assert 1: Assert
	 */
	public WebElement waitForElementNotPresent(Object locator, int... opParams) {
		WebElement elem = null;
		int timeout = opParams.length > 0 ? opParams[0] : defaultTimeOut;
		int isAssert = opParams.length > 1 ? opParams[1] : 1;
		int notDisplayE = opParams.length > 2 ? opParams[2] : 0;

		for (int tick = 0; tick < timeout / waitInterval; tick++) {
			if (notDisplayE == 2) {
				elem = getElement(locator);
			} else {
				elem = getDisplayedElement(locator);
			}
			if (null == elem)
				return null;
			Utils.pause(waitInterval);
		}

		if (isAssert == 1)
			assert false : ("Timeout after " + timeout + "ms waiting for element not present: " + locator);
		System.out.println("Element doesn't disappear after " + timeout / 1000 + "s.");
		return elem;
	}

	public void click(Object locator, Object... opParams) {

		int notDisplay = (Integer) (opParams.length > 0 ? opParams[0] : 0);
		int isAssert = (Integer) (opParams.length > 1 ? opParams[1] : 1);

		try {
			WebElement element = waitForAndGetElement(locator, defaultTimeOut, isAssert, notDisplay);
			if (element.isEnabled()) {
				element.click();

			} else {
				System.out.println("Element is not enabled");
				click(locator, notDisplay);
			}
		} catch (StaleElementReferenceException e) {
			checkCycling(e, defaultTimeOut / waitInterval);
			Utils.pause(waitInterval);
			click(locator, notDisplay);
		} catch (ElementNotVisibleException e) {
			checkCycling(e, defaultTimeOut / waitInterval);
			Utils.pause(waitInterval);
			click(locator, notDisplay);
		} catch (NullPointerException e) {
			System.out.println(e.getStackTrace().toString());
			checkCycling(e, defaultTimeOut / waitInterval);
			Utils.pause(waitInterval);
			click(locator, notDisplay);
		} finally {
			loopCount = 0;
		}
	}

	public void type(Object locator, String value, boolean validate) {
		try {
			for (int loop = 1;; loop++) {
				if (loop >= timesToRepeat) {
					System.out.println("Timeout at type: " + value + " into " + locator);
				}
				WebElement element = waitForAndGetElement(locator, 5000, 0);
				if (element != null) {
					if (validate)
						element.clear();
					// element.click();
					element.sendKeys(value);
					if (!validate || value.equals(getValue(locator))) {
						break;
					}
				}
				System.out.println("Repeat action..." + loop + "time(s)");
				Utils.pause(waitInterval);
			}
		} catch (StaleElementReferenceException e) {
			checkCycling(e, defaultTimeOut / waitInterval);
			Utils.pause(waitInterval);
			type(locator, value, validate);
		} catch (ElementNotVisibleException e) {
			checkCycling(e, defaultTimeOut / waitInterval);
			Utils.pause(waitInterval);
			type(locator, value, validate);
		} finally {
			loopCount = 0;
		}
	}

	public String getValue(Object locator) {
		try {
			return waitForAndGetElement(locator).getAttribute("text");
		} catch (StaleElementReferenceException e) {
			checkCycling(e, defaultTimeOut / waitInterval);
			Utils.pause(waitInterval);
			return getValue(locator);
		} finally {
			loopCount = 0;
		}
	}

	public void rotate(ScreenOrientation orientation) {
		driver.rotate(orientation);
		Utils.pause(3000);
	}

	public void close() {
		this.driver.close();
	}
}
