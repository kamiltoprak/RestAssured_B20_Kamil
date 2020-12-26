package zSelfPractice;


import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testbase.SpartanAdminTestBase;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class vehicles {


    @DisplayName("Test vpic xml response and vehicle info")
    @Test
    public void testVehicleVin(){
        Response response=given()
                .baseUri("https://vpic.nhtsa.dot.gov")
                .basePath("/api/vehicles")
                .pathParam("model","honda")
                .queryParam("format","xml")
                .queryParam("modelyear",2011).
        when()
                .get("/GetModelsForMake/{model}").prettyPeek().
        then()
                .statusCode(200)
                .body("Response.Count",is("429"))
                .body("Response.Count.toInteger()",is(429))
                .body("Response.Results.ModelsForMake[3].Model_ID.toInteger()",is(1865))
                .body("Response.Results.ModelsForMake[5].Model_Name",equalTo("Element"))
                .contentType(ContentType.XML)
                .extract().response();


       System.out.println("###############################################################");
        XmlPath xp=response.xmlPath();
        System.out.println("xp.getString(\"Response.Message\") = " 
                + xp.getString("Response.Message"));
        
        List<String> carModelName= new ArrayList<>();
        carModelName.addAll(xp.getList("Response.Results.ModelsForMake.Model_Name"));
        System.out.println("carModelName = " + carModelName);

        List<Integer> carModelID= new ArrayList<>();
        System.out.println("xp.getInt(\"Response.Results.ModelsForMake.Model_ID[0].toInteger()\") = "
                + xp.getInt("Response.Results.ModelsForMake.Model_ID[0].toInteger()"));


    }
}
