package Practice;

import java.util.ListIterator;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utility.ConfigurationReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import static io.restassured.RestAssured.*;
public class starwars {
        List<String> heightList ;

        @BeforeAll()
        public static void setUp(){
            baseURI = "https://swapi.dev";
            basePath ="/api";
        }
        @AfterAll
        public static void tearDown(){
            reset();
        }
        @DisplayName("1. Get average height")
        @Test()
        public void getAverageHeight(){
            heightList =
                    (given()
                            .contentType(ContentType.JSON)
                            .log().all().
                    when()
                            .get("/people").
                    then()
                            .log().all()
                            .statusCode(200)
                            .contentType(ContentType.JSON)
                            .extract()
                            .jsonPath()
                            .getList("results.height"));


            System.out.println("heightList = " + heightList);
            double sumOfHeight = 0;
            for(String each: heightList) {
                sumOfHeight += Integer.parseInt(each);
            }
            double averageHeight = sumOfHeight / heightList.size();
            System.out.println("averageHeight = " + averageHeight);
        }
    }

