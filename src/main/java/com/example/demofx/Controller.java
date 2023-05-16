package com.example.demofx;

import com.example.demofx.dto.CategoryDto;
import com.example.demofx.dto.LanguageDto;
import com.example.demofx.enums.AddressType;
import com.example.demofx.exception.QueryFailureException;
import com.example.demofx.exception.ValidationException;
import com.example.demofx.factory.CategoryCheckBoxFactory;
import com.example.demofx.factory.LanguageCheckBoxFactory;
import com.example.demofx.repository.DbRepository;
import com.example.demofx.utils.Constants;
import com.example.demofx.utils.ImageUtils;
import com.example.demofx.utils.PoiUtils;
import com.example.demofx.utils.VCardUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

@Slf4j
public class Controller implements Initializable {

    @FXML
    private TableView<LanguageDto> fromXlsxTableView;

    @FXML
    private TableView<CategoryDto> fromDbTableView;

    @FXML
    private Button runButton;

    @FXML
    private Button downloadButton;

    // Personal Details
    @FXML
    private TextField surname;
    @FXML
    private TextField firstName;
    @FXML
    private TextField middleNames;
    @FXML
    private TextField personalStreet;
    @FXML
    private TextField personalCity;
    @FXML
    private TextField personalRegion;
    @FXML
    private TextField personalPostalCode;
    @FXML
    private TextField personalCountry;

    @FXML
    private TextField personalEmailAddress;
    @FXML
    private TextField personalPhoneNumber;

    // ORG Details
    @FXML
    private TextField orgName;
    @FXML
    private TextField orgWebsiteUrl;
    @FXML
    private TextField orgStreet;
    @FXML
    private TextField orgCity;
    @FXML
    private TextField orgRegion;
    @FXML
    private TextField orgPostalCode;
    @FXML
    private TextField orgCountry;

    @FXML
    private TextField orgEmailAddress;
    @FXML
    private TextField orgPhoneNumber;

    @FXML
    private Tab qrTab;
    @FXML
    private ImageView qrImage;
    @FXML
    private TextArea qrContent;

    @FXML
    private TabPane tabPane;

    @FXML
    private TextField lastDownloadedLocation;

    private ObservableList<LanguageDto> fromXlsxRows = FXCollections.observableArrayList();
    private ObservableList<CategoryDto> fromDbRows = FXCollections.observableArrayList();

    private static final String FXML_NAME = "/demo-layout.fxml";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Load environmental properties to form
        middleNames.setText("");
        surname.setText("");
        firstName.setText("");

        try {
            // Just WARN during initial startup
            saveSettingsAndReloadData();
        } catch (QueryFailureException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING, ex.getMessage(), ButtonType.CANCEL);
            alert.showAndWait();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Unable to save settings/re-load data " + ex.getMessage(), ButtonType.CANCEL);
            alert.showAndWait();
        }

        prepareAndPopulateXlsxLanguageDataTable();

        prepareButtonActions();

        qrContent.setText("{}");
        personalStreet.setText("");

        personalEmailAddress.setText("");
        personalPhoneNumber.setText("");

    }

    private void prepareAndPopulateDefaults() throws QueryFailureException {

        fromDbRows.removeAll();
        fromDbTableView.setItems(fromDbRows);
        initialiseDBcategoriesTableMapping();
        try {
            List<HashMap<String, Object>> rs = new DbRepository().query(Constants.CATEGORY_QUERY);
            for (HashMap<String, Object> oneItem : rs) {
                CategoryDto dto = new CategoryDto();
                dto.setCategoryName((String) oneItem.get(Constants.CATEGORY_NAME));
                fromDbRows.add(dto);
            }
            if (fromDbRows.size() == 0) {
                log.warn("No data found for query {}", Constants.CATEGORY_QUERY);
                throw new QueryFailureException("Could not retrieve data from Interests table");
            }
        } catch (Exception ex) {
            log.error("Exception during attempt to query " + Constants.CATEGORY_QUERY, ex);
            throw new QueryFailureException("Could not retrieve Interests data from DB");
        }

    }

    private void prepareAndPopulateXlsxLanguageDataTable() {

        initialiseXlsxLanguageDataTableMapping();

        PoiUtils.populateListFromXlsx(fromXlsxRows, "xls/languages.xlsx");
    }

    private void prepareButtonActions() {

        runButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        try {
                            generateQRImageFromData();
                            // Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "QR has been generated ...", ButtonType.OK);
                            // alert.showAndWait();
                            downloadButton.setVisible(true);
                            downloadButton.setDisable(false);
                        } catch (Exception ex) {
                            var alert = new Alert(Alert.AlertType.ERROR, "Unable to generate QR " + ex.getMessage(), ButtonType.CANCEL);
                            alert.showAndWait();
                        }
                    }
                });

        downloadButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        try {
                            var node = (Node) e.getSource();
                            var thisStage = (Stage) node.getScene().getWindow();
                            lastDownloadedLocation.setText(downloadQrImage(thisStage));
                            // Displays behind current Stage
//                            var alert = new Alert(Alert.AlertType.INFORMATION, "QR saved to " + lastDownloadedLocation.getText(), ButtonType.OK);
//                            var stage = (Stage) alert.getDialogPane().getScene().getWindow();
//                            stage.setAlwaysOnTop(true);
//                            alert.showAndWait();
                        } catch (Exception ex) {
                            var alert = new Alert(Alert.AlertType.ERROR, "Unable to save QR " + ex.getMessage(), ButtonType.CANCEL);
                            alert.showAndWait();
                        }
                    }
                });

    }

    private String downloadQrImage(Stage primaryStage) throws IOException {
        var fileChooser = new FileChooser();
        fileChooser.setTitle("Select a folder and filename");
        var selectedDir = fileChooser.showSaveDialog(primaryStage);
        ImageUtils.downloadImage(qrImage.getImage(), selectedDir);
        return selectedDir.getAbsolutePath();
    }

    /*
    Only from DB details need to be reloaded on save of settings ....
     */
    private void saveSettingsAndReloadData() throws QueryFailureException {
        prepareAndPopulateDefaults();
    }

    private void initialiseDBcategoriesTableMapping() {

        fromDbTableView.setItems(fromDbRows);

        fromDbTableView.setEditable(false);

        TableColumn<CategoryDto, CheckBox> checkBoxColumn = new TableColumn<CategoryDto, CheckBox>();
        checkBoxColumn.setCellValueFactory(new CategoryCheckBoxFactory());

        TableColumn interestNameCol = new TableColumn("Name");
        interestNameCol.setMinWidth(300);
        interestNameCol.setCellValueFactory(
                new PropertyValueFactory<CategoryDto, String>(Constants.CATEGORY_NAME));

        fromDbTableView.getColumns().addAll(checkBoxColumn, interestNameCol);
    }

    private void initialiseXlsxLanguageDataTableMapping() {

        fromXlsxTableView.setItems(fromXlsxRows);
        fromXlsxTableView.setEditable(false);

        TableColumn<LanguageDto, CheckBox> checkBoxColumn = new TableColumn<LanguageDto, CheckBox>();
        checkBoxColumn.setCellValueFactory(new LanguageCheckBoxFactory());

        TableColumn codeCol = new TableColumn(Constants.LANGUAGE_CODE_NAME);
        codeCol.setMinWidth(100);
        codeCol.setCellValueFactory(
                new PropertyValueFactory<LanguageDto, String>(Constants.LANGUAGE_CODE_NAME));

        TableColumn nameCol = new TableColumn(Constants.LANGUAGE_NAME);
        nameCol.setMinWidth(300);
        nameCol.setCellValueFactory(
                new PropertyValueFactory<LanguageDto, String>(Constants.LANGUAGE_NAME));

        fromXlsxTableView.getColumns().addAll(checkBoxColumn, codeCol, nameCol);

    }

    private void generateQRImageFromData() throws Exception {

        qrContent.setText(prepareVCardProperties());

        qrImage.setImage(SwingFXUtils.toFXImage(ImageUtils.generateQRCodeImage(qrContent.getText()), null));

        navigate();
    }


    private String prepareVCardProperties() throws ValidationException {

        validateEnteredDataPreVCard();

        // see https://en.wikipedia.org/wiki/VCard for rules
        var contents = new StringBuilder();
        contents.append(VCardUtils.formatOpenTags());

        // Formatted Name
        contents.append(VCardUtils.formatFormattedName(firstName.getText(),
                middleNames.getText(),
                surname.getText()));

        // Display Name
        contents.append(VCardUtils.formatDisplayName(firstName.getText(),
                middleNames.getText(),
                surname.getText()));

        // Personal Email
        if (!personalEmailAddress.getText().isEmpty()) {
            contents.append(VCardUtils.formatEmailAddress(personalEmailAddress.getText(), AddressType.HOME.name()));
        }

        // Personal Phone
        if (!personalPhoneNumber.getText().isEmpty()) {
            contents.append(VCardUtils.formatPhoneNumber(personalPhoneNumber.getText(), AddressType.HOME.name()));
        }

        // Personal Address
        var formattedPersonalAddress = VCardUtils.prepareAddress(personalStreet.getText(),
                personalCity.getText(),
                personalRegion.getText(),
                personalPostalCode.getText(),
                personalCountry.getText());
        if (!formattedPersonalAddress.equals(VCardUtils.EMPTY_FORMATTED_ADDRESS)) {
            contents.append(VCardUtils.formatAddress(formattedPersonalAddress, AddressType.HOME.name()));
        }

        // ORG name
        if (!orgName.getText().isEmpty()) {
            contents.append(VCardUtils.formatOrgName(orgName.getText()));
        }

        // ORG url
        if (!orgWebsiteUrl.getText().isEmpty()) {
            contents.append(VCardUtils.formatUrl(orgWebsiteUrl.getText()));
        }

        // Org Email
        if (!orgEmailAddress.getText().isEmpty()) {
            contents.append(VCardUtils.formatEmailAddress(orgEmailAddress.getText(), AddressType.WORK.name()));
        }

        // Org Phone
        if (!orgPhoneNumber.getText().isEmpty()) {
            contents.append(VCardUtils.formatPhoneNumber(orgPhoneNumber.getText(), AddressType.WORK.name()));
        }

        // Organisation
        var formattedOrgAddress = VCardUtils.prepareAddress(orgStreet.getText(),
                orgCity.getText(),
                orgRegion.getText(),
                orgPostalCode.getText(),
                orgCountry.getText());
        if (!formattedOrgAddress.equals(VCardUtils.EMPTY_FORMATTED_ADDRESS)) {
            contents.append(VCardUtils.formatAddress(formattedOrgAddress, AddressType.WORK.name()));
        }

        var languages = new StringBuilder();
        for (LanguageDto dto : fromXlsxRows) {
            if (dto.getSelected() != null && dto.getSelected()) {
                if (languages.length() > 0) {
                    languages.append(VCardUtils.COMMA);
                }
                languages.append(dto.getCode());
            }
        }

        if (!languages.toString().isEmpty()) {
            // Languages
            contents.append(VCardUtils.LANG);
            contents.append(VCardUtils.COLON);
            contents.append(languages.toString());
            contents.append(VCardUtils.CRLF);
        }

        var categories = new StringBuilder();
        for (CategoryDto dto : fromDbRows) {
            if (dto.getSelected() != null && dto.getSelected()) {
                if (languages.length() > 0) {
                    languages.append(VCardUtils.COMMA);
                }
                languages.append(dto.getCategoryName());
            }
        }

        if (!categories.toString().isEmpty()) {
            // Categories
            contents.append(VCardUtils.CATEGORIES);
            contents.append(VCardUtils.COLON);
            contents.append(categories.toString());
            contents.append(VCardUtils.CRLF);
        }

        // End
        contents.append(VCardUtils.formatCloseTags());

        return contents.toString();
    }

    //TODO - add more validation ...
    private void validateEnteredDataPreVCard() throws ValidationException {

        if (surname.getText().isEmpty()) {
            throw new ValidationException("A Family Name is required.");
        }
        if (firstName.getText().isEmpty()) {
            throw new ValidationException("A Given Name is required.");
        }
        if (!personalEmailAddress.getText().isEmpty()) {
            if (!EmailValidator.getInstance().isValid(personalEmailAddress.getText())) {
                throw new ValidationException("Personal Email Address does not seem to be valid.");
            }
        }
        if (!orgEmailAddress.getText().isEmpty()) {
            if (!EmailValidator.getInstance().isValid(orgEmailAddress.getText())) {
                throw new ValidationException("Organisation Email Address does not seem to be valid.");
            }
        }
    }

    public void navigate() {
        tabPane.getSelectionModel().select(qrTab);
    }

}