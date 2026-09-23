package hooks;

import driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import utility.ConfigReader;
import utility.ScreenshotUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Hooks {

    public static Logger logger =
            LogManager.getLogger(Hooks.class);

    @Before
    public void setup() {

        DriverFactory.initDriver();

        DriverFactory.getDriver()
                .get(ConfigReader.getUrl());
    }

    @After
    public void tearDown(Scenario scenario) {

        try {

            if (scenario.isFailed()) {

                String screenshotPath = ScreenshotUtil.captureScreenshot(scenario.getName());

                byte[] screenshot = Files.readAllBytes(Paths.get(screenshotPath));

                // Cucumber HTML report
                scenario.attach(screenshot, "image/png", scenario.getName());

                // Attach screenshot to Allure report
                Allure.addAttachment(
                        "Failure Screenshot",
                        "image/png",
                        new ByteArrayInputStream(screenshot),
                        ".png"
                );

                logger.info("Screenshot captured: " + screenshotPath);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        DriverFactory.quitDriver();
    }


}