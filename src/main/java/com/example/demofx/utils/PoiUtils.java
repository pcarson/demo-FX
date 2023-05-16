package com.example.demofx.utils;

import com.example.demofx.dto.LanguageDto;
import javafx.collections.ObservableList;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Encapsulate the POI XLSX processing to
 * make testing simpler.
 */
@Slf4j
public class PoiUtils {

    private PoiUtils() {
    }

    public static void populateListFromXlsx(ObservableList<LanguageDto> fromXlsxRows, String fileName) {

        try {
            var fis = PoiUtils.class.getClassLoader().getResourceAsStream(fileName);

            // Finds the workbook instance for XLSX file
            var myWorkBook = new XSSFWorkbook(fis);

            // Return first sheet from the XLSX workbook
            var mySheet = myWorkBook.getSheetAt(0);

            // Get iterator to all the rows in current sheet
            var rowIterator = mySheet.iterator();

            // Traversing over each row of XLSX file
            var rowPos = 0;
            while (rowIterator.hasNext()) {
                var row = (Row) rowIterator.next();

                var dto = new LanguageDto();

                // For each row, iterate through each columns
                var cellIterator = row.cellIterator();
                var cellPos = 0;
                while (cellIterator.hasNext()) {

                    var cell = (Cell) cellIterator.next();

                    var type = cell.getCellType();
                    if (rowPos > 0) {
                        switch (type.name()) {
                            case "STRING":
                                if (cellPos == 1) {
                                    dto.setDescription(cell.getStringCellValue());
                                }
                                if (cellPos == 0) {
                                    dto.setCode(cell.getStringCellValue());
                                }
                                break;
                            case "BOOLEAN":
                                log.info(cell.getBooleanCellValue() + "");
                                break;
                            default:
                                log.info(cell.getCellType().toString());

                        }
                        cellPos++;
                    }
                }
                if (rowPos > 0) {
                    fromXlsxRows.add(dto);
                }
                rowPos++;
            }
        } catch (Exception ex) {
            log.error("Couldn't process Excel file");
        }
    }
}