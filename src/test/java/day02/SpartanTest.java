package day02;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SpartanTest {

    @BeforeAll
    public static void SetUp(){

        // Base URI and basePath is static fields of RestAssured Class
        // Since we static imported RestAssured , We Can access all static field  directly just like
        // it's in our own class here
        // you  can use  static  way  as  below
        // RestAssured .baseURI = ""http://100.26.101.158:8000""
        // or you can directly  use as  below
        RestAssured.baseURI="http://100.26.101.158:8000";

        // RestAssured.basePAth ="/Api";

        RestAssured.basePath="/api";

        //baseURI +basePath+whatever you provided in http method like get  post
        // for example :
        // get("/spartans") -->> get(baseURI + basePath + "/spartans")
    }

    @AfterAll
    public static void tearDown(){
        // resetting the value of  baseURI, basePath to  original value
        //RestAssured.reset();
        reset();
    }

    @DisplayName("Testing /api/ spartan endpoint")
    @Test
    public void testGetAllSpartan() {

        // send a  get  request to  above endpoint
        // sae the response
        // try to  assert  the status code
        // content type header

        Response response = get("/spartans");
        response.prettyPrint();


        assertThat(response.statusCode(), is(200));
        assertThat(response.contentType(), equalTo(ContentType.JSON.toString()));
        assertThat(response.contentType(), is("" + ContentType.JSON));


    }


    @DisplayName("Testing/api/spartans endpoint")
    @Test
    public void TestGetAllSpartanXML() {

        /**
         * given
         * --- Request specification
         *      used to provide additional information about the request
         *      base URL
         *      header,query params, path variable  , payload
         *      authentication, autorization
         *      logging , cookie
         * when
         *      -- this is where you  actually  send the request with  http  method
         *      -- like GET POST PUT PATCH DELETE  ,..... with URL
         *      -- We get Response  Object  after sending  the request
         *
         *  then
         *      -- validatableResponse
         *      -- validate status code ,  header , payload,  cookie
         *      -- response time
         *
         */
        given()
                .header("accept", "application/xml").
        when()
                .get("/spartans").

        then()
                //.assertThat() this is not required ,  but can be added to make
                // it  obvious  that this is where we start  assertions
                .statusCode(200)
                //.and()  this is not  required at all , just  for readability,  optional
                .header("Content-Type", "application/xml")


        ;

        //this will  do  same exact things as above in  slightly different way
        // since accept  header and  content type header is os common ,
        // RestAssured has good  or  those  header  by providing method directly
        // rather than  using header  method we used above

        given()
                .accept(ContentType.XML).
        when()
                .get("/spartans").
        then()
                .assertThat()
                .statusCode(is(200))
                .and()
                .contentType(ContentType.XML);
    }
}
