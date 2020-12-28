package zSelfPractice;

import com.github.javafaker.Faker;
import io.restassured.path.xml.XmlPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;

public class AllVehicle {

    //Department Of Transportation Public API for Practice
    @DisplayName("Test GET /api/vehicles/GetAllManufacturers?format=xml&page=2 " +
            "Endpoint and Catch the Mfr_ID and Test " +
            "/vehicles/GetManufacturerDetails/Mfr_ID Endpoint")
    @Test
    public void testAllManufacturers(){
        Faker faker = new Faker();
        int randomPageNumber = faker.number().numberBetween(1,196);
        XmlPath xp =
                given()
                        .log().uri()
                        .baseUri("https://vpic.nhtsa.dot.gov")
                        .basePath("/api")
                        .queryParam("format","xml")
                        .queryParam("page",randomPageNumber).
                when()
                        .get("/vehicles/GetAllManufacturers").prettyPeek().
                then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .xmlPath()
                ;
        int numberOfManufacturersOnThisPage = xp.getInt("Response.Count");
        System.out.println("numberOfManufacturersOnThisPage = " + numberOfManufacturersOnThisPage);

        int randomManufacturerOnThisPage = faker.number().numberBetween(1, numberOfManufacturersOnThisPage);
        System.out.println("randomManufacturerOnThisPage = " + randomManufacturerOnThisPage);

        int randomManufacturersMfr_ID = xp.getInt("Response.Results.Manufacturers[" + randomManufacturerOnThisPage + "].Mfr_ID");
        System.out.println("randomManufacturersMfr_ID = " + randomManufacturersMfr_ID);

        //Using the captured Mfr_ID Get Manufacturer Details by /vehicles/GetManufacturerDetails/Mfr_ID
        XmlPath xp1 =
                given()
                        .log().uri()
                        .baseUri("https://vpic.nhtsa.dot.gov")
                        .basePath("/api")
                        .pathParam("Mfr_ID",randomManufacturersMfr_ID).
                when()
                        .get("/vehicles/GetManufacturerDetails/{Mfr_ID}").
                then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .xmlPath()
                ;
        String Mfr_Name = xp1.getString("Response.Results.ManufacturerDetails.Mfr_Name");
        System.out.println("Mfr_Name = " + Mfr_Name);
    }
}
