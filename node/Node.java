package org.cascadelabs.parsey.node;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a node in the network. All nodes extends this.
 * 
 * @author Michael Baljet
 * @version 1.0
 */
public abstract class Node {
	
	/**
	 * The number of times this node has been calculated.
	 */
	protected int iteration;
	
	/**
	 * The iteration index of each input the last time this was calculated.
	 * This is used to detect changing inputs.
	 */
	protected int[] lastInputIteration;
	
	/**
	 * This node's inputs.
	 * @return This node's inputs.
	 */
	public abstract List<NodeInput<?>> inputs();
	
	/**
	 * This node's outputs.
	 * @return This node's outputs.
	 */
	public abstract List<NodeOutput<?>> outputs();
	
	/**
	 * Restarts this Node, should be called before every network run.
	 * Child classes should override this and call super.
	 */
	public void init() {
		List<NodeInput<?>> inputs = inputs();
		iteration = 0;
		lastInputIteration = new int[inputs.size()];
		for (NodeInput<?> input : inputs)
			input.init();
	}
	
	/**
	 * Calculates this node's outputs.
	 * @param inputs This node's inputs.
	 * @return The outputs of this node.
	 * @throws NodeException If the inputs are incomplete or invalid.
	 */
	protected abstract Object[] calculate(Object[] inputs) throws NodeException;
	
	/**
	 * Refreshes this node. Will recalculate if the inputs change.
	 * @return True if the node actually refreshed.
	 */
	public boolean refresh() throws NodeException {
		// Get lists.
		List<NodeInput<?>> inputs = inputs();
		List<NodeOutput<?>> outputs = outputs();
		// Check to see if inputs have changed, warranting a recalculation.
		boolean recalculate = false;
		for (int i = 0; i < inputs.size(); i++)
			if (inputs.get(i).getIteration() != lastInputIteration[i]) {
				recalculate = true;
				lastInputIteration[i] = inputs.get(i).getIteration();
			}
		if (!recalculate && inputs.size() > 0)
			return false; // Escape if a recalculation is not needed.
		// Check to see if all connected inputs are available, and build the list.
		List<Object> in = new ArrayList<Object>();
		for (NodeInput<?> input : inputs)
			if (input.isConnected() && input.hasValue() && !input.isDisabled())
				in.add(input.getValue());
			else if (!input.isConnected() && !input.isDisabled())
				in.add(null);
			else if (!input.isDisabled())
				return false; // If an input is connected, but does not have a value, then calculations can't be done.
		// Calculate the node.
		Object[] inArray = new Object[in.size()];
		in.toArray(inArray);
		Object[] out = calculate(inArray);
		// Update outputs.
		for (int i = 0; i < outputs.size(); i++) {
			outputs.get(i).setValue(out[i]);
			for (NodeConnection<?, ?> connection : outputs.get(i).getConnections())
				connection.push(); // Push the values.
		}
		// Increment iteration.
		iteration++;
		return true;
	}
	
	/**
	 * True if this node has an output available, false otherwise.
	 * @return Returns true if this node has an output available, false otherwise.
	 */
	public boolean hasOutputAvailable() {
		return iteration != 0;
	}
	
	/**
	 * Returns all connects to this node.
	 * @return All connects to this node.
	 */
	public NodeConnection<?, ?>[] getAllConnection() {
		List<NodeConnection<?, ?>> connections = new ArrayList<NodeConnection<?, ?>>();
		// Get connections from the inputs.
		for (NodeInput<?> input : inputs())
			if (input.isConnected())
				connections.add(input.getConnection());
		// Get connections from the outputs.
		for (NodeOutput<?> output : outputs())
			connections.addAll(output.getConnections());
		NodeConnection<?, ?>[] allConnections = new NodeConnection<?, ?>[connections.size()];
		connections.toArray(allConnections);
		return allConnections;
	}
	
}
