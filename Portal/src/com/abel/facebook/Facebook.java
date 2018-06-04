package com.abel.facebook;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class Facebook {
    private static final class BooleanWithSet{
        public boolean value;

        public BooleanWithSet(boolean value){
            this.value = value;
        }
        public void set(boolean bool){
            this.value = bool;
        }
    }
    public static void run(WebDriver driver) {
        driver.navigate().to("https://facebook.com/");
        driver.findElement(By.id("email")).sendKeys("email@facebook.com");
        driver.findElement(By.id("pass")).sendKeys("facebookpass");
        driver.findElement(By.id("login_form")).submit();

        driver.navigate().to("https://www.facebook.com/notifications");

        new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOf(driver.findElement(By.id("globalContainer"))));
        new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOf(driver.findElement(By.className("_33c"))));

        List<WebElement> elements = driver.findElement(By.id("globalContainer")).findElements(By.className("jewelItemNew"));
        ArrayList<String> notifications = new ArrayList<>();
        int count = 0;
        final BooleanWithSet finished = new BooleanWithSet(false);
        System.out.println(elements.isEmpty());

        for(WebElement element : elements){
            System.out.println("here");
            StringBuilder str = new StringBuilder("You have a new notification\n");

            if(count >= 5) {
                finished.set(true);
                break;
            }

            str.append(element.findElement(By.tagName("span")).getText() + '\n');
            str.append(element.findElement(By.tagName("a")).getAttribute("href"));

            System.out.println(str.toString());
            notifications.add(str.toString());
            ++count;
        }

        new WebDriverWait(driver, 20).until((WebDriver webDriver)-> {
            return finished.value;
        });

        Gmail.run(driver, notifications);
    }
}
