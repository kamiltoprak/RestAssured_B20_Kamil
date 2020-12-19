package day07.warmUpTask.locations;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.warmUpTask.locations.Location;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;

public class Locations_test {

    // http://54.90.101.103:1000/ords/hr/regions/3

    @BeforeAll
    public static void setUp(){
        baseURI = "http://54.90.101.103:1000";
        basePath = "/ords/hr" ;
    }
    @AfterAll
    public static void tearDown(){
        reset();
    }

    @DisplayName("Testing/locations/{location_id}")
    @Test
    public void testThirdDepartmentIsMarketing(){

        given()
                .pathParam("location_id",1300)
                .log().all().
        when()
                .get("/locations/{location_id}").
        then()
                .log().all()
                .assertThat()
                .statusCode(is(200))
                .contentType(ContentType.JSON)
                .body("city",is("Hiroshima"));
    }

    @DisplayName("Save Get/locations/{location_id} response as POJO ")
    @Test
    public void testSingleRegionToPOJO(){

        Response response=
        given()
                .pathParam("location_id",20)
                .log().all().
        when()
                .get("/locations/{location_id}")
                .prettyPeek();
        JsonPath jp=response.jsonPath();

        Location l3=jp.getObject("", Location.class);
        System.out.println("l3 = " + l3);

        Location l4=response.as(Location.class);
        System.out.println("l4 = " + l4);

        System.out.println("==============================");

        JsonPath jp1=given()
                .pathParam("location_id",20)
                .log().all().
                        when()
                .get("/departments/{location_id}")
                .prettyPeek().jsonPath();

        Location l5=jp1.getObject("", Location.class);
        System.out.println("l5 = " + l5);


    }


    @DisplayName("Save Get/employees response as list of  POJO ")
    @Test
    public void testAllRegionToListOfPOJO(){
        Response response=get("/locations");
        JsonPath jp=response.jsonPath();
        List<Location> allLocations=jp.getList("items",Location.class);
        allLocations.forEach(System.out::println);
        // with out using list
        // use jsonpath to get

        System.out.println("================");
        JsonPath jp1=get("/locations").jsonPath();
        List<Location> allLocations1=jp.getList("items",Location.class);
        allLocations1.forEach(System.out::println);

    }

}



