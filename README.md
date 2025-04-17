# Webscraper Stub (Spring Boot)

Detta är en minimal simulering av Webscraper AB:s API, utvecklad i syfte att testa en integration mot Dolibarrs REST-API. Applikationen är skriven i Java med Spring Boot och returnerar en lista med leads i XML-format enligt angiven XSD-struktur.

## Syfte

Simulera Webscrapers leverans av leads över ett HTTP-API så att resten av teamet kan utveckla och testa integrationen utan att vara beroende av Webscrapers riktiga miljö.

## Funktionalitet

- Exponerar ett GET-endpoint: `/api/leads`
- Returnerar XML-data enligt strukturen definierad XSD
- Innehåller tre statiska test-leads
- Fungerar helt utan databas – använder dummydata
