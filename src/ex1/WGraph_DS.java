package ex1;

import java.util.*;

public class WGraph_DS implements weighted_graph {
    private HashMap<Integer, node_info> graph;
    private HashMap<Integer, EdgeList> grNi;
    private int edge_counter = 0;
    private int MC = 0;

    /**
     * constructor
     */
    public WGraph_DS() {
        graph = new HashMap<Integer, node_info>();
        grNi = new HashMap<Integer, EdgeList>();
    }

    /**
     * copy constructor
     */
    public WGraph_DS(weighted_graph wgr) {        // creat all node and than connect them
        graph = new HashMap<Integer, node_info>();
        grNi = new HashMap<Integer, EdgeList>();
        for (node_info n : wgr.getV()) {
            addNode(n.getKey());
        }
        for (node_info n : wgr.getV()) {
            for (node_info k : wgr.getV(n.getKey())) {
                double w = wgr.getEdge(n.getKey(), k.getKey());
                this.connect(n.getKey(), k.getKey(), w);
            }
        }
    }

    /**
     * return the node date by the node id
     *
     * @param key - the node_id
     * @return node_info
     */
    @Override
    public node_info getNode(int key) {
        return this.graph.get(key);
    }

    /**
     * get 2 number that present a nodes and check if the are neighbor and have a edge between them.
     *
     * @param node1
     * @param node2
     * @return true or false
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if (graph.containsKey(node1) && graph.containsKey(node2)) {
            if (grNi.get(node1).hasE(node2)) {
                return true;
            }
        }
        return false;
    }

    /**
     * return the edge size between 2 nodes at the graph, they must to be neighbor.
     *
     * @param node1
     * @param node2
     * @return double
     */
    @Override
    public double getEdge(int node1, int node2) {
        if (hasEdge(node1, node2)) {
            return grNi.get(node1).getW(node2);

        }
        return -1;
    }

    /**
     * add a new node at the graph with a giver key id.
     *
     * @param key
     */
    @Override
    public void addNode(int key) {
        if (!graph.containsKey(key)) {
            node_info n = new NodeInfo(key);
            graph.put(key, n);
            EdgeList hni = new EdgeList();
            grNi.put(key, hni);
            this.MC++;
        }
    }

    /**
     * connect and creat a new edge between 2 nodes at the graph, if the edge exist, just update their distance
     *
     * @param node1
     * @param node2
     * @param w
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if (graph.containsKey(node1) && graph.containsKey(node2)) {
            if (w >= 0 && node1 != node2) {
                if (!hasEdge(node1, node2)) {
                    this.edge_counter++;
                }
                EdgeList tempH = grNi.get(node1);
                tempH.setW(node2, w);
                tempH = grNi.get(node2);
                tempH.setW(node1, w);
                this.MC++;
            }
        }

    }

    /**
     * creat and return a collection that present all the nodes at the graph.
     *
     * @return
     */
    @Override
    public Collection<node_info> getV() {
        return graph.values();
    }

    /**
     * creat and return a collection for all neighbor of specific node.
     *
     * @param node_id
     * @return
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        Collection<node_info> colNode = new ArrayList<>();
        if (graph.containsKey(node_id)) {
            EdgeList tempH = grNi.get(node_id);
            //get a set and creat list according to the key.
            Set<Integer> set = tempH.getKeyL();
            for (int i : set) {
                colNode.add(getNode(i));
            }
        }
        return colNode;
    }

    /**
     * remove a node from the graph.
     * and also remove the node from all is neighbor and edge
     *
     * @param key
     * @return node_info that removed
     */
    @Override
    public node_info removeNode(int key) {
        if (graph.containsKey(key)) {
            EdgeList tempH = grNi.get(key);
            Set<Integer> set = tempH.getKeyL();
            for (int i : set) {
                EdgeList nih = grNi.get(i);
                nih.removeE(key);
                this.edge_counter--;
                this.MC++;

            }
            this.MC++;
            return graph.remove(key);
        }
        return null;
    }

    /**
     * remove an edge between 2 nodes at the graph.
     *
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if (hasEdge(node1, node2)) {
            EdgeList tempH = grNi.get(node1);
            tempH.removeE(node2);
            tempH = grNi.get(node2);
            tempH.removeE(node1);
            this.edge_counter--;
            this.MC++;
        }
    }

    /**
     * return the node size at the graph
     *
     * @return
     */
    @Override
    public int nodeSize() {
        return this.graph.size();
    }

    /**
     * return the edge size at the graph
     *
     * @return
     */
    @Override
    public int edgeSize() {
        return this.edge_counter;
    }

    /**
     * return the counter for al changes that made at the graph
     *
     * @return
     */
    @Override
    public int getMC() {
        return this.MC;
    }

    /**
     * methode for equals between 2 graph
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (o.getClass() != getClass())
            return false;
        if (this == null && o == null) {
            return true;
        }
        weighted_graph g = (weighted_graph) o;
        if (this != null && g != null) {
            Collection<node_info> col1 = this.getV();
            Collection<node_info> col2 = g.getV();
            if (col1.size() == col2.size()) {
                //first compare between the node at the graph
                for (node_info n1 : col1) {
                    if (!col2.contains(n1))
                        return false;
                }
                // second compare between the neighbor for any node at the graph and their distance
                for (node_info n1 : col1) {
                    Collection<node_info> col1Ni = this.getV(n1.getKey());
                    Collection<node_info> col2Ni = g.getV(n1.getKey());
                    if (col1Ni.size() != col2Ni.size())
                        return false;
                    for (node_info n1Ni : col1Ni) {
                        if (!col2Ni.contains(n1Ni)) {
                            return false;
                        }
                        if (this.getEdge(n1.getKey(), n1Ni.getKey()) != g.getEdge(n1.getKey(), n1Ni.getKey())) {
                            return false;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * private class that present single node
     */
    private class NodeInfo implements node_info, Comparable<node_info> {
        private int key_id;
        private String info;
        private double tag;

        /**
         * constructor
         *
         * @return
         */

        public NodeInfo(int key) {
            this.key_id = key;
            this.tag = -1.0;
            this.info = "x";
        }

        /**
         * copy cnstructor
         *
         * @return
         */
        public NodeInfo(node_info n) {
            this.key_id = n.getKey();
            this.info = n.getInfo();
            this.tag = n.getTag();
        }

        /**
         * Return the unique key (id) associated with this node.
         *
         * @return
         */

        @Override
        public int getKey() {
            return this.key_id;
        }

        /**
         * return the info of the node
         *
         * @return
         */
        @Override
        public String getInfo() {
            return this.info;
        }

        /**
         * set the info of the node
         *
         * @param s
         */
        @Override
        public void setInfo(String s) {
            this.info = s;
        }

        /**
         * return the tag of the node
         *
         * @return
         */
        @Override
        public double getTag() {
            return this.tag;
        }

        /**
         * set a tag for the node
         *
         * @param t - the new value of the tag
         */
        @Override
        public void setTag(double t) {
            this.tag = t;
        }

        /**
         * methode to equals between 2 nodes.
         *
         * @param o
         * @return
         */
        @Override
        public boolean equals(Object o) {
            if (o.getClass() == getClass() && o != null) {
                node_info n = (node_info) o;
                if (this.key_id == n.getKey() && this.info.equals(n.getInfo()) && this.tag == n.getTag()) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Comparator for compare between the w-distance for node, used for Dijkstra algoritem on Priority Queue
         */
        @Override
        public int compareTo(node_info n) {
            if (this.getTag() > n.getTag()) {
                return 1;
            } else if (this.getTag() == n.getTag()) {
                return 0;
            }
            return -1;
        }
    }

    /**
     * private class that present a list of neighbor for a node
     */
    private class EdgeList {
        private HashMap<Integer, Double> NodeNi;

        /**
         * constructor
         */
        public EdgeList() {
            this.NodeNi = new HashMap<Integer, Double>();
        }

        /**
         * copy constructor
         *
         * @param l
         */
        public EdgeList(EdgeList l) {
            this.NodeNi = new HashMap<Integer, Double>();
            for (int i : l.getKeyL()) {
                addNi(i, l.getW(i));
            }

        }

        /**
         * add a new node to the specific node's neighbor list
         *
         * @param i
         * @param w
         */
        public void addNi(Integer i, Double w) {
            if (i > 0 && w > 0) {
                NodeNi.put(i, w);
            }
        }

        /**
         * get the distance for neighbor
         *
         * @param i
         * @return
         */
        public double getW(int i) {
            if (hasE(i)) {
                return NodeNi.get(i);
            }
            return -1;
        }

        /**
         * check if this node and the other node are neighbor
         *
         * @param i
         * @return
         */
        public boolean hasE(int i) {
            if (NodeNi.containsKey(i)) {
                return true;
            }
            return false;
        }

        /**
         * return a set values for all key that are neighbor for this node
         *
         * @return
         */
        public Set<Integer> getKeyL() {
            return this.NodeNi.keySet();
        }

        /**
         * set the distance for neighbor
         *
         * @param key
         * @param w
         */
        public void setW(int key, double w) {
            NodeNi.put(key, w);
        }

        /**
         * remove node from the specific node's neighbor list
         *
         * @param key
         */
        public void removeE(int key) {
            if (NodeNi.containsKey(key)) {
                NodeNi.remove(key);
            }
        }
    }
}
