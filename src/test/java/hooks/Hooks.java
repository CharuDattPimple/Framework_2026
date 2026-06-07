package hooks;

import driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utility.ConfigReader;
import utility.ScreenshotUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

                scenario.attach(screenshot, "image/png", scenario.getName());

                logger.info("Screenshot captured: " + screenshotPath);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        DriverFactory.quitDriver();
    }
}