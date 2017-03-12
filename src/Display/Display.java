package Display;

import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.application.Application;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.scene.Cursor;
import javafx.scene.*;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import Actor.Sniper;
import Scenes.EditorScene;
import Scenes.LevelSelect;
import Scenes.MainMenue;
import Scenes.PlayScene;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

public class Display extends Application {
	public final static boolean debug = true;

	public final int mainMenue = 0;

	GraphicsContext gc;

	final String appName = "Sniper! Get Down!";
	final int FPS = 30; // frames per second
	public final static int WIDTH = 1250;
	public final static int HEIGHT = 800;

	public Scenes.Scene ActiveScene;

	public Sniper cursor;

	double mouseX, mouseY;

	public static Stage theStage;

	void initialize(GraphicsContext gc) {
		ActiveScene = new Scenes.MainMenue();
		((MainMenue) ActiveScene).initScene(gc, this);

		cursor = new Sniper();
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
		if (debug && !ActiveScene.input.isEmpty())
			System.out.println(ActiveScene.input.toString());
		// ((Objects.Sniper) cursor).update();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void setHandlers(Scene scene) {
		scene.setOnMouseMoved(e -> {
			((Sniper) cursor).setPosition(e.getX(), e.getY());
		});
		scene.setOnMouseDragged(e -> {
			((Sniper) cursor).setPosition(e.getX(), e.getY());
			((Sniper) cursor).clicked = true;
			ActiveScene.checkClick(cursor.x, cursor.y, true); // drags not
																// allowed for
																// scene switch
		});
		scene.setOnMousePressed(e -> {
			((Sniper) cursor).clicked = true;
			String msg;
			if ((msg = ActiveScene.checkClick(cursor.x, cursor.y, false)) != null)
				handleSceneMessage(msg);
		});

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				String code = e.getCode().toString();
				// only add once... prevent duplicates
				if (!ActiveScene.input.contains(code))
					ActiveScene.input.add(code);
			}
		});

		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			public void handle(KeyEvent e) {
				String code = e.getCode().toString();
				ActiveScene.input.remove(code);
			}
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
				if (message.contains("GRID"))
					ActiveScene = new Scenes.PlayScene(ActiveScene.getGrid());
				else
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
		if (!debug)
			theScene.setCursor(Cursor.NONE);
		// Setup and start animation loop (Timeline)
		KeyFrame kf = new KeyFrame(Duration.millis(1000 / FPS), e -> {
			// update position
			update();
			// draw frame

			cursor.render(gc);
		});

		Timeline mainLoop = new Timeline(kf);
		mainLoop.setCycleCount(Animation.INDEFINITE);
		mainLoop.play();

		theStage.show();

	}
}