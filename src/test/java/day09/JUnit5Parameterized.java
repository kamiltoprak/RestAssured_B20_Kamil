package day09;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class JUnit5Parameterized {

    @ParameterizedTest
    @ValueSource(ints = {5, 6, 7, 8, 9})
    public void test1(int myNumber) {
        // assert  5.6.7.8,9  all  less  then 10

        System.out.println("myNumber = " + myNumber);
        assertTrue(myNumber < 10);
    }

    // using CVS file  as source  for parameterized  test
    @ParameterizedTest
    @CsvFileSource(resources = "/zipcode.csv", numLinesToSkip = 1)
    public void test2(String zip) {
        System.out.println("zip = " + zip);
        //sending request to  zipcode endpoint here
        // api. zippopotam.us/us/{zipcode}
        // base url:api.zippopotam.us
        // endpoint:/us/{zipcode}
        given()
                .log().uri()
                .baseUri("https://api.zippopotam.us")
                .basePath("/us")
                .pathParam("zipcode", zip).
                when()
                .get("/{zipcode}").
                then()
                .statusCode(200);

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/country_zip.csv", numLinesToSkip = 1)
    public void testCountryZipCodeCombo(String myCountry,int myZip) {
        given()
                .log().uri()
                .baseUri("https://api.zippopotam.us")
                .pathParam("country",myCountry)
                .pathParam("zipcode",myZip).
        when()
                .get("/{country}/{zipcode}").
        then()
                .statusCode(200);



    }
}



   


