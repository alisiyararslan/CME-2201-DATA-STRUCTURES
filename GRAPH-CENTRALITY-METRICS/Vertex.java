import java.util.ArrayList;

public class Vertex {
	private String name;
	private ArrayList<Edge> edges;

	private boolean visited;
	private double cost;
	private Vertex previousVertex;
	private int betweenness;
	private double closeness;
	private ArrayList<Vertex> neighborss;

	public Vertex(String name) {
		this.name = name;
		edges = new ArrayList<Edge>();

		cost = 0;
		visited = false;
		previousVertex = null;
		betweenness = 0;
		closeness = 0;
		neighborss = new ArrayList<Vertex>();

	}

	public ArrayList<Vertex> getNeighborss() {
		return neighborss;
	}

	public void setNeighborss(ArrayList<Vertex> neighborss) {
		this.neighborss = neighborss;
	}

	public void addNeighborss(Vertex v) {
		neighborss.add(v);
	}

	public double getCloseness() {
		return closeness;
	}

	public void setCloseness(double closeness) {
		this.closeness = closeness;
	}

	public int getBetweenness() {
		return betweenness;
	}

	public void setBetweenness(int betweenness) {
		this.betweenness = betweenness;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public void visit() {
		visited = true;

	}

	public boolean isVisited() {

		return visited;
	}

	public void unvisit() {
		visited = false;

	}

	public void addEdge(Edge e) {
		edges.add(e);
	}

	public ArrayList<Edge> getEdges() {
		return this.edges;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPredecessor(Vertex predecessor) {
		previousVertex = predecessor;

	}

	public Vertex getPredecessor() {
		return previousVertex;

	}

	public boolean hasPredecessor() {
		return previousVertex != null;

	}

}
