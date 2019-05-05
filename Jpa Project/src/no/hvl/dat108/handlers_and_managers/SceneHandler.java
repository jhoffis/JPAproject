package no.hvl.dat108.handlers_and_managers;

import java.awt.Color;
import java.util.ArrayList;

import no.hvl.dat108.game_elements.RaceCar;
import no.hvl.dat108.game_elements.RaceCarInteraction;
import no.hvl.dat108.game_elements.RaceTrack;
import no.hvl.dat108.main.GameObject;
import no.hvl.dat108.main.Main;
import no.hvl.dat108.model.Alias;
import no.hvl.dat108.simple_interactions.Button;
import no.hvl.dat108.simple_interactions.TextPlain;

/*
 * Holds the id to the current scene and lists of gameobjects
 * for each own scene. One can add and remove certain objects from
 * each scene and get the scene from the current scene.
 * 
 * $0 = login
 * $1 = main menu
 * $2 = create game
 * $3 = join game
 * $4 = load game
 * $5 = options
 * $6 = new singleplayer game
 * $7 = new multiplayer game
 * $8 = menu singleplayer game
 * $9 = racing
 */

public class SceneHandler {

	// Bytt om til Map slik at scener er satt til navn i stedet for tall
	private int currentScene;
	private ArrayList<ArrayList<GameObject>> scenes;
	private ArrayList<GameObject> globalScene;
	private int middleOfScreenBtn = Main.WIDTH / 2;
	private int stdWidth = 120;
	private TextPlain currentAliasText;
	private ModelHandler tempMH;

	public SceneHandler(int currentScene) {
		this.currentScene = currentScene;
		scenes = new ArrayList<ArrayList<GameObject>>();
		currentAliasText = new TextPlain(Main.WIDTH - 10, 20, "", 2, 24, "CurrentAliasText", Color.GREEN);

		// Back buttons added to scenes accessed by main menu
		globalScene = new ArrayList<GameObject>();
		globalScene.add(new Button(10, 10, getStandardWidth(), "Back to menu", () -> setScene(1)));
		globalScene.add(currentAliasText);
	}

	public void setScene(int currentScene) {
		Handler.instance.getGh().setUseGlobalScene(true);
		this.currentScene = currentScene;

		tempMH = Handler.instance.getMh();
		// Spesific operations
		switch (currentScene) {
		case 0:
			Handler.instance.getGh().setUseGlobalScene(false);
			break;
		case 1:
			// Reset current Alias
			Handler.instance.getGh().setUseGlobalScene(false);
			tempMH.nullifyAlias();
			break;
		case 4:
			sceneLoadGame();
			break;
		case 8:
			sceneLoadLevelsBeatenSinglePlayer();
			break;
		case 9:
			scenePlaySinglePlayer();
			break;
		}
		//Update game visuals and interactions
		Handler.instance.getGh().sceneChange(getScene(), getCurrentSceneList());
	}

	public int getScene() {
		return currentScene;
	}

	public void loadSceneAndPlaySinglePlayer(Integer aliasID) {
		Handler.instance.getMh().loadAlias(aliasID);
		Handler.instance.getSh().setScene(8);
	}

	public ArrayList<GameObject> getCurrentSceneList() {
		// Try to return the list, if it fails, that means it is null and must be
		// initiated
		try {
			return scenes.get(currentScene);
		} catch (IndexOutOfBoundsException e) {
			for (int i = scenes.size() - 1; i < currentScene; i++) {
				scenes.add(new ArrayList<GameObject>());
			}
		}
		return scenes.get(currentScene);
	}

	public void addToCurrentScene(GameObject go) {
		doAdd(currentScene, go);
	}

	public void addToAnyScene(int scene, GameObject go) {
		doAdd(scene, go);
	}

	public GameObject getGOAnyScene(int index, int scene) {
		return scenes.get(scene).get(index);
	}

	public GameObject getGOCurrentScene(int index) {
		return scenes.get(currentScene).get(index);
	}

	public GameObject getGOViaNameCurrentScene(String name) {
		return getGOViaName(name, currentScene);
	}

	public GameObject getGOViaNameAnyScene(String name, int scene) {
		return getGOViaName(name, scene);
	}

	public int getMiddleOfScreen() {
		return middleOfScreenBtn;
	}

	public int getStandardWidth() {
		return stdWidth;
	}
	//$4
	private void sceneLoadGame() {
		// Update the list of aliases related to current competitor
		int tempIndex = 0;
		int tempx = -2;
		try {
			
			forceUpdateScene(4);

			for (Alias element : tempMH.getCompetitor().getAliasList()) {
				if(tempIndex % 5 == 0) {
					tempx++;
					tempIndex = 0;
				}
				Button temp = new Button(middleOfScreenBtn + (200* tempx), 100 * tempIndex + 100, stdWidth, element.getName(),
						() -> loadSceneAndPlaySinglePlayer(element.getAliasid()));
				temp.nameID = "loadAliasListElement";
				addToAnyScene(4, temp);
				tempIndex++;
			}
		} catch (NullPointerException e) {
			// Competitor has not logged in yet
			System.out.println("Competitor has not logged in yet \n" + e.getMessage());
		}
		
		addToCurrentScene(new TextPlain(middleOfScreenBtn, 20, "Load a save", 0, 24));
	}
	//$8
	private void sceneLoadLevelsBeatenSinglePlayer() {
		
		forceUpdateScene(8);
		
		int tempLvlsBeat = tempMH.getCurrentAlias().getLevelsBeaten();
		addToCurrentScene(new TextPlain(middleOfScreenBtn, 100, String.valueOf(tempLvlsBeat), 0, 24));
		addToCurrentScene(new Button(middleOfScreenBtn, 200, 400, 200, new Color(150, 120, 120), new Color(160, 130, 130), new Color(150, 100, 100), "Play", () -> setScene(9), (byte) 1));
	}
	//$9
	private void scenePlaySinglePlayer() {
		forceUpdateScene(9);
		
		addToCurrentScene(new RaceTrack(3000, new RaceCar()));
	}

	private GameObject getGOViaName(String name, int scene) {
		for (GameObject go : scenes.get(scene)) {
			if (go.nameID.equals(name)) {
				return go;
			}
		}
		return null;
	}

	private void doAdd(int scene, GameObject o) {
		// Trys to add a Object into the array, if it fails; create a arraylist and then
		// add the object

		try {
			scenes.get(scene).add(o);
		} catch (IndexOutOfBoundsException e) {
			updateScenes(scene);
			scenes.get(scene).add(o);
		}
	}

	private void forceUpdateScene(int scene) {
		try {
			scenes.set(scene, new ArrayList<GameObject>());
		} catch (IndexOutOfBoundsException e) {
			updateScenes(scene);
		}
	}
	
	private void updateScenes(int toWhichScene) {
		for (int i = scenes.size(); i <= toWhichScene; i++) {
			scenes.add(new ArrayList<GameObject>());
		}
	}
	
	public ArrayList<GameObject> getGlobalScene() {
		return globalScene;
	}

	public TextPlain getCurrentAliasText() {
		return currentAliasText;
	}
}
