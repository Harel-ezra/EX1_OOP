package ex1.c;

import java.nio.IntBuffer;
import java.util.*;

public class WGraph_DS implements weighted_graph
{
    private HashMap<Integer, node_info> graph;
    private HashMap<Integer, HashMap<Integer, Double>> grNi;
    private int edge_counter = 0;
    private int MC = 0;

    /**
     * constructor
     */
    public WGraph_DS()
    {
        graph=new HashMap<Integer, node_info>();
        grNi=new HashMap<Integer, HashMap<Integer, Double>>();
    }

    /**
     * copy constructor
     */
    public WGraph_DS(weighted_graph wgr)
    {
        graph=new HashMap<Integer, node_info>();
        grNi=new HashMap<Integer, HashMap<Integer, Double>>();
        for (node_info n:wgr.getV())
        {
            addNode(n.getKey());
        }

        for (node_info n: wgr.getV())
        {
            Collection<node_info> ni=wgr.getV(n.getKey());
            for(node_info i: ni)
            {
                double w=getEdge(n.getKey(),i.getKey());
                this.connect(n.getKey(),i.getKey(),w);
            }
        }
    }

    /**
     * return the node date by the node id
     * @param key - the node_id
     * @return
     */
    @Override
    public node_info getNode(int key)
    {
        return this.graph.get(key);
    }


    @Override
    public boolean hasEdge(int node1, int node2) {
        if(graph.containsKey(node1)&&graph.containsKey(node2))
        {
            HashMap<Integer, Double> ni=grNi.get(node1);
            return ni.containsKey(node2);
        }
        return false;
    }

    @Override
    public double getEdge(int node1, int node2) {
        if(hasEdge(node1,node2))
        {
            HashMap<Integer, Double> ni=grNi.get(node1);
            return ni.get(node2);

        }

        return -1;
    }

    @Override
    public void addNode(int key) {
        if(!graph.containsKey(key))
        {
            node_info n=new NodeInfo(key);
            graph.put(key,n);
            HashMap<Integer, Double> hni=new HashMap<Integer, Double>();
            grNi.put(key, hni);
            this.MC++;
        }
    }

    @Override
    public void connect(int node1, int node2, double w) {
        if(graph.containsKey(node1)&& graph.containsKey(node2))
        {
            if(w>0)
            {
                HashMap<Integer, Double> tempH=grNi.get(node1);
                tempH.put(node2, w);
                tempH=grNi.get(node2);
                tempH.put(node1, w);
                this.MC++;
                this.edge_counter++;
            }
        }

    }

    @Override
    public Collection<node_info> getV() {
        return graph.values();
    }

    @Override
    public Collection<node_info> getV(int node_id) {
        Collection<node_info> colNode=new ArrayList<>();
        if (graph.containsKey(node_id))
        {
            HashMap<Integer, Double> tempH = grNi.get(node_id);
            Set<Integer> set = tempH.keySet();
            for (int i : set) {
                colNode.add(getNode(i));
            }
        }
        return colNode;
    }

    @Override
    public node_info removeNode(int key) {
        if(graph.containsKey(key))
        {
            HashMap<Integer, Double> tempH = grNi.get(key);
            Set<Integer> set = tempH.keySet();
            for(int i:set)
            {
                HashMap<Integer, Double> nih = grNi.get(i);
                if(nih.containsKey(key))
                {
                    nih.remove(key);
                    this.edge_counter--;
                    this.MC++;
                }
            }
            this.MC++;
            return graph.remove(key);
        }
        return null;
    }

    @Override
    public void removeEdge(int node1, int node2) {
        if(hasEdge(node1,node2))
        {
            HashMap<Integer, Double> tempH=grNi.get(node1);
            tempH.remove(node2);
            tempH=grNi.get(node2);
            tempH.remove(node1);
            this.edge_counter--;
            this.MC++;
        }
    }

    @Override
    public int nodeSize() {
        return this.graph.size();
    }

    @Override
    public int edgeSize() {
        return this.edge_counter;
    }

    @Override
    public int getMC() {
        return this.MC;
    }


    private class NodeInfo implements node_info
    {
        private int key_id;
        private String info;
        private double tag;

        /**
         * constructor
         * @return
         */

        public NodeInfo(int key)
        {
            this.key_id=key;
            this.tag=-1.0;
            this.info="x";
        }

        /**
         * copy cnstructor
         * @return
         */
        public NodeInfo(node_info n)
        {
            this.key_id=n.getKey();
            this.info=n.getInfo();
            this.tag=n.getTag();
        }

        /**
         * Return the unique key (id) associated with this node.
         * @return
         */

        @Override
        public int getKey()
        {
            return this.key_id;
        }

        /**
         * return the info of the node
         * @return
         */
        @Override
        public String getInfo() {
            return this.info;
        }

        @Override
        public void setInfo(String s) {
            this.info=s;
        }

        @Override
        public double getTag() {
            return this.tag;
        }

        @Override
        public void setTag(double t) {
            this.tag=t;
        }
    }

}
