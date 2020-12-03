package org.cascadelabs.parsey.node;

import java.util.ArrayList;
import java.util.List;

/**
 * The output of a node, the output is of type T.
 * 
 * @author Michael Baljet
 * @version 1.0
 * @param <T> The type of the output.
 */
public class NodeOutput<T> {
	
	/**
	 * The current cached value inside of this output node. This field is populated when calculate() is called.
	 */
	private T value;
	
	/**
	 * The connections this output node uses.
	 */
	private List<NodeConnection<?, T>> connections;
	
	/**
	 * Whether or not this node is connected.
	 */
	private boolean connected;
	
	/**
	 * The label of this output.
	 */
	private String label;
	
	/**
	 * True if this input is disabled.
	 */
	private boolean disabled;
	
	/**
	 * Constructs a NodeOutput.
	 * @param Label This output's label.
	 */
	public NodeOutput(String label) {
		connections = new ArrayList<NodeConnection<?, T>>();
		connected = false; // Starts unconnected.
		this.label = label;
	}
	
	/**
	 * Returns the label of this output.
	 * @return The label.
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * Sets whether or not this output is disabled.
	 * @param disabled True if this output is disabled, false otherwise.
	 */
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
	/**
	 * Returns true if this output is disabled.
	 * @return True if this output is disabled, false otherwise.
	 */
	public boolean isDisabled() {
		return disabled;
	}
	
	/**
	 * Adds a connection.
	 * @param connection Connection to be added.
	 */
	public void addConnection(NodeConnection<?, T> connection) {
		connection.setOutput(this);
		connections.add(connection);
		connected = true;
	}
	
	/**
	 * Removes the connection.
	 * @param connection Connection to be removed.
	 */
	public void removeConnection(NodeConnection<?, T> connection) {
		while(connections.remove(connection));
		if (connections.size() == 0) // Check to see if unconnected.
			connected = false;
	}
	
	/**
	 * Sets the value.
	 * @param value Value to be set.
	 */
	@SuppressWarnings("unchecked")
	public void setValue(Object value) {
		this.value = (T) value;
	}
	
	/**
	 * Returns the current cached value of this output.
	 * @return the current cached value of this output.
	 */
	public T getValue() {
		return value;
	}
	
	/**
	 * Returns the connections of this output node.
	 * @return the connections of this output node.
	 */
	public List<NodeConnection<?, T>> getConnections() {
		return connections;
	}
	
	/**
	 * Returns true if this output has any connections, false otherwise.
	 * @return true if this output has any connections, false otherwise.
	 */
	public boolean isConnected() {
		return connected;
	}
	
}
