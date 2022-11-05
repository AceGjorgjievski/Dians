package Classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeTag {

    private String nodeLine;
    private String id;

    public static Map<String, String> mapTags = new HashMap<>();
    public static List<Tag> tagList = new ArrayList<>();


    private NodeTag(String id) {
        this.nodeLine = id;
    }

    private NodeTag(Tag tag) {
        this.tagList.add(tag);
    }

    public static NodeTag createNode(String inputLine) {
        String [] parts = inputLine.split("\\s++")[1].split("=");
        return new NodeTag(parts[1]);
    }

    public NodeTag createTagList(String inputLine) {
        return new NodeTag(Tag.createTag(inputLine));
    }

    public String getNodeLine() {
        return nodeLine;
    }

    public void setNodeLine(String nodeLine) {
        this.nodeLine = nodeLine;
    }
}
