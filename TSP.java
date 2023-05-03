import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TSP {
    private int numNodes;
    private ArrayList<Node> nodes;

    public TSP(String dosyaAdi) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(dosyaAdi));
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

    public void calistir() {
        ArrayList<Integer> tur = enYakin();
        ArrayList<Integer> optimalSira = new ArrayList<Integer>(tur);
        double optimalMaliyet = maliyetHesapla(optimalSira);
        System.out.println("Optimal cozum sirasi: " + optimalSira);
        System.out.println("Optimal Maliyet: " + optimalMaliyet);
    }

          private class Node {
        public double x;
        public double y;

        public Node(double x, double y) {
        this.x = x;
        this.y = y;
        }
    }
    
    private ArrayList<Integer> enYakin() {
        ArrayList<Integer> tur = new ArrayList<Integer>();
        boolean[] ugra = new boolean[this.numNodes];
        int mevcutNode = 0;
        ugra[mevcutNode] = true;
        tur.add(mevcutNode);
        while (tur.size() < this.numNodes) {
            double minMesafe = Double.MAX_VALUE;
            int enYakinNode = -1;
            for (int i = 0; i < this.numNodes; i++) {
                if (!ugra[i]) {
                    double M = mesafe(this.nodes.get(mevcutNode), this.nodes.get(i));
                    if (M < minMesafe) {
                        minMesafe = M;
                        enYakinNode = i;
                    }
                }
            }
            ugra[enYakinNode] = true;
            tur.add(enYakinNode);
            mevcutNode = enYakinNode;
        }
        return tur;
    }

       private double mesafe(Node n1, Node n2) {
        double xdiff = n1.x - n2.x;
        double ydiff = n1.y - n2.y;
        double mesf = Math.sqrt(xdiff * xdiff + ydiff * ydiff);
        return mesf;
    }
    
    private double maliyetHesapla(ArrayList<Integer> tur) {
        double maliyet = 0;
        for (int i = 0; i < tur.size() - 1; i++) {
            int node1 = tur.get(i);
            int node2 = tur.get(i + 1);
            Node n1 = this.nodes.get(node1);
            Node n2 = this.nodes.get(node2);
            maliyet += mesafe(n1, n2);
        }
        int ilkIndex = tur.get(0);
        int sonIndex = tur.get(tur.size() - 1);
        Node ilkNode = this.nodes.get(ilkIndex);
        Node sonNode = this.nodes.get(sonIndex);
        maliyet += mesafe(sonNode, ilkNode);
        return maliyet;
    }

    public static void main(String[] args) {
        String[] dosyalar = {"tsp_5.txt", "tsp_124.txt", "tsp_1000.txt", "tsp_5915.txt", "tsp_11849.txt", "tsp_85900.txt"};
        for (String dosyaAdi : dosyalar) {
        long start = System.currentTimeMillis();
        System.out.println("" + dosyaAdi +" "+ "Dosyasi isleniyor...");
        TSP tsp = new TSP(dosyaAdi);
        tsp.calistir();
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("Harcanan sure(ms)"+" "+ timeElapsed);
        System.out.println();
        }
        }
}