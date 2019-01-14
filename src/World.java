package p1_group_10;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public abstract class World extends Pane{
	private AnimationTimer timer;
	
	public World() {
		super();
		timer = new AnimationTimer(){
			@Override
			public void handle(long now){
				act(now);
				List<Actor> list = getObjects(Actor.class);
				for(int i = 0; i < list.size(); i++){
					list.get(i).act(now);
				}
			}
		};
		timer.start();
	}

	public void start(){
		timer.start();
	}
	
	public void stop(){
		timer.stop();
	}
	
	public void add(Actor actor){
		super.getChildren().add(actor);
	}
	
	public void add(Text text){
		super.getChildren().add(text);
	}
	
	public void remove(Text text){
		super.getChildren().remove(text);
	}
	
	public void remove(Actor actor){
		super.getChildren().remove(actor);
	}
	
	public <A extends Actor> java.util.List<A> getObjects(Class<A> cls){
		ObservableList<Node> list = this.getChildren();
		ArrayList<A> newList = new ArrayList<A>();
		for(int i = 0; i < list.size(); i++){
			if (cls.isInstance(list.get(i))){
				newList.add(cls.cast(list.get(i)));
			}
		}
		return newList;
	}

	public abstract void act(long now);

}
