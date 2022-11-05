package Filters;

import Classes.NodeTagBody;

public class TagFilter implements Filter<String> {




    @Override
    public String execute(String inputLine) {


        return NodeTagBody.formatLine(inputLine);
    }
}
