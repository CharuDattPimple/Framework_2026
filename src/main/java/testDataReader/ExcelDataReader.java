package testDataReader;

import utility.ExcelUtil;

import java.util.ArrayList;
import java.util.List;

public class ExcelDataReader {

    public static List<OrderTestData> getOrderData() {

        String filePath =
                "src/test/resources/testData/ui/OrderTestData.xlsx";

        ExcelUtil excel =
                new ExcelUtil(
                        filePath,
                        "OrderData"
                );

        List<OrderTestData> data =
                new ArrayList<>();

        for (int row = 1;
             row < excel.getRowCount();
             row++) {

            String testCase =
                    excel.getCellData(
                            row,
                            "TestCase"
                    );

            String productName =
                    excel.getCellData(
                            row,
                            "ProductName"
                    );

            String country =
                    excel.getCellData(
                            row,
                            "Country"
                    );

            String runMode =
                    excel.getCellData(
                            row,
                            "RunMode"
                    );

            // Skip RunMode = N
            if (!"Y".equalsIgnoreCase(runMode)) {
                continue;
            }

            // Skip incomplete rows
            if (productName.isBlank()
                    || country.isBlank()) {

                continue;
            }

            data.add(
                    new OrderTestData(
                            testCase,
                            productName,
                            country,
                            runMode
                    )
            );
        }

        excel.close();

        return data;
    }
}