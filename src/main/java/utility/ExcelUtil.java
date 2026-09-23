package utility;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtil {


        private final Workbook workbook;
        private final Sheet sheet;

        public ExcelUtil(String filePath, String sheetName) {

            try {
                FileInputStream fis =
                        new FileInputStream(filePath);

                workbook = WorkbookFactory.create(fis);
                sheet = workbook.getSheet(sheetName);

            } catch (IOException e) {

                throw new RuntimeException(
                        "Unable to read Excel file: " + filePath,
                        e
                );
            }
        }

        public int getRowCount() {
            return sheet.getPhysicalNumberOfRows();
        }

        public String getCellData(
                int rowNumber,
                String columnName) {

            Row headerRow = sheet.getRow(0);

            int columnIndex = -1;

            for (Cell cell : headerRow) {

                if (cell.getStringCellValue()
                        .trim()
                        .equalsIgnoreCase(columnName)) {

                    columnIndex = cell.getColumnIndex();
                    break;
                }
            }

            if (columnIndex == -1) {

                throw new RuntimeException(
                        "Column not found: " + columnName
                );
            }

            Cell cell =
                    sheet.getRow(rowNumber)
                            .getCell(columnIndex);

            if (cell == null) {
                return "";
            }

            DataFormatter formatter =
                    new DataFormatter();

            return formatter.formatCellValue(cell);
        }

        public void close() {

            try {
                workbook.close();

            } catch (IOException e) {

                throw new RuntimeException(
                        "Unable to close Excel workbook",
                        e
                );
            }
        }


}
