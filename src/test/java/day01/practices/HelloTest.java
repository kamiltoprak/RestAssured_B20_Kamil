package day01.practices;


import org.junit.jupiter.api.*;

public class HelloTest {

    @BeforeAll
    public static void test1(){
        System.out.println("#####before all######");
    }
    @BeforeEach
    public void test2(){
        System.out.println("======before each=======");
    }
    @AfterAll
    public static void test3(){
        System.out.println("#####after all######");
    }

    @AfterEach
    public void test4(){
        System.out.println("======after each=======");

    }

    @Test
    public void test5(){
        System.out.println("test5");
    }

    @Test
    public void test6(){
        System.out.println("test6");
    }

    @Disabled
    @Test
    public void test7(){
        System.out.println("test7");
    }


}
