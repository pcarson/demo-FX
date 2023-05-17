package com.example.demofx.utils;

import com.example.demofx.dto.LanguageDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PoiUtilsTest {

    @Test
    public void testPopulationOfObservableList() {
        ObservableList<LanguageDto> list = FXCollections.observableArrayList();
        assertEquals(0, list.size());
        PoiUtils.populateListFromXlsx(list, "xls/languages.xlsx");
        assertEquals(268, list.size());
    }

    @Test
    public void testPopulationOfObservableListFailsSilently() {
        ObservableList<LanguageDto> list = FXCollections.observableArrayList();
        assertEquals(0, list.size());
        PoiUtils.populateListFromXlsx(list, "xls/does-not-exist.xlsx");
        assertEquals(0, list.size());
    }

}