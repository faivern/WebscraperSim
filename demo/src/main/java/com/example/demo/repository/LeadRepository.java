package com.example.demo.repository;

import com.example.demo.model.Lead;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * Returnerar en lista med slumpmässigt genererade testleads.
 */
@Repository
public class LeadRepository {

    private final Faker faker = new Faker(new Locale("sv-SE"));

    public List<Lead> findAll() {
        // Returnera de statiska testleads som tidigare
        return getStaticLeads();
    }

    public List<Lead> findAll(int count) {
        if (count <= 3) {
            // Om man bara vill ha få leads, returnera de statiska
            return getStaticLeads();
        }

        // Annars generera så många leads som efterfrågas (max 10 000)
        count = Math.min(count, 10000);
        List<Lead> generatedLeads = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            String id = String.valueOf(1000 + faker.random().nextInt(9000));
            String name = faker.company().name();
            String address = faker.address().streetAddress();
            String zip = faker.address().zipCode();
            String city = faker.address().city();
            String contact = faker.name().fullName();
            String tele = faker.phoneNumber().phoneNumber();

            // Slumpa företagsstorlek
            String[] sizes = {"Small", "Medium", "Large"};
            String size = sizes[faker.random().nextInt(sizes.length)];

            // Slumpa operatör
            String[] providers = {"Telia", "Telenor", "Tre", "Tele2", "Bahnhof", "Comhem"};
            String provider = providers[faker.random().nextInt(providers.length)];

            String email = faker.internet().emailAddress();

            generatedLeads.add(new Lead(id, name, address, zip, city, contact, tele, size, provider, email));
        }

        return generatedLeads;
    }

    private List<Lead> getStaticLeads() {
        return List.of(
                new Lead("2331", "Acme AB", "Storgatan 1", "12345", "Stockholm", "Anna Andersson", "08-123456", "Medium", "Telia", "anna@acme.se"),
                new Lead("4323", "Beta Technologies", "Industrigatan 42", "41122", "Göteborg", "Bengt Bengtsson", "031-654321", "Large", "Telenor", "bengt@betatech.se"),
                new Lead("5433", "Carat Konsult", "Centrumvägen 7", "22196", "Lund", "Cecilia Carlsson", "046-112233", "Small", "Tre", "cecilia@carat.se")
        );
    }
}