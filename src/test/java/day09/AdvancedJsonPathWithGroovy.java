package day09;

import TestBase.HR_ORDS_TestBase;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Department;
import TestBase.HR_ORDS_TestBase;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.get;

public class AdvancedJsonPathWithGroovy extends HR_ORDS_TestBase {

    @DisplayName("Testing out the lombok dependency")
    @Test
    public void testDepartmentPOJO(){

        Department d = new Department();
        d.setDepartment_id(100);
        System.out.println( d.getDepartment_id() );

        Department d2= new Department(100,"ABC",12,1700);
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

    @DisplayName("GET /departments and filter the result with JsonPath groovy")
    @Test
    public void testFilterResultWithGroovy(){

        JsonPath jp = get("/departments").jsonPath();
        List<Department> allDeps =
                jp.getList("items.findAll { it.manager_id > 0 }" , Department.class ) ;
        allDeps.forEach(System.out::println);
        // what if I just wanted to get List<String> to store DepartmentName
        List<String> depNames = jp.getList("items.department_name") ;
        System.out.println("depNames = \n\t" + depNames);
        // -->> items.department_name (all)
        // -->> items.findAll {it.manager_id>0 }.department_name (filtered for manager_id more than 0)
        List<String> depNamesFiltered = jp.getList("items.findAll {it.manager_id>0 }.department_name") ;
        System.out.println("depNamesFiltered = \n\t" + depNamesFiltered);

        // Get all departments ID if its more than 70
        List<Integer> allDepIds = jp.getList("items.department_id") ;
        System.out.println("allDepIds = \n\t" + allDepIds);

        List<Integer> allDepIdsFiltered =
                jp.getList("items.department_id.findAll{ it > 70 } ") ;
        System.out.println("allDepIdsFiltered = \n\t" + allDepIdsFiltered);

        // what if we have more than one condition for example : department_id between 70 - 100
        List<Integer> deps70to100 =
                jp.getList("items.department_id.findAll{ it >= 70 && it <= 100  }") ;
        System.out.println("deps70to100 = \n\t" + deps70to100);
        // get the name of departments if department_id between 70 - 100
        List<String> allDeps70To100 =
                jp.getList("items.findAll{ it.department_id >= 70 && it.department_id  <= 100  }.department_name ") ;
        System.out.println("allDeps70To100 = \n\t" + allDeps70To100);
        //allDeps70To100 = [Public Relations, Sales, Executive, Finance]

        List<String> aDeps =
                jp.getList("items.department_name.findAll{ it.startsWith('A') } ") ;
        System.out.println("Departments wit name start with A \n\t = " + aDeps);

        // findAll-->> will return all matching result
        // find -->> will return first match for the condition
        String dep10 =  jp.getString("items.find{ it.department_id==10 }.department_name");
        System.out.println("department 10 name = \n\t" + dep10);
        //department 10 name = Administration

        // sum / min / max  collect
        // get the sum of entire department_id
        int sumOfAllDepIDs = jp.getInt("items.department_id.sum()") ;
        int sumOfAllDepIDs2 = jp.getInt("items.sum {it.department_id} ") ;

        System.out.println("sumOfAllDepIDs = \n\t" + sumOfAllDepIDs);
        System.out.println("sumOfAllDepIDs2 = \n\t" + sumOfAllDepIDs2);

        // get the lowest department_id
        int lowestDepId = jp.getInt("items.department_id.min()") ;
        System.out.println("lowestDepId = \n\t" + lowestDepId);

        // get the highest department_id
        int highestDepId = jp.getInt("items.department_id.max()") ;
        System.out.println("highestDepId = \n\t" + highestDepId);

        String shortestDepName = jp.getString("items.department_name.min{ it.length() }") ;
        System.out.println("shortestDepName = \n\t" + shortestDepName);

        String longestDepName = jp.getString("items.department_name.max{ it.length() }") ;
        System.out.println("longestDepName = \n\t" + longestDepName);

        String shortestDepNameID =
                jp.getString("items.min{ it.department_name.length() }.department_id") ;
        System.out.println("shortestDepNameID = \n\t" + shortestDepNameID);

        // print number 5 dep ID
        System.out.println("number 5 dep ID" + jp.getInt("items.department_id[4]")   );
        // print number last dep ID
        System.out.println("last dep ID " + jp.getInt("items.department_id[-1]")   );
        // print from index 7 till index 10 dep ID
        System.out.println("index 7-10 dep ID " + jp.getList("items.department_id[7..10]")   );

        System.out.println("collect method demo \n");
        // get all the department name uppercase
        List<String> upperCaseDepNames
                = jp.getList("items.department_name.collect{ it.toUpperCase() }") ;
        upperCaseDepNames.forEach(System.out::println);

        System.out.println(" \ncollect method demo 2\n");
        List<String> idDepNames
                = jp.getList("items.collect{'Deps ' + it.department_id + ', ' + it.department_name }") ;
        idDepNames.forEach(System.out::println);


    }
}