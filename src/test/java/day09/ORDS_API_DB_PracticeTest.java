package day09;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Country;
import pojo.Region;
import utility.DB_Utility;
import io.restassured.response.Response;
import java.util.Map;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import TestBase.HR_ORDS_TestBase;
public class ORDS_API_DB_PracticeTest extends HR_ORDS_TestBase {


    @DisplayName("GET /countries/{country_id} Compare Resut with DB")
    @Test
    public void testResponseMatchDatabaseData(){
        // send request tp/countries / {country_id} for AR
        // save the  result as Country POJO

        String myCountryID="AR";
        given()
                .pathParam("country_id",myCountryID).
        when()
                .get("/countries/{country_id}")
                .as(Country.class);





    }

}
