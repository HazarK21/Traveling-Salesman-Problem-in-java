import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TSP {
    private int numNodes;
    private ArrayList<Node> nodes;

    public TSP(String fileName) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line = br.readLine();
            this.numNodes = Integer.parseInt(line);
            this.nodes = new ArrayList<Node>(this.numNodes);
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                double x = Double.parseDouble(parts[0]);
                double y = Double.parseDouble(parts[1]);
                Node node = new Node(x, y);
                this.nodes.add(node);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        ArrayList<Integer> tour = nearest();
        ArrayList<Integer> optimalOrder = new ArrayList<Integer>(tour);
        double optimalCost = calculateCost(optimalOrder);
        System.out.println("Optimal order: " + optimalOrder);
        System.out.println("Optimal Cost: " + optimalCost);
    }

          private class Node {
        public double x;
        public double y;

        public Node(double x, double y) {
        this.x = x;
        this.y = y;
        }
    }
    
    private ArrayList<Integer> nearest() {
        ArrayList<Integer> tour = new ArrayList<Integer>();
        boolean[] visit = new boolean[this.numNodes];
        int currentNode = 0;
        visit[currentNode] = true;
        tour.add(currentNode);
        while (tour.size() < this.numNodes) {
            double minDistance = Double.MAX_VALUE;
            int nearestNode = -1;
            for (int i = 0; i < this.numNodes; i++) {
                if (!visit[i]) {
                    double M = distance(this.nodes.get(currentNode), this.nodes.get(i));
                    if (M < minDistance) {
                        minDistance = M;
                        nearestNode = i;
                    }
                }
            }
            visit[nearestNode] = true;
            tour.add(nearestNode);
            currentNode = nearestNode;
        }
        return tour;
    }

       private double distance(Node n1, Node n2) {
        double xdiff = n1.x - n2.x;
        double ydiff = n1.y - n2.y;
        double mesf = Math.sqrt(xdiff * xdiff + ydiff * ydiff);
        return mesf;
    }
    
    private double calculateCost(ArrayList<Integer> tour) {
        double cost = 0;
        for (int i = 0; i < tour.size() - 1; i++) {
            int node1 = tour.get(i);
            int node2 = tour.get(i + 1);
            Node n1 = this.nodes.get(node1);
            Node n2 = this.nodes.get(node2);
            cost += distance(n1, n2);
        }
        int firstIndex = tour.get(0);
        int lastIndex = tour.get(tour.size() - 1);
        Node firstNode = this.nodes.get(firstIndex);
        Node lastNode = this.nodes.get(lastIndex);
        cost += distance(lastNode, firstNode);
        return cost;
    }

    public static void main(String[] args) {
        String[] files = {"tsp_5.txt", "tsp_124.txt", "tsp_1000.txt", "tsp_5915.txt", "tsp_11849.txt", "tsp_85900.txt"};
        for (String fileName : files) {
        long start = System.currentTimeMillis();
        System.out.println("" + fileName +" "+ "Reading file...");
        TSP tsp = new TSP(fileName);
        tsp.run();
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("Time elapsed(ms)"+" "+ timeElapsed);
        System.out.println();
        }
        }
}
