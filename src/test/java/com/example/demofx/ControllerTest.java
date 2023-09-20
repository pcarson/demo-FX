package com.example.demofx;

import com.example.demofx.dto.CategoryDto;
import com.example.demofx.dto.LanguageDto;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@ExtendWith(MockitoExtension.class)
class ControllerTest {

//    @Mock
//    private TableView<LanguageDto> fromXlsxTableView;
//
//    @Mock
//    private TableView<CategoryDto> fromDbTableView;
//
//    @Mock
//    private Button runButton;
//
//    @Mock
//    private Button downloadButton;
//
//    // Personal Details
//    @Mock
//    private TextField surname;
//    @Mock
//    private TextField firstName;
//    @Spy
//    private TextField middleNames = new TextField();
//    @Mock
//    private TextField personalStreet;
//    @Mock
//    private TextField personalCity;
//    @Mock
//    private TextField personalRegion;
//    @Mock
//    private TextField personalPostalCode;
//    @Mock
//    private TextField personalCountry;
//
//    @Mock
//    private TextField personalEmailAddress;
//    @Mock
//    private TextField personalPhoneNumber;
//
//    // ORG Details
//    @Mock
//    private TextField orgName;
//    @Mock
//    private TextField orgWebsiteUrl;
//    @Mock
//    private TextField orgStreet;
//    @Mock
//    private TextField orgCity;
//    @Mock
//    private TextField orgRegion;
//    @Mock
//    private TextField orgPostalCode;
//    @Mock
//    private TextField orgCountry;
//
//    @Mock
//    private TextField orgEmailAddress;
//    @Mock
//    private TextField orgPhoneNumber;
//
//    @Mock
//    private Tab qrTab;
//    @Mock
//    private ImageView qrImage;
//    @Mock
//    private TextArea qrContent;
//
//    @Mock
//    private TabPane tabPane;
//
//    @Mock
//    private TextField lastDownloadedLocation;
//
    @InjectMocks
    Controller controller;

    @BeforeAll
    public static void setUpJavaFXRuntime() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(() -> {
            latch.countDown();
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @AfterAll
    public static void tearDownJavaFXRuntime() {
        Platform.exit();
    }

    @Test
    @Disabled
    void testInitialisation() throws MalformedURLException {
        controller.initialize(new URL("http://localhost"), null);
    }
}