package day09;

import TestBase.HR_ORDS_TestBase;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Department;

import java.util.ArrayList;
import java.util.List;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TestingOutLombokDependency extends HR_ORDS_TestBase {

    @Test
    public void testDepartmentPOJO(){

        Department d = new Department();
        d.setDepartment_id(100);
        System.out.println( d.getDepartment_id() );

        Department d2
                = new Department(100,"ABC",12,1700);
        System.out.println("d2 = " + d2);
    }

    @DisplayName("GET /departments and save List of POJO")
    @Test
    public void testDepartmentJsonArrayToListOfPojo(){

        List<Department> allDeps = get("/departments")
                .jsonPath().getList("items", Department.class) ;
        //allDeps.forEach(System.out::println);


        // COPY THE CONTENT OF THIS LIST INTO NEW LIST
        // AND ONLY PRINT IF THE DEP MANAGER ID IS NOT NULL
        List<Department> allDepsCopy = new ArrayList<>(allDeps);
        allDepsCopy.removeIf( eachDep -> eachDep.getManager_id()==0 ) ;
        allDepsCopy.forEach(System.out::println);

    }


    @DisplayName("GET /departments and filter the result  with JsonPAth groovy")
    @Test
    public void testFilterResultWithGroovy(){
        JsonPath jp = get("/departments").jsonPath();
        System.out.println("======= All departments info ========");
        List<Department> allDeps = jp.getList("items.findAll { it.manager_id > 0 }" ,
                Department.class ) ;
        allDeps.forEach(System.out::println);

        // what if I just wanted to get List<String> to store DepartmentName
        System.out.println("======= All department names ========");
        List<String> depNames = jp.getList("items.department_name") ;
        System.out.println("depNames = " + depNames);

        // -->> items.department_name (all)
        // -->> items.findAll {it.manager_id>0 }.department_name (filtered for manager_id more than 0)
        System.out.println("=======All department name if manager id more than 0 ========");
        List<String> depNamesFiltered = jp.getList("items.findAll {it.manager_id>0 }.department_name") ;
        System.out.println("depNamesFiltered = " + depNamesFiltered);
        
        
        //all department id
        System.out.println("=======All department id ========");
        List<Integer> depID = jp.getList("items.department_id");
        System.out.println("depID = " + depID);

        //find – finds the first item matching a closure predicate
        //collect – collect the return value of calling a closure on each item in a collection
        //sum – Sum all the items in the collection
        //max/min – returns the max/min values of the collection
        //findAll – finds the all items matching a closure predicate

        // --> get all  departments id ,  if it  is  more than 70
        // -- it  --  means each item in groovy
        System.out.println("=======Department id  more than 70 ========");
       List<Integer> depIDsFiltered = jp.getList("items.department_id.findAll {it>70 }") ;
        System.out.println("depIDsFiltered = " + depIDsFiltered);
        
        // what if we  have more than one condition
        // for  example  department id  between 70- 100

        System.out.println("=======Department ID  between 70  to  100 ======");
        List<Integer> depIDsFiltered2 = jp.getList("items.department_id.findAll {it>70 && it<100 }") ;
        System.out.println("depIDs between 70  to  100 = " + depIDsFiltered2);

        //get the name of the departments if department_id  between 70 -100
        System.out.println("=======Department Name , if Department ID  between 70  to  100 ======");
        List<Integer> depNameIDBetween70to100 =
                jp.getList("items.findAll {it.department_id>=70 && it.department_id<=100 }.department_name") ;
        System.out.println("depIDs between 70  to  100 = " + depNameIDBetween70to100);


        //findAll -->  will  return all  matches
        //find --> will  return first  match  for the condition
        System.out.println("=======Department Name, if Department ID is 10======");
        String depNameIDis10 =
                jp.getString("items.find {it.department_id==10}.department_name") ;
        System.out.println("depIDs is 10= " + depNameIDis10);


        //min/mac/sum collect
        // get sum of entire dept_ID
        System.out.println("=======sum of department id ======");
        //int sumDepTID=jp.getInt("items.department_id.findAll{it}.sum()");
        //int sumDepTID=jp.getInt("items.department_id.sum()");
        int sumDepTID=jp.getInt("items.sum{it.department_id}");
        System.out.println("sumDeptID = " + sumDepTID);

        // get lowest department id
        int minDeptID=jp.getInt("items.department_id.min()");
        System.out.println("minDeptID = " + minDeptID);

        //get max dept ID
        int maxDeptID=jp.getInt("items.department_id.max()");
        System.out.println("maxDeptID = " + maxDeptID);
        
        //print number 5 dep  ID
        System.out.println("number5DeptID = " + jp.getInt("items.department_id[4]"));

        // print number last dept ID
        System.out.println("number5DeptID = " + jp.getInt("items.department_id[-1]"));

        // print 7-10 dept ID
        System.out.println("index 7-10 "+jp.getList("items.department_id[7..10]"));

    }

}