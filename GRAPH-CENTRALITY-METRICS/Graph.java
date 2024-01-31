import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import java.util.Queue;

import java.util.Stack;

public class Graph<T> {
	private HashMap<String, Vertex> vertices;
	private HashMap<String, Edge> edges;

	public Graph() {
		this.vertices = new HashMap<>();
		this.edges = new HashMap<>();
	}

	public void addEdge(String vertex1, String vertex2) {

		if (edges.get(vertex1 + "-" + vertex2) == null && edges.get(vertex2 + "-" + vertex1) == null) {
			Vertex vertex1_v, vertex2_v;

			if (vertices.get(vertex1) == null) {
				vertex1_v = new Vertex(vertex1);
				vertices.put(vertex1, vertex1_v);
			} else
				vertex1_v = vertices.get(vertex1);

			if (vertices.get(vertex2) == null) {
				vertex2_v = new Vertex(vertex2);
				vertices.put(vertex2, vertex2_v);
			} else
				vertex2_v = vertices.get(vertex2);

			Edge edge = new Edge(vertex1_v, vertex2_v);
			vertex1_v.addEdge(edge);
			vertex2_v.addEdge(edge);
			edges.put(vertex1 + "-" + vertex2, edge);
			vertex1_v.addNeighborss(vertex2_v);
			vertex2_v.addNeighborss(vertex1_v);
		} else {
			System.out.println("This edge has already added!");
		}

	}

	protected void resetVertices() {
		Iterator<Vertex> vertexIterator = vertices.values().iterator();// Iterator<VertexInterface<T>> vertexIterator =
																		// vertices.values().iterator()
		while (vertexIterator.hasNext()) {
			Vertex nextVertex = vertexIterator.next();
			nextVertex.unvisit();
			nextVertex.setCost(0);
			nextVertex.setPredecessor(null);
		} // end while
	} // end resetVertices

	public double getShortestPath(Vertex originVertex, Vertex endVertex, boolean betweennessProcess) {// 3
		Stack path = new Stack();
		resetVertices();
		boolean done = false;

		Queue<Vertex> vertexQueue = new LinkedList<>();

		originVertex.visit();

		vertexQueue.add(originVertex);
		while (!done && !vertexQueue.isEmpty()) {
			Vertex frontVertex = (Vertex) vertexQueue.poll();

			ArrayList<Vertex> verticess = frontVertex.getNeighborss();// --
			for (Vertex nextNeighbor : verticess) {

				if (nextNeighbor != null && !nextNeighbor.isVisited()) {
					nextNeighbor.visit();
					nextNeighbor.setCost(frontVertex.getCost() + 1);
					nextNeighbor.setPredecessor(frontVertex);
					vertexQueue.add(nextNeighbor);
				}
				if (nextNeighbor.equals(endVertex)) {
					break;
				}

			}

		}

		int pathLength = (int) endVertex.getCost();
		if (betweennessProcess) {
			path.push(endVertex.getName());
			Vertex v = endVertex;
			while (v.hasPredecessor()) {
				v.setBetweenness(v.getBetweenness() + 1);
				v = v.getPredecessor();
				path.push(v.getName());

			}
			v.setBetweenness(v.getBetweenness() + 1);
		}

		return pathLength;

	}

	

	public void calculateBetweenness_Closeness() {
		int count = 0;

		for (Vertex v : vertices()) {

			int count2 = 0;
			double closeness = 0;
			for (Vertex v2 : vertices()) {

				if (count2 > count) {
					double value = getShortestPath(v, v2, true);
					closeness += value;

				} else if (count != count2) {// --

					closeness += getShortestPath(v, v2, false);

				}
				count2++;
			}
			v.setCloseness(1 / closeness);
			count++;
		}
	}

	public Vertex highestBetweenness() {
		Vertex ver = null;
		int highest = 0;
		for (Vertex v : vertices()) {
			if (v.getBetweenness() > highest) {
				highest = v.getBetweenness();
				ver = v;
			}
		}
		return ver;
	}

	public Vertex highestCloseness() {
		Vertex ver = null;
		double highest = 0;
		for (Vertex v : vertices()) {
			if (v.getCloseness() > highest) {
				highest = v.getCloseness();
				ver = v;
			}
		}
		return ver;
	}

	public Iterable<Vertex> vertices() {
		return vertices.values();
	}

	public Iterable<Edge> edges() {
		return edges.values();
	}

	public int size() {
		return vertices.size();
	}

}
