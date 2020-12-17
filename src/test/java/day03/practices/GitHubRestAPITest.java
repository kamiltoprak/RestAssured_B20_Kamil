package day03.practices;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GitHubRestAPITest {

    @DisplayName("Test Github Get / Users /{username} ")
    @Test
    public void githubTest(){
        given()
                .pathParam("name","kamiltoprak").
        when()
                .get("https://api.github.com/users/{name}").
        then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .header("server","GitHub.com")
                .body("login",is("kamiltoprak"));

    }



}
