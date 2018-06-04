package com.abel.news;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.*;

public class Reuters extends  ArticleGrabber {
    Reuters(WebDriver driver){
        this.driver = driver;
    }

    public ArrayList<Map<String, String>> run() {
        driver.navigate().to("https://www.reuters.com/news/archive/africa");


        List<WebElement> articles;
        while (true){
            try {
                articles = driver.findElements(By.className("story"));
                if (articles.isEmpty())
                    driver.navigate().refresh();
                else
                    break;
            } catch (Exception except) {
                driver.navigate().refresh();
            }
        }

        String amPm;
        if(Calendar.getInstance().get(Calendar.AM_PM) == Calendar.PM)
            amPm = "pm";
        else
            amPm = "am";

        ArrayList<Map<String, String>> articleList = new ArrayList();

        for(WebElement article : articles){
            Map<String, String> urlMap = new HashMap<>();
            String articleTime = article.findElement(By.cssSelector("time[class='article-time']")).getText();
            if(articleTime.toLowerCase().contains(amPm)){
                urlMap.put("url", article.findElement(By.xpath("./div/a")).getAttribute("href"));
                articleList.add(urlMap);
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        for(Map<String, String> urlMap : articleList){
            driver.navigate().to(urlMap.get("url"));
            urlMap.put("article", driver.findElement(By.className("container_19G5B")).getAttribute("outerHTML"));

            //title
            urlMap.put("title", driver.findElement(By.cssSelector("h1[class='headline_2zdFM']")).getText());

            //get the scripts
            List<WebElement> scripts = driver.findElements(By.xpath("//script"));
            stringBuilder.delete(0, stringBuilder.length());
            for(WebElement script: scripts){
                stringBuilder.append(script.getAttribute("outerHTML"));
            }
            urlMap.put("scripts", stringBuilder.toString());

            // get the styles
            List<WebElement> styles = driver.findElements(By.xpath("//link[@rel='stylesheet']"));
            stringBuilder.delete(0, stringBuilder.length());
            for(WebElement style: styles){
                stringBuilder.append(style.getAttribute("outerHTML"));
            }
            urlMap.put("styles", stringBuilder.toString());

        }
        return articleList;
    }

    public static ArrayList<Map<String, String>> run(WebDriver driver){
        return (new Reuters(driver)).run();
    }
}
