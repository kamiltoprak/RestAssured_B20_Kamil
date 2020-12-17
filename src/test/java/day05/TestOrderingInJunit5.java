package day05;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.MethodOrderer.*;

//@TestMethodOrder(OrderAnnotation.class)
//@TestMethodOrder(Random.class)
//@TestMethodOrder(MethodName.class) // default option

@TestMethodOrder(MethodOrderer.DisplayName.class)
public class TestOrderingInJunit5 {
// by default the test is running on alphabetical order
// or the test method name

    @Order(3)
    @DisplayName("3. Test A Method")
    @Test
    public void testA(){
        System.out.println("running Test A");
    }

    @Order(1)
    @DisplayName("1. Test C Method")
    @Test
    public void testC(){
        System.out.println("running Test C");
    }

    @Order(4)
    @DisplayName("4. Test D Method")
    @Test
    public void testD(){
        System.out.println("running Test D");
    }

    @Order(2)
    @DisplayName("2. Test B Method")
    @Test
    public void testB(){
        System.out.println("running Test B");
    }
}
