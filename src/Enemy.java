package p1_group_10;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

public class Enemy extends Actor{
	double dx;
	double dy;
	int count =  0;
	double width;
	double height;
	int nextCount = 0;
	boolean pause;
	
	public Enemy() {
		super();
		dy = 0; 
		dx = -2;
	}
	

	public Enemy(int x, int y) {
		super();
		this.setX(x);
		this.setY(y);
		dy = 0;
		dx = -7;
		Image im = new Image("file:Images/bird.png");
		this.setImage(im);
		width = im.getWidth() / 4;
		height = im.getHeight();
		pause = false;
	}


	@Override
	public void act(long now) {
		move(dx, dy);
		if(nextCount == 10){
			next(count);
			nextCount = 0;
		}
		nextCount++;
		if(count == 3){
			count = 0;
		}else{
			count++;
		}
	}

	public void setHeight(double y){
		
	}
	
	public void changeEnemy(Image i){

	}
	
	public void setDX(int dx){
		this.dx = dx;
	}
	
	public void setDY(int dy){
		this.dy = dy;
	}
	
	private void next(int k) {
		double x = k * width + 5;
		double y = 0;
		if(!pause){
			this.setViewport(new Rectangle2D(x, y, width, height));
		}
	}


	public void pause() {
		pause = true;
	}
	
	public void resume(){
		pause = false;
	}
}
