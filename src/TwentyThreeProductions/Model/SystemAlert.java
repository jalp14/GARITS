package TwentyThreeProductions.Model;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXButton;



public class SystemAlert {

    private JFXDialogLayout dialogLayout;
    private JFXDialog dialog;
    private JFXButton button;

    public SystemAlert(StackPane currentPane, String mainMsg, String subMsg) {
        dialogLayout = new JFXDialogLayout();
        dialog = new JFXDialog(currentPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
        dialogLayout.setHeading(new Text(mainMsg));
        dialogLayout.setBody(new Text(subMsg));
        button = new JFXButton("Ok");
        button.setDefaultButton(true);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });
        dialogLayout.setActions(button);
        dialog.show();
    }

}
