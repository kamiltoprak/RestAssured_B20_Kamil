package day03.practices;


import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class JasonPath {

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

        Response response=given()
                .accept(ContentType.JSON)
                .pathParam("id",100).
        when()
                .get("/spartans/{id}");


        JsonPath jp=response.jsonPath();

        int id=jp.getInt("id");
        String name=jp.getString("name");
        String gender= jp.getString("gender");
        long phone=jp.getLong("phone");

        System.out.println("id = " + id);
        System.out.println("name = " + name);
        System.out.println("gender = " + gender);
        System.out.println("phone = " + phone);


    }


    @DisplayName("Extracting data from JSON array Response")
    @Test
    public void getAllSpartanExtraData(){

        JsonPath jp=get("/spartans").jsonPath();
        System.out.println("jp.getString(\"name[0]\") = " + jp.getString("name[0]"));
        System.out.println("jp.getString(\"phone[0]\") = " + jp.getString("gender[0]"));
        System.out.println("jp.getInt(\"id[0]\") = " + jp.getInt("id[0]"));
        System.out.println("jp.getLong(\"phone[0]\") = " + jp.getLong("phone[0]"));

        for (int i = 0; i < jp.getList("name").size() ; i++) {
            System.out.println("Name "+(i+1)+" = " + jp.getString("name["+i+"]"));
        }


        System.out.println("jp.getString(\"name[-1]\") = "
                + jp.getString("name[-1]"));
        System.out.println("jp.getString(\"name[-2]\") = "
                + jp.getString("name[-2]"));


    }

    @DisplayName("Extracting data from JSON array Response")
    @Test
    public void testSearch(){

        JsonPath jp=
                given()
                        .log().all()
                        .accept(ContentType.JSON)
                        .queryParam("nameContains","de")
                        .queryParam("gender", "Male").
                when()
                        .get("/spartans/search").jsonPath();


        System.out.println("jp.getList(\"gender\") = " + jp.getList("content.gender"));
        System.out.println("jp.getList(\"gender\") = " + jp.getList("content.name"));
        System.out.println("jp.getList(\"gender\") = " + jp.getList("content.phone"));


        System.out.println("value of field empty " +
                jp.getBoolean("pageable.sort.empty") );

    }


}
