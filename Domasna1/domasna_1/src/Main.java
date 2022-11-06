import Filters.*;
import Helpers.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.*;
import java.util.stream.IntStream;

import Classes.Map.BoundingBox;
import Classes.Map.OpenStreetMap.OpenStreetMap;
import Pipe.Pipe;

public class Main {
    private static final String OUTPUT_FILE_PATH = "Domasna1/output/output.txt";
    private static final String ATTRIBUTE_NAMES = "ID, Name, Address, Amenity, Longitute, Latitude";

    private static final List<BoundingBox> BOUNDING_BOXES_FOR_SKOPJE = new ArrayList<BoundingBox>(Arrays.asList(
        new BoundingBox(21.3822, 41.9890, 21.4290, 42.0092),
        new BoundingBox(21.4290, 41.9890, 21.4758, 42.0092),
        new BoundingBox(21.4290, 41.9688, 21.4758, 41.9890)
    ));
    private static final List<String> API_URLS_FOR_FACILITIES_IN_SKOPJE = BOUNDING_BOXES_FOR_SKOPJE.stream().map(bbox -> {
        return new OpenStreetMap().setBoundingBox(bbox).getApiUrlForFacilitiesInBoundingBox();
    }).toList();

    private static final List<Filter<String>> FILTERS = new ArrayList<Filter<String>>(Arrays.asList(
        new GetSelfClosingNodesFilter(),
        new TagFilter(),
        new ColumnFilter(),
        new TypeFilter()
    ));

    private static final Logger LOGGER = new Logger();

    public static void main(String[] args) throws IOException {
        Pipe<String> pipe = new Pipe<>();
        FILTERS.stream().forEach(filter -> pipe.addFilter(filter));

        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(new File(OUTPUT_FILE_PATH)));
        writer.write(ATTRIBUTE_NAMES + "\n");

        IntStream.range(0, API_URLS_FOR_FACILITIES_IN_SKOPJE.size()).forEach(idxUrl -> {
            LOGGER.log("Started getting data from api url " + (idxUrl+1));

            URL urlObject;
            try {
                urlObject = new URL(API_URLS_FOR_FACILITIES_IN_SKOPJE.get(idxUrl));
            } catch (MalformedURLException e) {
                LOGGER.warn("Exception upon trying to create a URL object", "idxUrl = " + idxUrl);
                e.printStackTrace();
                return ;
            }
            
            HttpURLConnection connection;
            try {
                connection = (HttpURLConnection) urlObject.openConnection();
            } catch (IOException e) {
                LOGGER.warn("Exception upon trying to open a connection", "idxUrl = " + idxUrl);
                e.printStackTrace();
                return ;
            }

            try {
                connection.setRequestMethod("GET");
            } catch (ProtocolException e) {
                LOGGER.warn("Exception upon trying to set GET as the request method", "idxUrl = " + idxUrl);
                e.printStackTrace();
                return ;
            }

            BufferedReader bufferedReader;
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } catch (IOException e) {
                LOGGER.warn("Exception upon trying to get input stream from the connection", "idxUrl = " + idxUrl);
                e.printStackTrace();
                return ;
            }

            Set<String> set = new HashSet<>();

            String inputLine = null, resultLine = null;
            try {
                while ((inputLine = bufferedReader.readLine()) != null) {
                    resultLine = pipe.runFilter(inputLine.trim());
                    if (resultLine != null) {
                        set.add(resultLine);
                    }
                }
            } catch (IOException e) {
                LOGGER.warn("Exception upon trying to read line from bufferedReader", "idxUrl = " + idxUrl);
                e.printStackTrace();
                return ;
            }

            Iterator<String> iterator = set.iterator();

            while (iterator.hasNext()) {
                String line = (String) iterator.next();
                try {
                    writer.write(line + "\n");
                    writer.flush();
                } catch (IOException e) {
                    LOGGER.warn("Exception upon trying to write into the file", "idxUrl = " + idxUrl);
                    e.printStackTrace();
                    return ;
                }
            }

            LOGGER.log("Done getting data from api url " + (idxUrl+1));
        });

        writer.close();
        LOGGER.log("End of program");
    }
}
