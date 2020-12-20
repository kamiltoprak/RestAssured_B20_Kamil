package day04.practice;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LibraryAppTest {
    
    private static String myToken;

    @BeforeAll
    public static void setUp(){
        baseURI = "http://library1.cybertekschool.com";
        basePath = "/rest/v1/" ;

    }

    @AfterAll
    public static void tearDown(){
        reset();
    }
    @Order(1)
    @ DisplayName("Testing / login Endpoint")
    @ Test
    public void testALogin(){
        /*
        Librarian1  email       librarian69@library
        Librarian   password    KNPXrm3S
         */
        myToken=given()
                .log().all()
                .contentType(ContentType.URLENC)
                .formParam("email","librarian69@library")
                .formParam("password","KNPXrm3S").
        when()
                .post("/login").
        then()
                .log().all()
                .statusCode(200)
                .body("token",is(not(emptyString())))
                .extract()
                .jsonPath()
                .getString("token");

        System.out.println("myToken = " + myToken);
    }

    @Order(2)
    @ DisplayName("Testing Get / dashboard _ starts endpoint")
    @ Test
    public void testzDashboard_stats(){
        given()
                .log().all()
                .header("x-library-token",myToken).
        when()
                .get("/dashboard_stats").
        then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }
}
