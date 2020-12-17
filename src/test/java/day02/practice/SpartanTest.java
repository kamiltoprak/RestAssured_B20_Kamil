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

public class SpartanTest {

    @BeforeAll
    public static  void setUp(){
        baseURI="http://100.26.101.158:8000";
        basePath="/api";
    }

    @AfterAll
    public static void tearDown(){
        reset();
    }

    @DisplayName("Testing /api/ spartan endpoint")
    @Test
    public void testGetAllSpartan(){

        Response response=get("/spartans");

        assertThat(response.statusCode(),is(200));
        assertThat(response.contentType(),is(""+ContentType.JSON));
        assertThat(response.contentType(),equalTo(ContentType.JSON.toString()));
    }

    @DisplayName("Testing/api/spartans endpoint")
    @Test
    public void TestGetAllSpartanXML() {

        given()
                .header("accept", "application/xml").
        when()
                .get("/spartans").
        then()
                .statusCode(200)
                .header("Content-Type", "application/xml");


        given()
                .accept(ContentType.XML).
        when()
                .get("/spartans").
        then()
                .statusCode(is(200))
                .contentType(ContentType.XML);
    }


}
