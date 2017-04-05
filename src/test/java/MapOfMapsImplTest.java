import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentMap;

import static org.junit.Assert.*;

/**
 * Created by amitkuma on 4/4/17.
 */
public class MapOfMapsImplTest {

          @Test
    public void test_get_values() throws Exception{
              MapOfMaps<String,String,String> testMap = new MapOfMapsImpl<>();
              testMap.put("test1","test2-1","value1");
              testMap.put("test2","test2-2","value2");
              testMap.put("test3","test2-3","value3");
              testMap.put("test4","test2-4","value4");

              Collection<String> innnerMap = testMap.values();

              assertTrue(innnerMap.containsAll(Arrays.asList("value1","value2","value3","value4")));
              //System.out.println(innnerMap);
          }

            @Test
            public void test_putifAbsent() throws Exception{
              MapOfMaps<String,String,String> testMap = new MapOfMapsImpl<>();
              testMap.put("test1","test2-1","value1");
              testMap.put("test1","test2-1","value1");
              testMap.put("test2","test2-1","value1");
              assertTrue(testMap.subMaps().size()  == 2);

          }

}