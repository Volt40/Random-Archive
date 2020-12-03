package org.cascadelabs.parsey.node;

/**
 * Thrown if something goes wrong when calculating the node network.
 * 
 * @author Michael Baljet
 * @version 1.0
 *
 */
public class NodeException extends Exception {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 5348179140485417590L;

	public NodeException(String errorMessage) {
		super(errorMessage);
	}

}
