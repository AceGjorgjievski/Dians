import Filters.ColumnFilter;
import Filters.NodeFilter;
import Filters.TagFilter;
import Filters.TypeFilter;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(new File("C:\\Users\\Ace\\Desktop\\Dians_Git\\Dians\\domasna_1\\src\\output\\output.txt")));
        writer.write("ID, Name, Address, Amenity, Longitute, Latitude\n");

        List<String> links = new ArrayList<String>(Arrays.asList(
                "https://openstreetmap.org/api/0.6/map?bbox=21.3822,41.9890,21.4290,42.0092",
                "https://openstreetmap.org/api/0.6/map?bbox=21.4290,41.9890,21.4758,42.0092",
                "https://openstreetmap.org/api/0.6/map?bbox=21.4290,41.9688,21.4758,41.9890"));

        for (int j = 0; j < links.size(); j++) {
            System.out.println("START " + j);

            URL obj = new URL(links.get(j));
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            Pipe<String> pipe = new Pipe<>();

            NodeFilter nodeFilter = new NodeFilter();
            TagFilter tagFilter = new TagFilter();
            ColumnFilter columnFilter = new ColumnFilter();
            TypeFilter typeFilter = new TypeFilter();

            pipe.addFilter(nodeFilter);
            pipe.addFilter(tagFilter);
            pipe.addFilter(columnFilter);
            pipe.addFilter(typeFilter);

            Set<String> set = new HashSet<>();

            String inputLine = null, resultFilter = null;
            while ((inputLine = br.readLine()) != null) {

                resultFilter = pipe.runFilter(inputLine.trim());
                if (resultFilter != null) {
                    set.add(resultFilter);
                }
            }

            Iterator itr = set.iterator();

            while (itr.hasNext()) {
                String line = (String) itr.next();
//                System.out.println(itr.next());
                writer.write(line + "\n");
                writer.flush();
            }

            System.out.println("DONE " + j);
        }

        writer.close();
        System.out.println("DONE");
    }
}
