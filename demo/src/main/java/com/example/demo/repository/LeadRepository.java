package com.example.demo.repository;

import com.example.demo.model.Lead;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * returnerar en statisk lista med testleads.
 */
@Repository
public class LeadRepository {

    public List<Lead> findAll() {
        return List.of(
                new Lead("2331", "Acme AB", "Storgatan 1", "12345", "Stockholm", "Anna Andersson", "08-123456", "Medium", "Telia", "anna@acme.se"),
                new Lead("4323", "Beta Technologies", "Industrigatan 42", "41122", "Göteborg", "Bengt Bengtsson", "031-654321", "Large", "Telenor", "bengt@betatech.se"),
                new Lead("5433", "Carat Konsult", "Centrumvägen 7", "22196", "Lund", "Cecilia Carlsson", "046-112233", "Small", "Tre", "cecilia@carat.se")
        );
    }
}