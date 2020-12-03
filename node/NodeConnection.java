package org.cascadelabs.parsey.node;

/**
 * Represents a connection between the input and output of two node.
 * 
 * @author Michael Baljet
 * @version 1.0
 * @param <I, O extends I> Data type this connection works with.
 */
public class NodeConnection<I, O extends I> {
	
	/**
	 * NodeOutput this connection is linked to. Must be of type O.
	 */
	private NodeOutput<O> output;
	
	/**
	 * NodeInput this connection is linked to. Must be of type I.
	 */
	private NodeInput<I> input;
	
	/**
	 * Pushes from the linked output to the linked input.
	 */
	public void push() {
		input.setValue(output.getValue());
	}
	
	/**
	 * Sets the linked NodeOutput.
	 * @param output NodeOutput to be linked. Must be of type O.
	 */
	public void setOutput(NodeOutput<O> output) {
		this.output = output;
	}
	
	/**
	 * Sets the linked NodeInput.
	 * @param input NodeInput to be linked. Must be of type I.
	 */
	public void setInput(NodeInput<I> input) {
		this.input = input;
	}
	
	/**
	 * Returns the linked output of this connection.
	 * @return the linked output of this connection.
	 */
	public NodeOutput<O> getLinkedOutput() {
		return output;
	}
	
	/**
	 * Returns the linked input of this connection.
	 * @return the linked input of this connection.
	 */
	public NodeInput<I> getLinkedInput() {
		return input;
	}
	
	/**
	 * Removes the connection from its linked nodes.
	 */
	public void remove() {
		output.removeConnection(this);
		input.setConnection(null);
	}
	
}
