package p1_group_10;

public class Level extends World{
	int level;
	
	public Level(int level) {
		super();
		this.level = level;
	}

	@Override
	public void act(long now) {
		
	}
	
	public int getLevel(){
		return level;
	}

	public void setLevel(int level){
		this.level = level;
	}
}
