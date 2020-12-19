package day07;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import io.restassured.http.ContentType;
import pojo.Spartan;
import pojo.SpartanRead;
import utility.ConfigurationReader;
import utility.SpartanUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;

import static io.restassured.RestAssured.*;

public class PatchOneSpartanTest {


    @BeforeAll
    public static void setUp(){
        //RestAssured.filters().add(new AllureRestAssured() ) ;
        baseURI = ConfigurationReader.getProperty("spartan.base_url");
        basePath = "/api" ;
    }
    @AfterAll
    public static void tearDown(){
        reset();
    }

    @DisplayName("Patching 1 data with Java Object")
    @Test
    public void testPatch1DataPartialUpdateWithMAp(){
        //we just want to update the name and phone number

        Map<String,Object> patchBodyMap=new LinkedHashMap<>();
        patchBodyMap.put("name","B20 Voila");
        patchBodyMap.put("phone",1234567890L);

        given()
                .auth().basic("admin","admin")
                .log().all()
                .pathParam("id",100)
                .contentType(ContentType.JSON)
                .body(patchBodyMap).
        when()
                .patch("/spartans/{id}").
        then()
                .log().all()
                .statusCode(204);
    }


    @DisplayName("Patching 1 data with Java Object")
    @Test
    public void testPatch1DataPartialUpdateWithPOJO(){
        //we just want to  update the name and phone  number

        Spartan sp=new Spartan();//"B20 Voila","",9876543210L);
        sp.setName("B20 VOILA");
        sp.setPhone(9876543210L);

        // map is a better option with minimal effort
        // POJO class need some handling to ignore empty values
        // when being serialized

        given()
                .auth().basic("admin","admin")
                .log().all()
                .pathParam("id",100)
                .contentType(ContentType.JSON)
                .body(sp).
       when()
                .patch("/spartans/{id}").
       then()
                .log().all()
                .statusCode(500);
    }
}
