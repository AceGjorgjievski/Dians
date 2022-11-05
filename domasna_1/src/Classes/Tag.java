package Classes;

import java.util.HashMap;
import java.util.Map;

public class Tag {

    public Map<String, String> tagMap = new HashMap<>();

    private Tag(String key, String value) {

        tagMap.putIfAbsent(key,value);
    }

    public static Tag createTag(String line) {
        line = line.substring(1,line.length()-2);

        String [] parts = line.split("\"");
        String keyPart = parts[1];
        String valuePart = parts[3];


        return new Tag(keyPart, valuePart);
    }

    public String getValueForKey(String keyToSearch) {
        return tagMap.get(keyToSearch);
    }
}
