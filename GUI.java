import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by Karola on 2016-12-20.
 */
public class GUI extends Application
{
	public static void main(String[] args)
	{
		launch(args);
	}
	@Override
	public void start(Stage stage) throws Exception
	{
		stage.setTitle("GoGame");
		stage.setWidth(1000);
		stage.setHeight(830);
		stage.setResizable(false);
		HBox root = new HBox();

		setLeftPanel(root);
		drawBoard(root);


		Scene scene = new Scene(root);
		scene.getStylesheets().add("mystyles.css");
		stage.setScene(scene);
		stage.show();

	}

	private void setLeftPanel(HBox root)
	{

		VBox panel1 = new VBox();
		panel1.setAlignment(Pos.TOP_CENTER);
		panel1.setPrefWidth(200);
		panel1.setPrefHeight(800);
		panel1.getStyleClass().add("nice_background");


		Button giveUp = new Button("Give up");
		giveUp.setPrefWidth(100);
		panel1.getChildren().add(giveUp);
		VBox.setMargin(giveUp, new Insets(150,0,0,0));


		Button endgame = new Button("End game");
		endgame.setPrefWidth(100);
		panel1.getChildren().add(endgame);
		VBox.setMargin(endgame, new Insets(50,0,0,0));

		Button countPoints = new Button("Count points");
		countPoints.setPrefWidth(100);
		panel1.getChildren().add(countPoints);
		VBox.setMargin(countPoints, new Insets(50,0,0,0));

		Button pass = new Button("Pass");
		pass.setPrefWidth(100);
		panel1.getChildren().add(pass);
		VBox.setMargin(pass, new Insets(50,0,0,0));

		root.getChildren().add(panel1);
	}
	private void drawBoard(HBox root)
	{
		Canvas canvas = new Canvas(800,800);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		drawShapes(gc); //this will draw my board
		root.getChildren().add(canvas);
	}

	private void drawShapes(GraphicsContext gc)
	{
		gc.setFill(Color.rgb(130,140,255));
		gc.fillRect(20,20,760,760);
	}
}
