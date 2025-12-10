package com.office.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.office.models.Product;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JsonReader {
    
    private static final ObjectMapper mapper = new ObjectMapper();
    
    public static List<Product> readProductsFromJson(String filePath) {
        try {
            Product[] products = mapper.readValue(new File(filePath), Product[].class);
            return Arrays.asList(products);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

