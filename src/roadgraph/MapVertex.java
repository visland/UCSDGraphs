package roadgraph;

import java.util.ArrayList;
import java.util.List;

import geography.GeographicPoint;

public class MapVertex {
  public MapVertex() {
    edges = new ArrayList<MapEdge>();
  }
  
  public List<MapEdge> getEdges() {
    return edges;
  }
  
  // Returns GeographicPoint locations of all ends.
  public List<GeographicPoint> getAllEnds(){
    List<GeographicPoint> result = new ArrayList<GeographicPoint>();
    for (MapEdge mEdge : edges) {
      result.add(mEdge.getEnd());
    }
    return result;
  }
  private List<MapEdge> edges;
}
