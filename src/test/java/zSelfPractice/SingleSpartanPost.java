package zSelfPractice;


////self Practice
//    // challenge yourself to parameterize this test
//    // with csv file source with different error count
//    // csv should have columns including name, gender, phone
//    // add any additional column needed to make it easy.
//    @DisplayName("Test POST /api/spartans Endpoint negative scenario: {0}--{1}--{2}")
//    @ParameterizedTest
//    @CsvFileSource(resources = "/singlePostSpartan.csv",numLinesToSkip = 1)
//    public void testBadRequest400responseBody_2(String name, String gender, long phone){
//        Spartan badPayload = new Spartan(name,gender,phone);
//        String nameErrorMessage = "name should be at least 2 character and max 15 character";
//        String phoneErrorMessage = "Phone number should be at least 10 digit and UNIQUE!!";
//        String genderErrorMessage = "Gender should be either Male or Female";
//        String allErrorMessages = nameErrorMessage + phoneErrorMessage + genderErrorMessage;
//        ValidatableResponse rs = given()
//                .spec(postReqSpec)
//                .body(badPayload).

//                        when()
//                              .post("/spartans").
//                        then()
//                              .log().all()
//                              .statusCode(400);

//                JsonPath jp = rs.extract().jsonPath();
//                String errorMessage = jp.getString("message");
//                System.out.println("errorMessage = " + errorMessage);
//                int errorMessageSize = jp.getInt("message.size()");
//                System.out.println("errorMessageSize = " + errorMessageSize);

//        rs.body("errors.defaultMessage.toString()",anyOf(containsString(nameErrorMessage),containsString(genderErrorMessage),containsString(phoneErrorMessage)));


import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import pojo.Spartan;
import utility.ConfigurationReader;


import static io.restassured.RestAssured.*;

public class SingleSpartanPost{


    @DisplayName("Test POST /api/spartans Endpoint negative scenario:")
    @ParameterizedTest
    @CsvFileSource(resources = "/SingleSpartanPost.csv",numLinesToSkip = 1)
    public void spartanPost(String name,String gender,long phone){
        baseURI = ConfigurationReader.getProperty("spartan.base_url");
        basePath = "/api" ;

        Spartan spartan=new Spartan(name,gender,phone);
        String errorMessageForName = "name should be at least 2 character and max 15 character";
        String errorMessageForPhone = "Phone number should be at least 10 digit and UNIQUE!!";
        String errorMessageForGender = "Gender should be either Male or Female";

        JsonPath js=
                given()
                        .log().all()
                        .auth().basic("admin","admin")
                        .body(spartan).
                when()
                        .post("/spartans").

                then()
                           .log().all()
                           .statusCode(400).extract().jsonPath();
    }


}
