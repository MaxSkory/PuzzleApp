package dev.mskory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Graph {
    private Set<Node> graph;
    private Node nodeWithLongestChain;

    public Graph(List<String> list) {
        fill(list);
    }

    public void fill(List<String> list) {
        graph = list.stream().map(Node::new).collect(Collectors.toSet());
        for (Node node : graph) {
            for (Node adj : graph) {
                if (node.isAdjacent(adj)) {
                    node.addAdjacentNode(adj);
                }
            }
        }
    }

    public int getLongestChainLength() {
        if (nodeWithLongestChain == null) {
            App.LOGGER.info("Longest distances aren't set!");
            App.LOGGER.info("Starting to determine the longest distance for each Node.");
            fillLongestDistance();
            setNodeWithLongestChain();
        }
        return nodeWithLongestChain.getLongestDistance();
    }

    public String getLongestChain() {
        if (nodeWithLongestChain == null) {
            fillLongestDistance();
        }
        return getLongestChain(nodeWithLongestChain, "");
    }

    private String getLongestChain(Node node, String prevChain) {
        if (node.isVisited()) {
            return prevChain;
        }
        node.setVisited(true);
        List<String> possibleChains = new ArrayList<>(node.getAdjacentNodes().size());
        prevChain += " " + node.getName();
        for (Node adjNode : node.getAdjacentNodes()) {
            String chain = getLongestChain(adjNode, prevChain);
            possibleChains.add(chain);
        }
        node.setVisited(false);
        return possibleChains.stream().max(Comparator.comparingInt(String::length)).orElse("");
    }

    private void fillLongestDistance() {
        for (Node node : graph) {
            App.LOGGER.info("Searching longest distance from " + node.name);
            int longestDistance = fillLongestDistance(node, 0);
            node.setLongestDistance(longestDistance);
        }
        setNodeWithLongestChain();
    }

    private int fillLongestDistance(Node node, int prevCount) {
        if (node.isVisited()) {
            return prevCount;
        }
        int count = ++prevCount;
        node.setVisited(true);
        for (Node adjNode : node.getAdjacentNodes()) {
            int nextCount = fillLongestDistance(adjNode, prevCount);
            count = Math.max(count, nextCount);
        }
        node.setVisited(false);
        return Math.max(prevCount, count);
    }

    private void setNodeWithLongestChain() {
        this.nodeWithLongestChain = graph.stream()
                .max(Comparator.comparing(Node::getLongestDistance))
                .filter(n -> n.longestDistance > 1)
                .orElseThrow(() -> new RuntimeException("There is no chains in the graph"));
    }

    private static class Node {
        private final String name;
        private final List<Node> adjacentNodes;
        private boolean visited;
        private int longestDistance;

        public Node(String name) {
            this.name = name;
            visited = false;
            adjacentNodes = new ArrayList<>();
        }

        public boolean isVisited() {
            return visited;
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }

        public String getName() {
            return name;
        }

        public int getLongestDistance() {
            return longestDistance;
        }

        public void setLongestDistance(int longestDistance) {
            this.longestDistance = longestDistance;
        }

        public List<Node> getAdjacentNodes() {
            return adjacentNodes;
        }

        public void addAdjacentNode(Node node) {
            adjacentNodes.add(node);
        }

        private boolean isAdjacent(Node node) {
            return this.name.endsWith(node.getName().substring(0, 2));
        }
    }
}
