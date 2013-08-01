import static org.junit.Assert.fail;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.Select;

public class HRMS {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		// System.setProperty("webdriver.firefox.bin", "C:/Program Files (x86)/Mozilla Firefox/firefox.exe");
		FirefoxProfile fp = new FirefoxProfile();
		fp.setPreference("browser.download.folderList", 2);
		fp.setPreference("browser.download.manager.showWhenStarting", false);
		fp.setPreference("browser.download.dir", "C:\\Users\\karan.gusani\\Downloads");
		fp.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/vnd.ms-excel");

		driver = new FirefoxDriver();
		baseUrl = "https://myhris.adrenalin.in/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testHRMS() throws Exception {
		driver.get(MessageFormat.format("{0}myadrenalin/login.aspx", baseUrl));
		driver.findElement(By.id("txtID")).sendKeys("F00774");
		driver.findElement(By.id("txtPwd")).sendKeys("#^)degrees1");
		driver.findElement(By.id("txtCompName")).clear();
		driver.findElement(By.id("txtCompName")).sendKeys("fractal");
		driver.findElement(By.id("LocalizedButton1")).click();
		driver.get(MessageFormat.format("{0}myadrenalin/eReports/DynamicConditioner.aspx?id=EMPLOYEE_DETAILS", baseUrl));
		new Select(driver.findElement(By.id("DDL_PAYGROUPID"))).selectByVisibleText("Fractal");
		new Select(driver.findElement(By.id("DDL_EMPSTATUS"))).selectByVisibleText("Active");
		driver.findElement(By.id("DP_DOJ_dateInput")).clear();
		driver.findElement(By.id("CHK_DIRECT")).click();
		driver.findElement(By.id("CHK_INDIRECT")).click();
		driver.findElement(By.id("CHK_ROLE")).click();
		new Select(driver.findElement(By.id("cbofileformat"))).selectByVisibleText("Export to Excel");
		driver.findElement(By.id("btnSubmit")).click();
		driver.get(MessageFormat.format("{0}myadrenalin/New_Index.aspx#", baseUrl));
		TimeUnit.SECONDS.sleep(30);
		boolean isExit = isElementPresent(By.id("lblExit"));
		if (isExit) {
			driver.findElement(By.id("lblExit")).click();
		}
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alert.getText();
		} finally {
			acceptNextAlert = true;
		}
	}
}
