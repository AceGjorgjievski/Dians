package Filters;

import Classes.TagElements.NodeTagBody;

public class TagFilter implements Filter<String> {

    @Override
    public String execute(String inputLine) {
        return NodeTagBody.formatLine(inputLine);
    }
}
