package com.google.engedu.wordladder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Aicun on 6/19/2017.
 */

public class Graph {

    private HashMap<String, Node> vertices;
    private HashMap<Integer, Edge> edges;

    public Graph(){
        this.vertices = new HashMap<String, Node>();
        this.edges = new HashMap<Integer, Edge>();
    }

    public Graph(HashSet<Node> vertices){
        this.vertices = new HashMap<String, Node>();
        this.edges = new HashMap<Integer, Edge>();

        for(Node v: vertices){
            this.vertices.put(v.getLabel(), v);
        }
    }

    public boolean addEdge(Node one, Node two){
        return addEdge(one, two, 1);
    }


    public boolean addEdge(Node one, Node two, int weight){
        if(one.equals(two)){
            return false;
        }

        //ensures the Edge is not in the Graph
        Edge e = new Edge(one, two, weight);
        if(edges.containsKey(e.hashCode())){
            return false;
        }

        //and that the Edge isn't already incident to one of the vertices
        else if(one.containsNeighbor(e) || two.containsNeighbor(e)){
            return false;
        }

        edges.put(e.hashCode(), e);
        one.addNeighbor(e);
        two.addNeighbor(e);
        return true;
    }

    public boolean containsEdge(Edge e){
        if(e.getOne() == null || e.getTwo() == null){
            return false;
        }

        return this.edges.containsKey(e.hashCode());
    }


    public Edge removeEdge(Edge e){
        e.getOne().removeNeighbor(e);
        e.getTwo().removeNeighbor(e);
        return this.edges.remove(e.hashCode());
    }

    public boolean containsNode(Node Node){
        return this.vertices.get(Node.getLabel()) != null;
    }

    public Node getNode(String label){
        return vertices.get(label);
    }

    public boolean addNode(Node Node, boolean overwriteExisting){
        Node current = this.vertices.get(Node.getLabel());
        if(current != null){
            if(!overwriteExisting){
                return false;
            }

            while(current.getNeighborCount() > 0){
                this.removeEdge(current.getNeighbor(0));
            }
        }
        vertices.put(Node.getLabel(), Node);
        return true;
    }

    public Node removeNode(String label){
        Node v = vertices.remove(label);

        while(v.getNeighborCount() > 0){
            this.removeEdge(v.getNeighbor((0)));
        }

        return v;
    }

    public Set<String> NodeKeys(){
        return this.vertices.keySet();
    }

    public Set<Edge> getEdges(){
        return new HashSet<Edge>(this.edges.values());
    }
}
