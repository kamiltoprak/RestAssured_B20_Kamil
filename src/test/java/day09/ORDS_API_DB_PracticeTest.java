package day09;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Country;
import utility.DB_Utility;

import java.util.List;
import java.util.Map;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import testbase.HR_ORDS_TestBase;
public class ORDS_API_DB_PracticeTest extends HR_ORDS_TestBase {

    @DisplayName("GET /countries/{country_id} Compare Resut with DB")
    @Test
    public void testResponseMatchDatabaseData(){
        // send request tp/countries / {country_id} for AR
        // save the  result as Country POJO

        String myCountryID="AR";
       Country arPOJO= given().log().ifValidationFails()
                .pathParam("country_id",myCountryID).
        when()
                .get("/countries/{country_id}").prettyPeek()
                .as(Country.class);

       //here  the shorter way of above code
       //Country arPOJO1= get("/countries/{country_id}",myCountryID).as(Country.class);

        System.out.println("asPOJO = " + arPOJO);

        String query="Select * from countries where Country_ID = '"+myCountryID+"'";
        System.out.println("query = " + query);

        DB_Utility.runQuery(query);
        Map<String,String> dbResultMap=DB_Utility.getRowMap(1);

        //now start validating the  actual response to  expected result from  database
        assertThat(arPOJO.getCountry_id(),is(dbResultMap.get("COUNTRY_ID")));
        assertThat(arPOJO.getCountry_name(),is(dbResultMap.get("COUNTRY_NAME")));

        //  we convert region ID from  int  to  string  in pojo  file
        //assertThat(arPOJO.getRegion_id()+"",equalTo(dbResultMap.get("REGION_ID")));
        
        //save region_id convert  to integer from string  in the the map
        int expectedRegionID=Integer.parseInt(dbResultMap.get("REGION_ID"));
        assertThat(arPOJO.getRegion_id(),equalTo(expectedRegionID));

    }


    @DisplayName("GET /countries capture all country ID Compare Result with DB")
    @Test
    public void testResponseAllCountryIdsMatchDatabaseData(){

        List<String> allCountriesIDs=get("/countries").jsonPath().getList("items.country_id");
      // allCountriesIDs.forEach(System.out::println);

        System.out.println("========Countries ID  from DB UTILITY==============");

       DB_Utility.runQuery("select * from countries");
       List<String> expectedListFromDB=DB_Utility.getColumnDataAsList("COUNTRY_ID");
      // expectedListFromDB.forEach(System.out::println);

       //assert both list  has same information
        assertThat(allCountriesIDs,equalTo(expectedListFromDB));
    }
}
