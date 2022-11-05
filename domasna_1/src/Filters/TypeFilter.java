package Filters;

import java.util.ArrayList;
import java.util.List;

public class TypeFilter implements Filter<String>{

    private List<String> buildingList = new ArrayList<>();

    public TypeFilter() {
        buildingList.add("cafe");
        buildingList.add("fast_food");
        buildingList.add("pub");
        buildingList.add("bar");
        buildingList.add("restaurant");
    }


    @Override
    public String execute(String inputLine) {

        if(inputLine != null) {
            String [] parts = inputLine.split(", ");
            if(this.buildingList.contains(parts[3])) {
                return inputLine;
            }
        }

        return null;
    }
}
