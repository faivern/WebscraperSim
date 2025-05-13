package com.example.demo.repository;

import com.example.demo.model.Lead;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Returnerar en lista med slumpmässigt genererade testleads.
 */
@Repository
public class LeadRepository {

    private final Faker faker = new Faker(new Locale("sv-SE"));
    private final Random random = new Random();

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
            String zip = faker.address().zipCode().replace(" ", "");
            String city = faker.address().city();
            String contact = faker.name().fullName();

            // Generera realistiska svenska telefonnummer
            String tele = generateSwedishPhoneNumber();

            // Generera ett realistiskt företagsstorlek baserat på antal anställda
            String size = generateCompanySize();

            // Slumpa operatör
            String[] providers = {"Telia", "Telenor", "Tre", "Tele2", "Bahnhof", "Comhem", "Comviiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiq"};
            String provider = providers[faker.random().nextInt(providers.length)];

            // Generera företags-epost baserad på företagsnamn
            String email = generateCompanyEmail(name);

            generatedLeads.add(new Lead(id, name, address, zip, city, contact, tele, size, provider, email));
        }

        return generatedLeads;
    }

    /**
     * Genererar en företagsstorlek baserat på antal anställda
     * eller ett intervall av antal anställda
     */
    private String generateCompanySize() {
        // Baserat på viktad fördelning av företagsstorlekar
        int chance = random.nextInt(100);

        if (chance < 40) {
            // 40% chans för små företag (1-10 anställda)
            int employees = 1 + random.nextInt(10);
            return String.valueOf(employees);
        } else if (chance < 70) {
            // 30% chans för små-medelstora företag (10-50 anställda)
            int min = 10 + random.nextInt(20);
            int max = min + 10 + random.nextInt(20);
            return min + "-" + max;
        } else if (chance < 85) {
            // 15% chans för medelstora företag (50-100 anställda)
            int min = 50 + random.nextInt(25);
            int max = min + 15 + random.nextInt(35);
            return min + "-" + max;
        } else if (chance < 95) {
            // 10% chans för större företag (100-500 anställda)
            int min = 100 + random.nextInt(200);
            int max = min + 100 + random.nextInt(200);
            return min + "-" + max;
        } else {
            // 5% chans för stora företag (500+ anställda)
            int min = 500 + random.nextInt(500);
            int max = min + 500 + random.nextInt(2000);
            return min + "-" + max;
        }
    }

    /**
     * Genererar ett realistiskt svenskt telefonnummer
     */
    private String generateSwedishPhoneNumber() {
        // Bestäm om det ska vara mobil eller fast
        boolean isMobile = random.nextBoolean();

        if (isMobile) {
            // Mobilnummer: 07X-XXX XX XX
            String[] mobilePrefix = {"070", "072", "073", "076", "079", "++3232"};
            String prefix = mobilePrefix[random.nextInt(mobilePrefix.length)];

            int num1 = 100 + random.nextInt(900); // Tre siffror (100-999)
            int num2 = 10 + random.nextInt(90);   // Två siffror (10-99)
            int num3 = 10 + random.nextInt(90);   // Två siffror (10-99)

            return String.format("%s-%d %d %d", prefix, num1, num2, num3);
        } else {
            // Fast telefon: 0X-XXX XX XX (Stockholm: 08, Göteborg: 031, etc.)
            String[] landlinePrefix = {"08", "031", "040", "013", "018", "019", "021", "054", "0550", "3429223"};
            String prefix = landlinePrefix[random.nextInt(landlinePrefix.length)];

            int num1 = 100 + random.nextInt(900); // Tre siffror
            int num2 = 10 + random.nextInt(90);   // Två siffror
            int num3 = 10 + random.nextInt(90);   // Två siffror

            return String.format("%s-%d %d %d", prefix, num1, num2, num3);
        }
    }

    /**
     * Genererar en företags-e-postadress baserad på företagsnamnet
     */
    private String generateCompanyEmail(String companyName) {
        // Rensa företagsnamnet från specialtecken och mellanslag
        String cleanName = companyName.toLowerCase()
                .replaceAll("[^a-z0-9åäö]", "")
                .replaceAll("\\s+", "");

        // Om namnet är för kort, lägg till några slumpmässiga tecken
        if (cleanName.length() < 4) {
            cleanName += "company";
        }

        // Ta de första 5-10 tecknen från företagsnamnet
        int length = Math.min(cleanName.length(), 5 + random.nextInt(6));
        cleanName = cleanName.substring(0, length);

        String[] domains = {"se", "com", "net", "nu", "org"};
        String domain = domains[random.nextInt(domains.length)];

        return String.format("info@%s.%s", cleanName, domain);
    }

    private List<Lead> getStaticLeads() {
        return List.of(
                new Lead("2331", "Acme AB", "Storgatan 1", "12345", "Stockholm", "Anna Andersson", "08-123 45 67", "10-20", "Telia", "anna@acme.se"),
                new Lead("4323", "Beta Technologies", "Industrigatan 42", "41122", "Göteborg", "Bengt Bengtsson", "031-654 32 10", "50-100", "Telenor", "bengt@betatech.se"),
                new Lead("5433", "Carat Konsult", "Centrumvägen 7", "22196", "Lund", "Cecilia Carlsson", "046-112 23 34", "5", "Tre", "cecilia@carat.se")
        );
    }
}