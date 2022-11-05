package Filters;

import java.util.ArrayList;
import java.util.List;

public class DuplicatesFilter implements Filter<String>{

    private static List<String> list = new ArrayList<>();

    @Override
    public String execute(String inputLine) {

        if(inputLine != null) {
            if(!list.contains(inputLine)) {
                list.add(inputLine);
                return list.get(list.size()-1);
            }
        }

        return null;
    }
}
