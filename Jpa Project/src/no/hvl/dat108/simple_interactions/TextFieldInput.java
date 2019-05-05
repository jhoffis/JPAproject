package no.hvl.dat108.simple_interactions;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EmptyStackException;

import no.hvl.dat108.actions.Action;
import no.hvl.dat108.handlers_and_managers.Handler;
import no.hvl.dat108.main.GameObject;

/**
 * @param TextFieldInput write in info that can later be used in some sence.
 *                       TODO implement Focus so that you don't type in a hotkey
 *                       or something while typing in here
 * 
 *                       TODO Go through this class and clean it up
 * 
 *                       This class only checks whether or not it has been
 *                       pressed, unlike Button which also checks where the
 *                       mouse is all the time. This does check where it was
 *                       pressed though.
 */
public class TextFieldInput extends GameObject {

	private boolean drawLine;
	private boolean visibleText;
	private boolean firstTime;
	private long time = 0;
	private int cursorPosition;
	private float fontLengthPx;
	private float mouseCalcPosition;
	private int width;
	private int height;
	private int fontSize;
	private int maxTextLength;
	private Color none;
	private Color over;
	private Color down;
	private Color currentColor;
	private Color textColor;
	private String title;
	private Point p;
	private TextFieldIterator text;
	private String actualText;
	private Font font;
	private Action action;
	private int newX;
	private int type;

	public TextFieldInput(int x, int y, String title, boolean visibleText, Action action, String name) {
		// Standard values
		init(x, y, 200, 36, Color.WHITE, Color.LIGHT_GRAY, Color.green, title, 24, visibleText, action, name, 0);
	}

	public TextFieldInput(int x, int y, int width, int height, Color none, Color over, Color down, String title,
			int fontSize, boolean visibleText, Action action, String name, int type) {
		// Custom values
		init(x, y, width, height, none, over, down, title, fontSize, visibleText, action, name, type);
	}

	private void init(int x, int y, int width, int height, Color none, Color over, Color down, String title,
			int fontSize, boolean visibleText, Action action, String name, int type) {
		this.x = x;
		this.fontSize = fontSize;
		this.y = y;
		this.width = width;
		this.height = height;
		this.none = none;
		this.over = over;
		this.down = down;
		currentColor = none;
		textColor = Color.black;
		this.title = title;
		text = new TextFieldIterator(title);
		font = new Font("Lucida Console", Font.PLAIN, fontSize);
		fontLengthPx = (float) fontSize * (7f / 12f);
		this.visibleText = visibleText;
		if (visibleText)
			actualText = text.getActualString();
		else
			actualText = invisibleText();
		firstTime = true;
		this.action = action;
		maxTextLength = (int) (width / (fontLengthPx));
		super.nameID = name;
		this.type = type;
		changeNewX(type);
	}

	@Override
	public void render(Graphics g) {
		g.setFont(font);
		g.setColor(currentColor);
		g.fillRect(newX, y, width, height);
		g.setColor(textColor);
		g.drawString(actualText, newX, y + height / 2);
		if (super.inFocus && drawLine())
			g.drawString(drawLineString(cursorPosition), newX, y + height / 2);
	}

	/**
	 * @param drawLineString returns a string that has a '_' at the requested index.
	 */

	private String drawLineString(int n) {
		char[] arr = new char[n + 1];
		for (int i = 0; i < arr.length - 1; i++) {
			arr[i] = ' ';
		}
		arr[arr.length - 1] = '_';
		return String.valueOf(arr);
	}

	/**
	 * @param drawLine returns a boolean that alteres between true and false every
	 *                 given interval. The current interval is 500ms.
	 */

	private boolean drawLine() {

		if (time - System.currentTimeMillis() < 0) {

			time = System.currentTimeMillis() + 500;
			if (!drawLine)
				drawLine = true;
			else
				drawLine = false;
		}
		return drawLine;
	}

	/**
	 * @param invisibleText takes all character in the currently showing text and
	 *                      turns every character into dots. Used mainly for
	 *                      password input
	 */

	private String invisibleText() {
		char[] arr = new char[text.getAmount()];
		String dot = "\u2022";
		for (int i = 0; i < arr.length; i++) {
			arr[i] = dot.charAt(0);
		}

		return String.valueOf(arr);
	}

	/**
	 * @param getContrastColor returns a color that is the oppisite of the color
	 *                         given as parameter
	 */

	public Color getContrastColor(Color color) {
		double y = (299 * color.getRed() + 587 * color.getGreen() + 114 * color.getBlue()) / 1000;
		return y >= 128 ? Color.black : Color.white;
	}

	/**
	 * @param tick checks statuses and updates accordingly.0
	 */
	@Override
	public void tick() {
		checkMouseStatus();
		textColor = getContrastColor(currentColor);
		drawLine();

		if (super.inFocus) {
			updateCursorPosition();
			checkKeyStatus();
		}
	}

	/**
	 * @param checkKeyStatus controls keyinput and redirects to the correct methods.
	 *                       Allows input
	 */

	public void checkKeyStatus() {
		try {
			if (Handler.instance.getKm().getPressed()) {
				KeyEvent c = Handler.instance.getKm().pop();
				if (String.valueOf(c.getKeyChar()).matches("[A-Za-z0-9 ,.-_*!\"#¤%&/()=@£$€|§]$")) {
					if (text.getAmount() < maxTextLength)
						text.add(c.getKeyChar());
				} else if (c.getKeyCode() == 8) {
					// Backspace
					text.removeThis();
				} else if (c.getKeyCode() == 37) {
					// Left-Arrow
					text.iterateToIndex(cursorPosition - 1);
				} else if (c.getKeyCode() == 39) {
					// Right-Arrow
					text.iterateToIndex(cursorPosition + 1);
				} else if (c.getKeyCode() == 127) {
					// Delete
					text.removeNext();
				} else if (c.getKeyCode() == 9 && c.isShiftDown()) {
					// Tab and shift
					tabSearch(-1);
				} else if (c.getKeyCode() == 9) {
					// Tab without shift
					tabSearch(1);
				} else if (c.getKeyCode() == 10) {
					// Enter / Return
					if (action != null) {
						action.run();
					}
				}
				// Update the actualText after button press
				if (visibleText)
					actualText = text.getActualString();
				else
					actualText = invisibleText();
			}
		} catch (EmptyStackException e) {

		}
	}
	
	public void changeNewX(int type) {
		if (type == 1)
			newX = x - (int) (width / 2);
		else if (type == 2)
			newX = x - (int) (width);
		else
			newX = x;
	}


	private void tabSearch(int distance) {
		ArrayList<GameObject> tempList = Handler.instance.getGh().getObjects();

		try {
			for (int i = 0; i < tempList.size(); i++) {
				if (this.equals(tempList.get(i)))
					if (tempList.get(i + distance).getClass().equals(this.getClass())) {
						Handler.instance.getFm().setCurrentFocus(tempList.get(i + distance));
						((TextFieldInput) tempList.get(i + distance)).doCheckFirstTime();
						break;
					} else
						break;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			// Fail
			return;
		}
	}

	/**
	 * @param checkMouseStatus Check first where the mouse is. If the mouse is over
	 *                         the button, check if it is pressed. Give appropiate
	 *                         color.
	 */
	private void checkMouseStatus() {

		try {
			p = Handler.instance.getMm().getPoint();
			if ((p.x >= x && p.x <= width + x) && (p.y >= y && p.y <= height + y)) {
				if (Handler.instance.getMm().peekPressed())
					currentColor = down;
				else
					currentColor = over;
			} else
				currentColor = none;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateCursorPositionByMouse() {
		mouseCalcPosition = p.x - x;
		cursorPosition = (int) (mouseCalcPosition / fontLengthPx);
		if (cursorPosition > text.getAmount())
			cursorPosition = text.getAmount();
		text.iterateToIndex(cursorPosition);
	}

	public void doCheckFirstTime() {
		if (firstTime) {
			firstTime = false;
			text.removeAll();
			actualText = "";
		}
	}

	private void doDown() {
		doCheckFirstTime();
		Handler.instance.getFm().setCurrentFocus(this);
		updateCursorPositionByMouse();
		super.inFocus = Handler.instance.getFm().getCurrentFocus().equals(this);
	}

	private void updateCursorPosition() {
		cursorPosition = text.getCurrentIndex();
	}

	private void doOver() {
		currentColor = over;
	}

	private void doNone() {
		GameObject temp = Handler.instance.getFm().getCurrentFocus();
		if (temp != null && temp.equals(this)) {
			Handler.instance.getFm().setCurrentFocus(null);
		}
		if (super.inFocus)
			super.inFocus = false;
		currentColor = none;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the visual string in the inputfield. That includes returning dots
	 *         instead of text if the text is "invisible".
	 */

	public String getActualText() {
		return actualText;
	}

	/**
	 * @return the underlying string that does not include dots. Just the raw text
	 *         that has been inputed.
	 */
	public String getActualString() {
		return text.getActualString();
	}

	@Override
	public boolean checkMouse(MouseEvent e) {

		if ((e.getX() >= x && e.getX() <= width + x) && (e.getY() >= y && e.getY() <= height + y)) {
			doDown();
			return true;
		}
		doNone();
		return false;
	}

}
