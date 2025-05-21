package com.example.demo.model;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;

@Data
@Builder
public class Location {
    private String name;
    private HashMap<String, Float> xyCoordinate;

}
