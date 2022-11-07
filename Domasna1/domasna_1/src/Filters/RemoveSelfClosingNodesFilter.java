package Filters;

public class RemoveSelfClosingNodesFilter implements Filter<String> {

    private boolean hasNodeBody = false;

    @Override
    public String execute(String inputLine) {
        if(inputLine.startsWith("<node") && inputLine.charAt(inputLine.length()-2) != '/') {
            this.hasNodeBody = true;
            return inputLine;
        }

        if(this.hasNodeBody) {
            if(inputLine.contains("tag")) {
                return inputLine;
            } else if(inputLine.equals("</node>")) {
                this.hasNodeBody = false;
                return inputLine;
            }
        }

        return null;
    }
}
