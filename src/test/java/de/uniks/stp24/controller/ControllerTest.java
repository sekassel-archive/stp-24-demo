package de.uniks.stp24.controller;

import de.uniks.stp24.App;
import javafx.stage.Stage;
import org.mockito.Spy;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.Locale;
import java.util.ResourceBundle;

public class ControllerTest extends ApplicationTest {

    @Spy
    public final App app = new App();
    @Spy
    ResourceBundle resources = ResourceBundle.getBundle("de/uniks/stp24/lang/main", Locale.ROOT);

    protected Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        super.start(stage);
        this.stage = stage;
        stage.requestFocus();
        app.start(stage);
    }
}
