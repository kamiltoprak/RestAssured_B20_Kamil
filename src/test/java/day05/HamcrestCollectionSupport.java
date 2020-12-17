package day05;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HamcrestCollectionSupport {


    @Test
    public void testList(){
        List<Integer> numList= Arrays.asList(4,5,6,9,4,88,90);

        // use hamcrest matcher to  test the size of this  list
        assertThat(numList,hasSize(7));

        // assert that this list  contains 9
        assertThat(numList,hasItem(9));

        // assert that this list  contains 9 ,88
        assertThat(numList, hasItems(9,88));

        // assert that every item in the list  are more than  3
        assertThat(numList,everyItem(greaterThan(3)));

        assertThat(numList, everyItem(is(greaterThan(3))));

        List<String> names=Arrays.asList("Rory hogan", "Marianna", "Olivia",
                "Gulbadan","Tucky tag","ayxamgul","Kareem","Virginia","Tahir Zohra");


        assertThat(names,hasSize(9));
        assertThat(names,hasItems("ayxamgul","Kareem"));

        // check every item has letter a,
        assertThat(names, everyItem(containsString("a")));

        // check  every  items has letter a in  case  insensitive  manner
        assertThat(names, everyItem(containsStringIgnoringCase("a")));

        // How  to  do  and or in  hamcrest syntax
        // all of -->  and any of --> or
        assertThat("Murat Degirmenci", allOf(startsWith("Mu"),containsString("men")));

        // anyOf -- >> or
        assertThat("Ramazan Alic", anyOf(is("Ramazan"),endsWith("ic")));


    }
}
