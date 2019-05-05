package no.hvl.dat108.handlers_and_managers;

import java.io.IOException;
import java.util.Stack;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import no.hvl.dat108.actions.Action;
import no.hvl.dat108.actions.CheckLoginAction;
import no.hvl.dat108.actions.CreateAliasAction;
import no.hvl.dat108.back.Settings;
import no.hvl.dat108.back.Window;
import no.hvl.dat108.bundles.Menu;
import no.hvl.dat108.game_elements.RaceCarInteraction;
import no.hvl.dat108.simple_interactions.Button;
import no.hvl.dat108.simple_interactions.TextFieldInput;
import no.hvl.dat108.simple_interactions.TextPlain;

/**
 * @param Handler is the handler that handles handlers. This is also static.
 */

public class Handler {

	public static Handler instance;
	private SceneHandler sh;
	private GameHandler gh;
	private ModelHandler mh;
	private MouseManager mm;
	private KeyManager km;
	private FocusManager fm;
	private boolean onOffStatus;
	private RaceCarInteraction rci;

	public Handler() {
		// Make this class static
		if (instance != null)
			// Destroy myself
			return;
		else
			instance = this;

		// Standard volum value (on / off switch)
		onOffStatus = false;

		try {
			new Settings();
		} catch (IOException e) {
			e.printStackTrace();
		}
		rci = new RaceCarInteraction(null);
		// Create and initiate sub-handlers
		fm = new FocusManager();
		mm = new MouseManager();
		km = new KeyManager();
		km.addToKeyListeners("rci", rci);
		sh = new SceneHandler(0);
		mh = new ModelHandler();
		initSceneHandler();
		gh = new GameHandler(sh.getCurrentSceneList(), 0, false);

	}

	/**
	 * @param initSceneHandler initiates all gameobjects to their given scene. In a
	 *                         way adds gameobjects to the memory. Perhaps I should
	 *                         use .xml to contain the information as it is
	 *                         non-dynamic.
	 * 
	 *                         How?
	 * 
	 *                         Contain properties about current run and any run in a
	 *                         .xml file. For instance who is logged in and chosen
	 *                         options. Store in either %appdata% or docs. Perhaps
	 *                         mix .properties and .xml
	 * 
	 *                         If connected and in multiplayer connect an .xml file
	 *                         to the database in addition to enities
	 */

	private void initSceneHandler() {

		// Might be that the arraylists within must be initialized first

		sh.addToCurrentScene(new TextFieldInput(sh.getMiddleOfScreen(), 300, "Write Here", true, new CheckLoginAction(),
				"CompetitorUsername"));
		sh.addToCurrentScene(new TextFieldInput(sh.getMiddleOfScreen(), 350, "Write Here", false,
				new CheckLoginAction(), "CompetitorPassword"));
		sh.addToCurrentScene(
				new Button(sh.getMiddleOfScreen(), 400, sh.getStandardWidth(), "Login", new CheckLoginAction()));

		// Midlertidig bytte av scene slik at jeg ikke trenger å logge inn hver gang
//		sh.setScene(1);

		// Create the main menu
		sh.addToAnyScene(1, new TextPlain(sh.getMiddleOfScreen(), 50, "This is a game about something", 1, 24));
		Menu menu = new Menu(sh.getMiddleOfScreen(), 100);
		sh.addToAnyScene(1, menu);
		menu.addElement(new Button(1, 1, sh.getStandardWidth(), "Create Game", () -> getSh().setScene(2)));
		menu.addElement(new Button(1, 1, sh.getStandardWidth(), "Exercises", () -> getSh().setScene(3)));
		menu.addElement(new Button(1, 1, sh.getStandardWidth(), "Load Game", () -> getSh().setScene(4)));
		menu.addElement(new Button(1, 1, sh.getStandardWidth(), "Options", () -> getSh().setScene(5)));
		menu.addElement(new Button(1, 1, sh.getStandardWidth(), "Log out", () -> getSh().setScene(0)));

		// Lambda code that exits program.
		menu.addElement(new Button(1, 1, sh.getStandardWidth(), "Exit Game", () -> Window.close()));

		// 3: Exercises
		Action action = null;

		TextPlain exerciseOutput = new TextPlain(sh.getMiddleOfScreen(), 50, "Hallo", 0, 24);

		TextFieldInput exerciseInput1 = new TextFieldInput(sh.getMiddleOfScreen(), 100, "Backward It", true, () -> {
		}, "ExerciseBackwardsText");
		Button exerciseButton1 = new Button(sh.getMiddleOfScreen(), 200, sh.getStandardWidth(), "Submit", () -> {
			char[] textIn = exerciseInput1.getActualString().toCharArray();
			char[] textOut = new char[textIn.length];
			Stack<Character> stack = new Stack<Character>();
			System.out.println("Legger til Characters");
			for(char e : textIn) {
				stack.push(e);
				System.out.print(stack.peek());
			}
			System.out.println("Popper Characters");
			for(int i = 0; i < textOut.length; i++) {
				textOut[i] = stack.pop();
				System.out.print(textOut[i]);
			}
			
			exerciseOutput.changeText(new String(textOut));
		});

		sh.addToAnyScene(3, exerciseOutput);
		sh.addToAnyScene(3, exerciseInput1);
		sh.addToAnyScene(3, exerciseButton1);

		// "Create Game" menu
//		sh.addToAnyScene(2, new (middleOfSceenBtn, 350, ""));
		sh.addToAnyScene(2, new Button(sh.getMiddleOfScreen(), 100, sh.getStandardWidth() + 50, "New Singleplayer",
				() -> getSh().setScene(6)));
		sh.addToAnyScene(2, new Button(sh.getMiddleOfScreen(), 200, sh.getStandardWidth() + 50, "New Multiplayer",
				() -> getSh().setScene(6)));

		// Options
		sh.addToAnyScene(5, new Button(sh.getMiddleOfScreen(), 100, sh.getStandardWidth(), "Fullscreen",
				() -> Window.fullscreen()));
		sh.addToAnyScene(5, new Button(sh.getMiddleOfScreen(), 200, sh.getStandardWidth(), "Volume On/Off", () -> {
			setVolumeStatus(!getVolumeStatus());
			try {
				if (getVolumeStatus())
					gh.playBackgroundMusic();
				else
					gh.turnOffBackgroundMusic();
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				e.printStackTrace();
			}
		}));

		// Create new Alias scene
		sh.addToAnyScene(6, new TextPlain(sh.getMiddleOfScreen(), 50, "Create a new alias", 0, 18));
		sh.addToAnyScene(6, new TextFieldInput(sh.getMiddleOfScreen(), 100, "Name", true, null, "NewAliasUsername"));
		sh.addToAnyScene(6,
				new Button(sh.getMiddleOfScreen(), 200, sh.getStandardWidth(), "Create", new CreateAliasAction()));

	}

	/*
	 * Getters and setters
	 */

	public void setVolumeStatus(boolean value) {
		onOffStatus = value;
	}

	public boolean getVolumeStatus() {
		return onOffStatus;
	}

	/**
	 * @return The FocusManager
	 */
	public FocusManager getFm() {
		return fm;
	}

	/**
	 * @return The SceneHandler
	 */
	public SceneHandler getSh() {
		return sh;
	}

	/**
	 * @return The GameHandler
	 */
	public GameHandler getGh() {
		return gh;
	}

	/**
	 * @return The MouseManager
	 */
	public MouseManager getMm() {
		return mm;
	}

	/**
	 * @return The KeyManager
	 */
	public KeyManager getKm() {
		return km;
	}

	/**
	 * @return The ModelHandler
	 */
	public ModelHandler getMh() {
		return mh;
	}

	public RaceCarInteraction getRci() {
		return rci;
	}

	public void setRci(RaceCarInteraction rci) {
		this.rci = rci;
	}
}
