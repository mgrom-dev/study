package suffix_tree;

import java.util.*;

public class SuffixTreeBuilding {
    private HashMap<String, SuffixTreeBuilding> fragments = new HashMap<>();
    private String fragment;
    private LinkedList<Integer> positions = new LinkedList<>();

    private SuffixTreeBuilding(){}

    public SuffixTreeBuilding(String ... comparingWords) {
        fragment = "root";
        int i = 0;
        for (String word : comparingWords){
            SuffixTreeBuilding currentNode = this;
            for (String l : word.split("")){
                SuffixTreeBuilding fragment = currentNode.fragments.getOrDefault(l, new SuffixTreeBuilding());
                fragment.fragment = l;
                fragment.positions.add(i);
                currentNode.fragments.put(l, fragment);
                currentNode = fragment;
            }
            i += word.length() + 1;
        }
        joinSuffixes();
    }

    private void joinSuffixes(){
        LinkedList<SuffixTreeBuilding> nodes = new LinkedList<>(fragments.values());
        while(nodes.size() > 0){
            SuffixTreeBuilding node = nodes.get(0);
            if (node.fragments.size() > 1) {
                nodes.addAll(0, node.fragments.values());
            } else if (node.fragments.size() == 1) {
                SuffixTreeBuilding next = new ArrayList<>(node.fragments.values()).get(0);
                node.fragments = next.fragments;
                node.fragment += next.fragment;
                nodes.add(0, node);
            }
            nodes.remove(node);
        }
    }

    public ArrayList<Node> getNodes(HashMap<String, SuffixTreeBuilding> fragments){
        if (fragments == null) {
            fragments = this.fragments;
        }
        ArrayList<Node> nodes = new ArrayList<>();
        LinkedList<SuffixTreeBuilding> suffixes = new LinkedList<>(fragments.values());
        for (SuffixTreeBuilding suffix : suffixes) {
            if (suffix.fragments.size() > 0) {
                Node node = new Node(suffix.fragment, new ArrayList<>(suffix.positions));
                node.setNextNodes(getNodes(suffix.fragments));
                nodes.add(node);
            } else {
                nodes.add(new Node(suffix.fragment, new ArrayList<>(suffix.positions)));
            }
        }
        return nodes;
    }
}
