package Filters;

import Classes.NodeTag;

import java.util.ArrayList;
import java.util.List;

public class NodeFilter implements Filter<String> {


    private boolean hasNodeBody = false;
    private static List<NodeTag> nodeTagList = new ArrayList<>();

    private void getAllNodeBody(String inputLine) {
        if(inputLine.equals("</node>") && this.hasNodeBody) {
            this.hasNodeBody = false;
        }

        //to be continued...


    }


    @Override
    public String execute(String inputLine) {

//        if(inputLine.startsWith("<node") && inputLine.charAt(inputLine.length()-2) != '/') {
//            this.hasNodeBody = true;
//            nodeTagList.add(NodeTag.createNode(inputLine));
//        }
//
//
//        //if the body is opened.
//        if(this.hasNodeBody) {
//            if(inputLine.contains("tag")) {
//                nodeTagList.get(nodeTagList.size()-1).createTagList(inputLine);
//            } else if(inputLine.equals("</node>")) {
//                this.hasNodeBody = false;
//
//
//            }
//        }

        if(inputLine.startsWith("<node") && inputLine.charAt(inputLine.length()-2) != '/') {
            this.hasNodeBody = true;
            return inputLine;
        }

        if(this.hasNodeBody) {
            if(inputLine.contains("tag")) {
                return inputLine;
            } else if(inputLine.equals("</node>")) {
                this.hasNodeBody = false;
                return  inputLine;
            }
        }

        return null;
    }
}
