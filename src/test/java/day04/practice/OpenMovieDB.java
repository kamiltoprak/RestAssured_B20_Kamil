package day04.practice;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;

public class OpenMovieDB {

    @BeforeAll
    public static void setUp(){
        RestAssured.baseURI = "http://www.omdbapi.com" ;
    }
    @AfterAll
    public static void tearDown(){
        reset();
    }

    @DisplayName("Test Search Movie or OpenMovieDB Test")
    @Test
    public void testMovie(){
        // http://www.omdbapi.com/?t=The Orville&apiKey=5b5d0fe8
        given()
                .log().all()
                .queryParam("t","The Orville")
                .queryParam("apiKey","5b5d0fe8").
        when()
                .get().prettyPeek().
        then()
              .statusCode(is(200))
              .contentType(ContentType.JSON)
              .body("Title",is("The Orville"))
                .body("Ratings[0].Value",is("8.0/10"));
    }
    @DisplayName("Getting the log of request and response")
    @Test
    public void testSendingRequestAndGetTheLog(){
        given()
                .queryParam("apiKey","5b5d0fe8" )
                .queryParam("t","John Wick")
                .log().all().
        when()
                .get().prettyPeek().
        then()
                .log().ifValidationFails()
                .statusCode(  is(200)  )
                .body("Plot", containsString("ex-hit-man") )
                // second Ratings source is Rotten Tomato
                .body("Ratings[2].Source",is("Metacritic") )
        ;


    }



}
