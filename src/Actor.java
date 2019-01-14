package p1_group_10;
 
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.ImageView;

public abstract class Actor extends ImageView{
	public abstract void act(long now);
	
	public double getHeight(){
		return super.getFitHeight();
	}
	
	public <A extends Actor> java.util.List<A> getIntersectingObjects(java.lang.Class<A> cls){
		List<Actor> list = getWorld().getObjects(Actor.class);
		ArrayList<A> newList = new ArrayList<A>();
		for(int i = 0; i < list.size(); i++){
			if (list.get(i) != this && list.get(i).intersects(this.getBoundsInLocal())){
				newList.add(cls.cast(list.get(i)));
			}
		}
		return newList;
	}
	
	public <A extends Actor> A getOneIntersectingObject(java.lang.Class<A> cls){
		List<Actor> list = getWorld().getObjects(Actor.class);
		for(int i = 0; i < list.size(); i++){
			if (list.get(i) != this && list.get(i).getClass() == cls && list.get(i).intersects(this.getBoundsInLocal())){
				return cls.cast(list.get(i));
			}
		}
		return null;
	}
	
	public void move(double dx, double dy){
		super.setX(this.getX() + dx);
		super.setY(this.getY() + dy);
	}
	
	public World getWorld(){
		return (World)getParent();
	}
	
	public double getWidth(){
		return super.getFitWidth();
	}
	
}
