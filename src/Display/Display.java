package Display;

import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.application.Application;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import javafx.scene.Cursor;
import javafx.scene.*;
import Scenes.EditorScene;
import Scenes.LevelSelect;
import Scenes.MainMenue;
import Scenes.PlayScene;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

public class Display extends Application {
	public final int mainMenue = 0;

	GraphicsContext gc;

	final String appName = "Sniper! Get Down!";
	final int FPS = 30; // frames per second
	public final static int WIDTH = 1000;
	public final static int HEIGHT = 750;

	public Scenes.Scene ActiveScene;

	public Objects.GameObject cursor;

	double mouseX, mouseY;

	public static Stage theStage;

	void initialize(GraphicsContext gc) {
		ActiveScene = new Scenes.MainMenue();
		((MainMenue) ActiveScene).initScene(gc, this);

		cursor = new Objects.Sniper();
	}

	public void update() {
		if (ActiveScene instanceof MainMenue)
			((MainMenue) ActiveScene).updateScene(cursor.x, cursor.y);
		if (ActiveScene instanceof EditorScene)
			((EditorScene) ActiveScene).updateScene(cursor.x, cursor.y);
		if (ActiveScene instanceof LevelSelect)
			((LevelSelect) ActiveScene).updateScene(cursor.x, cursor.y);
		if (ActiveScene instanceof PlayScene)
			((PlayScene) ActiveScene).updateScene(cursor.x, cursor.y);
		cursor.update();

		// ((Objects.Sniper) cursor).update();

	}

	public void render(GraphicsContext gc) {
		cursor.render(gc);
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void setHandlers(Scene scene) {
		scene.setOnMouseMoved(e -> {
			((Objects.Sniper) cursor).setPosition(e.getX(), e.getY());
		});
		scene.setOnMousePressed(e -> {
			((Objects.Sniper) cursor).clicked = true;
			String msg;
			if ((msg = ActiveScene.checkClick(cursor.x, cursor.y)) != null)
				handleSceneMessage(msg);
		});
	}

	private void handleSceneMessage(String message) {
		System.out.println("message= " + message);
		boolean sceneSwitch = false;
		if (message != null) {
			if (message.equals("levelEditor")) {
				ActiveScene = new Scenes.EditorScene();
				sceneSwitch = true;
			}
			if (message.equals("levelSelect")) {
				ActiveScene = new Scenes.LevelSelect();
				sceneSwitch = true;
			}
			if (message.contains("playLevel")) {
				System.out.println(message.substring(message.indexOf(',') + 1));
				ActiveScene = new Scenes.PlayScene(message.substring(message.indexOf(',') + 1));
				sceneSwitch = true;
			}
			if (sceneSwitch)
				ActiveScene.initScene(gc, this);
		}
	}

	@Override
	public void start(Stage theStage) {
		this.theStage = theStage;
		theStage.setTitle(appName);

		Group root = new Group();
		Scene theScene = new Scene(root);
		theStage.setScene(theScene);

		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		root.getChildren().add(canvas);

		gc = canvas.getGraphicsContext2D();
		// gc.setFill(Color.AQUA);
		// gc.fillRect(0, 0, WIDTH, HEIGHT);
		initialize(gc);
		setHandlers(theScene);
		theScene.setCursor(Cursor.NONE);
		// Setup and start animation loop (Timeline)
		KeyFrame kf = new KeyFrame(Duration.millis(1000 / FPS), e -> {
			// update position
			update();
			// draw frame

			render(gc);
		});

		Timeline mainLoop = new Timeline(kf);
		mainLoop.setCycleCount(Animation.INDEFINITE);
		mainLoop.play();

		theStage.show();

	}
}