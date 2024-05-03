package de.uniks.stp24.component;

import de.uniks.stp24.model.User;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.fulib.fx.annotation.controller.Component;
import org.fulib.fx.constructs.listview.ReusableItemComponent;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

@Component(view = "User.fxml")
public class UserComponent extends HBox implements ReusableItemComponent<User> {

    @FXML
    ImageView avatarView;
    @FXML
    Text nameText;

    @Inject
    public UserComponent() {

    }

    @Override
    public void setItem(@NotNull User user) {
        nameText.setText(user.name());
    }
}
