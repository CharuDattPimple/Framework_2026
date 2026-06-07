package utility;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class ConfigReader {

    private static Map<String, Object> config;

    static {

        String env = System.getProperty("env", "qa").toLowerCase();

        String fileName = "env/" + env + ".yml";

        System.out.println("Loading Config File: " + fileName);

        Yaml yaml = new Yaml();

        InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream(fileName);

        System.out.println("InputStream: " + inputStream);

        config = yaml.load(inputStream);

        System.out.println("Config Data: " + config);
    }

    public static String getUrl() {

        Map<String,Object> app = (Map<String, Object>) config.get("application");

        return app.get("url").toString();
    }

    public static String getUsername() {

        Map<String,Object> cred = (Map<String, Object>) config.get("credentials");

        return cred.get("username").toString();
    }

    public static String getPassword() {

        Map<String,Object> cred = (Map<String, Object>) config.get("credentials");

        return cred.get("password").toString();
    }

    public static int getWait() {

        Map<String,Object> timeout = (Map<String, Object>) config.get("timeout");

        return Integer.parseInt(timeout.get("explicitWait").toString());
    }
}