package p1_group_10;

import javafx.scene.image.Image;

public class Cactus extends Actor{
	double dy;
	double dx;
	Image cactusImg;
	double groundLevel;
	
	public Cactus() {
		super();
		dx = -5;
		dy = 0;
		groundLevel = 250;
	}
	
	public Cactus(int x, int y){
		super();
		dx = -5; 
		dy = 0;
		this.setX(x);
		this.setY(y);
		cactusImg = new Image("file:Images/cactus.png");
		this.setImage(cactusImg);
		groundLevel = 250;
	}

	@Override
	public void act(long now) {
		move(dx, dy);
	}
	
	public void explode(Actor a){
		
	}
	
	public void setDX(int dx){
		this.dx = dx;
	}
	
	public void setDY(int dy){
		this.dy = dy;
	}
	
	public double getGroundLevel(){
		return groundLevel;
	}

}
