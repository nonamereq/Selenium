package com.abel.facebook;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.function.Function;

public class Gmail {
    String email, password;
    WebDriver driver;
    WebDriverWait waitDrive;

    Gmail(String email, String password, WebDriver driver){
        this.email = email;
        this.password = password;
        this.driver = driver;
        this.waitDrive = new WebDriverWait(this.driver, 20);
    }

    public void mrun(){
        driver.navigate().to("https://accounts.google.com/signin/v2/identifier?continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&service=mail&sacu=1&rip=1&flowName=GlifWebSignIn&flowEntry=ServiceLogin");
        fillFirstPage();

        waitDrive.until((WebDriver driver) -> !("username".equals(((JavascriptExecutor) driver).executeScript("return arguments[0].autocomplete",
                                                                driver.findElement(By.cssSelector("input[class='whsOnd zHQkBf']"))))));
        //second page
        fillSecondPage();

        String url = "https://mail.google.com/mail/#inbox";
        waitDrive.until(ExpectedConditions.urlToBe(url));
    }

    public void fillFirstPage(){
        driver.findElement(By.cssSelector("input[class='whsOnd zHQkBf']")).sendKeys(email);
        driver.findElement(By.xpath("//span[@class='RveJvd snByac' and contains(text(), 'Next')]")).click();
    }

    public void fillSecondPage(){
        driver.findElement(By.cssSelector("input[class='whsOnd zHQkBf']")).sendKeys(password);
        driver.findElement(By.xpath("//span[@class='RveJvd snByac' and contains(text(), 'Next')]")).click();
    }

    private void processConfirm(){
        try {
            WebElement element = driver.findElement(By.xpath("//input[contains(@value, 'HTML Gmail')]"));
            if (element.isDisplayed())
                element.click();
        }catch(Exception except){

        }
    }

    public void composeMessage(String notificationMessage){
        //get the html page
        driver.navigate().to("https://mail.google.com/mail/h/");

        processConfirm();

        driver.findElement(By.xpath("//a[contains(text(), 'Compose')]")).click();
        driver.findElement(By.id("to")).sendKeys(email+"@gmail.com");
        driver.findElement(By.cssSelector("textarea[title='Message Body']")).sendKeys("New Notification\n"+notificationMessage+"\n");
        driver.findElement(By.cssSelector("input[value='Send']")).click();
    }

    public static void run(WebDriver driver, List<String> notifications) {
        Gmail gmail = new Gmail("gmailemail", "gmailpass", driver);
        gmail.mrun();
        for(String notification : notifications){
            gmail.composeMessage(notification);
        }
    }
}
