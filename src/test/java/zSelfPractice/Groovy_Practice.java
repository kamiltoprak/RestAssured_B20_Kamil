package zSelfPractice;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testbase.HR_ORDS_TestBase;

import java.util.List;

import static io.restassured.RestAssured.*;
public class Groovy_Practice extends HR_ORDS_TestBase {

    @DisplayName("Groovy   GET - /employees")
    @Test
    public void groovyMethod() {

        JsonPath jp = get("/employees").jsonPath();
        jp.prettyPrint();
        
        // find max salary 
        
        int maxSalary=jp.getInt("items.salary.max()");
        System.out.println("maxSalary = " + maxSalary);

        // find max salary employee phone_number
        String phoneNumber=jp.getString("items.max{it.salary}.phone_number");
        System.out.println("phoneNumber = " + phoneNumber);

        // find max salary employee first_name

        String firstName=jp.getString("items.max{it.salary}.first_name");
        System.out.println("firstName = " + firstName);

        //  find min salary if job_id is FI_ACCOUNT
        String minSalaryForFI_ACCOUNT=jp.getString("items.findAll{it.job_id=='FI_ACCOUNT'}.salary.min()");
        System.out.println("minSalaryForFI_ACCOUNT = " + minSalaryForFI_ACCOUNT);

        // findAll email of employees belong to department 100
        List<String> dept100employees=jp.getList("items.findAll{it.department_id==100}.email");
        System.out.println("dept100employees = " + dept100employees);

        //  findAll first_name with salary more than 15000
        List<String> salaryMoreThan1500=jp.getList("items.findAll{it.salary>=15000}.first_name");
        System.out.println("salaryMoreThan1500 = " + salaryMoreThan1500);

        //findAll last_name of employee from index 10-15
        List<String> lastNameList=jp.getList("items[10..15].last_name");
        System.out.println("lastNameList = " + lastNameList);

        //findAll phone_number startWith 590 (it.phone_number.startWith('590'))
        List<String> phoneNumberStart590=jp.getList("items.findAll{it.phone_number.startsWith('590')}.phone_number");
        System.out.println("phoneNumberStart590 = " + phoneNumberStart590);

        //full name 
        List<String> fullName=jp.getList("items.collect{it.first_name+' '+it.first_name}");
        System.out.println("fullName = " + fullName);



    }
}

