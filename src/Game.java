import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Game extends Application 
{
	private Stage window;

	private static int BOARD_SIZE = 450;

	private static List<Integer> XSpaces = new ArrayList<Integer>();
	private static List<Integer> OSpaces = new ArrayList<Integer>();

	private boolean isXTurn = true;
	private boolean isWin = false;

	public static void main(String[] args) 
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) 
	{
		window = primaryStage;
		window.setTitle("Tic-Tac-Toe Game");
		window.setResizable(false);
		
		Button startButton = new Button("Start Game");
		Button quitButton = new Button("Quit Game");

		startButton.setPrefWidth(300);
		quitButton.setPrefWidth(300);
		
		VBox vBox = new VBox(10);
		vBox.setAlignment(Pos.CENTER);
		vBox.setPadding(new Insets(20, 20, 20, 20));
		vBox.setSpacing(10);
		vBox.getChildren().addAll(startButton, quitButton);

		Scene startMenu = new Scene(vBox, BOARD_SIZE, BOARD_SIZE);

		Group root = new Group();
		Canvas canvas = new Canvas(BOARD_SIZE, BOARD_SIZE);
		root.getChildren().add(canvas);
		GraphicsContext gc = canvas.getGraphicsContext2D();

		Scene game = new Scene(root, BOARD_SIZE, BOARD_SIZE);
		
		game.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				int squareClicked = getSquareClicked(event);

				performPlayerTurn(gc, squareClicked, isXTurn);

				if(isWin)
				{
					boolean playAgain = PlayAgainBox.display("Player " + (isXTurn ? "X" : "O") + " wins!");

					if(playAgain)
						resetGame(gc, canvas);
					else
						window.close();
				}
				else
				{
					if(XSpaces.size() + OSpaces.size() == 9)
					{
						PlayAgainBox.display("It's a tie!");
						resetGame(gc, canvas);
					}
				}

				isXTurn = !isXTurn;
			}

		});

		startButton.setOnAction(e -> window.setScene(game));
		quitButton.setOnAction(e -> window.close());

		window.setScene(startMenu);

		drawBoard(gc);

		window.show();
	}

	private void performPlayerTurn(GraphicsContext gc, int squareClicked, boolean isXTurn)
	{
		if(isXTurn)
		{
			drawX(gc, squareClicked);
			XSpaces.add(squareClicked);
			checkIfWin(gc, XSpaces);
		}
		else
		{
			drawO(gc, squareClicked);
			OSpaces.add(squareClicked);
			checkIfWin(gc, OSpaces);
		}
	}

	private void resetGame(GraphicsContext gc, Canvas canvas)
	{
		isWin = false;
		XSpaces.clear();
		OSpaces.clear();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		drawBoard(gc);
	}

	public static void drawBoard(GraphicsContext gc)
	{
		gc.setStroke(Color.BLUE);
		gc.setLineWidth(5);

		// vertical lines
		gc.strokeLine(158, 25, 158, 425);
		gc.strokeLine(292, 25, 292, 425);

		// horizontal line
		gc.strokeLine(25, 158, 425, 158);
		gc.strokeLine(25, 292, 425, 292);
	}

	private int getSquareClicked(MouseEvent event)
	{
		// Top Left Square
		if(event.getSceneX() > 25 && event.getSceneX() < 158
				&& event.getSceneY() > 25 && event.getSceneY() < 158)
			return 1;
		// Top Center Square
		else if(event.getSceneX() >= 158 && event.getSceneX() < 292
				&& event.getSceneY() > 25 && event.getSceneY() < 158)
			return 2;
		// Top Right Square
		else if(event.getSceneX() >= 292 && event.getSceneX() < 425
				&& event.getSceneY() > 25 && event.getSceneY() < 158)
			return 3;
		// Center Left Square
		else if(event.getSceneX() > 25 && event.getSceneX() < 158
				&& event.getSceneY() > 158 && event.getSceneY() < 292)
			return 4;
		// Center Center Square
		else if(event.getSceneX() >= 158 && event.getSceneX() < 292
				&& event.getSceneY() > 158 && event.getSceneY() < 292)
			return 5;
		// Center Right Square
		else if(event.getSceneX() >= 292 && event.getSceneX() < 425
				&& event.getSceneY() > 158 && event.getSceneY() < 292)
			return 6;
		// Bottom Left Square
		else if(event.getSceneX() > 25 && event.getSceneX() < 158
				&& event.getSceneY() >= 292 && event.getSceneY() < 425)
			return 7;
		// Bottom Center Square
		else if(event.getSceneX() >= 158 && event.getSceneX() < 292
				&& event.getSceneY() >= 292 && event.getSceneY() < 425)
			return 8;
		// Bottom Right Square
		else if(event.getSceneX() >= 292 && event.getSceneX() < 425
				&& event.getSceneY() >= 292 && event.getSceneY() < 425)
			return 9;

		return -1; // for the compiler, return but won't be used anywhere
	}

	public void drawX(GraphicsContext gc, int squareClicked)
	{
		switch(squareClicked)
		{
		case 1:
			gc.strokeLine(50, 50, 135, 135);
			gc.strokeLine(135, 50, 50, 135);
			break;

		case 2:
			gc.strokeLine(183, 50, 268, 135);
			gc.strokeLine(268, 50, 183, 135);
			break;

		case 3:
			gc.strokeLine(316, 50, 401, 135);
			gc.strokeLine(401, 50, 316, 135);
			break;

		case 4:
			gc.strokeLine(50, 183, 135, 268);
			gc.strokeLine(135, 183, 50, 268);
			break;

		case 5:
			gc.strokeLine(183, 183, 268, 268);
			gc.strokeLine(268, 183, 183, 268);
			break;

		case 6:
			gc.strokeLine(316, 183, 401, 268);
			gc.strokeLine(401, 183, 316, 268);
			break;

		case 7:
			gc.strokeLine(50, 316, 135, 401);
			gc.strokeLine(135, 316, 50, 401);
			break;

		case 8:
			gc.strokeLine(183, 316, 268, 401);
			gc.strokeLine(268, 316, 183, 401);
			break;

		case 9:
			gc.strokeLine(316, 316, 401, 401);
			gc.strokeLine(401, 316, 316, 401);
			break;
		}
	}

	public void drawO(GraphicsContext gc, int squareClicked)
	{
		switch(squareClicked)
		{

		case 1:
			gc.strokeOval(35, 35, 115, 115);
			break;

		case 2:
			gc.strokeOval(168, 35, 115, 115);
			break;

		case 3:
			gc.strokeOval(301, 35, 115, 115);
			break;

		case 4:
			gc.strokeOval(35, 168, 115, 115);
			break;

		case 5:
			gc.strokeOval(168, 168, 115, 115);
			break;

		case 6:
			gc.strokeOval(301, 168, 115, 115);
			break;

		case 7:
			gc.strokeOval(35, 301, 115, 115);
			break;

		case 8:
			gc.strokeOval(168, 301, 115, 115);
			break;

		case 9:
			gc.strokeOval(301, 301, 115, 115);
			break;
		}
	}

	public void checkIfWin(GraphicsContext gc, List<Integer> playerSpaces)
	{
		// VERTICAL WINS

		if(playerSpaces.contains(1) && playerSpaces.contains(4) && playerSpaces.contains(7)) // Spaces filled: 1, 4, 7
		{
			gc.strokeLine(93, 25, 93, 425);
			isWin = true;
		}
		else if(playerSpaces.contains(2) && playerSpaces.contains(5) && playerSpaces.contains(8)) // Spaces filled: 2, 5, 8
		{	
			gc.strokeLine(226, 25, 226, 425);
			isWin = true;
		}
		else if(playerSpaces.contains(3) && playerSpaces.contains(6) && playerSpaces.contains(9)) // Spaces filled: 3, 6, 9
		{	
			gc.strokeLine(359, 25, 359, 425);
			isWin = true;
		}

		// HORIZONTAL WINS

		else if(playerSpaces.contains(1) && playerSpaces.contains(2) && playerSpaces.contains(3)) // Spaces filled: 1, 2, 3
		{	
			gc.strokeLine(25, 93, 425, 93);
			isWin = true;
		}
		else if(playerSpaces.contains(4) && playerSpaces.contains(5) && playerSpaces.contains(6)) // Spaces filled: 4, 5, 6
		{
			gc.strokeLine(25, 226, 425, 226);
			isWin = true;
		}
		else if(playerSpaces.contains(7) && playerSpaces.contains(8) && playerSpaces.contains(9)) // Spaces filled: 7, 8, 9
		{	
			gc.strokeLine(25, 359, 425, 359);
			isWin = true;
		}

		// DIAGONAL WINS

		else if(playerSpaces.contains(1) && playerSpaces.contains(5) && playerSpaces.contains(9)) // Spaces filled: 1, 5, 9
		{	
			gc.strokeLine(15, 15, 425, 425);
			isWin = true;
		}
		else if(playerSpaces.contains(3) && playerSpaces.contains(5) && playerSpaces.contains(7)) // Spaces filled: 3, 5, 7
		{	
			gc.strokeLine(425, 15, 15, 425);
			isWin = true;
		}
		else
			isWin = false;
	}
}
