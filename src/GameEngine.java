/**
 * Idea for scrolling background from http://wecode4fun.blogspot.com.
 */
package p1_group_10;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameEngine extends Application{
	World world;

	private double SCENE_WIDTH = 800;
	private double SCENE_HEIGHT = 400;

	Actor dino = new Dinosaur(); //not an actual dinosaur in game
	Actor enemy = new Enemy();
	Image cactusImg = new Image("file:Images/cactus.png");
	int cactusCounter = 50;
	int enemyCounter;
	int enemyChance = 0;

	private AnimationTimer backgroundTimer;

	ImageView backgroundImageView;
	double backgroundScrollSpeed = 5;
	Pane backgroundPane;

	double PIC_LENGTH;

	boolean start;
	int scroll = 0;

	Text text;
	Text bulletText;
	Text timeText;
	Text levelText;

	List<Actor> removalList;

	int currentLevel;
	int numLevels;

	int numBullets = 5;
	int time;
	int timeCounter;
	
	
	 ColorAdjust colorAdjust;
	 double brightness = 0;

	public GameEngine() {

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stage) throws Exception {
		try{
			start = true;

			currentLevel = 1;
			numLevels = 10;

			PIC_LENGTH = 2500;

			stage.setTitle("T-Rex Game");
			world = new Level(1);

			backgroundPane = new Pane();

			world.getChildren().add(backgroundPane);

			Scene scene = new Scene(world, SCENE_WIDTH, SCENE_HEIGHT);
			stage.setScene(scene);

			removalList = new ArrayList<Actor>();

			dino.setY(250);
			dino.setX(25);

			world.add(dino);

			text = new Text(25, 25, "press ENTER to start");
			text.setFill(Color.BLACK);
			text.setFont(Font.font(java.awt.Font.SERIF, 25));
			text.setX(200);
			text.setY(100);

			timeText = new Text(25, 25, "Time: "+time);
			timeText.setFill(Color.BLACK);
			timeText.setFont(Font.font(java.awt.Font.SERIF, 25));
			timeText.setX(450);
			timeText.setY(30);

			bulletText = new Text(25, 25, "Bullets remaining: "+numBullets);
			bulletText.setFill(Color.BLACK);
			bulletText.setFont(Font.font(java.awt.Font.SERIF, 25));
			bulletText.setX(550);
			bulletText.setY(30);
			
			levelText = new Text(25, 25, "Level: " + ((Level) world).getLevel() + "/10");
			levelText.setFill(Color.BLACK);
			levelText.setFont(Font.font(java.awt.Font.SERIF, 25));
			levelText.setX(310);
			levelText.setY(30);

			stage.setScene(scene);
			stage.show();
			
			 colorAdjust = new ColorAdjust();
		     

			loadGame(); 

			scene.setOnKeyPressed(event -> {
				if(event.getCode() == KeyCode.ENTER && start){
					((Dinosaur)dino).setStart();
					startGameLoop();
					start = false;
					world.remove(text);
					((Dinosaur) dino).resume();
				}
				if (event.getCode() == KeyCode.SPACE) {
					((Dinosaur) dino).jump();
				}
				if (event.getCode() == KeyCode.S) //&& numBullets>0)
				{
					bulletText.setText("Bullets remaining: "+(--numBullets));
					Image bulletImage = new Image("file:Images/bullet.png");
					Actor bullet = new Bullet();
					bullet.setScaleX(.2);
					bullet.setScaleY(.2);
					bullet.setX(180);
					bullet.setY(dino.getY());
					bullet.setImage(bulletImage);
					world.add(bullet);

				}
				if (event.getCode() == KeyCode.DOWN){
					//dino.setImage(ducking);
					dino.setY(250);
				}
			});

			scene.setOnKeyReleased(event -> {
				if(event.getCode() == KeyCode.DOWN){
					//dino.setImage(dinoImg);
					dino.setY(200);
				}

			});			
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

	private void startGameLoop() {

		List<Actor> list = world.getObjects(Actor.class);
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).getClass() == Cactus.class){
				if (currentLevel == 1)
					((Cactus)list.get(i)).setDX(-5);
				else if (currentLevel == 2)
					((Cactus)list.get(i)).setDX(-6);
				else 
					((Cactus)list.get(i)).setDX(-7);
			}else if(list.get(i).getClass() == Bullet.class){
				((Bullet)list.get(i)).setDX(8);
			}else if(list.get(i).getClass() == Enemy.class){
				((Enemy)list.get(i)).setDX(-2);
			}

		}


		backgroundTimer = new AnimationTimer() {

			@Override
			public void handle(long l) {
				double x = backgroundImageView.getLayoutX() - backgroundScrollSpeed;
				if( Double.compare(PIC_LENGTH  - 5 + backgroundImageView.getLayoutX(), SCENE_WIDTH) <= 0) {
					x = 0;

				}
				backgroundImageView.setLayoutX(x);
				
				if (time>100)
				{
					if(((Level) world).getLevel() < numLevels){
						nextLevel();
					}else{
						backgroundTimer.stop();
						Text win = new Text("You Win!");
						win.setX(SCENE_WIDTH / 2);
						win.setY(SCENE_HEIGHT / 2);
						world.add(win);
					}
				}

				if(removalList.size() > 0){
					for(int i = 0; i < removalList.size(); i++){
						world.remove(removalList.get(i));
						removalList.remove(removalList.get(i));
					}
				}

				List<Actor> actorList = world.getObjects(Actor.class);
				for(int i = 0; i < actorList.size(); i++){
					if(actorList.get(i).getClass() == Bullet.class){
						Actor temp2 = actorList.get(i).getOneIntersectingObject(Cactus.class);
						if(temp2 != null && temp2.getClass() == Cactus.class){
							removalList.add(temp2);
							removalList.add(actorList.get(i));
						}
					}
					if (actorList.get(i).getClass() == Dinosaur.class){
						Actor temp2 = dino.getOneIntersectingObject(Cactus.class);
						if(temp2 != null && temp2.getClass() == Cactus.class){
							backgroundTimer.stop();
							gameOver();
						}
						else
						{
							temp2 = dino.getOneIntersectingObject(Enemy.class);
							if(temp2 != null && temp2.getClass() == Enemy.class){
								backgroundTimer.stop();
								gameOver();
							}
						}
					}

				}

				int cactusRandom = (int)(Math.random() * 1000);

				if(cactusRandom < 50 && cactusCounter == 0){
					cactusCounter = 90;
					world.add(new Cactus(800, 250));
				}

				if(cactusCounter > 0){
					cactusCounter--;
				}

				int enemyRandom = (int)(Math.random()*1000);

				if (enemyRandom< enemyChance && enemyCounter == 0){
					enemyCounter = 100;
					world.add(new Enemy(800, 130));

				}

				if (enemyCounter > 0){
					enemyCounter--;
				}

				timeCounter++;
				if (timeCounter > 10)
				{
					timeText.setText("Time: "+(++time) );
					timeCounter = 0;
				}
			}

			


		};

		backgroundTimer.start();
	}

	private void pause() {
		List<Actor> list = world.getObjects(Actor.class);
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).getClass() == Cactus.class){
				((Cactus)list.get(i)).setDX(0);
			}else if(list.get(i).getClass() == Bullet.class){
				((Bullet)list.get(i)).setDX(0);
			}else if(list.get(i).getClass() == Enemy.class){
				((Enemy)list.get(i)).setDX(0);
				((Enemy)list.get(i)).pause();
			}

		}
		((Dinosaur) dino).pause();
		
	}
	
	private void loadGame() {

		world.add(text);
		world.add(bulletText);
		world.add(timeText);
		world.add(levelText);

		backgroundImageView = new ImageView("file:Images/levelOne.png");

		backgroundImageView.setFitHeight(400);

		backgroundImageView.relocate(0, 0);

		backgroundPane.getChildren().add(backgroundImageView);
	}	

	private void gameOver() {
		//System.out.println("game over");
		pause();
		text.setText("Game Over");
		world.add(text);
	}

	public void nextLevel(){
		//System.out.println("level Up! ");
		pause();
		((Level) world).setLevel(((Level) world).getLevel() + 1);
		numBullets = 5;
		bulletText.setText("Bullets remaining: "+ numBullets);
		backgroundTimer.stop();

		start = true;
		text.setText("press ENTER to play level "+ ((Level) world).getLevel());

		world.add(text);
		levelText.setText("Level: " + ((Level) world).getLevel() + "/10");
		enemyChance += 5;

		List<Actor> list = world.getObjects(Actor.class);
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).getClass() == Cactus.class || list.get(i).getClass() == Bullet.class ||
					list.get(i).getClass() == Enemy.class){
				world.remove(list.get(i));;
			}
		}
		
		time = 0;
		timeText.setText("Time: "+(time) );
		
		brightness -= 0.05;
		colorAdjust.setBrightness(brightness);
		backgroundImageView.setEffect(colorAdjust);
	}
}
