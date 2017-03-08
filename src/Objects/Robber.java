package Objects;

import javafx.scene.image.Image;

public class Robber extends GameObject {

	public Robber(int playerNum, double x, double y) {
		switch (playerNum) {
		case 1:
			img = new Image("objectAssets/blueCharacter.png");
			break;
		case 2:
			img = new Image("objectAssets/greenCharacter.png");
			break;
		default:
			break;
		}
		this.x = 200;
		this.y = 500;
	}
	
	public void update(){
		
	}

	public void drawActor() {

	}
}
