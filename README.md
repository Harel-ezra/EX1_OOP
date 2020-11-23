EX1 Project

at this project we implement the data structure of unadjusted weighted graph.
we implement a tow class.

WGraph_DS class:
This class present a Graph.
for this class have 2 private class.
nodeInfo and EdgeList.
nodeInfo class present single node. 
the parameter for any node it is key, tag and info.
EdgeList class present the list of node's neighbor.
this class implment with an hashMap that contains node key and the distance for this node.
all the node at the graph sava at HashMap for a fast runtime and direct access for every node at the graph.
and also all the edge sava at another hashMap, for any node we save is edge list.
also, for this class have some methode like get a key, connect tow nodes and creat between them a edge.
remove and add node to the graph and more.
for the private class hava also some method for help the graph to be present well.

WGraph_Algo class:
At this class we have some action and algorithm on the graph.
the complicated method used at Dijkstra algorithm.
the Dijkstra algorithm its an algorithm to find the sorties distance way between tow nodes at the graph.
we used this algo for check if the graph is "connected graph",
that its mean all the nodes at the graph have a path between them, and to check the shortest path between
tow nodes and return the number, and the nodes at the way.
also we can load and save a grpah to a file.

for all this class and method i write a class test that check the property of the code.

 
