package day01.practices;


import static io.restassured.RestAssured.*;

import io.restassured.http.ContentType;
import  io.restassured.response.Response;
import  org.junit.jupiter.api.DisplayName;
import  org.junit.jupiter.api.Test;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;



public class RestAssuredIntro {


    @DisplayName("spartan/api/hello Endpoint test")
    @Test
    public void test(){

        Response response=get("http://100.26.101.158:8000/api/hello");

        System.out.println("status code "+response.statusCode());
        int st=response.statusCode();
        assertThat(st,is(equalTo(200)));

        response.prettyPrint();
        String str=response.prettyPrint();

        assertThat(response.prettyPrint(),is(equalTo("Hello from Sparta")));

        System.out.println("response.getHeader() = " + response.getHeader("Content-Type"));
        System.out.println("response.getContentType() = " + response.getContentType());
        System.out.println("response.contentType() = " + response.contentType());

    }


    @DisplayName("Common Matchers for Strings")
    @Test
    public void testString() {
        String str = "Rest Assured is cool so far";

        assertThat(str,startsWith("Res"));
        assertThat(str,endsWith("far"));
        assertThat(str,is("Rest Assured is cool so far"));
        assertThat(str,containsStringIgnoringCase("cool"));
        assertThat(str,equalToIgnoringCase("Rest Assured IS cool so far"));


    }




}
