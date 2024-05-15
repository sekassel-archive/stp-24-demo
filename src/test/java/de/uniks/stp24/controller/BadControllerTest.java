package de.uniks.stp24.controller;

import de.uniks.stp24.ControllerTest;
import javafx.scene.Node;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.util.DebugUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.function.Function;

public class BadControllerTest extends ControllerTest {
    @Test
    void bad() {

        try {
            FxAssert.verifyThat("some button that does not exist", Node::isVisible, collectInfos());
        } catch (Exception ex) {
            printInfos();
        }
    }

    public void printInfos() {
        System.err.println(collectInfos().apply(new StringBuilder()));
    }

    public Function<StringBuilder, StringBuilder> collectInfos() {
        final Path dir = Paths.get("build", "failed-test-screenshots");
        try {
            Files.createDirectories(dir);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return DebugUtils.compose(
            DebugUtils.insertHeader("Context:"),
            DebugUtils.showKeysPressedAtTestFailure(this),
            DebugUtils.showMouseButtonsPressedAtTestFailure(this),
            DebugUtils.showFiredEvents(),
            DebugUtils.saveWindow(
                stage,
                () -> dir.resolve(getClass().getSimpleName() + " " + LocalTime.now() + ".png"),
                "\t"));
    }
}
