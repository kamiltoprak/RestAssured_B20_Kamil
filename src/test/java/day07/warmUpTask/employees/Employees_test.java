package day07.warmUpTask.employees;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import io.restassured.http.ContentType;
import pojo.warmUpTask.employees.Employee;

import java.util.List;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;

public class Employees_test {

    // http://54.90.101.103:1000/ords/hr/regions/3

    @BeforeAll
    public static void setUp(){
        baseURI = "http://54.90.101.103:1000";
        basePath = "/ords/hr" ;
    }
    @AfterAll
    public static void tearDown(){
        reset();
    }

    @DisplayName("Testing/regions/{employee_id}")
    @Test
    public void testThirdRegionIsAsia(){

        given()
                .pathParam("employee_id",104)
                .log().all().
        when()
                .get("/employees/{employee_id}").
        then()
                .log().all()
                .assertThat()
                .statusCode(is(200))
                .contentType(ContentType.JSON)
                .body("first_name",is("Bruce"));
    }

    @DisplayName("Save Get/employees/{employee_id} response as POJO ")
    @Test
    public void testSingleRegionToPOJO(){

        Response response=
        given()
                .pathParam("employee_id",104)
                .log().all().
        when()
                .get("/employees/{employee_id}")
                .prettyPeek();
        JsonPath jp=response.jsonPath();

        Employee e3=jp.getObject("", Employee.class);
        System.out.println("r3 = " + e3);

        Employee e4=response.as(Employee.class);
        System.out.println("r4 = " + e4);

        System.out.println("==============================");

        JsonPath jp1=given()
                .pathParam("employee_id",104)
                .log().all().
                        when()
                .get("/employees/{employee_id}")
                .prettyPeek().jsonPath();

        Employee e5=jp1.getObject("",Employee.class);
        System.out.println("e5 = " + e5);


    }


    @DisplayName("Save Get/employees response as list of  POJO ")
    @Test
    public void testAllRegionToListOfPOJO(){
        Response response=get("/employees");
        JsonPath jp=response.jsonPath();
        List<Employee> allEmployee=jp.getList("items",Employee.class);
        allEmployee.forEach(System.out::println);
        // with out using list
        // use jsonpath to get

        System.out.println("================");
        JsonPath jp1=get("/employees").jsonPath();
        List<Employee> allEmployee1=jp.getList("items",Employee.class);
        allEmployee.forEach(System.out::println);

    }

}



