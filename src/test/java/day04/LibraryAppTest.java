package day04;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class LibraryAppTest {

    private static String mytoken;

    @BeforeAll
    public static void setUp(){
        baseURI = "http://library1.cybertekschool.com";
        basePath = "/rest/v1/" ;

    }

    @AfterAll
    public static void tearDown(){
        reset();
    }


    @ DisplayName("Testing / login Endpoint")
    @ Test
    public void testALogin(){
        /*
        Librarian1  email       librarian69@library
        Librarian   password    KNPXrm3S
         */

        mytoken=
        given()
                .log().all()
                .contentType(ContentType.URLENC)
                .formParam("email","librarian69@library")
                .formParam("password","KNPXrm3S").
        when()
                .post("/login").
        then()
                .log().all()
                .assertThat()
                .statusCode(is(200))
                .body("token", is(not(emptyString())))
                .extract()
                .jsonPath()
                .getString("token")
                ;
        System.out.println("my token  "+mytoken);
        // how to  extract some data  out of response object
        // after doing validation in then section
        // without  breaking the chain -- >> use exact () method that return
    }

    @ DisplayName("Testing Get / dashboard _ starts endpoint")
    @ Test
    public void testzDashboard_stats(){

        given()
     // this is  how we provide header  .header("headerName","header Value ")
             .log().all()
             .header("x-library-token","mytoken").
        when()
             .get("/dashboard_stats").
        then()
                .log().all()
                .assertThat()
                .statusCode(is(200))
                .contentType(ContentType.JSON)
                ;
    }

    // create a utility class library Utility
    // create a static method  called get Token ( environment ,  username, password)

}