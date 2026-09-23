package utility;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class JsonUtil {

        public static String readJsonFile(String filePath) {

            try {
                return Files.readString(Paths.get(filePath));
            } catch (IOException e) {
                throw new RuntimeException(
                        "Unable to read JSON file: " + filePath, e);
            }
        }

        public static String replacePlaceholders(
                String json,
                Map<String, String> values) {

            for (Map.Entry<String, String> entry : values.entrySet()) {

                String placeholder =
                        "${" + entry.getKey() + "}";

                json = json.replace(
                        placeholder,
                        entry.getValue());
            }

            return json;
        }

}
