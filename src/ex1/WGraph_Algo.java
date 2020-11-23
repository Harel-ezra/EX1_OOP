package ex1;

import java.io.*;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms {
    private weighted_graph grAl;

    public WGraph_Algo()
    {
        this.grAl=new WGraph_DS();
    }

    public WGraph_Algo (WGraph_DS g)
    {
        this.grAl=g;
    }


    /**
     * copy constructor
     */
    public WGraph_Algo(weighted_graph_algorithms g) {
        this.grAl = g.getGraph();
    }

    @Override
    public void init(weighted_graph g) {
        this.grAl = g;
    }

    @Override
    public weighted_graph getGraph() {
        return this.grAl;
    }

    @Override
    public weighted_graph copy() {
        if (grAl != null) {
            weighted_graph gCopy = new WGraph_DS(this.grAl);
            return gCopy;
        }
        return null;
    }

    @Override
    public boolean isConnected() {
        if (grAl.nodeSize() <= 1) {
            return true;
        }
        node_info n = grAl.getV().iterator().next();
        HashMap<Integer, Integer> temp = Dijkstra(n.getKey());
        cleanNodeDetails();
        if (temp.size() == grAl.nodeSize()) {
            return true;
        }
        return false;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        if (grAl.getNode(src) != null && grAl.getNode(dest) != null) {
            Dijkstra(src);
            double path = grAl.getNode(dest).getTag();
            cleanNodeDetails();
            return path;
        }
        return -1;
    }

    @Override
    public List<node_info> shortestPath(int src, int dest) {
        List<node_info> path = new LinkedList<node_info>();
        if (grAl.getNode(src) != null && grAl.getNode(dest) != null) {
            HashMap<Integer, Integer> tempH = Dijkstra(src);
            int i = dest;
            path.add(0, grAl.getNode(dest));
            while (i != src) {
                int k = tempH.get(i); // get the father of the currently node
                path.add(0, grAl.getNode(k));
                i = k;
            }
            cleanNodeDetails();
        }
        return path;
    }

    @Override
    public boolean save(String file)
    {
        try {
            PrintWriter gFile=new PrintWriter(new File(file));
            StringBuilder sb=new StringBuilder();

            for(node_info i:grAl.getV())
            {
                gFile.write(i.getKey()+"\n");
            }
            gFile.write("!"+"\n");

            for(node_info i:grAl.getV())
            {
                sb.append(i.getKey());
                sb.append(",");

                for(node_info j:grAl.getV(i.getKey()))
                {
                    sb.append(j.getKey());
                    sb.append(",");
                    sb.append(grAl.getEdge(i.getKey(),j.getKey()));
                    sb.append(",");
                }
                sb.append("\n");
                gFile.write(sb.toString());
                sb.setLength(0);
                gFile.close();
            }
            return true;
        }
            catch (FileNotFoundException e)
            {
                System.out.println("can't write the graph to a file ");
                e.printStackTrace();
            }
        return false;
    }

    @Override
    public boolean load(String file) {
        String line;
        try
        {
            BufferedReader gFile=new BufferedReader(new FileReader(file));
            line=gFile.readLine();
            while(!line.equals("!"))
            {
                int i=Integer.parseInt(line);
                grAl.addNode(i);
                line=gFile.readLine();
            }
            while ((line=gFile.readLine())!=null)
            {
                String[] s=line.split(",");
                for(int i=1;i<s.length-1;i+=2)
                {
                    int n=Integer.parseInt(s[0]);
                    int k=Integer.parseInt(s[i]);
                    double w=Double.parseDouble(s[i+1]);
                    grAl.connect(n,k,w);
                }
            }
            return true;

        }
        catch (IOException e)
        {
            System.out.println("can't read the graph from the file");
            e.printStackTrace();
        }
        return false;
    }

    private HashMap<Integer, Integer> Dijkstra(int src) {
        Queue<node_info> queue = new PriorityQueue<node_info>();
        HashMap<Integer, Integer> hash = new HashMap<Integer, Integer>(); // hash for father key.
        hash.put(src, null);
        grAl.getNode(src).setTag(0);
        queue.add(grAl.getNode(src));
        double w;
        while (!queue.isEmpty()) {
            node_info n = queue.remove();
            if(n.getInfo().equals("x"))
            {
                for (node_info i : grAl.getV(n.getKey()))
                {
                    if (i.getInfo().equals("x"))
                    {
                        w=n.getTag() + grAl.getEdge(n.getKey(), i.getKey());
                        if (i.getTag() < 0 || w < i.getTag())
                        {
                            i.setTag(w);
                            queue.add(i);
                            hash.put(i.getKey(), n.getKey());
                        }
                    }
                }
                n.setInfo("v");
            }
        }
        return hash;
    }

    /**
     * clean all node detils
     */
    private void cleanNodeDetails ()
    {
        for (node_info n : grAl.getV()) {
            n.setInfo("x");
            n.setTag(-1);
        }
    }
}

