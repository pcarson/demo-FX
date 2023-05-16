package com.example.demofx.factory;

import com.example.demofx.dto.CategoryDto;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class CategoryCheckBoxFactory implements Callback<TableColumn.CellDataFeatures<CategoryDto, CheckBox>, ObservableValue<CheckBox>> {
    @Override
    public ObservableValue<CheckBox> call(TableColumn.CellDataFeatures<CategoryDto, CheckBox> param) {
        CategoryDto company = param.getValue();
        CheckBox checkBox = new CheckBox();
        checkBox.selectedProperty().setValue(company.getSelected());
        checkBox.selectedProperty().addListener((ov, oldVal, newVal) -> {
            company.setSelected(newVal);
        });
        return new SimpleObjectProperty<>(checkBox);
    }
}