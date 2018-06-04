package com.abel.news;
import org.openqa.selenium.WebDriver;

public class News{
    public static void run(WebDriver driver){
        while(true){
//            Economist.run(driver);
//            AfricanCapitalMarketsNews.run(driver);
            NewsPoster.post(driver, NewYorkTimes.run(driver), "The New York Times");
            NewsPoster.post(driver, Reuters.run(driver), "Reuters");
            try {
                Thread.sleep(1080000);
            } catch (InterruptedException e) {
            }
        }
    }
}
