package day10;

import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.*;

import pojo.Spartan;
import utility.ConfigurationReader;
import utility.SpartanUtil;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SpartanWithReusableSpecForAdminRoleTest {

    static RequestSpecification givenSpec;
    static ResponseSpecification thenSpec;

    @BeforeAll
    public static void setUp() {
        baseURI = ConfigurationReader.getProperty("spartan.base_url");
        basePath = "/api";

        givenSpec = given().log().all()
                .auth().basic("admin", "admin");

        thenSpec = expect().logDetail(LogDetail.ALL)
                .statusCode(is(200))
                .contentType(ContentType.JSON);
    }


    @DisplayName("GET /api/spartans/{id} Endpoint Test")
    @Test
    public void testOneSpartan() {
        given()
                .spec(givenSpec)
                .pathParam("id", 34).
                when()
                .get("/spartans/{id}").
                then()
                .spec(thenSpec);

        //response object VS response Specification
        // Response Object=this is reality, what you sent and what you got --> it is actual burger
        // Response Specification= this is your expectation --> you  wish  to  get burger but you  do not have yet

        //alternative  way , since the data type  of givenSpec is already a RequestSpecification
        givenSpec
                .pathParam("id", 34).
                when()
                .get("/spartans/{id}").
                then()
                .spec(thenSpec);
    }

    @DisplayName("POST /api/spartans Endpoint Test")
    @Test
    public void testPost1Data() {

        Spartan randomSpartanPayload = SpartanUtil.getRandomSpartanPOJO_Payload();

        RequestSpecification postReqSpec = given().spec(givenSpec)
                .contentType(ContentType.JSON)
                .body(randomSpartanPayload);

        ResponseSpecification postResponseSpec = expect().logDetail(LogDetail.ALL)
                .statusCode(is(201))
                .contentType(ContentType.JSON)
                .body("success", is("A Spartan is Born!"))
                .body("data.id", notNullValue())
                .body("data.name", is(randomSpartanPayload.getName()))
                .body("data.gender", is(randomSpartanPayload.getGender()))
                .body("data.phone", is(randomSpartanPayload.getPhone()));

        given()
                .spec(postReqSpec).
                when()
                .post("/spartans").
                then()
                .spec(postResponseSpec)
        ;
    }


    @DisplayName("Get/api/Spartans Endpoint  Test ")
    @Test
    public void testAllSpartan() {
        // log().all() wil not  work  with expect()
        // in order to  make it work wwe need to  use  different  method
        // logDetail(LogDetail.All) to provide how much  we  want to log
        given()
                .spec(givenSpec).
                when()
                .get("/spartans").
                then()
                .spec(thenSpec);
    }

}