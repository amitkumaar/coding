import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by amitkuma on 4/4/17.
 */
public interface MapOfMaps<K1, K2, V> {

    V putIfAbsent(K1 key1, K2 key2, V value);

    V put(K1 key1, K2 key2, V value);

    Map<K2, V> get(K1 key);

    V get(K1 key1, K2 key2);

    V remove(K1 key1, K2 key2);

    Map<K2, V> removeAll(K1 key1);

    boolean contains(K1 key1, K2 key2);

    Collection<K1> keySet();

    Collection<ConcurrentMap<K2, V>> subMaps();

    Collection<V> values();
}
