package testDataReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FeatureFileGenerator {

    private static final String TEMPLATE_FILE =
            "src/test/resources/featureTemplates/Order_template.feature";

    private static final String GENERATED_FILE =
            "target/test-classes/features/Order.feature";

    public static void generateOrderFeature() {

        try {

            String template =
                    Files.readString(
                            Path.of(TEMPLATE_FILE)
                    );

            List<OrderTestData> testData =
                    ExcelDataReader.getOrderData();

            if (testData.isEmpty()) {

                throw new RuntimeException(
                        "No executable test data found in Excel"
                );
            }

            StringBuilder examples =
                    new StringBuilder();

            examples.append("\n\n    Examples:\n");

            examples.append(
                    "      | TestCase | ProductName | Country |\n"
            );

            for (OrderTestData data : testData) {

                examples.append(
                        "      | "
                                + data.getTestCase()
                                + " | "
                                + data.getProductName()
                                + " | "
                                + data.getCountry()
                                + " |\n"
                );
            }

            String generatedFeature =
                    template + examples;

            Path outputPath =
                    Path.of(GENERATED_FILE);

            Files.createDirectories(
                    outputPath.getParent()
            );

            Files.writeString(
                    outputPath,
                    generatedFeature
            );

            System.out.println(
                    "========================================"
            );

            System.out.println(
                    "Excel driven feature generated"
            );

            System.out.println(
                    "Test cases: "
                            + testData.size()
            );

            System.out.println(
                    "Output: "
                            + outputPath
            );

            System.out.println(
                    "========================================"
            );

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to generate feature file",
                    e
            );
        }
    }

    public static void main(String[] args) {

        generateOrderFeature();
    }
}