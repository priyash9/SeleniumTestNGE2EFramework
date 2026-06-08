package org.example.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotHandler {

    public static String takeScreenShot(WebDriver driver, String fileName) throws IOException {
        TakesScreenshot takesScreenshot = ((TakesScreenshot) driver);
        File screenshot = takesScreenshot.getScreenshotAs(OutputType.FILE);
        String fileNameWithTime = updateFileNameWithDateTime(fileName);
        String finalDirectory = createDirectoryPath(fileNameWithTime);
        File targetFile = new File(finalDirectory);
        FileUtils.copyFile(screenshot, targetFile);
        return targetFile.getAbsolutePath();
    }

    private static String createDirectoryPath(String fileNameWithTime) {
        return System.getProperty("user.dir") + "/src/reports/" + LocalDate.now() + "/" + fileNameWithTime + ".png";
    }

    private static String updateFileNameWithDateTime(String fileName) {
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss"));
        String fileNameWithDT = dateTime + "_" + fileName;
        return fileNameWithDT.replaceAll("[+:^,\\s]", "");
    }
}
