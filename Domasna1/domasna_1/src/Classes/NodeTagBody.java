package Classes;

import java.util.HashMap;
import java.util.Map;

public class NodeTagBody {
    private String id;
    private String latitude;
    private String longitude;
    private Map<String, String> map = new HashMap<>();

    private static NodeTagBody instance = new NodeTagBody();

    public NodeTagBody() {
    }

    public static String formatLine(String inputLine) {
        if(inputLine != null && inputLine.startsWith("<node")) {
            return formatNodeLine(inputLine);
        } else if(inputLine != null && inputLine.startsWith("<tag")) {
            return formatTagLine(inputLine);
        } else {
            return null;
        }
    }

    private static String formatNodeLine(String inputLine) {
        String [] parts = inputLine.split("\"");
        String localID = parts[1];
        String localLat = parts[parts.length-4];
        String localLon = parts[parts.length-2];

        instance.create(localID, localLon, localLat);

        return localID + ", " + localLat +", " + localLon;
    }

    private static String formatTagLine(String inputLine) {
        // <tag k="crossing" v="traffic_signals"/>
        String [] parts = inputLine.split("\"");
        String localKey = parts[1];
        String localValue = parts[3];
        instance.map.putIfAbsent(localKey, localValue);

        return localKey + ", " + localValue;
    }

    private NodeTagBody(String id, String longitude, String latitude) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    private NodeTagBody create(String id, String longitude, String latitude) {
        return new NodeTagBody(id, longitude, latitude);
    }

}
