package day07.practice.Departments;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.practice.employees.Employee;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;

public class Departments_test {

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

    @DisplayName("Testing/departments/{employee_id}")
    @Test
    public void testThirdDepartmentIsMarketing(){

        given()
                .pathParam("department_id",20)
                .log().all().
        when()
                .get("/departments/{department_id}").
        then()
                .log().all()
                .assertThat()
                .statusCode(is(200))
                .contentType(ContentType.JSON)
                .body("department_name",is("Marketing"));
    }

    @DisplayName("Save Get/department/{department_id} response as POJO ")
    @Test
    public void testSingleRegionToPOJO(){

        Response response=
        given()
                .pathParam("department_id",20)
                .log().all().
        when()
                .get("/departments/{department_id}")
                .prettyPeek();
        JsonPath jp=response.jsonPath();

        Employee e3=jp.getObject("", Employee.class);
        System.out.println("r3 = " + e3);

        Employee e4=response.as(Employee.class);
        System.out.println("r4 = " + e4);

        System.out.println("==============================");

        JsonPath jp1=given()
                .pathParam("department_id",104)
                .log().all().
                        when()
                .get("/department/{department_id}")
                .prettyPeek().jsonPath();

        Employee e5=jp1.getObject("",Departments.class);
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



