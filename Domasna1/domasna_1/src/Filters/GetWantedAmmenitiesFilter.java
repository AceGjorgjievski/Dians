package Filters;

import java.util.ArrayList;
import java.util.List;

public class GetWantedAmmenitiesFilter implements Filter<String>{

    private List<String> allowedAmenitiesList = new ArrayList<>();

    public GetWantedAmmenitiesFilter() {
        allowedAmenitiesList.add("cafe");
        allowedAmenitiesList.add("fast_food");
        allowedAmenitiesList.add("pub");
        allowedAmenitiesList.add("bar");
        allowedAmenitiesList.add("restaurant");
    }


    @Override
    public String execute(String inputLine) {

        if(inputLine != null) {
            String [] parts = inputLine.split(", ");
            if(this.allowedAmenitiesList.contains(parts[3])) {
                return inputLine;
            }
        }

        return null;
    }
}
