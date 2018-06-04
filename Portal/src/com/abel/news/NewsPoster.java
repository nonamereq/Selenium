package com.abel.news;

import javafx.util.Pair;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Map;

public class NewsPoster{
    public static void post(WebDriver driver, ArrayList<Map<String, String>> articles, String from){
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        for(Map<String, String> article : articles) {
            System.out.println("here newsposter 1");
            driver.navigate().to("http://localhost:8080/add.html");
            System.out.println("here newsposter 2");
            executor.executeScript("arguments[0].setAttribute('value', arguments[1])", driver.findElement(By.id("title")), article.get("title"));
            driver.findElement(By.id("from")).sendKeys(from);
            driver.findElement(By.id("article")).sendKeys(article.get("article"));
            driver.findElement(By.id("link")).sendKeys(article.get("url"));
            driver.findElement(By.id("stylesheet")).sendKeys(article.get("styles"));
            driver.findElement(By.id("script")).sendKeys(article.get("scripts"));
            driver.findElement(By.id("form-submit")).click();
        }
    }
}
