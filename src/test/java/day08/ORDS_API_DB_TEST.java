package day08;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Region;
import utility.DB_Utility;
import io.restassured.response.Response;
import java.util.Map;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import TestBase.HR_ORDS_TestBase;


public class ORDS_API_DB_TEST extends HR_ORDS_TestBase {

    @DisplayName("Testing the connection with query")
    @Test
    public void TestDB_Connection(){
        DB_Utility.runQuery("Select * from regions");
        DB_Utility.displayAllData();
    }

    /**
     * Send an get/Regions/{region_id} request
     * check status code is 200
     * save it as Region POJO status Check
     * get your expected result from Database query
     * select * from regions
     * save the third row  as a map
     * verify the data from response match the data from database
     */

    @DisplayName("Testing GET/regions/{region_id} Data Match Database Data")
    @Test
    public void testRegionDataFromResponseMatchDB_Data(){

        int myID=3;
        Response response=given()
                                    .pathParam("region_id",myID).

                            when()
                                    .get("/regions/{region_id}").

                            then()
                                    .log().body()
                                    .statusCode(200)
                                    .extract().response();

        Region r3=response.as(Region.class);
        System.out.println("r3 = " + r3);

        DB_Utility.runQuery("select * from regions  where region_id ="+myID);
        Map<String,String> expectedResultMap=DB_Utility.getRowMap(1);
        System.out.println("expectedResultMap = " + expectedResultMap);

        // verify the actual result from api response match expected database result

        assertThat(r3.getRegion_id()+"",equalTo(expectedResultMap.get("REGION_ID")));
        assertThat(r3.getRegion_name(),is(expectedResultMap.get("REGION_NAME")));
    }


    @DisplayName("Testing GET/regions/{region_id} Data Match Database Data with  both maps")
    @Test
    public void testRegionDataFromResponseMatchDB_Data2(){

        int myID=3;
        JsonPath jp=given()
                .pathParam("region_id",myID).

                        when()
                .get("/regions/{region_id}").

                        then()
                .log().body()
                .statusCode(200)
                .extract()
                .jsonPath();

        // save the response json as a Map object
        // Here we are calling the overloaded version of getMap method with 3 params
        // 1. jsonPath String
        // 2. Data type Map key
        // 3. Data type Map value
        // so we can make sure we get exactly what we asked for
        Map<String, String> actualResultMap= jp.getMap("",String.class,String.class);

        // do not need to remove extra links from json result
        // because we are checking key value pair , anything we dont check will not matter
        System.out.println("actualResultMap = " + actualResultMap);

        System.out.println("=======================================");

        DB_Utility.runQuery("select * from regions where region_id =" +myID);
        Map<String, String> expectedResultMap= DB_Utility.getRowMap(1);
        System.out.println("expectedResultMap = " + expectedResultMap);

        System.out.println("================================================");


        // since the keyName is different in both map we can not directly
        // compare map to map object
        // we have to compare the value of key step by step

        assertThat(actualResultMap.get("region_id"),
                equalTo(expectedResultMap.get("REGION_ID")));
        assertThat(actualResultMap.get("region_name"),equalTo(expectedResultMap.get("REGION_NAME")));
    }

    @DisplayName("Testing GET/regions/{region_id} Data Match Database Data with just value by value")
    @Test
    public void testRegionDataFromResponseMatchDB_Data3() {

        int myID = 3;
        JsonPath jp = given()
                .pathParam("region_id", myID).

                        when()
                .get("/regions/{region_id}").

                        then()
                .log().body()
                .statusCode(200)
                .extract()
                .jsonPath();


        String actualRegionId = jp.getString("region_id");
        String actualRegionName = jp.getString("region_name");

        DB_Utility.runQuery("select region_id, region_name from regions where region_id = "+myID);
        String expectedRegionId = DB_Utility.getColumnDataAtRow(1,"REGION_ID");
        String expectedRegionName = DB_Utility.getColumnDataAtRow(1,"REGION_NAME");

        System.out.println("DB_Utility.getColumnNames() = " + DB_Utility.getColumnNames());


        assertThat(actualRegionId,is(expectedRegionId));
        assertThat(actualRegionName,equalTo(expectedRegionName));
    }


}
