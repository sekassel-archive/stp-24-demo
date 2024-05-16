package de.uniks.stp24;

import javafx.stage.Stage;
import org.mockito.Spy;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.Locale;
import java.util.ResourceBundle;

public class ControllerTest extends ApplicationTest {

    @Spy
    protected App app = new App();
    @Spy
    protected ResourceBundle resources = ResourceBundle.getBundle("de/uniks/stp24/lang/main", Locale.ROOT);

    protected Stage stage;
    protected TestComponent testComponent;

    @Override
    public void start(Stage stage) throws Exception {
        super.start(stage);
        this.stage = stage;
        testComponent = (TestComponent) DaggerTestComponent.builder().mainApp(app).build();
        app.setComponent(testComponent);
        app.start(stage);
        stage.requestFocus();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        app.stop();
        app = null;
        stage = null;
        testComponent = null;
    }
}
