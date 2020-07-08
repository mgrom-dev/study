package suffix_tree;

import java.util.*;

public class SuffixTree
{
    private String text;
    private ArrayList<Node> nodes;

    public SuffixTree(String text)
    {
        this.text = text;
        nodes = new ArrayList<>();
        build();
    }

    private void build()
    {
        nodes = new SuffixTreeBuilding(text.split(" ")).getNodes(null);
    }

    public List<Integer> search(String query)
    {
        List<Integer> positions = new ArrayList<>();
        List<Node> scannedNodes = new ArrayList<>(nodes);
        StringBuilder matchingString = new StringBuilder();
        while (scannedNodes.size() > 0) {
            Node scanNode = scannedNodes.get(0);
            String fragment = scanNode.getFragment();
            if (matchingString.length() + fragment.length() <= query.length() && fragment.equals(query.substring(matchingString.length(), matchingString.length() + fragment.length()))){
                matchingString.append(fragment);
                if (matchingString.toString().equals(query)) {
                    positions = scanNode.getPosition();
                    break;
                }
                scannedNodes.addAll(0, scanNode.getNextNodes());
            }
            scannedNodes.remove(scanNode);
        }
        return positions;
    }


}