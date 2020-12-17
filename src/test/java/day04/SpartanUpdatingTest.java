package day04;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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

    @DisplayName("Testing PUT /api/spartans/{id} with string  body  ")
    @Test
    public void testUpdatingSingleSpartanWithStringBody(){

        String updateStrPayload = "{\n" +
                "        \"id\": 101,\n" +
                "        \"name\": \"Mehmet\",\n" +
                "        \"gender\": \"Male\",\n" +
                "        \"phone\": 2223334455\n" +
                "    },";

        given()
                .log().all()
                .auth().basic("admin","admin")
                .contentType(ContentType.JSON)
                .pathParam("id",1)
                .body(updateStrPayload).
        when()
                .put("/spartans/{id}").
       then()
                .log().all()
                .assertThat()
                .statusCode(is(204))
                // this is  how wwe   can check a header   exist by  checking  the value  is not null
                // using  not null value
                .header("Date",is(notNullValue()))
                 ;
    }


    @DisplayName("testing Patch/ api/ spartans/ {id} with String Body")
    @Test
    public void testPartialUpdatingSingleSpartanWithStringBody(){

        // update the name to B20  patched
        // {"name" : "B20 Patched"}
        String patchBody ="{\"name\" : \"B20 Patched\"}";
        given()
                .auth().basic("admin","admin")
                .log().all()
                .pathParam("id",1)
                .contentType(ContentType.JSON)
                .body(patchBody).
        when()
                .patch("spartans/{id}").
        then()
                .log().all()
                .assertThat()
                .statusCode(is(204))
                // body  for 204 response  is  always  empty
                // we  can validate it using empty String() matcher
                .body(emptyString())
                ;
    }


    @DisplayName("Testing Delete / api / spartans / {id }")
    @Test
    public void testDeletingSingleSpartan(){
        given()
                .log().all()
                .auth().basic("admin","admin")
                .pathParam("id",102).
        when()
                .delete("/spartans/{id}").
       then()
                .log().all()
                .assertThat()
                .statusCode(is(204))
                .body(emptyString());

    }

}
