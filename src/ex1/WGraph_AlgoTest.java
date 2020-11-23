package ex1;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoTest {
    /**
     * test for init method.
     *
     */
    @Test
    void init() {
        weighted_graph g = new WGraph_DS();
        weighted_graph g1 = new WGraph_DS();
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g);
        assertTrue(ga.getGraph().equals(g1));
        ga.init(g1);
        g = WGraph_DSTest.graph_creator(50, 100, 1);
        g1 = WGraph_DSTest.graph_creator(50, 100, 1);
        assertFalse(ga.getGraph().equals(g1));
        ga.init(g1);
        assertTrue(ga.getGraph().equals(g1));
    }

    /**
     * test for getGraph method.
     */
    @Test
    void getGraph() {
        weighted_graph g = WGraph_DSTest.graph_creator(50000, 100000, 1);
        weighted_graph g1 = WGraph_DSTest.graph_creator(50000, 100000, 1);
        weighted_graph_algorithms ga = new WGraph_Algo();
        assertFalse(ga.getGraph().equals(g1));
        ga.init(g1);
        assertTrue(ga.getGraph().equals(g1));
    }

    @Test
    void copy() {
        weighted_graph g = WGraph_DSTest.graph_creator(1000000, 10000000, 1);
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g);
        weighted_graph g1 = ga.copy();
        assertTrue(g.equals(g1));
    }

    /**
     * test for isConnected method.
     * creat some different graph and check if it done
     */
    @Test
    void isConnected() {
        weighted_graph g = WGraph_DSTest.graph_creator(0, 0, 1);
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g);
        assertTrue(ga.isConnected());

        g = WGraph_DSTest.graph_creator(1, 0, 1);
        ga.init(g);
        assertTrue(ga.isConnected());

        g = WGraph_DSTest.graph_creator(10, 45, 1);
        ga.init(g);
        assertTrue(ga.isConnected());

        g = WGraph_DSTest.graph_creator(10, 8, 1);
        ga.init(g);
        assertFalse(ga.isConnected());

        g = WGraph_DSTest.graph_creator(100, 4950, 1);
        ga.init(g);
        assertTrue(ga.isConnected());

        g = WGraph_DSTest.graph_creator(10, 0, 1);
        g.connect(0, 1, 1);
        g.connect(0, 2, 1);
        g.connect(0, 3, 1);
        g.connect(3, 5, 1);
        g.connect(4, 5, 1);
        g.connect(5, 6, 1);
        g.connect(5, 7, 1);
        g.connect(5, 8, 1);
        g.connect(0, 9, 1);
        ga.init(g);
        assertTrue(ga.isConnected());
        ga.getGraph().removeEdge(9, 0);
        assertFalse(ga.isConnected());
    }

    /**
     * test for shortestPathDist method.
     * creat graph and check if it done
     */
    @Test
    void shortestPathDist() {
        weighted_graph_algorithms ga=newEyseG();
        double path=ga.shortestPathDist(0,0);
        assertEquals(0,path);

        path=ga.shortestPathDist(0,1);
        assertEquals(1,path);
        path=ga.shortestPathDist(0,3);
        assertEquals(0.2,path);
        path=ga.shortestPathDist(3,0);
        assertEquals(0.2,path);
        ga.getGraph().removeEdge(0,2);
        path=ga.shortestPathDist(10,2);
        assertEquals(4.132,path);
        path=ga.shortestPathDist(3,4);
        assertEquals(1.8,path);
        path=ga.shortestPathDist(2,5);
        assertEquals(3.1,path);

        ga.getGraph().removeEdge(10,9);
        path=ga.shortestPathDist(10,2);
        assertNotEquals(4.132,path);
        assertEquals(9.1,path);

        ga.getGraph().connect(2,0,1);
        path=ga.shortestPathDist(2,5);
        assertEquals(2.5,path);
        path=ga.shortestPathDist(10,2);
        assertEquals(8.5,path);

        path=ga.shortestPathDist(7,4);
        assertEquals(7.1,path);
        ga.getGraph().connect(5,4,15);
        path=ga.shortestPathDist(7,4);
        assertEquals(12.5,path);

        ga.getGraph().connect(10,7,0.5);
        path=ga.shortestPathDist(7,4);
        assertEquals(10.5,path);

        ga.getGraph().removeEdge(10,7);
        ga.getGraph().removeEdge(6,7);
        path=ga.shortestPathDist(7,4);
        assertEquals(-1,path);
    }

    /**
     * test for shortestPath method.
     * creat graph and check if it done
     */
    @Test
    void shortestPath() {
        weighted_graph_algorithms ga=newEyseG();
        List<node_info> path=ga.shortestPath(0,0);
        assertEquals(1,path.size());
        assertTrue(path.get(0).equals(ga.getGraph().getNode(0)));

        path=ga.shortestPath(3,0);
        assertEquals(2,path.size());
        assertTrue(path.get(1).equals(ga.getGraph().getNode(0)));

        path=ga.shortestPath(3,4);
        assertEquals(5,path.size());
        assertTrue(path.get(2).equals(ga.getGraph().getNode(1)));

        path=ga.shortestPath(4,3);
        assertEquals(5,path.size());
        assertTrue(path.get(3).equals(ga.getGraph().getNode(0)));

        path=ga.shortestPath(9,6);
        assertEquals(4,path.size());
        assertTrue(path.get(3).equals(ga.getGraph().getNode(6)));

        path=ga.shortestPath(10,0);
        assertEquals(6,path.size());
        double w=ga.shortestPathDist(10,0);
        assertEquals(2.732,w);
        assertTrue(path.get(0).equals(ga.getGraph().getNode(10)));
        assertTrue(path.get(1).equals(ga.getGraph().getNode(9)));
        assertTrue(path.get(2).equals(ga.getGraph().getNode(4)));
        assertTrue(path.get(3).equals(ga.getGraph().getNode(5)));
        assertTrue(path.get(4).equals(ga.getGraph().getNode(1)));
        assertTrue(path.get(5).equals(ga.getGraph().getNode(0)));


        ga.getGraph().removeNode(4);
        path=ga.shortestPath(5,9);
        assertEquals(4,path.size());
        assertTrue(path.get(2).equals(ga.getGraph().getNode(10)));
        ga.getGraph().connect(9,3,1);
        path=ga.shortestPath(5,9);
        assertEquals(5,path.size());
        assertTrue(path.get(2).equals(ga.getGraph().getNode(0)));

    }

    /**
     * test for save and load
     * write and read some graph and cehck if the are equals
     */

    @Test
    void save_load() {
        weighted_graph g = WGraph_DSTest.graph_creator(100, 300, 1);
        weighted_graph_algorithms ga = new WGraph_Algo();
        String s="test";
        ga.init(g);
        ga.save(s);
        ga.load(s);
        assertTrue(g.equals( ga.getGraph()));

        g = WGraph_DSTest.graph_creator(1000, 1000, 1);
        s="test2";
        ga.init(g);
        ga.save(s);
        weighted_graph_algorithms ga1 = new WGraph_Algo();
        weighted_graph g1 =new WGraph_DS();
        ga1.init(g1);
        ga1.load(s);
        ga1.save("test3");
        assertTrue(g.equals( ga.getGraph()));
        assertTrue(ga1.getGraph().equals( ga.getGraph()));// line 78 key id 20 or 36

    }

    /**
     * mehod to creat a simple graph
     * @return
     */
    private weighted_graph_algorithms newEyseG()
    {
        weighted_graph g=new WGraph_DS();
        weighted_graph_algorithms ga=new WGraph_Algo();
        for (int i=0;i<=10;i++)
        {
            g.addNode(i);
        }
        g.connect(0,1 ,1);
        g.connect(0,2,1);
        g.connect(0,3,0.2);
        g.connect(1,5,0.5);
        g.connect(1,6,6);
        g.connect(2,4,3);
        g.connect(6,5,2);
        g.connect(2,3,5);
        g.connect(4,5,0.1);
        g.connect(4,9,0.132);
        g.connect(4,10,10);
        g.connect(9,10,1);
        g.connect(10,8,2);
        g.connect(8,5,4);
        g.connect(6,7,5);
        ga.init(g);
        return ga;
    }


}