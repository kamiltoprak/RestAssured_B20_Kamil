package day02;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SingleSpartanTest {

    @BeforeAll
    public static void Setup(){
        baseURI="http://100.26.101.158:8000";
        basePath="/api";
    }

    @BeforeAll
    public static void TearDown(){
        reset();
    }


    @DisplayName("Testing GEt /Spartan/{id} endpoint")
    @Test
    public void test1spartan(){
        // I  want to  get  json result out
        // when I  send get request to  / spartans / {id } endpoint
        // and  expecting 200  status  code
        given()
                .accept(ContentType.JSON).
        when()
                .get("/spartans/100").
        then()
                .statusCode(is(200))
                .contentType(ContentType.JSON);


        // I  want to  make it  obvious for
        // the value  200 is  path  variable  / params
        // to uniquely identify the resource


        // this will whole  Resquest URL  this test \
        // http://100.26.101.158:8000/api/spartans/100
        given()
                .accept(ContentType.JSON)
                .pathParam("id",100 ).
        when()
                .get("/spartans/{id}").
        then()
                .assertThat()
                .statusCode(is(200))
                .contentType(ContentType.JSON);


        // this is  the easieast one ,  same result

        given()
                .accept(ContentType.JSON).
        when()
                .get("spartam/{id}",100).
        then()
                .assertThat()
                .statusCode(is(200))
                .contentType(ContentType.JSON);
    }

    @DisplayName("Testing GET /Spartan/{id} endpoint Payload")
    @Test
    public void test1spartanPayload(){
        /**
         * {
         *   "id": 100,
         *   "name": "SpartanX",
         *   "gender": "Female",
         *   "phone": 9999999999
         * }
         */


        given()
                .accept(ContentType.JSON).
        when()
                .get("/spartans/{id}",100).
        then()
                .assertThat()
                .statusCode(is(200))
                .contentType(ContentType.JSON)
                .body("id",is(100))
                .body("name",equalTo("SpartanX"))
                .body("gender",equalTo("Female"))
                .body("phone",equalTo(9999999999L));

    }



}
