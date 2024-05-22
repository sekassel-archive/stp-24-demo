package de.uniks.stp24;

import de.uniks.stp24.dagger.DaggerMainComponent;
import de.uniks.stp24.dagger.MainComponent;
import fr.brouillard.oss.cssfx.CSSFX;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.fulib.fx.FulibFxApp;

import javax.imageio.ImageIO;
import java.awt.*;
import java.nio.file.Path;
import java.util.logging.Level;

import static javafx.scene.input.KeyEvent.KEY_PRESSED;

public class App extends FulibFxApp {
    private MainComponent component;

    public App() {
        super();

        this.component = DaggerMainComponent.builder().mainApp(this).build();
    }

    // package-private - only for testing
    void setComponent(MainComponent component) {
        this.component = component;
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            super.start(primaryStage);

            registerRoutes(component.routes());

            stage().addEventHandler(KEY_PRESSED, event -> {
                if (event.getCode() == KeyCode.F5) {
                    this.refresh();
                }
            });

            primaryStage.getScene().getStylesheets().add(App.class.getResource("styles.css").toExternalForm());
            CSSFX.start(primaryStage);

            primaryStage.setWidth(1200);
            primaryStage.setHeight(800);

            // TODO add image/icon.png and uncomment
            setAppIcon(primaryStage);
            setTaskbarIcon();

            autoRefresher().setup(Path.of("src/main/resources/de/uniks/stp24"));

            if (component.loginService().autoLogin()) {
                show("/main-menu");
            } else {
                show("/login");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred while starting the application: " + e.getMessage(), e);
        }
    }


    private void setAppIcon(Stage stage) {
        final Image image = component.imageCache().get("icon.png");
        stage.getIcons().add(image);
    }

    private void setTaskbarIcon() {
        if (GraphicsEnvironment.isHeadless()) {
            return;
        }

        try {
            final Taskbar taskbar = Taskbar.getTaskbar();
            final java.awt.Image image = ImageIO.read(Main.class.getResource("icon.png"));
            taskbar.setIconImage(image);
        } catch (Exception ignored) {
        }
    }

    public MainComponent component() {
        return component;
    }
}
