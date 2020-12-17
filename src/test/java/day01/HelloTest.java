package day01;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledIf;


@DisplayName("Day1 Hello Test")
public class HelloTest {

    //Junit5 Annotation
    // @BeforeAll @ AfterAll @BeforeEach @ AfterEach
    @BeforeAll
    public static void setup(){
        System.out.println("######@BeforeAll is Running##########");
    }

    @BeforeEach
    public  void setup1(){
        System.out.println("========@BeforeEach is Running========");
    }

    @AfterAll
    public static void tearDown(){
        System.out.println("##########@AfterAll is Running#########");
    }

    @AfterEach
    public  void tearDown1(){
        System.out.println("========@AfterEach is Running========");
    }

    @DisplayName("Test 1+3=4 ")
    @Test
    public void test(){
        Assertions.assertEquals(4,1+3);

        System.out.println("Test1 is running");
    }

    @Disabled
    @DisplayName("Test 3*4=12 ")
    @Test
    public void test2(){
        Assertions.assertEquals(12,3*4);

        System.out.println("Test2 is running");
    }


}
