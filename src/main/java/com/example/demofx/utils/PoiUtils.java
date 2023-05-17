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
            var sheet = myWorkBook.getSheetAt(0);

            for (Row row : sheet) {
                fromXlsxRows.add(processRow(row));
            }
        } catch (Exception ex) {
            log.error("Couldn't process Excel file");
        }
    }

    private static LanguageDto processRow(Row row) {
        final LanguageDto dto = new LanguageDto();
        var iter = 0;
        for (Cell cell : row) {
            switch (cell.getCellType()) {
                case STRING:
                    if (iter == 0) {
                        dto.setCode(cell.getStringCellValue());
                    }
                    if (iter == 1) {
                        dto.setDescription(cell.getStringCellValue());
                    }
                    break;
                default:
                    // not sure what this is, but we're not interested
            }
            iter++;
        }
        return dto;
    }

}