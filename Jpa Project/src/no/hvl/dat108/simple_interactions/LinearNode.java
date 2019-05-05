package no.hvl.dat108.simple_interactions;

public class LinearNode {
	private LinearNode next;
	private LinearNode prev;
	private char element;
	
	/**
	 * Creates a node storing the specified element.
	 * @param elem element to be stored
	 */
	public LinearNode(char elem) {
		next = null;
		prev = null;
		element = elem;
	}
	
	public LinearNode(char elem, LinearNode prev) {
		next = null;
		this.prev = prev;
		element = elem;
	}
	
	public LinearNode(char elem, LinearNode prev, LinearNode next) {
		this.next = next;
		this.prev = prev;
		element = elem;
	}
	
	/**
	 * Returns the node that follows this one.
	 * @return reference to next node
	 */
	public LinearNode getNext() {
		return next;
	}
	/**
	 * Sets the node that follows this one.
	 * @param next node to follow this one
	 */
	public void setNext(LinearNode next) {
		this.next = next;
	}
	/**
	 * Returns the element stored in this node
	 * @return element stored at the node
	 */
	public char getElement() {
		return element;
	}
	/**
	 * Sets the element stored in this node.
	 * @param element element to be stored at this node
	 */
	public void setElement(char element) {
		this.element = element;
	}
	/**
	 * Returns the previous node stored in this node
	 * @return reference to previous node
	 */
	public LinearNode getPrev() {
		return prev;
	}
	/**
	 * Sets the node that comes before this one.
	 * @param prev node to predecess this one
	 */
	public void setPrev(LinearNode prev) {
		this.prev = prev;
	}
}
