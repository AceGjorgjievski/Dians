package com.example.diansspring.bootstrap;

import com.example.diansspring.model.Amenity;
import com.example.diansspring.model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


@Component
public class DataHolder {
    public static List<Amenity> amenities = new ArrayList<>();
    public static List<User> users = new ArrayList<>();

//    @PostConstruct
//    public void init() {
//
//        users.add(new User("sa","sa","simon","anastasov"));
//        users.add(new User("va","va","verche","andonova"));
//        users.add(new User("ji","ji","joana","ilievska"));
//        users.add(new User("vg","vg","velin","gjorgiev"));
//        users.add(new User("ag","ag","ace","gjorgjievski"));
//
//
//
//
//        // dynamically taken from git repo output text file?
//        //or two download the file
//
////        URL url = new URL("HTTP","GITHUB",9090);
////        url.
//        amenities.addAll(amenities); // ??
//    }

}
