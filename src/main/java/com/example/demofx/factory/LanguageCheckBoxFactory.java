package com.example.demofx.factory;

import com.example.demofx.dto.LanguageDto;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class LanguageCheckBoxFactory implements Callback<TableColumn.CellDataFeatures<LanguageDto, CheckBox>, ObservableValue<CheckBox>> {
    @Override
    public ObservableValue<CheckBox> call(TableColumn.CellDataFeatures<LanguageDto, CheckBox> param) {
        LanguageDto language = param.getValue();
        CheckBox checkBox = new CheckBox();
        checkBox.selectedProperty().setValue(language.getSelected());
        checkBox.selectedProperty().addListener((ov, oldVal, newVal) -> {
            language.setSelected(newVal);
        });
        return new SimpleObjectProperty<>(checkBox);
    }
}