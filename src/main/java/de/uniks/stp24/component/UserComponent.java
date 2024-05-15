package de.uniks.stp24.component;

import de.uniks.stp24.model.User;
import de.uniks.stp24.service.ImageCache;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.fulib.fx.annotation.controller.Component;
import org.fulib.fx.annotation.event.OnInit;
import org.fulib.fx.constructs.listview.ReusableItemComponent;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

@Component(view = "User.fxml")
public class UserComponent extends HBox implements ReusableItemComponent<User> {

    @FXML
    ImageView avatarView;
    @FXML
    Text nameText;

    private final ImageCache imageCache;

    @Inject
    public UserComponent(
        ImageCache imageCache
    ) {
        this.imageCache = imageCache;
    }

    @Override
    public void setItem(@NotNull User user) {
        nameText.setText(user.name());

        if (user.avatar() != null) {
            avatarView.setImage(imageCache.get(user.avatar()));
        } else {
            avatarView.setImage(imageCache.get("icon.png"));
        }
    }
}
