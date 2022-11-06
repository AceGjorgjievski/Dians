package Filters;

public class ConvertSpecialCharStringToSpecialChar implements Filter<String>{

    @Override
    public String execute(String inputLine) {
        if (inputLine == null) return null;
        return inputLine.replaceAll("&amp;", "&").replaceAll("&quot;", "\"");
    }
    
}
