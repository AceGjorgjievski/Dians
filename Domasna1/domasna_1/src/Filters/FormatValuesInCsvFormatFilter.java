package Filters;

public class ColumnFilter implements Filter<String> {

    public static String nodeID;
    public static String longitude;
    public static String latitude;

    public static String name;
    public static String street;
    public static String amenity;

    //?? ovie dali gi ima kaj site ustanovi?
    //nema kaj site
    public static String phone;
    public static String houseNumber;

    public static int checkCounter = 0;


    private void handleNodeID(String id, String lat, String lon) {
        nodeID = id;
        latitude = lat;
        longitude = lon;
    }

    private String handleTags(String key, String value){
        if(key.equals("name")) {
            name = value;
            checkCounter++;
        } else if(key.equals("addr:street")) { //street:en?
            street = value;
            checkCounter++;
        } else if(key.equals("amenity")) {
            amenity = value;
            //vo shop da se stavi mapa i spored toa da se filtriraat ustanovite
            checkCounter++;
        }

        /**
         *  ,.... Wi-Fi, wheelchair, smoking?
         *  da se menuva counter-ot vo zavisnost od toa kolku atributi kje ima
         */


        if(checkCounter == 3) {
            checkCounter = 0;
            return String.format("%s, %s, %s, %s, %s, %s",
                    nodeID,
                    name,
                    street,
                    amenity,
                    latitude,
                    longitude);
        } else return null;
    }


    @Override
    public String execute(String inputLine) {

        if(inputLine != null) {
            String [] parts = inputLine.split(", ");

            if(Character.isDigit(parts[0].charAt(0))) {
                //node id
                checkCounter = 0;
                this.handleNodeID(parts[0], parts[1], parts[2]);

            } else if(!Character.isDigit(parts[0].charAt(0))) {
                //tag - key - value

                return this.handleTags(parts[0], parts[1]);

            } else {
                return null;
            }
        }
        return null;
    }
}
