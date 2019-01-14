package p1_group_10;

public class Bullet extends Actor{

	private double dx;
	private double dy;
	
	public Bullet()
	{
		super();
		dx = 4;
		dy = 0;
	}

	@Override
	public void act(long now) {
		move(dx, dy);
	}

	public void setDX(int i) {
		dx = i;
	}
	
	public void setDY(int i){
		dy = i;
	}
}
