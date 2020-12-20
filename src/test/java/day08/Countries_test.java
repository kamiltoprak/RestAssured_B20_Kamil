package day08;


import testbase.HR_ORDS_TestBase;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.warmUpTask.countries.Country;

import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class Countries_test extends HR_ORDS_TestBase {


    @DisplayName("Test Get / countries /{country_id} to POJO ")
    @Test
    public  void getSingleCountryAndAddPOJO(){

        //Response response=get("/countries/{country_id} ","AR").prettyPeek()
        Response response=given()
                .pathParam("country_id","AR").
        when()
                .get("/countries/{country_id}").prettyPeek();

        //JsonPath jp=response.jsonPath();

        Country ar=response.as(Country.class);
        System.out.println("Argentina = " + ar);

        Country ar1=response.jsonPath().getObject("",Country.class);
        System.out.println("Argentina with  jsonpath  = " + ar1);

    }

    @DisplayName("Test GET /countries to List of POJO")
    @Test
    public void testAllCountriesResponseToListOfPOJO(){

        Response response=get("countries");
        List<Country> allCountries=response.jsonPath().getList("items",Country.class);
        allCountries.forEach(System.out::println);

    }

}
