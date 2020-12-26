package day11;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testbase.SpartanAdminTestBase;
import utility.SpartanUtil;


import java.io.File;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.RestAssured.*;
public class JsonSchemaValidationPractice extends SpartanAdminTestBase {

    @DisplayName("testing GET /spartans endpoint structure")
    @Test
    public void testAllSpartansResponseSchema(){
        given()
                .spec(adminReqSpec).
        when()
                .get("/spartans").
        then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("allSpartansSchema.json"))
        ;
    }

    @DisplayName("testing Post /spartans endpoint response structure")
    @Test
    public void testPostSpartansResponseSchema() {

        //we can  use  matches  Json Schema method if  we  want to provide  full path for this file
        File schemaFile = new File("src/test/resources/postSuccessResponseSchema.json");

        given()
                .spec(adminReqSpec)
                .contentType(ContentType.JSON)
                .body(SpartanUtil.getRandomSpartanPOJO_Payload()).
        when()
                .post("/spartans").
        then()
                //.body(matchesJsonSchemaInClasspath("postSuccessResponseSchema.json") )
                // what if my schema file is somewhere else other than resource folder ?
                // then you need t provide full path and use different method
                .body(matchesJsonSchema( schemaFile )  )
        ;


    }

    @DisplayName("Testing GET /spartans/search endpoint response structure")
    @Test
    public void testSearchSpartanResponseSchema(){

        given()
                .spec(adminReqSpec)
                .queryParam("nameContains","a")
                .queryParam("gender","Female").
        when()
                .get("spartans/search").
        then()
                .body(matchesJsonSchemaInClasspath("searchSpartanSchema.json"));

    }
}
