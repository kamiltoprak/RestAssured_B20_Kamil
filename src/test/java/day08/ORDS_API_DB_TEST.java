package day08;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Region;

import utility.DB_Utility;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;




import TestBase.HR_ORDS_TestBase;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Region;
import utility.DB_Utility;

import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;

public class ORDS_API_DB_TEST extends HR_ORDS_TestBase {

    @DisplayName("Testing the connection with query")
    @Test
    public void TestDB_Connection(){
        DB_Utility.runQuery("Select * from regions");
        DB_Utility.displayAllData();
    }

    /**
     * Send an get / Regions/ {region_id} request
     * check status code is 200
     * save it as Region POJO status Check
     * get your  expected result  from  Database query
     * select * from regions
     * save the  third row  as a map
     * verify the data from response match the data from  database
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




    }


}
