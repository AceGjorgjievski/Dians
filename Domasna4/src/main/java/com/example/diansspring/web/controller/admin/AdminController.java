package com.example.diansspring.web.controller.admin;

import com.example.diansspring.model.Facility;
import com.example.diansspring.service.FacilityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final FacilityService facilityService;

    public AdminController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @GetMapping
    public String getAdminDashboard(Model model) {
        model.addAttribute("pageTitle", "Admin Dashboard - Findify");
        model.addAttribute("mainBodyContent", "admin/admin-dashboard");

        return "master-template";
    }

    /**
     * This method is used for updating the facilities in the database.
     * All the process is seperated in three different inner methods:
     *      - this.getConnection();
     *      - this.transformData(BufferedReader)
     *      - this.insertDatabase(List<String> facilities)
     *
     * @return success if the process succeeds or error if the process fails ?
     */
    @PostMapping("/update-facilities-in-database")
    public String updateFacilitiesInDatabase() {
        BufferedReader bufferedReader;
        List<String> facilitiesInputData;

        try {
            bufferedReader = this.getConnection();
            facilitiesInputData = this.readingDataAndAddingDiscount(bufferedReader);
            this.insertDataToDatabase(facilitiesInputData);

            //this.insertDataToDatabase(this.readingDataAndAddingDiscount(this.getConnection()))
            //decorator of methods? :P
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/admin?error";
        }
        return "redirect:/admin?success";
    }

    /**
     * This method is responsible for making connecting to GitHub raw file from out first homework
     * where we filtered our facilities. After the connection is made, all the data is stored in
     * BufferedReader object.
     *
     * @return new BufferedReader object with all the data needed for later manipulation.
     * @throws IOException
     */
    private BufferedReader getConnection() throws IOException {
        URL urlObject = new URL("https://raw.githubusercontent.com/SimonAnastasov/Dians/main/Domasna1/output/output.txt");

        HttpURLConnection connection;
        connection = (HttpURLConnection) urlObject.openConnection();
        connection.setRequestMethod("GET");

        return new BufferedReader(new InputStreamReader(connection.getInputStream()));
    }

    //podobro ime na metodov?

    /**
     * This method is responsible for reading the data from the given bufferedReader object
     * and adding discount to every single facility.
     *
     * @param bufferedReader object with data needed for adding discount
     * @return List<String> of the facilities with added discount
     * @throws IOException
     */
    private List<String> readingDataAndAddingDiscount(BufferedReader bufferedReader) throws IOException {
        String inputLine = null;
        List<String> inputData = new ArrayList<>();

        Random rand = new Random();
        List<Integer> allowedRandoms = new ArrayList<>(Arrays.asList(0, 10, 15, 25));
        while ((inputLine = bufferedReader.readLine()) != null) {
            int discount = allowedRandoms.get(rand.nextInt(4));
            inputData.add(inputLine + ", " + discount);
        }

        return inputData;
    }

    /**
     * This method is used for transforming the facilities list of strings into Facilities objects
     * done by java Streams.
     * @param facilitiesInputData list of strings for later transforming the data into Facilities objects.
     */
    private void insertDataToDatabase(List<String> facilitiesInputData) {
        List<Facility> facilitiesList = facilitiesInputData.stream()
                .skip(1)
                .map(Facility::create).toList();

        facilitiesList.forEach(this.facilityService::save);
    }
}
