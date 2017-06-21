package com.google.engedu.wordladder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aicun on 6/19/2017.
 */

public class Node {
    private String label;
    private List<Edge> neghbours;

    public Node(String label) {
        this.label = label;
        this.neghbours = new ArrayList<Edge>();
    }

    public void addNeighbor(Edge edge){
        if(this.neghbours.contains(edge)){
            return;
        }
        this.neghbours.add(edge);
    }

    public boolean containsNeighbor(Edge other){
        return this.neghbours.contains(other);
    }

    public Edge getNeighbor(int index){
        return this.neghbours.get(index);
    }

    Edge removeNeighbor(int index){
        return this.neghbours.remove(index);
    }

    public void removeNeighbor(Edge e){
        this.neghbours.remove(e);
    }

    public int getNeighborCount(){
        return this.neghbours.size();
    }

    public String getLabel(){
        return this.label;
    }

    public String toString(){
        return "Node " + label;
    }

    public int hashCode(){
        return this.label.hashCode();
    }

    public boolean equals(Object other){
        if(!(other instanceof Node)){
            return false;
        }

        Node v = (Node)other;
        return this.label.equals(v.label);
    }

    public ArrayList<Edge> getNeighbors(){
        return new ArrayList<Edge>(this.neghbours);
    }

}
