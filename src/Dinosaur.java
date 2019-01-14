package p1_group_10;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

public class Dinosaur extends Actor {
	double dx;
	double dy;
	double jumpHeight;
	double groundHeight;
	double jumpDy;
	double gravity;
	int count =  0;
	double width;
	double height;
	int nextCount = 0;
	boolean start;
	boolean pause;
	
	public Dinosaur() {
		super();
		dx = 0;
		dy = 0; 
		jumpHeight = 25;
		groundHeight = 250;
		jumpDy = 8;
		gravity = 0.15;
		Image im = new Image("file:Images/catSpriteSheet.png");
		this.setImage(im);
		width = im.getWidth() / 8;
		height = im.getHeight();
		this.setViewport(new Rectangle2D(0, 0, width, height));
		start = false;
		pause = false;
	}

	@Override
	public void act(long now) {
		move(dx, dy);
		if(this.getY() < groundHeight){
			dy = dy + gravity;
		}else{
			dy = 0;
		}
		if(nextCount >= 10 && start){
			next(count);
			nextCount = 0;
		}
		nextCount++;
		if(count == 7){
			count = 0;
		}else{
			count++;
		}
		
	}
	
	public void jump(){
		if(this.getY() + 10 > groundHeight){
			dy = -jumpDy;
		}
	}


	public void shoot(Actor a){
		Bullet b = new Bullet();
	}
	
	public void duck(double dx, double dy){
		dy = 3;
	}
	
	public double getGroundLevel(){
		return groundHeight;
	}
	
	private void next(int k) {
		double x = k * width + 5;
		double y = 0;
		if(!pause){
			this.setViewport(new Rectangle2D(x, y, width, height));
		}
	}
	
	public void setStart(){
		start = true;
	}

	public void pause() {
		pause = true;
	}
	
	public void resume(){
		pause = false;
	}
}
