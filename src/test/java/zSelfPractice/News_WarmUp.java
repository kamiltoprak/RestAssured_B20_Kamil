package zSelfPractice;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import zSelfPractice.POJO.NewsPojo;

import java.util.List;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class News_WarmUp {

    @BeforeAll
    public static void setUp(){
        //RestAssured.filters().add(new AllureRestAssured() ) ;
        baseURI ="https://newsapi.org" ;
        basePath = "/v2";
    }
    @AfterAll
    public static void tearDown(){
        reset();
    }
    @DisplayName("article authers")
    @Test
    public void articleAuthors(){

        JsonPath jp=given()
                .log().all()
                .contentType(ContentType.JSON)
                .queryParam("country","us")
                .queryParam("apiKey","4c0cad403a3046e6b1814715437bef9e").
        when()
                .get("/top-headlines").
        then()
                .statusCode(is(200))
                .contentType(ContentType.JSON)
                .extract()
                .jsonPath();
        
        /*List<String> author=jp.getList("articles.author");
        List<String> title=jp.getList("articles.title");
        List<String> description=jp.getList("articles.description");
        System.out.println("Author = " + author);
        System.out.println("Title = " + title);
        System.out.println("description = " + description);*/


        System.out.println("=============list of  author  when author is not null======================");
        List<NewsPojo> allAuthors = jp.getList("articles.findAll { !it.author.equals(null) }" ,
                NewsPojo.class ) ;
        allAuthors.forEach(System.out::println);

        System.out.println("=============list of author  when id is not null======================");

        List<NewsPojo> allAuthors2 = jp.getList("articles.findAll { !it.source.id.equals(null) }" ,
                NewsPojo.class ) ;
        allAuthors2.forEach(System.out::println);


    }
}
