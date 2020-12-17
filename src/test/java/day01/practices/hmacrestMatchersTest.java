package day01.practices;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class hmacrestMatchersTest {

    @DisplayName("1+3 = 4 ")
    @Test
    public void test1(){

        assertThat(1+3,is (4));
        assertThat(1+3,is(not(5)));
        assertThat(1+3,lessThan(6));
        assertThat(1+3,is (lessThan(6)));
        assertThat(1+3,is(not(lessThan(4))));
        assertThat(1+3,greaterThan(2));
        assertThat(1+3,is(greaterThan(2)));
        assertThat(1+3,is(not(greaterThan(6))));



    }
}
