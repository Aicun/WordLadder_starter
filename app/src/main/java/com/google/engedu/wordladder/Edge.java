package com.google.engedu.wordladder;

import java.util.Comparator;

/**
 * Created by Aicun on 6/19/2017.
 */

public class Edge implements Comparable<Edge> {

    private Node nodeOne,nodeTwo;
    private int weight;

    public Edge(Node one,Node two) {
        this(one,two,1);
    }

    public Edge(Node one, Node two, int weight){
        this.nodeOne = (one.getLabel().compareTo(two.getLabel()) <= 0) ? one : two;
        this.nodeTwo = (this.nodeOne == one) ? two : one;
        this.weight = weight;
    }

    public Node getNeighbor(Node current){
        if(!(current.equals(nodeOne) || current.equals(nodeTwo))){
            return null;
        }

        return (current.equals(nodeOne)) ? nodeTwo : nodeOne;
    }

    public Node getOne(){
        return this.nodeOne;
    }

    public Node getTwo(){
        return this.nodeTwo;
    }

    public int getWeight(){
        return this.weight;
    }

    public void setWeight(int weight){
        this.weight = weight;
    }

    public String toString(){
        return "({" + nodeOne + ", " + nodeTwo + "}, " + weight + ")";
    }

    @Override
    public int compareTo(Edge edge) {
        return this.weight - edge.weight;
    }

    public int hashCode(){
        return (nodeOne.getLabel() + nodeTwo.getLabel()).hashCode();
    }

    public boolean equals(Object other){
        if(!(other instanceof Edge)){
            return false;
        }
        Edge e = (Edge)other;
        return e.nodeOne.equals(this.nodeOne) && e.nodeTwo.equals(this.nodeTwo);
    }
}
