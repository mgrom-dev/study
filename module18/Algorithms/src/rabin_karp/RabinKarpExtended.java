package rabin_karp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class RabinKarpExtended
{
    private String text;
    private TreeMap<Integer, Integer> number2position = new TreeMap<>();
    private HashMap<String, Integer> alphabet = new HashMap<>();
    private static final int PRIME = 11;

    public RabinKarpExtended(String text)
    {
        this.text = text;
        createIndex();
    }

    public List<Integer> search(String query)
    {
        ArrayList<Integer> indices = new ArrayList<>();
        int hashQuery = 0, i = 0;
        for(String l : query.split("")){
            int n = alphabet.getOrDefault(l, -1);
            if (n == -1) {
                return indices;
            } else {
                hashQuery += n * Math.pow(PRIME, i);
                i++;
            }
        }
        int compareQuery = 0, p = 0;
        for(i = 0; i < number2position.size(); i++) {
            if (p < query.length()) {
                compareQuery += number2position.get(i) * Math.pow(PRIME, p);
                p++;
            }
            if (p == query.length()) {
                int f = i - (query.length() - 1);
                if (hashQuery == compareQuery && query.equals(text.substring(f, i + 1))) {
                    indices.add(f);
                }
                compareQuery = (compareQuery - number2position.get(f)) / PRIME;
                p--;
            }
        }
        return indices;
    }

    private void createIndex()
    {
        int index = 0;
        for(String letter : text.split("")){
            int key = alphabet.getOrDefault(letter, alphabet.size() + 1);
            alphabet.put(letter, key);
            number2position.put(index++, key);
        }
    }
}