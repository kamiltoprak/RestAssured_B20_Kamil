package zSelfPractice;

import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import pojo.Spartan;
import utility.ConfigurationReader;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class NegativeSingleSpartanPost {
    static String baseUrl;
    static String basePath;
    static RequestSpecification postRequestSpec;

    @BeforeAll
    public static void setUp() {
        baseUrl = ConfigurationReader.getProperty("spartan.base_url");
        basePath = "/api/spartans";
        postRequestSpec = given()
                .log().all()
                .auth().basic("admin", "admin")
                .contentType(ContentType.JSON);
    }
    @ParameterizedTest
    @CsvFileSource(resources = "/singleSpartanPost.csv", numLinesToSkip = 1)
    public void negativeTest400BadRequest(String gender, String name, Long phone) {
        String nameErrorMessage     = "name should be at least 2 character and max 15 character" ;
        String genderErrorMessage   = "Gender should be either Male or Female" ;
        String phoneErrorMessage    = "Phone number should be at least 10 digit and UNIQUE!!" ;

        Spartan badRequestBody = new Spartan(gender, name, phone);

        ResponseSpecification postResponseSpec = expect().logDetail(LogDetail.ALL)
                .statusCode(is(400))
                .contentType(ContentType.JSON)
                .body("errors.defaultMessage.toString()",
                        anyOf(containsString(nameErrorMessage),
                                containsString(genderErrorMessage),
                                containsString(phoneErrorMessage)));

        JsonPath jp =
        given()
                .spec(postRequestSpec)
                .body(badRequestBody).
        when()
                .post(baseUrl + basePath).
        then()
                .spec(postResponseSpec)
                .extract().jsonPath();

        List<String> errorList = jp.getList("errors");
        System.out.println("errorList = " + errorList);

        int numOfErrors = errorList.size();
        System.out.println("numOfErrors = " + numOfErrors);

        List<String> defaultMessageList = jp.getList("errors.defaultMessage");
        System.out.println("defaultMessageList = " + defaultMessageList);

        int numOfDefaultMessages = defaultMessageList.size();
        System.out.println("numOfDefaultMessages = " + numOfDefaultMessages);
        assertThat( numOfErrors , equalTo(numOfDefaultMessages) );
    }
}
