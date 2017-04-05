import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * Created by amitkuma on 4/4/17.
 */
public class MapOfMapsImpl<K1, K2, V> implements MapOfMaps<K1, K2, V>{

     ConcurrentMap<K1, ConcurrentMap<K2 ,V>> nestedMap = new ConcurrentHashMap<>();

    public MapOfMapsImpl() {
        this.nestedMap = new ConcurrentHashMap<>();
    }

    public MapOfMapsImpl(ConcurrentMap<K1, ConcurrentMap<K2, V>> nestedMap) {
        this.nestedMap = nestedMap;
    }

    @Override
    public V putIfAbsent(K1 key1, K2 key2, V value) {
        V  prevValue = null;
        ConcurrentMap<K2, V> k2VConcurrentMap = nestedMap.get(key1);
        if(k2VConcurrentMap == null) {
            k2VConcurrentMap = new ConcurrentHashMap<K2, V>();
            k2VConcurrentMap.putIfAbsent(key2,value);
        }
        else{
            prevValue = k2VConcurrentMap.putIfAbsent(key2,value);
        }
        nestedMap.putIfAbsent(key1,k2VConcurrentMap);
        return prevValue;
    }

    @Override
    public V put(K1 key1, K2 key2, V value) {
        if(nestedMap.get(key1) == null){
            nestedMap.put(key1 , new ConcurrentHashMap<K2, V>());
        }
        return nestedMap.get(key1).put(key2,value);
    }

    @Override
    public Map<K2, V> get(K1 key) {
        return nestedMap.get(key);
    }

    @Override
    public V get(K1 key1, K2 key2) {
        return nestedMap.get(key1) == null ? null : nestedMap.get(key1).get(key2);
    }

    @Override
    public V remove(K1 key1, K2 key2) {
        Map<K2, V> k2VHashMap = nestedMap.get(key1);
        V remove = k2VHashMap.remove(key2);
        nestedMap.remove(key1);
        return remove;

    }

    @Override
    public Map<K2, V> removeAll(K1 key1) {
        Map<K2, V> k2VHashMap = nestedMap.get(key1);
        nestedMap.remove(key1);
        return k2VHashMap;
    }

    @Override
    public boolean contains(K1 key1, K2 key2) {
        return nestedMap.containsKey(key1) && nestedMap.get(key1).containsKey(key2);
    }

    @Override
    public Collection<K1> keySet() {
        return nestedMap.keySet();
    }

    @Override
    public Collection<ConcurrentMap<K2, V>> subMaps() {
        return nestedMap.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }

    @Override
    public Collection<V> values() {
        return nestedMap.entrySet().stream().flatMap((entry) -> entry.getValue().values().stream()).collect(Collectors.toList());
    }
}
