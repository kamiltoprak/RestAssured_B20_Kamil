package day06;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import io.restassured.http.ContentType;
import pojo.Spartan;
import pojo.SpartanRead;
import utility.ConfigurationReader;
import utility.SpartanUtil;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;
public class JsonToJavaObject {
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
    @DisplayName("Get 1 Data and  Save Response Json As Java Object")
    @Test
    public void getOneSpartanAndSaveResponseJsonAsMap(){

        Response response= given()
                .auth().basic("admin","admin")
                .log().all()
                .pathParam("id",15).
        when()
                .get("/spartans/{id}").prettyPeek();


        JsonPath jp=response.jsonPath();
        Map<String,Object> responseMap = jp.getMap("");
        System.out.println("responseMap = " + responseMap);


        SpartanRead sp=jp.getObject("", SpartanRead.class);
        System.out.println("sp :  "+sp);




        /**
         *
         *{
         *     "id": 5,
         *     "name": "B20 Vola",
         *     "gender": "Male",
         *     "phone": 1231231230
         * }
         *
         * Json PAth to  get  whole  json object is just  empty string
         * assume this is a car object
         *
         * {
         *         "make" = "Honda"
         *         "color" = "white"
         *         "engine" = {
         *                         "type" : "V8"
         *                         "horsepower" : 350
         *                      }
         * }
         *
         * jsonPath  for horse power  -- >> engine .horsepower
         * jsonPath  for engine itself  -- >> engine
         * jsonPath  for entire car JasonObject  -- >> ""
         *
         *
         */


    }


    @DisplayName("Get all Data and  Save Response JsonArray As Java Object")
    @Test
    public void getAllSpartanAndSaveResponseJsonAsJavaObject(){

            Response response =given()
                    .auth().basic("admin","admin").
            when()
                    .get("/spartans");
            JsonPath jp=response.jsonPath();

            List<SpartanRead> allSpartanPOJOs =jp.getList("",SpartanRead.class);

            System.out.println("allSpartanPOJOs = " + allSpartanPOJOs);

    }
}


/*
    {
        "id": 101,
            "name": "Mehmet",
            "gender": "Male",
            "phone": 2223334455
    }
*/


