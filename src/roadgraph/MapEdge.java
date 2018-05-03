package roadgraph;

import geography.GeographicPoint;

public class MapEdge{
  public MapEdge(GeographicPoint _start, GeographicPoint _end, String _roadName, String _roadType,
      double _distance) {
    start = _start;
    end = _end;
    roadName = _roadName;
    roadType = _roadType;
    distance = _distance;
  }

  public GeographicPoint getEnd() {
    return end;
  }

  public double getDistance() {
    return distance;
  }

  private GeographicPoint start;
  private GeographicPoint end;
  private String roadName;
  private String roadType;
  private double distance;
}
