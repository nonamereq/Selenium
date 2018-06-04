package com.abel.news;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewYorkTimes extends  ArticleGrabber{
    NewYorkTimes(WebDriver driver){
        this.driver = driver;
    }

    public static ArrayList<Map<String, String>> run(WebDriver driver){
        return (new NewYorkTimes(driver).run());
    }

    @Override
    public ArrayList<Map<String, String>> run() {
        ArrayList<Map<String, String>> articleList = new ArrayList();

        driver.navigate().to("https://www.nytimes.com/section/world/africa");

        String date = DateTimeFormatter.ofPattern("MMM dd, yyyy").format(LocalDate.now());
//        String date = "May 30, 2018";
        List<WebElement> webElements;
        while(true) {
            try {
                webElements = driver.findElements(By.cssSelector("article[class='story theme-summary  ']"));
                if(webElements.isEmpty())
                    driver.navigate().refresh();
                else
                    break;
            } catch (Exception except) {
                driver.navigate().refresh();
            }
        }

        for(WebElement webElement : webElements){
            if(webElement.getText().contains(date)) {
                Map<String, String> urlMap = new HashMap<>();
                urlMap.put("url", webElement.findElement(By.xpath("./div/a[@class='story-link']")).getAttribute("href"));
                urlMap.put("title", webElement.findElement(By.cssSelector("h2[class='headline']")).getText());
                articleList.add(urlMap);
            }
        }

        StringBuilder stringBuilder = new StringBuilder();

        for(Map<String, String> urlMap : articleList){
            driver.navigate().to(urlMap.get("url"));
            try {
                urlMap.put("article", driver.findElement(By.className("story-body-supplemental")).getAttribute("innerHTML"));
            }catch(Exception except) {
                List<WebElement> list = driver.findElements(By.className("StoryBodyCompanionColumn"));
                for (WebElement element : list) {
                    stringBuilder.append(element.getAttribute("innerHTML"));
                }
                urlMap.put("article", stringBuilder.toString());
            }

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

}
