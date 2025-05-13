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
        // Validera count
        if (count < 0) {
            count = 3;
        }
        if (count > 10000) {
            count = 10000;
        }

        List<Lead> leads = leadRepository.findAll(count);

        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        // Huvudnamespace för leads-taggen
        xml.append("<leads xmlns=\"http://webscraper.se.leads-format.1.0\" xmlns:syn=\"http://ws.apache.org/ns/synapse\">\n");

        for (Lead lead : leads) {
            // Använd namespace för lead-taggen
            xml.append("  <syn:lead id=\"").append(lead.getId()).append("\">\n");
            // Använd namespace för alla element
            xml.append("    <syn:name>").append(lead.getName()).append("</syn:name>\n");
            xml.append("    <syn:address>").append(lead.getAddress()).append("</syn:address>\n");
            xml.append("    <syn:zip>").append(lead.getZip()).append("</syn:zip>\n");
            xml.append("    <syn:city>").append(lead.getCity()).append("</syn:city>\n");
            xml.append("    <syn:contact>").append(lead.getContact()).append("</syn:contact>\n");
            xml.append("    <syn:tele>").append(lead.getTele()).append("</syn:tele>\n");
            xml.append("    <syn:size>").append(lead.getSize()).append("</syn:size>\n");
            xml.append("    <syn:current_provider>").append(lead.getCurrentProvider()).append("</syn:current_provider>\n");
            xml.append("    <syn:email>").append(lead.getEmail()).append("</syn:email>\n");
            xml.append("  </syn:lead>\n");
        }

        xml.append("</leads>");
        return xml.toString();
    }
}