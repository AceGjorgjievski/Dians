package Classes.TagElements;

import java.util.HashMap;
import java.util.Map;

public class NodeTagBody {
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

        return localID + ", " + localLat + ", " + localLon;
    }

    private static String formatTagLine(String inputLine) {
        // <tag k="crossing" v="traffic_signals"/>
        String [] parts = inputLine.split("\"");
        String localKey = parts[1];
        String localValue = parts[3];
        instance.map.putIfAbsent(localKey, localValue);

        return localKey + ", " + localValue;
    }
}
