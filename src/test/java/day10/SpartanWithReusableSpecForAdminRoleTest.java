package day10;

import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.*;
import pojo.Spartan;
import utility.ConfigurationReader;
import utility.SpartanUtil;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.* ;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.* ;

public class SpartanWithReusableSpecForAdminRoleTest {

    static RequestSpecification givenSpec ;
    static ResponseSpecification thenSpec ;
    static RequestSpecification postReqSpec ;
    static Spartan randomSpartanPayload ;

    @BeforeAll
    public static void setUp(){

        baseURI = ConfigurationReader.getProperty("spartan.base_url");
        basePath = "/api" ;

        givenSpec =  given().log().all()
                .auth().basic("admin","admin") ;
        // log().all() will not work with expect()
        // in order to make it work we need to use different method
        // logDetail(LogDetail.ALL) to provide how much we want to log
        thenSpec = expect().logDetail(LogDetail.ALL)
                .statusCode(is(200) )
                .contentType(ContentType.JSON) ;

        randomSpartanPayload = SpartanUtil.getRandomSpartanPOJO_Payload();
        postReqSpec = given().spec(givenSpec)
                .contentType(ContentType.JSON)
                .body(randomSpartanPayload) ;
    }

    @DisplayName("GET /api/spartans/{id} Endpoint Test")
    @Test
    public void testOneSpartan(){

        given()
                .spec(givenSpec)
                .pathParam("id",34).
        when()
                .get("/spartans/{id}").
        then()
                .spec(thenSpec)
        ;
        // alternative way , since the data type of givenSpec is already a RequestSpecification
        givenSpec
                .pathParam("id",34).
        when()
                .get("/spartans/{id}").
        then()
                .spec(thenSpec)
        ;

    }

    @DisplayName("POST /api/spartans Endpoint Test")
    @Test
    public void testPost1Data() {


        ResponseSpecification postResponseSpec =  expect().logDetail(LogDetail.ALL)
                .statusCode(is(201))
                .contentType(ContentType.JSON)
                .body("success",is("A Spartan is Born!") )
                .body("data.id",notNullValue() )
                .body("data.name", is( randomSpartanPayload.getName() ) )
                .body("data.gender", is(randomSpartanPayload.getGender() ))
                .body("data.phone", is(randomSpartanPayload.getPhone()) )
                ;
        given()
                .spec(postReqSpec).
                when()
                .post("/spartans").
                then()
                .spec(postResponseSpec)
        ;
    }

    @DisplayName("Test POST /api/spartans Endpoint negative scenario ")
    @Test
    public void testBadRequest400responseBody(){

        Spartan badPayload = new Spartan("A","A",100L) ;

        String nameErrorMessage     = "name should be at least 2 character and max 15 character" ;
        String genderErrorMessage   = "Gender should be either Male or Female" ;
        String phoneErrorMessage    = "Phone number should be at least 10 digit and UNIQUE!!" ;



        given()
                .spec( postReqSpec )
                .body(badPayload).
        when()
                .post("/spartans").
        then()
                .statusCode(400)
                .body("errors",hasSize(3))
//                .body("errors[0].defaultMessage",is("Gender should be either Male or Female"))
//                .body("errors[1].defaultMessage",is("name should be at least 2 character and max 15 character"))
//                .body("errors[2].defaultMessage",is("Phone number should be at least 10 digit and UNIQUE!!"))
//                .body("errors",containsInAnyOrder("error for phone","error for gender","error for name"))
                .body("errors.defaultMessage",containsInAnyOrder(nameErrorMessage,
                        genderErrorMessage,phoneErrorMessage))
                .body("message",containsString("Error count: 3"))


        ;

        //verify the errors fields has value  of  json array with  3 items
        //verify  default  message for those errors
        /**
         * gender should be ither male  or female
         * name filed at least 2  characters and at most 25  chracters
         * phone number should be at least  10  digit  and unique
         *
         */


    }





    @DisplayName("GET /api/spartans Endpoint Test")
    @Test
    public void testAllSpartan(){


        given()
                .spec(givenSpec).
                when()
                .get("/spartans").
                then()
                .spec(thenSpec)
        ;


    }


    @DisplayName("GET /api/spartans check response time < 2 second")
    @Test
    public void testResponseTime(){

        given()
                .spec(givenSpec).
                when()
                .get("/spartans").
                then()
                .spec(thenSpec)
                .time( lessThan(2000L) )
                .time( lessThan(2L)  , TimeUnit.SECONDS )

        ;
    }





}
