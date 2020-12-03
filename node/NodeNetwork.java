package org.cascadelabs.parsey.node;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a network of nodes.
 * 
 * @author Michael Baljet
 * @version 1.0
 *
 */
public class NodeNetwork {
	
	/**
	 * The nodes in this network.
	 */
	private List<Node> nodes;
	
	/**
	 * The connections between nodes in this network.
	 */
	private List<NodeConnection<?, ?>> connections;
	
	/**
	 * Constructs a node network.
	 */
	public NodeNetwork() {
		nodes = new ArrayList<Node>();
		connections = new ArrayList<NodeConnection<?, ?>>();
	}
	
	/**
	 * Inits the network, call before evaluate.
	 */
	public void init() {
		for (Node node : nodes)
			node.init();
	}
	
	/**
	 * Evaluates the network in it's current state.
	 * Will escape when the network has stabilized, or the max iteration is reached on any node.
	 * @param maxIteration Max iteration. Used has a safety, generally a very large number.
	 * @throws NodeException // TODO remove this
	 */
	public void evaluate(int maxIteration) throws NodeException {
		// Reset all the nodes.
		for (Node node : nodes)
			node.init();
		int iteration = 0;
		while (iteration < maxIteration) {
			boolean stabilized = true;
			// Refresh all nodes.
			for (Node node : nodes)
				if (node.refresh())
					stabilized = false;
			// A network has stabilized if no actions have occurred.
			if (stabilized)
				break; // Escape.
			// Increment the iteration.
			iteration++;
		}
	}
	
	/**
	 * Adds the given node.
	 * @param node Node to be added.
	 */
	public void addNode(Node node) {
		nodes.add(node);
	}
	
	/**
	 * Adds the connection.
	 * @param connection Connection to be added.
	 */
	public void addConnection(NodeConnection<?, ?> connection) {
		connections.add(connection);
	}
	
	/**
	 * Removes the nodes (and their connections) from the network.
	 * @param nodes Nodes to be removed.
	 */
	public void removeNode(Node... nodes) {
		for (Node node : nodes) {
			while(this.nodes.remove(node));
			removeConnections(node.getAllConnection());
		}
	}
	
	/**
	 * Removes the connections from the network.
	 * @param connections Connections to be removed.
	 */
	public void removeConnections(NodeConnection<?, ?>... connections) {
		for (NodeConnection<?, ?> connection : connections) {
			while(this.connections.remove(connection));
			connection.remove();
		}
	}
	
}
