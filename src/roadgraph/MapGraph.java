/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 *         A class which represents a graph of geographic locations Nodes in the
 *         graph are intersections between
 *
 */
public class MapGraph {
  private int numVertices;
  private int numEdges;
  HashMap<GeographicPoint, MapVertex> vertice = new HashMap<GeographicPoint, MapVertex>();

  /**
   * Create a new empty MapGraph
   */
  public MapGraph() {
    numVertices = 0;
    numEdges = 0;
  }

  /**
   * Get the number of vertices (road intersections) in the graph
   * 
   * @return The number of vertices in the graph.
   */
  public int getNumVertices() {
    return numVertices;
  }

  /**
   * Return the intersections, which are the vertices in this graph.
   * 
   * @return The vertices in this graph as GeographicPoints
   */
  public Set<GeographicPoint> getVertices() {
    Set<GeographicPoint> result = new HashSet<GeographicPoint>();
    for (GeographicPoint gp : vertice.keySet()) {
      result.add(gp);
    }
    return result;
  }

  /**
   * Get the number of road segments in the graph
   * 
   * @return The number of edges in the graph.
   */
  public int getNumEdges() {
    return numEdges;
  }

  /**
   * Add a node corresponding to an intersection at a Geographic Point If the
   * location is already in the graph or null, this method does not change the
   * graph.
   * 
   * @param location
   *        The location of the intersection
   * @return true if a node was added, false if it was not (the node was already
   *         in the graph, or the parameter is null).
   */
  public boolean addVertex(GeographicPoint location) {
    if (location == null || vertice.containsKey(location)) {
      return false;
    } else {
      vertice.put(location, new MapVertex());
      numVertices++;
      return true;
    }
  }

  /**
   * Adds a directed edge to the graph from pt1 to pt2. Precondition: Both
   * GeographicPoints have already been added to the graph
   * 
   * @param from
   *        The starting point of the edge
   * @param to
   *        The ending point of the edge
   * @param roadName
   *        The name of the road
   * @param roadType
   *        The type of the road
   * @param length
   *        The length of the road, in km
   * @throws IllegalArgumentException
   *         If the points have not already been added as nodes to the graph, if
   *         any of the arguments is null, or if the length is less than 0.
   */
  public void addEdge(GeographicPoint from, GeographicPoint to, String roadName, String roadType,
      double length) throws IllegalArgumentException {
    if (vertice.containsKey(from) && vertice.containsKey(to)) {
      vertice.get(from).getEdges().add(new MapEdge(from, to, roadName, roadType, length));
      numEdges++;
    }
  }

  /**
   * Find the path from start to goal using breadth first search
   * 
   * @param start
   *        The starting location
   * @param goal
   *        The goal location
   * @return The list of intersections that form the shortest (unweighted) path
   *         from start to goal (including both start and goal).
   */
  public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
    // Dummy variable for calling the search algorithms
    Consumer<GeographicPoint> temp = (x) -> {
    };
    return bfs(start, goal, temp);
  }

  /**
   * Find the path from start to goal using breadth first search
   * 
   * @param start
   *        The starting location
   * @param goal
   *        The goal location
   * @param nodeSearched
   *        A hook for visualization. See assignment instructions for how to use
   *        it.
   * @return The list of intersections that form the shortest (unweighted) path
   *         from start to goal (including both start and goal).
   */

  public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal,
      Consumer<GeographicPoint> nodeSearched) {
    Queue<GeographicPoint> q = new LinkedList<GeographicPoint>();
    HashMap<GeographicPoint, GeographicPoint> parent = new HashMap<GeographicPoint, GeographicPoint>();
    HashSet<GeographicPoint> visited = new HashSet<GeographicPoint>();
    GeographicPoint curr;

    q.add(start);
    while (!q.isEmpty()) {
      curr = q.remove();
      visited.add(curr);
      // Hook for visualization. See writeup.
      nodeSearched.accept(curr);
      // Finds all the ends of the current node and add the end to queue if not visited.
      for (GeographicPoint end : vertice.get(curr).getAllEnds()) {
        if (visited.contains(end)) continue;
//        if (!visited.contains(end)) {
          q.add(end);
          visited.add(end);
          // Adds current node as the parent of this end.
          parent.put(end, curr);
          // Returns result when one of the ends of current node is the goal.
          if (end.distance(goal) == 0) {
            List<GeographicPoint> r = new ArrayList<GeographicPoint>();
            r.add(goal);
            // Traces through HashMap(parent) to find the route from goal to start.
            GeographicPoint child = goal;
            while (parent.get(child) != null) {
              child = parent.get(child);
              r.add(child);
            }
            Collections.reverse(r);
            return r;
          }
//        }
      }
    }
    return null;
  }

  /**
   * Find the path from start to goal using Dijkstra's algorithm
   * 
   * @param start
   *        The starting location
   * @param goal
   *        The goal location
   * @return The list of intersections that form the shortest path from start to
   *         goal (including both start and goal).
   */
  public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
    // Dummy variable for calling the search algorithms
    // You do not need to change this method.
    Consumer<GeographicPoint> temp = (x) -> {
    };
    return dijkstra(start, goal, temp);
  }

  /**
   * Find the path from start to goal using Dijkstra's algorithm
   * 
   * @param start
   *        The starting location
   * @param goal
   *        The goal location
   * @param nodeSearched
   *        A hook for visualization. See assignment instructions for how to use
   *        it.
   * @return The list of intersections that form the shortest path from start to
   *         goal (including both start and goal).
   */
  public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal,
      Consumer<GeographicPoint> nodeSearched) {
    // TODO: Implement this method in WEEK 4

    // Hook for visualization. See writeup.
    // nodeSearched.accept(next.getLocation());

    return null;
  }

  /**
   * Find the path from start to goal using A-Star search
   * 
   * @param start
   *        The starting location
   * @param goal
   *        The goal location
   * @return The list of intersections that form the shortest path from start to
   *         goal (including both start and goal).
   */
  public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
    // Dummy variable for calling the search algorithms
    Consumer<GeographicPoint> temp = (x) -> {
    };
    return aStarSearch(start, goal, temp);
  }

  /**
   * Find the path from start to goal using A-Star search
   * 
   * @param start
   *        The starting location
   * @param goal
   *        The goal location
   * @param nodeSearched
   *        A hook for visualization. See assignment instructions for how to use
   *        it.
   * @return The list of intersections that form the shortest path from start to
   *         goal (including both start and goal).
   */
  public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal,
      Consumer<GeographicPoint> nodeSearched) {
    // TODO: Implement this method in WEEK 4

    // Hook for visualization. See writeup.
    // nodeSearched.accept(next.getLocation());

    return null;
  }

  public static void main(String[] args) {
    System.out.print("Making a new map...");
    MapGraph firstMap = new MapGraph();
    System.out.print("DONE. \nLoading the map...");
    GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
    System.out.println("DONE.");

    // You can use this method for testing.

    /*
     * Here are some test cases you should try before you attempt the Week 3 End of
     * Week Quiz, EVEN IF you score 100% on the programming assignment.
     */

    MapGraph simpleTestMap = new MapGraph();
    GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
    GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
    GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
    System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
    List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart, testEnd);
    List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart, testEnd);
    MapGraph testMap = new MapGraph();
    GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
    // A very simple test using real data
    testStart = new GeographicPoint(32.869423, -117.220917);
    testEnd = new GeographicPoint(32.869255, -117.216927);
    System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
    testroute = testMap.dijkstra(testStart, testEnd);
    testroute2 = testMap.aStarSearch(testStart, testEnd);
    // A slightly more complex test using real data
    testStart = new GeographicPoint(32.8674388, -117.2190213);
    testEnd = new GeographicPoint(32.8697828, -117.2244506);
    System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
    testroute = testMap.dijkstra(testStart, testEnd);
    testroute2 = testMap.aStarSearch(testStart, testEnd);

    /* Use this code in Week 3 End of Week Quiz */

    MapGraph theMap = new MapGraph();
    System.out.print("DONE. \nLoading the map...");
    GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
    System.out.println("DONE.");
    GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
    GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
    List<GeographicPoint> route = theMap.dijkstra(start, end);
    List<GeographicPoint> route2 = theMap.aStarSearch(start, end);
  }

}
