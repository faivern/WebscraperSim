package com.example.demo.controller;

import com.example.demo.model.Lead;
import com.example.demo.repository.LeadRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * controller för att returnera leads som XML.
 */
@RestController
public class LeadController {

    private final LeadRepository leadRepository;

    public LeadController(LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    @GetMapping(value = "/api/leads", produces = MediaType.APPLICATION_XML_VALUE)
    public String getLeadsAsXml(@RequestParam(required = false, defaultValue = "3") Integer count) {
        // Validera count för att undvika negativa värden eller för stora värden
        if (count < 0) {
            count = 3; // Default om felaktigt värde
        }
        if (count > 10000) {
            count = 10000; // Max 10 000 leads
        }

        List<Lead> leads = leadRepository.findAll(count);

        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<leads xmlns=\"urn:leads\">\n");

        for (Lead lead : leads) {
            xml.append("<lead id=\"").append(lead.getId()).append("\">\n");
            xml.append(" <name>").append(lead.getName()).append("</name>\n");
            xml.append(" <address>").append(lead.getAddress()).append("</address>\n");
            xml.append(" <zip>").append(lead.getZip()).append("</zip>\n");
            xml.append(" <city>").append(lead.getCity()).append("</city>\n");
            xml.append(" <contact>").append(lead.getContact()).append("</contact>\n");
            xml.append(" <tele>").append(lead.getTele()).append("</tele>\n");
            xml.append(" <size>").append(lead.getSize()).append("</size>\n");
            xml.append(" <current_provider>").append(lead.getCurrentProvider()).append("</current_provider>\n");
            xml.append(" <email>").append(lead.getEmail()).append("</email>\n");
            xml.append("</lead>\n");
        }

        xml.append("</leads>");
        return xml.toString();
    }
}