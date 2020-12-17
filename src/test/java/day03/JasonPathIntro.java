package day03;


import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;



public class JasonPathIntro {
    @BeforeAll
    public static void setUp(){
        baseURI = "http://100.26.101.158:8000";
        basePath = "/api" ;
    }
    @AfterAll
    public static void tearDown(){
        reset();
    }

    @DisplayName("Extracting data out of Spartan JSON OBJECT")
    @Test
    public void test1SpartanPayload(){
        // send a request to get 1 spartan
// by providing path params with valid id
// save it into Response object
// NEW : create an object with type JsonPath
// by calling the method jsonPath() on response object
// extract id , name , gender , phone
// and save it into variable of correct type

        Response response=given()
                .pathParam("id",100).
        when()
                .get("/spartans/{id}");

        //response.prettyPrint(); // return response object continue do change
        //response.prettyPeek();  // giving the string , that means done

        JsonPath jp=response.jsonPath();
        int myID=jp.getInt("id");
        String myNAME=jp.getString("name");
        String myGENDER=jp.getString("gender");
        long myPHONE=jp.getLong("id");


        System.out.println("myID = " + myID);
        System.out.println("myNAME = " + myNAME);
        System.out.println("myGENDER = " + myGENDER);
        System.out.println("myPHONE = " + myPHONE);
    }

    @DisplayName("Extracting data from JSON array Response")
    @Test
    public void getAllSpartanExtraData(){

       // Response response=get("/spartans");
       /// JsonPath jp=response.jsonPath();

        JsonPath jp=get("/spartans").jsonPath();

        // get the first  json object name  field
        System.out.println("first name in the list "+jp.getString("name[0]"));

        System.out.println("jp.getLong(\"phone[0]\") = " + jp.getLong("phone[0]"));

        // get the 7th json object gender field from json array
        System.out.println("jp.getString(\"gender[6]\") = "
                + jp.getString("gender[6]"));

        // get the last json object name field
        System.out.println("jp.getString(\"name[-1]\") = "
                + jp.getString("name[-1]"));


        // getting all the name fields  from the json Array Response
        //and storing as  a list

        List<String> allNames=jp.getList("name");
       // System.out.println("allNames = " + allNames);
        int count=1;
        for (String each:allNames) {
            System.out.println(count+" "+each);
            count++;
        }

        List<Long> allPhones=jp.getList("Phone");
       // System.out.println("allPhones = " + allPhones);
        int count1=1;
        for (Long each:allPhones) {
            System.out.println(count+" "+each);
            count1++;
        }
    }

    // send request this request URL
    // http://100.26.101.158:8000/api/spartans/search?nameContains=de&gender=Male
    // get the name of  first guy in the result
    // get the phone of  the guy in the  result
    // get all names and all phone save it  as  list
    // save  the value of the field empty under pageable

    @DisplayName("Extracting data from JSON array Response")
    @Test
    public void testSearch(){
        JsonPath jp = given()
                .queryParam("nameContains","de")
                .queryParam("gender","Male").
        when()
                .get("/spartans/search")// this is where we get response object
                .jsonPath();

        System.out.println("first guy name " +
                jp.getString("content[0].name")    );
        System.out.println("third guy phone number " +
                jp.getLong("content[2].phone")  );


        System.out.println("allNames " + jp.getList("content.name"));
        System.out.println("allPhones " + jp.getList("content.phone"));

        System.out.println("value of field empty " +
                jp.getBoolean("pageable.sort.empty") );

    }
}
