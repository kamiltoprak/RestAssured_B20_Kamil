package day02.practice;

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
    public static void Setup() {
        baseURI = "http://100.26.101.158:8000";
        basePath = "/api";
    }

    @BeforeAll
    public static void TearDown() {
        reset();
    }


    @DisplayName("Testing GET /Spartan/{id} endpoint")
    @Test
    public void test1spartan() {
            given()
                    .accept(ContentType.JSON).
            when()
                    .get("/spartans/100").
            then()
                    .statusCode(is(200))
                    .contentType(ContentType.JSON);


            given()
                    .accept(ContentType.JSON)
                    .pathParam("id",100).
            when()
                    .get("/spartans/{id}").
            then()
                    .contentType(ContentType.JSON)
                    .statusCode(is(200));

            given()
                .accept(ContentType.JSON).
            when()
                .get("/spartans/{id}",100).
                    then()
                    .contentType(ContentType.JSON)
                    .statusCode(is(200));


    }

    @DisplayName("Testing GET /Spartan/{id} endpoint Payload")
    @Test
    public void test1spartanPayload(){

        given()
                .accept(ContentType.JSON).
        when()
                .get("/spartans/{id}",101).
        then()
                .statusCode(is(200))
                .contentType(ContentType.JSON)
                .body("id",is(101))
                .body("name",equalTo("Mylene"))
                .body("gender",equalTo("Male"))
                .body("phone",is(1608076921));


    }


}
