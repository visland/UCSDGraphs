package roadgraph;

import java.util.ArrayList;
import java.util.List;

import geography.GeographicPoint;

public class MapVertex implements Comparable<MapVertex>{
  public MapVertex(GeographicPoint _location) {
    location = _location;
    edges = new ArrayList<MapEdge>();
  }
  
  public List<MapEdge> getEdges() {
    return edges;
  }
  
  public GeographicPoint getLocation() {
    return location;
  }
  
  public double getEdgeDistance(GeographicPoint end) {
    for (MapEdge edge : edges) {
      if (edge.getEnd().distance(end) == 0) {
        return edge.getDistance();
      }
    }
    return 0;
  }
  
  // Returns GeographicPoint locations of all ends.
  public List<GeographicPoint> getAllEnds(){
    List<GeographicPoint> result = new ArrayList<GeographicPoint>();
    for (MapEdge mEdge : edges) {
      result.add(mEdge.getEnd());
    }
    return result;
  }
  
  @Override
  public int compareTo(MapVertex that) {
    if (currDistance + endDistance < that.currDistance + endDistance) {
      return -1;
    } else if (currDistance + endDistance > that.currDistance + endDistance) {
      return 1;
    } else {
      return 0;
    }
  }
  
  public double currDistance = Double.POSITIVE_INFINITY;
  public double endDistance = 0;
  private List<MapEdge> edges;
  private GeographicPoint location;
}
