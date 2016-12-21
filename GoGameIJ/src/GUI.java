import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karola on 2016-12-20.
 */
public class GUI extends Application
{
	HBox root;

	//int size;
	Board board;
	Canvas canvas;

	public EchoClient client;
//uczymy sie gita
	public GUI()
	{
		client = new EchoClient("localhost", 9000, this);
//		size = 9;
//
//		fields = new ArrayList<ArrayList<whoseField>>();
//		for(int i=0; i<size; i++)
//		{
//			fields.add(new ArrayList<whoseField> ());
//			for(int j=0; j<size; j++)
//			{
//				fields.get(i).add(whoseField.empty);
//			}
//		}
	//TEST
//		fields.get(5).set(6, whoseField.white);
//		fields.get(2).set(3, whoseField.black);
	}

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
		root = new HBox();

		setLeftPanel();
		canvas = new Canvas(800,800);
		root.getChildren().add(canvas);

		Scene scene = new Scene(root);
		scene.getStylesheets().add("mystyles.css");
		stage.setScene(scene);
		stage.show();

	}

	private void setLeftPanel()
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
	public void drawBoard(Board fboard)
	{
		this.board = fboard;
		GraphicsContext gc = canvas.getGraphicsContext2D();
		drawShapes(gc, board); //this will draw my board
		canvas.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				double x = event.getX();
				double y = event.getY();
				int x1,y1;
				int size = board.getSize();
				x1 =  (int)((x-(400/(size+1)))/(800/(size+1)));
				y1 =  (int)((y-(400/(size+1)))/(800/(size+1)));
				Command command = new Command("MOVE",x1,y1);
				client.SendCommand(command);
		//		TODO: a function that sends x1,y1 as (x,y) coordinates to client (/server?)  -- > server         which handles the move     as a result we get a refreshed board (after the move)
			//bool - is the move legal?
				// yes - refresh your board and disable gui changes
				// no - pop up window with error message
			}
		});
	}

	private void drawShapes(GraphicsContext gc, Board board)
	{
		int size = board.getSize();
		gc.clearRect(0,0,800,800);
		gc.setFill(Color.rgb(130,140,255));
		gc.fillRect(20,20,760,760);
		for(int i=0; i<size; i++)
		{
			//draws a grid (board field)
			gc.strokeLine((i+1)*(800/(size+1)), 800/(size+1), (i+1)*(800/(size+1)), 800-(800/(size+1)));
			gc.strokeLine(800/(size+1), (i+1)*800/(size+1), 800-(800/(size+1)), (i+1)*(800/(size+1)));
		}

		/*
			The following loop checks ownership of every field and draws every pawn.
		 */
		double r = (800/(size+1))*0.25;
		for(int j=0; j<size; j++)
		{
			for(int k=0; k<size; k++)
			{
				if (board.getField(j,k) == whosefield.black)
				{
					gc.setFill(Color.rgb(0,0,0));
					gc.fillOval( (800/(size+1))+ j*(800/(size+1))-r,(800/(size+1))+ k*(800/(size+1))-r, 2*r, 2*r);
				}
				else if (board.getField(j,k) == whosefield.white)
				{
					gc.setFill(Color.rgb(255,255,255));
					gc.fillOval( (800/(size+1))+ j*(800/(size+1))- r,(800/(size+1))+ k*(800/(size+1))-r, 2*r, 2*r);
				}
			}
		}
	}

}
