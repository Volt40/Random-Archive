package org.cascadelabs.parsey.node;

import java.util.function.Supplier;

/**
 * The input of a node, the input is of type T.
 * 
 * @author Michael Baljet
 * @version 1.0
 * @param <T> The type of the input.
 */
public class NodeInput<T> {
	
	/**
	 * The current cached input value, this is updated when calculate() is called.
	 */
	private T value;
	
	/**
	 * This inputs's connection.
	 */
	private NodeConnection<T, ?> connection;
	
	/**
	 * True if this input is connected, false otherwise.
	 */
	private boolean connected;
	
	/**
	 * This input's static input supplier (sometimes null if unused).
	 */
	private Supplier<T> staticInput;
	
	/**
	 * True if this input should use the static input, false otherwise.
	 */
	private boolean useStaticInput;
	
	/**
	 * Amount of times this input has changed.
	 */
	private int iteration;
	
	/**
	 * The label of this input.
	 */
	private String label;
	
	/**
	 * True if this input is disabled.
	 */
	private boolean disabled;
	
	/**
	 * Constructs a NodeInput.
	 * @param Label The label of this input.
	 */
	public NodeInput(String label) {
		connected = false; // Starts unconnected.
		useStaticInput = false;
		this.label = label;
	}
	
	/**
	 * Returns the label of this input.
	 * @return The label.
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * Sets whether or not this input is disabled.
	 * @param disabled True if this input is disabled, false otherwise.
	 */
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
	/**
	 * Returns true if this input is disabled.
	 * @return True if this input is disabled, false otherwise.
	 */
	public boolean isDisabled() {
		return disabled;
	}
	
	/**
	 * Resets this NodeInput.
	 */
	public void init() {
		if (useStaticInput)
			iteration = 1;
		else
			iteration = 0;
	}
	
	/**
	 * Returns the cached input value.
	 * @return the cached input value.
	 */
	public T getValue() {
		if (useStaticInput)
			return staticInput.get();
		return value;
	}
	
	/**
	 * Sets the cached value.
	 * @param value Value to be set.
	 */
	public void setValue(T value) {
		if (!value.equals(this.value))
			iteration++;
		this.value = value;
	}
	
	/**
	 * Returns true if this input has a value ready, false otherwise.
	 * @return True if this input has a value ready, false otherwise.
	 */
	public boolean hasValue() {
		return iteration != 0;
	}
	
	/**
	 * Returns this input's iteration.
	 * @return this input's iteration.
	 */
	public int getIteration() {
		return iteration;
	}
	
	/**
	 * Sets the connection.
	 * @param connection Connection to be set.
	 */
	public void setConnection(NodeConnection<T, ?> connection) {
		this.connection = connection;
		connected = true;
	}
	
	/**
	 * Removes the current connection.
	 */
	public void removeConnection() {
		connection = null;
		connected = false;
	}
	
	/**
	 * Returns this input's connection (or null).
	 * @return this input's connection (or null).
	 */
	public NodeConnection<T, ?> getConnection() {
		return connection;
	}
	
	/**
	 * Returns if this input is connected or not.
	 * @return True if this input is connected, false otherwise.
	 */
	public boolean isConnected() {
		return connected;
	}
	
	/**
	 * Tells this input to use the static input.
	 * @param staticInput The static input to use.
	 */
	public void useStaticInput(Supplier<T> staticInput) {
		this.staticInput = staticInput;
		useStaticInput = true;
		connected = true;
	}
	
	/**
	 * Tells this input to not use the static input.
	 */
	public void removeStaticInput() {
		this.staticInput = null;
		useStaticInput = false;
		if (connection == null)
			connected = false;
	}
	
}
