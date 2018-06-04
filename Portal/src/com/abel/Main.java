package com.abel;

import com.abel.news.*;
import com.abel.portal.Portal;
import com.abel.facebook.Facebook;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {
    public static void main(String[] args){
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        WebDriver driver = new ChromeDriver();
//        Portal.run(driver);
//        Facebook.run(driver);
        //News.run(driver);
        driver.close();
    }
}
