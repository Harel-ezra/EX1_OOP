package ex1.c;

import java.nio.IntBuffer;
import java.util.*;

public class WGraph_DS implements weighted_graph
{
    private HashMap<Integer, node_info> graph;
    private HashMap<Integer, EdgeList> grNi;
    private int edge_counter = 0;
    private int MC = 0;

    /**
     * constructor
     */
    public WGraph_DS()
    {
        graph=new HashMap<Integer, node_info>();
        grNi=new HashMap<Integer, EdgeList>();
    }

    /**
     * copy constructor
     */
    public WGraph_DS(weighted_graph wgr)
    {
        graph=new HashMap<Integer, node_info>();
        grNi=new HashMap<Integer, EdgeList>();
        for (node_info n:wgr.getV())
        {
            addNode(n.getKey());
        }

        for (node_info n: wgr.getV())
        {
            EdgeList l =((WGraph_DS)wgr).getNiL(n.getKey());
            for(Integer i: l.getKeyL())
            {
                double w=getEdge(n.getKey(),i);
                this.connect(n.getKey(),i,w);
            }
        }
    }
    private EdgeList getNiL(int i)
    {
        return grNi.get(i);
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
            if(grNi.get(node1).hasE(node2))
                return true;
        }
        return false;
    }

    @Override
    public double getEdge(int node1, int node2) {
        if(hasEdge(node1,node2))
        {
            return grNi.get(node1).getW(node2);

        }
        return -1;
    }

    @Override
    public void addNode(int key) {
        if(!graph.containsKey(key))
        {
            node_info n=new NodeInfo(key);
            graph.put(key,n);
            EdgeList hni=new EdgeList();
            grNi.put(key, hni);
            this.MC++;
        }
    }

    @Override
    public void connect(int node1, int node2, double w) {
        if(graph.containsKey(node1)&& graph.containsKey(node2))
        {
            if(w>=0)
            {

                EdgeList tempH=grNi.get(node1);
                tempH.setW(node2, w);
                tempH=grNi.get(node2);
                tempH.setW(node1, w);
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
            EdgeList tempH = grNi.get(node_id);
            Set<Integer> set = tempH.getKeyL();
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
            EdgeList tempH = grNi.get(key);
            Set<Integer> set = tempH.getKeyL();
            for(int i:set)
            {
                EdgeList nih = grNi.get(i);
                if(nih.hasE(key))
                {
                    nih.removeE(key);
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
            EdgeList tempH=grNi.get(node1);
            tempH.removeE(node2);
            tempH=grNi.get(node2);
            tempH.removeE(node1);
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


    private class NodeInfo implements node_info, Comparable<node_info>
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

        /**
         *Comparator
         */
        @Override
        public int compareTo(node_info n)
        {
            if(this.getTag()>n.getTag())
            {
                return 1;
            }
            else
                if(this.getTag()==n.getTag())
                {
                    return 0;
                }

            return -1;
        }

    }


    private class EdgeList
    {
        private HashMap<Integer, Double> NodeNi;

        public EdgeList()
        {
            this.NodeNi= new HashMap<Integer, Double>();
        }

        public EdgeList(EdgeList l)
        {
            for(int i:l.getKeyL())
            {
                addNi(i, l.getW(i));
            }

        }

        public HashMap<Integer, Double> getEL()
        {
            return this.NodeNi;
        }

        public void addNi(Integer i, Double w)
        {
            if( i>0 &&w>0)
            {
                NodeNi.put(i,w);
            }
        }

        public double getW(int i)
        {
            if(hasE(i))
            {
                return NodeNi.get(i);
            }
            return -1;
        }

        public boolean hasE(int i)
        {
            if(NodeNi.containsKey(i))
            {
                return true;
            }
            return false;

        }

        public Set<Integer> getKeyL()
        {
            return this.NodeNi.keySet();
        }

        public void setW(int key, double w)
        {
            if (NodeNi.containsKey(key))
            {
                NodeNi.put(key, w);
            }
        }
         public void removeE(int key)
         {
             if(NodeNi.containsKey(key))
             {
                 NodeNi.remove(key);
             }
         }




    }

}
