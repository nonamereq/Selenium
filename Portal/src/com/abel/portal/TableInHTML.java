package com.abel.portal;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TableInHTML {
    //driver with the page loaded
    public static void formTable(WebDriver driver){

        //Display only this semesters grade on the page
        List<WebElement> table = driver.findElements(By.tagName("tr"));
        WebElement firstTr = null; //first element with class yrsm
        StringBuilder tableHtml= new StringBuilder();

        //first build the wraper of the table
        tableHtml.append("<table class='");
        tableHtml.append(driver.findElement(By.tagName("table")).getAttribute("class"));
        tableHtml.append("'>\n");

        for(WebElement element : table){
            if(firstTr != null){
                //get the outerHTML of the element
                tableHtml.append(element.getAttribute("outerHTML"));
            }
            if(element.getAttribute("class").equals("yrsm")){
                if(firstTr == null) {
                    firstTr = element; //when we get it we begin to get the HTML
                    tableHtml.append(element.getAttribute("outerHTML"));
                }
                else {
                    break;
                }
            }
        }

        tableHtml.append("\n</table>"); // end your table

        ((JavascriptExecutor)driver).executeScript("document.body.innerHTML='';document.body.outerHTML=arguments[0]",
                                                    tableHtml.toString()); //first clean all body elements then make the inner HTML our table
    }
}
