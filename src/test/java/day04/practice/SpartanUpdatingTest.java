package day04.practice;

import org.junit.jupiter.api.*;
import io.restassured.http.ContentType;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;


public class SpartanUpdatingTest {

    @BeforeAll
    public static void setUp(){
        baseURI = "http://54.90.101.103:8000";
        basePath = "/api" ;
    }

    @AfterAll
    public static void tearDown(){
        reset();
    }

    @DisplayName("Testing PUT /api/spartans/{id} with String body")
    @Test
    public void testUpdatingSingleSpartanWithStringBody(){
        String employee1= "{\n" +
                "               \"id\": 312,\n" +
                "            \"name\": \"Olivia\",\n" +
                "              \"gender\": \"Female\",\n" +
                "              \"phone\": 6549873210\n" +
                "          }";

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .auth().basic("admin","admin")
                .pathParam("id",312)
                .body(employee1).
        when()
                .put("spartans/{id}").
        then()
                .log().all()
                .assertThat()
                .statusCode( is(204))
                .header("Date", is(notNullValue() ) )
                .body(emptyString());


    }

    @DisplayName("Testing PATCH /api/spartans/{id} with String body")
    @Test
    public void testPartialUpdatingSingleSpartanWithStringBody(){

        // update the name to B20 Patched
        // {"name" : "B20 Patched"}
        String patchBody = "{\"name\" : \"omer\"}";
        given()
                .auth().basic("admin","admin")
                .log().all()
                .pathParam("id",1)
                .contentType(ContentType.JSON)
                .body(patchBody).
                when()
                .patch("/spartans/{id}").
                then()
                .log().all()
                .assertThat()
                .statusCode( is(204))
                // body for 204 response is always empty
                // we can validate it using emptyString() matcher
                .body( emptyString() );
    }

    @DisplayName("Testing Delete /api/spartans/{id}")
    @Test
    public void testDeletingSingleSpartan(){

        given()
                .log().all()
                .auth().basic("admin","admin")
                .pathParam("id",10).
                when()
                .delete("/spartans/{id}").
                then()
                .log().all()
                .assertThat()
                .statusCode(is(204) )
                .body( emptyString() )  ;

    }

}
