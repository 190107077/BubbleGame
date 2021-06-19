import javafx.application.Application;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import java.util.ArrayList;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.layout.Pane;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.text.Font; 
import javafx.scene.text.FontWeight; 
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import javafx.scene.shape.StrokeType;

import java.util.Random;

public class BubbleGame extends Application {

	private static final double WIDTH = 600;
    private static final String GAME_PANE_COLOR = "#F0F8FF";
    private static final Font FONT_FOR_TEXT = Font.font("Courier", FontWeight.BOLD,FontPosture.ITALIC, 30);

    //all Scenes for programm 
    private Scene startScene;
    private Scene gameScene;
    private Scene gameOverScene;

    //main menu Pane
    private Pane startMenuPane;

    //main menu Pane elements
    private Button startBT;

    //game Pane
    private Pane gamePane;

    // game Pane elements
    private Text scoreText;
    private Text timeText;
    private Line divider;
    private Circle ballGame;

    //game over menu Pane
    private Pane gameOverMenuPane;
    
    //game over menu Pane elements
    private Text lastScoreText;
    private Button playAgainBT;
	private Button exitBT;

	//Animation data fields
	private Timeline timerAnimation;

	//for counting scores
	private int score;
	private int time=30;

	@Override
    public void start(Stage primaryStage) throws Exception {

    	//adding elements for start page and inseting in pane
    	startMenuPane = new StackPane();
        startBT = new Button("START");
        startMenuPane.getChildren().add(startBT);

       
        initLayout();
    	initHandlers(primaryStage);
    	initAnimation(primaryStage);
       
    	
    	startScene = new Scene(startMenuPane,WIDTH,WIDTH);
    	gameScene = new Scene(gamePane,WIDTH,WIDTH);
    	gameOverScene = new Scene(gameOverMenuPane, WIDTH, WIDTH);

    	primaryStage.setTitle("Bubble game");
    	primaryStage.setScene(startScene);
    	primaryStage.show();
        
    }

	public void initLayout() {
		//creating pane
		gamePane = new Pane();

		//create instance and set font for scoreText
		scoreText = new Text(10, 30, "Score: " + score);
		scoreText.setFont(FONT_FOR_TEXT);

		//create instance and set font for timeText
		timeText = new Text(WIDTH-150, 30, "Time: " + time);
		timeText.setFont(FONT_FOR_TEXT);

		//create instance of Line class
		divider = new Line(0, 50, WIDTH, 50);

		ballGame = new Circle(WIDTH/2, WIDTH/2, 40);
		ballGame.setStroke(Color.BLACK);

		gamePane.getChildren().addAll(scoreText, timeText, divider, ballGame); //adding all elements
		gamePane.setStyle("-fx-background-color: " + GAME_PANE_COLOR); //change background color

		
		//game over pane
		gameOverMenuPane = new StackPane();
		lastScoreText = new Text(WIDTH/2, WIDTH/2, "Score: " + score);
		lastScoreText.setFont(FONT_FOR_TEXT);

		Circle circle = new Circle(WIDTH/2, WIDTH/2, 100);
        circle.setStrokeWidth(8);
        circle.setFill(Color.WHITE);	
        circle.setStroke(Color.BLACK);
        playAgainBT = new Button("PLAY AGAIN");
		exitBT = new Button("EXIT");

		VBox vbox = new VBox();
		vbox.getChildren().addAll(playAgainBT, exitBT);
		vbox.setSpacing(5);
		vbox.setAlignment(Pos.BOTTOM_CENTER);
		
		gameOverMenuPane.getChildren().addAll(circle, lastScoreText,vbox);

	}

	public void initHandlers(Stage primaryStage) {

		startBT.setOnAction(e -> {
			primaryStage.setScene(gameScene);
		});

		ballGame.setOnMousePressed(e -> {
			score++;
			scoreText.setText("Score: " + score);
			lastScoreText.setText("Score: " + score);
		});

		playAgainBT.setOnAction(e-> {
			score = 0;
			time = 30;
			scoreText.setText("Score: " + score);
			lastScoreText.setText("Score: " + score);
			primaryStage.setScene(gameScene);
			initHandlers(primaryStage);
    		initAnimation(primaryStage);
		});

		exitBT.setOnAction(e -> {
			System.exit(0);
		});

	}

	public void initAnimation(Stage primaryStage) {

		EventHandler<ActionEvent> eventHandler = e -> {
			ballGame.setFill(Color.color(Math.random(), Math.random(), Math.random()));
			ballGame.setRadius(Math.random() * 100 + 10);

			double maxY = 551-ballGame.getRadius(); 
			double minY = 51 + ballGame.getRadius();

			double maxX = 601-ballGame.getRadius();
			double minX = ballGame.getRadius()+1;

			ballGame.setCenterY(Math.random() * (maxY - minY) + minY);
			ballGame.setCenterX(Math.random() * (maxX - minX) + minX);
				
			time--;
			timeText.setText("Time: " + time);
				
			if(time <= 0){
				timerAnimation.stop();
				primaryStage.setScene(gameOverScene);
			}

		};
			
		timerAnimation = new Timeline(
		new KeyFrame(Duration.millis(750), eventHandler));
		timerAnimation.setCycleCount(Timeline.INDEFINITE);
		timerAnimation.play(); 

		if(timerAnimation.getStatus() == Animation.Status.STOPPED){
			primaryStage.setScene(gameOverScene);	
		}
	}
}
