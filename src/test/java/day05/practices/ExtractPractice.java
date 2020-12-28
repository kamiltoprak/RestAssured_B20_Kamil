package day05.practices;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.*;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ExtractPractice {
    /*
     extract() method of RestAssured
     enable you to extract data after validation
     in then section of the method chaining
     */

    @BeforeAll
    public static void setUp() {
        baseURI = "http://54.90.101.103:8000";
        basePath = "/api";
    }

    @AfterAll
    public static void tearDown() {
        reset();
    }


    @DisplayName("Testing GET /api/spartans/search with Basic auth")
    @Test
    public void testSearchAndExtractData() {

        // search for nameContains : a , gender Female
        // verify status code is 200
        // extract jsonPath object after validation
        // use that jsonPath object to get the list of all results
        // and get the numberOfElements field value
        // compare those 2

        JsonPath jp =
                given()
                        .contentType(ContentType.JSON)
                        .log().all()
                        .auth().basic("admin", "admin")
                        .queryParam("gender", "Female")
                        .queryParam("nameContains", "a").
                        when()
                        .get("/spartans/search").
                        then()
                        .contentType(ContentType.JSON)
                        .statusCode(200)
                        .log().all()
                        .extract().jsonPath();
        // get the list of all names in String
        List<String> allNames=jp.getList("content.name");
        System.out.println("allNames = " + allNames);

        // we are getting numberOfElements field from json result
        // since it's a top level key , json path will be just numberOfElements
        
        int numberOfElements=jp.getInt("numberOfElements");
        System.out.println("numberOfElements = " + numberOfElements);

        // verifying the numOfElements match the size of list we got
        // assertThat(numOfElements , equalTo( allNames.size()));

        int sizeOfList=jp.getInt("content.size()");
        System.out.println("sizeOfList = " + sizeOfList);

        assertThat(numberOfElements,is(sizeOfList));

    }


}
