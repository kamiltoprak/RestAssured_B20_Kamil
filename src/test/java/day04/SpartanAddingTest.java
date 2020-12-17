package day04;

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


    //@DisplayName("Adding one Data")

    @DisplayName("Testing GET /api/spartans with Basic auth")
    @Test
    public void testAllSpartanWithBasicAuth(){
        given()
                .auth().basic("admin","admin")
                .log().all().
        when()
                .get("/spartans").
        then()
                .log().all()
                .statusCode( is(200) ) ;
    }


    @DisplayName("Add 1 Data with Raw Json String POST /api/spartans")
    @Test
    public void testAddOneData(){
        /*
            {
                "name": "Gulbadan",
                "gender": "Male",
                "phone": 9876543210
            }
         */
        String newSpartanStr =  "    {\n" +
                "        \"name\": \"Gulbadan\",\n" +
                "        \"gender\": \"Male\",\n" +
                "        \"phone\": 9876543210\n" +
                "    }" ;
        System.out.println(newSpartanStr);

        given()
                .log().all()
                .auth().basic("admin","admin")
                .contentType(ContentType.JSON)
                .body(newSpartanStr).
        when()
                .post("/spartans").
        then()
                .log().all()
                .assertThat()
                .statusCode( is(201) )
                .contentType(ContentType.JSON)
                .body("success",is("A Spartan is Born!"))
                .body("data.name",is("Gulbadan"))
                .body("data.gender",is("Male"))
                .body("data.phone",is(9876543210L))
        ;
    }


    @DisplayName("Add 1 Data with Raw Json String POST /api/spartans")
    @Test
    public void testAddOneDataWithMapBody(){

        Map<String,Object> paylaodMap=new LinkedHashMap<>();
        paylaodMap.put("name","Tucky");
        paylaodMap.put("gender","Male");
        paylaodMap.put("phone","9876543210");

        System.out.println("payloadMap= "+paylaodMap);

        given()
                .log().all()
                .auth().basic("admin","admin")
                .contentType(ContentType.JSON)
                .body(paylaodMap).
        when()
                .post("/spartans").
       then()
                .log().all()
                .assertThat()
                .statusCode( is(201) )
                .contentType(ContentType.JSON)
                .body("success",is("A Spartan is Born!"))
                .body("data.name",is("Tucky"))
                .body("data.gender",is("Male"))
                .body("data.phone",is(9876543210L))
        ;
    }

    @DisplayName("Add 1 Data with External json file POST /api/spartans")
    @Test
    public void testAddOneDataWithJsonFileAsBody(){
        // Create a file called singleSpartan.json right under root directory
        // with below content
        /*
        {
            "name": "Olivia",
            "gender": "Female",
            "phone": 6549873210
        }
        add below code to point File object to this singleSpartan.json

         */
        File externalJson = new File("singleSpartan.json");

        given()
                .auth().basic("admin","admin")
                .log().all()
                .contentType(ContentType.JSON)
                .body( externalJson ).
        when()
                .post("/spartans").
        then()
                .log().all()
                .statusCode( is(201) )
                .contentType(ContentType.JSON)
                .body("success" , is("A Spartan is Born!") )
                .body("data.name" ,  is("Olivia")  )
                .body("data.gender" ,  is("Female")  )
                .body("data.phone" ,  is(6549873210L)  )
        ;
    }
}
