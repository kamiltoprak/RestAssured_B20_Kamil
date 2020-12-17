package day03;





import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;



public class GitHubRestAPITest {



    @DisplayName("Test Github Get / Users /{username} ")
    @Test
    public void testGitHub(){

        // provide  your  username as path variable in give section of  the  chain
        given()
                .pathParam("blabla","kamiltoprak").
        when()
                .get("https://api.github.com/users/{blabla}").
        then()
                .assertThat()
                .statusCode(is(200))
                .contentType(ContentType.JSON)
                .header("server","GitHub.com")
                .body("login",is("kamiltoprak"));


    }

}
