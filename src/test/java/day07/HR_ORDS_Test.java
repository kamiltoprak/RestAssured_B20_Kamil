package day07;

import TestBase.HR_ORDS_TestBase;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import io.restassured.http.ContentType;
import pojo.Region;
import java.util.List;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;

public class HR_ORDS_Test extends HR_ORDS_TestBase {



    @DisplayName("Testing/regions/{region_id}")
    @Test
    public void testThirdRegionIsAsia(){

        given()
                .pathParam("region_id",3)
                .log().all().
        when()
                .get("/regions/{region_id}").
        then()
                .log().all()
                .assertThat()
                .statusCode(is(200))
                .contentType(ContentType.JSON)
                .body("region_name",is("Asia"));
    }

    @DisplayName("Save Get/regions/{region_id} response as POJO ")
    @Test
    public void testSingleRegionToPOJO(){

        Response response=

        given()
                .pathParam("region_id",3)
                .log().all().
        when()
                .get("/regions/{region_id}")
                .prettyPeek();
        JsonPath jp=response.jsonPath();
        Region r3=jp.getObject("",Region.class);
        System.out.println("r3 = " + r3);

        Region r4=response.as(Region.class);
        System.out.println("r4 = " + r4);
    }


    @DisplayName("Save Get/regions response as list of  POJO ")
    @Test
    public void testAllRegionToListOfPOJO(){
        Response response=get("/regions");
        JsonPath jp=response.jsonPath();
        List<Region> allRegions=jp.getList("items",Region.class);
        allRegions.forEach(System.out::println);
        // with out using list
        // use jsonpath to get
    }

}



