package com.ngocanh.googlemapapi.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.ngocanh.googlemapapi.model.LocationLL;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class IndexController {

    public IndexController() {

    }

    @GetMapping("/location")
    public String getIndex(Model model){

        model.addAttribute("locationLL", new LocationLL());

        return "index";

    }

    @PostMapping("/location")
    public String getShow(@ModelAttribute LocationLL locationLL, Model model) throws InterruptedException, ApiException, IOException {

        LatLng location = new LatLng(locationLL.getLattitude(), locationLL.getLongtitude());

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyAwLSLsdEsdt4u-jgCCO6oxBG96V-0TxXw")
                .build();

        GeocodingResult[] results = GeocodingApi.reverseGeocode(context, location).await();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        System.out.println(gson.toJson(results[0].formattedAddress));

        model.addAttribute("address", gson.toJson(results[0].formattedAddress));

        return "show";
    }
}
