package day04.practice;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SpartanAddingTest {

    @BeforeAll
    public static void setUp(){

        baseURI="http://54.166.200.114:8000";
        basePath="/api";

    }

    @AfterAll
    public static void tearDown(){
        reset();
    }

    @DisplayName("Testing GET /api/spartans with Basic auth")
    @Test
    public void testAllSpartanWithBasicAuth(){

        given()
                .log().all()
                .auth().basic("admin","admin").
        when()
                .get("/spartans").
        then()
                .log().all()
                .statusCode(is(200));


    }

    @DisplayName("Add 1 Data with Raw Json String POST /api/spartans")
    @Test
    public void testAddOneData(){
        given()
                .log().all()
                .auth().basic("admin","admin")
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "        \"name\": \"TTTT\",\n" +
                        "        \"gender\": \"Male\",\n" +
                        "        \"phone\": 9876543210\n" +
                        "    }").
        when()
                .post("/spartans").
        then()
                .log().all()
                .statusCode(is(201))
                .contentType(ContentType.JSON)
                .body("success",is("A Spartan is Born!"))
                .body("data.name",is("TTTT"))
                .body("data.gender",is("Male"))
                .body("data.phone",is(9876543210L));

    }

    @DisplayName("Add 1 Data with Map Object POST /api/spartans")
    @Test
    public void testAddOneDataWithMapAsBody(){
        Map<String,Object> payloadMap=new LinkedHashMap<>();
        payloadMap.put("name","Ali");
        payloadMap.put("gender","Male");
        payloadMap.put("phone",9876753423L);

        given()
                .log().all()
                .auth().basic("admin","admin")
                .contentType(ContentType.JSON)
                .body(payloadMap).
        when()
                .post("/spartans").
        then()
                .log().all()
                .statusCode(is(201))
                .contentType(ContentType.JSON)
                .body("success",is("A Spartan is Born!"))
                .body("data.name",is("Ali"))
                .body("data.gender",is("Male"))
                .body("data.phone",is(9876753423L));
    }

    @DisplayName("Add 1 Data with External json file POST /api/spartans")
    @Test
    public void testAddOneDataWithJsonFileAsBody(){

        File externalJson = new File("singleSpartan.json");

        given()
                .log().all()
                .auth().basic("admin","admin")
                .contentType(ContentType.JSON)
                .body(externalJson).
        when()
                .post("/spartans").
        then()
                .log().all()
                .contentType(ContentType.JSON)
                .statusCode(201)
                .body("success" , is("A Spartan is Born!") )
                .body("data.name" ,  is("Olivia")  )
                .body("data.gender" ,  is("Female")  )
                .body("data.phone" ,  is(6549873210L)  );


    }
}
