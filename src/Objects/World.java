package Objects;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;

public class World {
	public ArrayList<GameObject> worldArray; // list of world objects

	public World() {
		worldArray = new ArrayList<GameObject>();
	}
	
	public void update(){
		for(GameObject o : worldArray){
			o.update();
		}
	}

	public void populateWorld(Grid grid) {
		for (int i = 0; i < grid.cols; i++) {
			for (int j = 0; j < grid.rows; j++) {
				double x = j * grid.cellSize;
				double y = i * grid.cellSize;

				if (grid.gridCode[j][i] == BrickBlock.gridCode)
					worldArray.add(new BrickBlock(x, y, grid.cellSize));
				if (grid.gridCode[j][i] == CoverBlock.gridCode)
					worldArray.add(new CoverBlock(x, y, grid.cellSize));
			}
		}
		//debugPrint();
	}

	public void debugPrint() {
		for (GameObject o : worldArray)
			System.out.println(o.getClass().getName() + " " + o.x + "," + o.y);
	}

	public void drawWorld(GraphicsContext gc) {
		for (GameObject o : worldArray) {
			o.render(gc);
		}
	}

	public int getSize() {
		return worldArray.size();
	}

	public void addObject(Grid grid, int r, int c) {
		double y = c * grid.cellSize;
		double x = r * grid.cellSize;
		if (grid.gridCode[r][c] == BrickBlock.gridCode)
			worldArray.add(new BrickBlock(x, y, grid.cellSize));
		if (grid.gridCode[r][c] == CoverBlock.gridCode)
			worldArray.add(new CoverBlock(x, y, grid.cellSize));
	}

}
