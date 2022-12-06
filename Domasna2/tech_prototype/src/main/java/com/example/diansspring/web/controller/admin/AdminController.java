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
import java.util.List;

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
        model.addAttribute("mainCssFile", "admin/admin.css");
        model.addAttribute("mainBodyContent", "admin/admin-dashboard");

        return "master-template";
    }

    @PostMapping("/update-facilities-in-database")
    public String updateFacilitiesInDatabase() {
        URL urlObject;
        try {
            urlObject = new URL("https://raw.githubusercontent.com/SimonAnastasov/Dians/main/Domasna1/output/output.txt");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "redirect:/admin?error";
        }

        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) urlObject.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/admin?error";
        }

        try {
            connection.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
            return "redirect:/admin?error";
        }

        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/admin?error";
        }

        List<String> facilitiesInputData = new ArrayList<>();

        String inputLine = null;
        try {
            while ((inputLine = bufferedReader.readLine()) != null) {
                facilitiesInputData.add(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/admin?error";
        }

        List<Facility> facilitiesList = facilitiesInputData.stream()
                .skip(1)
                .map(Facility::create).toList();

        facilitiesList.forEach(this.facilityService::save);

        return "redirect:/admin?success";
    }
}
