import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PlayAgainBox
{
	private static boolean playAgain;
	
	public static boolean display(String message)
	{
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setMinWidth(250);
		Label label = new Label();
		label.setText(message);

		Button yesButton = new Button("Yes");
		yesButton.setOnAction(e -> {
			playAgain = true;
			window.close();
		});

		Button noButton = new Button("No");
		noButton.setOnAction(e -> {
			playAgain = false;
			window.close();
		});
		
		BorderPane borderPane = new BorderPane();
		HBox hbox = new HBox(10);
		hbox.getChildren().addAll(yesButton, noButton);
		hbox.setAlignment(Pos.BOTTOM_RIGHT);
		
		borderPane.setPadding(new Insets(25, 25, 25, 25));
		borderPane.setTop(label);
		borderPane.setBottom(hbox);
		
		Scene scene = new Scene(borderPane, 200, 200);
		window.setScene(scene);
		window.showAndWait();
		
		return playAgain;
	}
}
