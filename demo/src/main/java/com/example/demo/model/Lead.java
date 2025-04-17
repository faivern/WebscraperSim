package com.example.demo.model;

import lombok.Data;
import lombok.AllArgsConstructor;

/**
 * simpel model för ett lead.
 * används för att mappa ett lead.
 */

@Data // automatisk getters o setters för controllern
@AllArgsConstructor // automatisk konstruktor för repository med testleads
public class Lead {
    private String id;
    private String name;
    private String address;
    private String zip;
    private String city;
    private String contact;
    private String tele;
    private String size;
    private String currentProvider;
    private String email;
}