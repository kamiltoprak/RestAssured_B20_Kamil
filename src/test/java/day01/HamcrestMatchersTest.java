package day01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


//hamcrest assertion library already part of our
// restAssured Dependency in pom file   no separate dependency needed

public class HamcrestMatchersTest {
    @DisplayName("test 1+3 is 4")
    @Test
    public void test1(){

        assertThat(1+3,is(4));
        assertThat(1+3,equalTo(4));

        //add some nice error message if it  fails
        //assertThat("Wrong result!!",1+3,equalTo(5));


        assertThat(1+3,not(5));
        assertThat(1+3,is(not(5)));
        assertThat(1+3,not(equalTo(5)));

        // test 1+3 is less than 5
        assertThat(1+3,lessThan(5));

        // test 1+3 is more than 2
        assertThat(1+3,greaterThan(5));



        //test 1+3 is less than 5
        assertThat(1+3,lessThan(5));
        assertThat(1+3,is(lessThan(5)));
        assertThat(1+3,lessThanOrEqualTo(5));
        assertThat(1+3,is(lessThanOrEqualTo(5)));

        //test 1+3 is greater than 5
        assertThat(1+3,greaterThan(2));
        assertThat(1+3,is(greaterThan(2)));
        assertThat(1+3,greaterThanOrEqualTo(2));
        assertThat(1+3,is(greaterThanOrEqualTo(2)));


    }
}
