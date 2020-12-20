package day10;



import TestBase.HR_ORDS_TestBase;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.ArticlePOJO;
import pojo.Department;
import TestBase.HR_ORDS_TestBase;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.reset;


public class NewsAPI_Homework {

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

    @DisplayName(" get all Articles author if  source is is not null")
    @Test
    public void testGetAllArticleAuthor(){
        JsonPath jp=given()
                //.baseUri()
                //.basePath()
                .queryParam("apiKey","4c0cad403a3046e6b1814715437bef9e")
                .queryParam("country","us").
        when()
                .get("/top-headlines").jsonPath();

        List<String> noFilterAllAuthors= jp.getList("articles.author");
        System.out.println("boFilterAllAuthors = " + noFilterAllAuthors);
        System.out.println("noFilterAllAuthors.size() = " + noFilterAllAuthors.size());

        List<String> allAuthors= jp.getList("articles.findAll{it.source.id!=null}.author");
        System.out.println("allAuthors = " + allAuthors);
        System.out.println("allAuthors.size() = " + allAuthors.size());

        // additional requirement - remove anu author with null value
        List<String> allAuthorsWithNoNull= jp.getList("articles.findAll{it.source.id!=null&& it.author!=null}.author");
        System.out.println("allAuthorsWithNoNull = " + allAuthorsWithNoNull);
        System.out.println("allAuthorsWithNoNull.size() = " + allAuthorsWithNoNull.size());


        List<ArticlePOJO> allArticles
                =jp.getList("articles.findAll{it.source.id!=null&&it.author!=null}",ArticlePOJO.class);
                allArticles.forEach(System.out::println);

    }

}
