package com.abel.news;

import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Map;

public abstract class  ArticleGrabber {

    protected WebDriver driver;

    public abstract ArrayList<Map<String, String>> run();
}
