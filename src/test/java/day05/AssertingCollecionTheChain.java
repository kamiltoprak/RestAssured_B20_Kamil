package day05;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AssertingCollecionTheChain {

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
        // check the size of  result is some hardcoded number
        // verify all names from the result contains a
        // verify  all gender  is Female only
        // do it in the chain

        given()
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
                .body("numberOfElements",equalTo(36))
                .body("content",hasSize(36))
                .body("content.name",everyItem(containsStringIgnoringCase("a")))
                .body("content.gender",everyItem(is("Female")))
                ;
    }
}
