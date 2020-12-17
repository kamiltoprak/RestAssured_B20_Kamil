package day05;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ExtractPractice {

    /*
    Extract () method of RestAsssured
    enable  you  to  extract data after validation
    in then  sections of  the method chaining
     */

    @BeforeAll
    public static void setUp(){

        baseURI="http://54.166.200.114:8000";
        basePath="/api";

    }

    @AfterAll
    public static void tearDown(){
        reset();
    }


    @ DisplayName("Testing Get / api /spartans /search  woth basic auth")
    @Test
    public void testSearchAndExtractData(){
        // search for nameContains : a , gender Female
        // verify status code is 200
        // extract jsonPath object after validation
        // use that jsonPath object to get the list of all results
        // and get the numberOfElements field value
        // compare those 2

        JsonPath jp = given()
                .log().all()
                .auth().basic("admin", "admin")
                .queryParam("nameContains", "a")
                .queryParam("gender", "Female").

        when()

                .get("/spartans/search").

        then()
                .log().all()
                .assertThat()
                .statusCode(is(200))
                .extract()
                .jsonPath();

        //get the list of all names in string

        List<String> allNames = jp.getList("content.name");
        System.out.println("allNames = " + allNames);

        // we  are  getting number of elements
        int numOfElements=jp.getInt("numberOfElements");
        System.out.println("numOfElements = " + numOfElements);
        //verifiying the number of element match the size  of list we got
        assertThat(numOfElements,equalTo(allNames.size()));

        assertThat(allNames.size(),equalTo(numOfElements));

        //using hamcrest matcher collection  support for asserting the list  size
        assertThat(allNames, hasSize(numOfElements));


    }


}
