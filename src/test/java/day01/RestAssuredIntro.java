package day01;

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
    public void testHello(){

        // this is  the public  IP I  shared for  spartan 2
        // use it if you  do  have  your  own
        //if  you have your own , use  your own Ip
        //http://100.26.101.158:8000/api/hello

        // amek sure  this is what is  imported  for dat tyoe response
        // import  io.restassured.response.Response
        Response response= get("http://100.26.101.158:8000/api/hello");

        // get status code out of this  response object
        System.out.println("status code is : "+response.statusCode());

        // assert the status code is 200
        assertThat(response.statusCode(),is(200));


        // how to pretty print entire response body
        // prettyPrint() -- print and return  the body as string
        response.prettyPrint();
        String bodyAsStr=response.prettyPrint();

        // assertThat  the body  is hello  from sparta

        assertThat(bodyAsStr,is("Hello from Sparta"));

        // get the header called ContentType from response
        // there are always multiple way  to same thing in  restassured
        System.out.println("get header (header) " + response.getHeader("Content-Type"));
        System.out.println("get ContentType() = " + response.getContentType());
        System.out.println("content type() = "+response.contentType());

        // assert tht  content type is  test/ plain
        System.out.println(response.contentType());
        assertThat(response.contentType(),is("text/plain;charset=UTF-8"));

        //assert that content-type  start with text
        assertThat(response.contentType(),startsWith("text"));

        //easy way  to work  with  content without  typing much
        // we can use  content type enum  from  rest Assured to  easily  get  main  part  content-type
        //Content Type . tEXT  test/plain as enum
        //start with
        assertThat(response.contentType(),startsWith(ContentType.TEXT.toString()));

        assertThat(response.contentType(),is(not(ContentType.JSON)));

        // response.prettyPeek();



    }

    @DisplayName("Common Mathers for Strings")
    @Test
    public void testString() {
        String str = "Rest Assured is cool so far";


        //assert the str  is "Rest Assured is cool so far"
        assertThat(str,is("Rest Assured is cool so far"));

        //assert the str is " Rest Assured  is COOL so far " in  case  insensitive manner
        assertThat(str,equalToIgnoringCase("Rest Assured is COOL so far"));

        // assert the str startsWith "Rest"   and end with "far"
        assertThat(str,startsWith("Rest"));
        assertThat(str,endsWith("far"));

        //assert the str contains "is cool"
        assertThat(str,containsString("is cool"));

        //assert the str contains "IS COOL "  case  insensitive manner
        assertThat(str,containsStringIgnoringCase("IS COOL"));



    }
}
