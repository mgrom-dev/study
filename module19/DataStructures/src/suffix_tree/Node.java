package suffix_tree;

import java.util.ArrayList;
import java.util.List;

public class Node
{
    private String fragment;
    private ArrayList<Node> nextNodes;
    private ArrayList<Integer> position;

    public Node(String fragment, ArrayList<Integer> position)
    {
        this.fragment = fragment;
        nextNodes = new ArrayList<>();
        this.position = position;
    }

    public String getFragment()
    {
        return fragment;
    }

    public List<Integer> getPosition()
    {
        return position;
    }

    public List<Node> getNextNodes()
    {
        return nextNodes;
    }

    public void setNextNodes(ArrayList<Node> nextNodes) {
        this.nextNodes = nextNodes;
    }
}