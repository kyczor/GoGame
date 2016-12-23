import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.awt.SystemColor.text;

/**
 * A whole GUI.
 * Created by Karola on 2016-12-20.
 */
public class GUI extends Application
{
	HBox root;
	Dialog<Pair<Integer, Boolean>> dialogwin;

	int mysize;
	boolean opptype;
	Board board;
	Canvas canvas;

	public EchoClient client;

	/**
	 * Constructor (empty)
	 */
	public GUI()
	{
		//client = new EchoClient("localhost", 9000, this);
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

	/**
	 * Main method
	 * @param args initial arguments
	 */
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
		setDialog();

		Optional<Pair<Integer, Boolean>> choice = dialogwin.showAndWait();
		choice.ifPresent(result1 ->
		{
			mysize = result1.getKey();
			opptype = result1.getValue();
		}
		);

		//1 element: getkey , 2 element: getvalue
	}

	/**
	 * A function that sets the left panel with buttons.
	 */
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
		giveUp.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			/**
			 * @param event mouse event (a button "giveup" clicked)
			 */
			@Override
			public void handle(MouseEvent event)
			{
				Command command = new Command("GIVEUP", board);
				client.SendCommand(command);
			}
		});


//		Button endgame = new Button("End game");
//		endgame.setPrefWidth(100);
//		panel1.getChildren().add(endgame);
//		VBox.setMargin(endgame, new Insets(50,0,0,0));
//		endgame.setOnMouseClicked(new EventHandler<MouseEvent>()
//		{
//			@Override
//			public void handle(MouseEvent event)
//			{
//				Command command = new Command("ENDGAME", board);
//				client.SendCommand(command);
//			}
//		});

		//now it does nothing since we didn't manage to finish all the game rules
		Button countPoints = new Button("Count points");
		countPoints.setPrefWidth(100);
		panel1.getChildren().add(countPoints);
		VBox.setMargin(countPoints, new Insets(50,0,0,0));
		countPoints.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			/**
			 *
			 * @param event mouse event (a button "countpoints" clicked)
			 */
			@Override
			public void handle(MouseEvent event)
			{
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("It's not that easy");
				alert.setHeaderText("This feature is not available... yet");
				alert.setContentText("You have to do it YOURSELF.");
				alert.showAndWait();
			}
		});

		Button pass = new Button("Pass");
		pass.setPrefWidth(100);
		panel1.getChildren().add(pass);
		VBox.setMargin(pass, new Insets(50,0,0,0));
		pass.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			/**
			 *
			 * @param event mouse event (a button "pass" clicked)
			 */
			@Override
			public void handle(MouseEvent event)
			{
				Command command = new Command("PASS", board);
				client.SendCommand(command);
			}
		});

		root.getChildren().add(panel1);


	}

	/**
	 * Sets the whole dialog window.
	 * The window appears before you start the game and lets you choose board size (and opponent type, though we didn't manage to create any bot   )
	 * WARNING: The board is being created at a size THAT HAS BEEN SELECTED FIRST. It doesn't matter what the other player chooses. (It's not a bug, it's a feature, OF COURSE. Latecomers must be punished)
	 */
	public void setDialog()
	{
		dialogwin = new Dialog<>();
		dialogwin.setTitle("Configurations");
		dialogwin.setHeaderText("Choose your preferences:");

		ButtonType ok = new ButtonType("CONFIRM", ButtonBar.ButtonData.OK_DONE);
		dialogwin.getDialogPane().getButtonTypes().add(ok);

		GridPane mygrid = new GridPane();
		Label boardsize = new Label("Board size:");
		mygrid.add(boardsize, 0,0);
		Label opptype = new Label("Opponent type:");
		mygrid.add(opptype, 0, 1);
		ComboBox<String> boardsizechoice = new ComboBox<>();
		boardsizechoice.getItems().add("9 x 9");
		boardsizechoice.getItems().add("13 x 13");
		boardsizechoice.getItems().add("19 x 19");
		boardsizechoice.getSelectionModel().select(0);
		boardsizechoice.setPrefWidth(100);
		mygrid.add(boardsizechoice, 1,0);
		ComboBox<String> opptypechoice = new ComboBox<>();
		opptypechoice.getItems().add("human");
		opptypechoice.getItems().add("bot");
		opptypechoice.getSelectionModel().select(0);
		opptypechoice.setPrefWidth(100);
		mygrid.add(opptypechoice, 1,1);
		dialogwin.getDialogPane().setContent(mygrid);

	//the following fragment of code returns chosen size of board and type of opponent (false-bot, true-human)
		dialogwin.setResultConverter( e ->
		{
			int chosensize = 1;
			Boolean humanornot = false;
			if(boardsizechoice.getSelectionModel().getSelectedIndex() == 0)
				chosensize = 9;
			else if(boardsizechoice.getSelectionModel().getSelectedIndex() == 1)
				chosensize = 13;
			else chosensize = 19;

			if(opptypechoice.getSelectionModel().getSelectedIndex() == 0)
				humanornot = true;
			else humanornot = false;

			client = new EchoClient("localhost", 9000, this);
			client.SendCommand(new Command((humanornot ? "HUMAN_SIZE" : "BOT_SIZE"), new Board(chosensize)));
			return new Pair<>(chosensize, humanornot);
		}
		);
	}

	/**
	 *
	 * @param fboard board on which a player clicked
	 */
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

	/**
	 * Draws a board (at a selected size) with a blue background. Fills the board with pawns at their actual positions.
	 * @param gc Graphics context that is needed to be passed
	 * @param board actual board to be repainted
	 */
	private void drawShapes(GraphicsContext gc, Board board)
	{
		double size = board.getSize();
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
