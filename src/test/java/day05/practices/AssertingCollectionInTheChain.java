package day05.practices;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.*;
import utility.ConfigurationReader;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AssertingCollectionInTheChain {

    @BeforeAll
    public static void setUp(){
        baseURI=ConfigurationReader.getProperty("spartan.base_url");
        basePath="/api";
    }

    @AfterAll
    public static void tearDown(){
        reset();
    }

    @DisplayName("Testing GET /api/spartans/search with Basic auth")
    @Test
    public void testSearchAndExtractData(){

        // search for nameContains : a , gender Female
        // verify status code is 200
        // check the size of result is Some hardcoded number
        // verify all names from the result contains a
        // verify all gender is Female only
        // do it in the chain


        given()
                .contentType(ContentType.JSON)
                .log().all()
                .queryParam("nameContains","a")
                .queryParam("gender","Female")
                .auth().basic("admin","admin").
        when()
                .get("/spartans/search").

        then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("numberOfElements",is(28))
                .body("content",hasSize(28))
                .body("content.name",everyItem(containsStringIgnoringCase("a")))
                .body("content.gender",everyItem(is("Female")));





    }

}
