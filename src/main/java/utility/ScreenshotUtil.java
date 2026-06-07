package utility;

import driver.DriverFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ScreenshotUtil {

    public static String captureScreenshot(String scenarioName) {

        File sourceFile = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.FILE);

        String destinationPath = "target/screenshots/" + scenarioName.replaceAll(" ", "_") + "_" + System.currentTimeMillis() + ".png";

        File destinationFile = new File(destinationPath);

        try {

            destinationFile.getParentFile().mkdirs();

            Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {

            e.printStackTrace();
        }

        return destinationPath;
    }
}