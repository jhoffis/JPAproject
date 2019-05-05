package no.hvl.dat108.simple_interactions;

/**
 * Skal først sjekke hvor mange bokstaver det er i textfielden. Så finne ut
 * hvilket tall man havner på ved å ta float totalWidthOfCurrentText =
 * (bokstavcount * (fontsizef * (7f / 12f));
 * 
 * int actualXPosOnField = mousepos.x - textfield.x;
 * 
 * @param TextFieldIterator used to implement iterator, but being so customized
 *                          it worked better without
 * 
 *                          Current is the letter that is selected. Change is
 *                          done between current and prevh
 */

public class TextFieldIterator {

	// Lag en liste som holder også på nodene. Siden denne allerede holder på
	// indexen så skal det gå greit
	private int amount;
	private int currentIndex;
	private LinearNode origin;
	private LinearNode current;
	private LinearNode prev;
	private String actualText;
	private boolean initiating;

	public TextFieldIterator(String s) {
		initiating = true;
		char[] text = s.toCharArray();
		origin = new LinearNode(text[0]);
		currentIndex = 0;
		for (char value : text) {
			add(value);
			if (hasNext())
				next();
		}
		initiating = false;
	}

	/**
	 * @param hasNext checks local node if next node isn't null
	 */
	public boolean hasNext() {
		return current.getNext() != null;
	}

	/**
	 * @param hasPrev checks local node if previous node isn't null
	 */
	public boolean hasPrev() {
		return prev != null;
	}

	/**
	 * @param next checks hasNext and updates node to the next node if possible.
	 */
	public char next() {
		if (!hasNext())
			// FIXME Cannot return a char as null
			return (Character) null;
		prev = current;
		current = current.getNext();
		currentIndex++;
		return current.getElement();
	}

	/**
	 * @param previous checks hasPrev and updates node to the previous node if
	 *                 possible.
	 */
	public char previous() {
		if (!hasPrev())
			return (Character) null;
		current = prev;
		prev = prev.getPrev();
		currentIndex--;
		return current.getElement();
	}

	public boolean iterateToIndex(int i) {
		// Flytt nåværende posisjon mot i. Eller prøv i alle fall.

		// Fail is parameter is invalid
		if (i >= amount || i < 0)
			return false;

		// iterate towards the given parameters value
		while (currentIndex != i) {
			// Go forwards
			if (currentIndex < i) {
				next();
			}
			// Go backwards
			if (currentIndex > i) {
				previous();
			}
		}
		return true;
	}

	public void removeNext() {
		// Checks if it is empty. Return if true
		if (isEmpty() || !hasNext())
			return;

		// Updates amount
		amount--;
		// Removes references to the next node
		try {
			current.getNext().getNext().setPrev(current);
			current.setNext(current.getNext().getNext());
		} catch (NullPointerException e) {
			// The next node does not have a node after itself. Reference to null
			current.setNext(null);
		}

		updateString();
	}

	public void removeThis() {
		// TODO oppdater linearnode sin prev, self og next, samme med her.
		// Move current to previous node and connect previous node to the node after
		// this node

		// Checks if it is empty. Return if true
		if (isEmpty())
			return;

		// Updates index and numeric values
		if (currentIndex > 0)
			currentIndex--;
		amount--;

		// Moves current to next if there is any
		if (!hasPrev()) {
			if (hasNext()) {
				current = current.getNext();
				current.setPrev(null);
			} else
				current = null;
			origin = current;
			updateString();
			return;
		}

		// Moves current to previous node
		if (!hasNext())
			prev.setNext(null);
		else {
			prev.setNext(current.getNext());
			current = prev;
			current.getNext().setPrev(current);
		}
		updateString();
	}

	public void removeAll() {
		// Checks if it is empty. Return if true
		if (isEmpty())
			return;
		
		while(amount > 0) {
			removeThis();
		}
	}

	public void add(char c) {
		// TODO oppdater linearnode sin prev, self og next, samme med her.
		// Forklar hva ting gjør

		if (isEmpty()) {
			current = new LinearNode(c);
			currentIndex = 0;
			amount++;
			origin = current;
			updateString();
			return;
		}

		LinearNode temp;

		// Create a new LinearNode and move it in after current
		if (hasPrev())
			prev = current;

		// Add on left side if all the way to the left
		if (!initiating && currentIndex == 0 && amount > 1) {
			temp = new LinearNode(c, null, current);
			origin = temp;
			current.setPrev(temp);
			current = temp;
		} else if (!hasNext()) {
			temp = new LinearNode(c, current);
			current.setNext(temp);
			current = temp;
		} else {
			temp = new LinearNode(c, current, current.getNext());
			current.getNext().setPrev(temp);
			current.setNext(temp);
			current = temp;
		}
		// move currentIndex and amount
		currentIndex++;
		amount++;

		updateString();
	}

	public boolean isEmpty() {
		return amount == 0 && current == null;
	}

	private void updateString() {
		actualText = getString();
	}

	public String getString() {
		// TODO Få denne til å fungere skikkelig og finn ut av hvor denne kan bli brukt
		// mest effektivt.
		// Arrayen finnes ikke lengre så ja.
		if (isEmpty())
			return "";

		int tempCurrentIndex = currentIndex;
		reset();

		char[] tempArr = new char[amount];

		while (hasNext() || current != null) {
			tempArr[currentIndex] = current.getElement();
			if (hasNext())
				next();
			else
				break;
		}

		iterateToIndex(tempCurrentIndex);

		return String.valueOf(tempArr);
	}

	public boolean reset() {
		prev = null;
		current = origin;
		currentIndex = 0;
		return current != null;
	}

	public String getActualString() {
		return actualText;
	}

	public int getAmount() {
		return amount;
	}

	public void setCurrentIndex(int i) {
		currentIndex = i;
	}

	public void setCurrentNode(LinearNode ln) {
		current = ln;
	}

	public void setPreviousNode(LinearNode ln) {
		prev = ln;
	}

	public void setNextNode(LinearNode ln) {
		current.setNext(ln);
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	public LinearNode getCurrent() {
		return current;
	}

	public LinearNode getPrev() {
		return prev;
	}
}
