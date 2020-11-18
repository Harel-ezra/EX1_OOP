package ex1.c;

import java.util.List;

public class WGraph_Algo implements weighted_graph_algorithms
{
    private weighted_graph grAl;

    public WGraph_Algo()
    {

    }

    /**
     * copy constructor
     */
    public WGraph_Algo(weighted_graph_algorithms g)
    {
        this.grAl=g.getGraph();
    }
    @Override
    public void init(weighted_graph g) {
        this.grAl=g;

    }

    @Override
    public weighted_graph getGraph() {
        return this.grAl;
    }

    @Override
    public weighted_graph copy() {
        weighted_graph gCopy=new WGraph_DS(grAl);
        return null;
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        return 0;
    }

    @Override
    public List<node_info> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
    }
}
