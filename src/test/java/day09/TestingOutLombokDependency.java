package day09;

import org.junit.jupiter.api.Test;
import pojo.Department;

public class TestingOutLombokDependency {

    @Test
    public void testDepartmentPOJO(){
        Department d=new Department();
        d.setDepartment_id(100);
        System.out.println(d.getDepartment_id());

        Department d2=new Department(100,"abc",12,1400);
        System.out.println("d2 = " + d2);


    }





}
