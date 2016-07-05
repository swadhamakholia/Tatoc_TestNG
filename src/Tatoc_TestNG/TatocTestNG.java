// Automate TATOC Basic course and apply test cases.

package Tatoc_TestNG;


import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class TatocTestNG {
	WebDriver driver;

	@BeforeClass
	public void setup(){

		driver = new FirefoxDriver();
		driver.get("http://10.0.1.86");

	}
	//-------------------------------------------------------------------------------------	
	
	@Test(description= "Verify homePage title")
	public void verifyHomepageTitle() {
		String expectedTitle= "TAP Utility Server";
		String actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);

	}

	@Test(description= "Verify welcome Page title")
	public void verifyCourseSelectionPageTitle() {
		driver.findElement(By.partialLinkText("tatoc")).click(); 
		String expectedTitle= "Welcome - T.A.T.O.C";
		String actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);

	}

	//-------------------------------------------------------------------------------------	
	// *** Grid Gate ***

	@Test(description= "Verify Grid gate title")
	public void verifyGridGatePageTitle() {
		driver.findElement(By.linkText("Basic Course")).click();
		String expectedTitle= "Grid Gate - Basic Course - T.A.T.O.C";
		String actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);

	}

	@Test(description= "Whether red box exists")
	public void testRedButtonDisplayed(){
		Boolean expected = true;
		Boolean actual = driver.findElement(By.cssSelector(".redbox")).isDisplayed();
		Assert.assertEquals(actual, expected);
	}

	@Test(description= "Whether green box exists")
	public void greencolorboxdisplayed(){
		Boolean actual , expected ;
		expected = true ;
		WebElement box1 = driver.findElement(By.cssSelector(".greenbox"));
		actual = box1.isDisplayed();
		Assert.assertEquals(actual, expected);
		driver.findElement(By.className("greenbox")).click();
	}


	//-------------------------------------------------------------------------------------
	// *** Frame Dungeon ***

	@Test(description= "Frame Dungeon Page Displayed")
	public void testFrameDungeonTitle(){
		String expectedTitle = "Frame Dungeon - Basic Course - T.A.T.O.C";
		String actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);
	}

	@Test(description= "Verifying color box exists")
	public void verifycolorboxdisplayed(){

		Boolean actual , expected ;
		expected = true ;
		driver.switchTo().frame("main");
		WebElement box1 = driver.findElement(By.cssSelector("#answer"));
		actual = box1.isDisplayed();
		Assert.assertEquals(actual, expected);
	}

	@Test(description= "Whether clicking repaint box2 actully repaints the box")
	public void testBox2RepaintWorks(){
		driver.switchTo().parentFrame();
		driver.switchTo().frame("main");
		driver.switchTo().frame("child");
		WebElement box2= driver.findElement(By.cssSelector("#answer"));
		String color1 = box2.getAttribute("class");
		driver.switchTo().parentFrame();
		driver.findElement(By.partialLinkText("Repaint Box 2")).click();
		driver.switchTo().frame("child");
		box2 = driver.findElement(By.cssSelector("#answer"));
		String color2 =  box2.getAttribute("class");
		driver.switchTo().parentFrame();
		Assert.assertNotEquals(color1, color2, "repaint works");
	}

	@Test(description= "Proceeds to next Page when the colors are same")
	public void testDragAroundTitle() {
		String color1, color2;
		boolean color=true;
		do{
			color1 = driver.findElement(By.cssSelector("#answer")).getAttribute("class");
			driver.switchTo().frame("child");
			color2 = driver.findElement(By.cssSelector("#answer")).getAttribute("class");
			if(!(color1.equals(color2))) {
				driver.switchTo().parentFrame();
				driver.findElement(By.partialLinkText("Repaint Box 2")).click();
			}
			else {
				driver.switchTo().parentFrame();
				break;
			}

		} while(color);
		Assert.assertTrue(color1.equals(color2));
		driver.findElement(By.partialLinkText("Proceed")).click();
	}
	//-------------------------------------------------------------------------------------
	// *** Drag Around ***

	@Test(description= "Whether moves to Drag Around page")
	public void verifyDragAroundPage() {
		String expectedTitle= "Drag - Basic Course - T.A.T.O.C";
		String actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);
	}

	@Test(description= "Whether Drag box displayed")
	public void verifyDragBoxdisplayed(){

		Boolean actual , expected ;
		expected = true ;
		WebElement dragbox = driver.findElement(By.cssSelector("#dragbox"));
		actual = dragbox.isDisplayed();
		Assert.assertEquals(actual, expected);
	}

	@Test(description= "Whether Drop Box displayed")
	public void verifydropboxdisplayed(){ 
		Boolean actual , expected ;
		expected = true ;
		WebElement dropbox = driver.findElement(By.cssSelector("#dropbox"));
		actual = dropbox.isDisplayed();
		Assert.assertEquals(actual, expected);

	}

	@Test(description= "Whether Drag box lies within drop box")
	public void verifyDragging()
	{
		Actions action = new Actions(driver);
		WebElement From = driver.findElement(By.id("dragbox"));
		WebElement To = driver.findElement(By.id("dropbox"));
		action.dragAndDrop(From, To).build().perform();

		Point drag= From.getLocation();
		int xdragbox = drag.getX();
		int ydragbox = drag.getY();

		Point drop= To.getLocation();
		int xdropbox = drop.getX();
		int ydropbox = drop.getY();

		String cond = null ; 
		if (ydragbox >= ydropbox && xdragbox >= xdropbox)
			cond = "true" ; 

		Assert.assertEquals(cond,"true");

		driver.findElement(By.partialLinkText("Proceed")).click();
	}

	//-------------------------------------------------------------------------------------
	// *** Popup - Basic Course - T.A.T.O.C ***

	@Test(description= "Whether Proceeded to Windows Page")
	public void popupWindowsPage() {
		String expectedTitle= "Windows - Basic Course - T.A.T.O.C";
		String actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);
	}


	@Test(description= "Whether moves to Popup Window, text box is there and text value is set ")
	public void verifyPopUpWindow() {
		driver.findElement(By.xpath("//div[@class='page']/a[text()='Launch Popup Window']")).click();
		String expectedTitle= "Popup - Basic Course - T.A.T.O.C";
		String handle = driver.getWindowHandle();
		for(String webhand: driver.getWindowHandles()) {
			driver.switchTo().window(webhand);
		}
		String actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);

		WebElement  textbox = driver.findElement(By.cssSelector("#name"));
		Boolean actual = textbox.isDisplayed();
		Boolean expected = true ; 
		Assert.assertEquals(actual, expected);

		driver.findElement(By.cssSelector("#name")).sendKeys("your name");
		driver.findElement(By.cssSelector("#submit")).click();

		driver.switchTo().window(handle);
		String name;
		if (driver instanceof JavascriptExecutor) {
			name= (String)((JavascriptExecutor)driver).executeScript("return name");
		} else {
			throw new IllegalStateException("This driver does not support JavaScript!");
		}
		Assert.assertEquals(name, "your name");

		driver.findElement(By.linkText("Proceed")).click();
	}
	//------------------------------------------------------------------------------------
	// *** Cookie Handling ***

	@Test(description= "Whether Proceeded to Cookie Handling Page")
	public void CookieHandlingPage() {
		String expectedTitle= "Cookie Handling - Basic Course - T.A.T.O.C";
		String actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);
	}

	@Test(description= "Whether cookie is set and Proceed") 
	public void verifyCookieSet(){
		driver.findElement(By.partialLinkText("Generate Token")).click();
		String token=driver.findElement(By.cssSelector("#token")).getText();
		String[] tokenarr = token.split(": ");
		Cookie cookie = new Cookie("Token",tokenarr[1]);
		String expectedCookieValue="Token="+tokenarr[1];
		driver.manage().addCookie(cookie);

		String actualCookieValue= (String)((JavascriptExecutor)driver).executeScript("return document.cookie");
		Assert.assertEquals(actualCookieValue, expectedCookieValue);
		driver.findElement(By.partialLinkText("Proceed")).click();
	}

	//------------------------------------------------------------------------------------
	@AfterTest
	public void closing(){
		driver.quit();
	}
}