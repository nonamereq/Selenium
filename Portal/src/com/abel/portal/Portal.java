package com.abel.portal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class Portal {
    public static void run(WebDriver driver) {
        ;

        driver.navigate().to("http://portal.aait.edu.et");

        WebElement username = driver.findElement(By.id("UserName"));
        WebElement password = driver.findElement(By.id("Password"));
        WebElement button = driver.findElement(By.cssSelector("button[class='btn btn-success']"));

        while(!username.isDisplayed()){
            driver.navigate().refresh();
        }

        username.sendKeys("ATR/1621/08");
        password.sendKeys("309646");
        button.click();

        driver.navigate().to("http://portal.aait.edu.et/Grade/GradeReport");

//        driver.navigate().to("file:///home/abel/Downloads/hackerrank/portal/Grade%20Report%20-%20Addis%20Ababa%20University.html");
        //print to command line

        List<WebElement> yrsm = driver.findElements(By.className("yrsm"));

        for(WebElement element : yrsm){
            System.out.println(element.findElement(By.tagName("p")).getText());

        }

        TableInHTML.formTable(driver);

    }
}
