package graapphhss;

import java.util.HashMap;
import java.util.PriorityQueue;

public class Graph1 {

    private static class Edge implements Comparable<Edge> {
        int u;
        int v;
        int weight;

        Edge(int u, int v, int weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }

        @Override

        public int compareTo(Edge o) {
            return this.weight - o.weight;
        }
    }

    private static class DSNode {
        int vtex;
        DSNode parent;
        int rank;

        DSNode(int vtex) {
            this.vtex = vtex;
            this.parent = this;
            this.rank = 1;
        }
    }

    private static DSNode find(DSNode vnode) {
        if (vnode.parent == vnode) {
            return vnode;
        } else {
            DSNode pnode = find(vnode.parent);
            vnode.parent = pnode;
            return pnode;
        }

    }

    private static void merge(DSNode ulead, DSNode vlead) {
        if (ulead.rank > vlead.rank) {
            vlead.parent = ulead;
        } else if (ulead.rank < vlead.rank) {
            ulead.parent = vlead;
        } else {
            ulead.parent = vlead;
            vlead.rank++;
        }
    }

    public static Integer[][] kruskal(Integer[][] graph) {
        Integer[][] mst = new Integer[graph.length][graph.length];
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        HashMap<Integer, DSNode> djset = new HashMap<>();

        for (int i = 0; i < graph.length; i++) {
            djset.put(i, new DSNode(i));
            for (int j = 0; j < graph.length; j++) {
                if (graph[i][j] != null) {
                    Edge e = new Edge(i, j, graph[i][j]);
                    pq.add(e);
                }

            }
        }

        while (pq.size() > 0) {
            Edge rem = pq.remove();

            DSNode ulead = find(djset.get(rem.u));
            DSNode vlead = find(djset.get(rem.v));

            if (ulead != vlead) {
                mst[rem.u][rem.v] = rem.weight;
                merge(ulead, vlead);
            }
        }

        return mst;
    }

    public static void main(String[] args) {
        Integer graph[][] = new Integer[6][6];

        graph[0][1] = 2;
        graph[0][2] = 3;
        graph[1][3] = 5;
        graph[2][3] = 1;

        graph[2][4] = 3;
        graph[4][5] = 7;
        graph[3][5] = 2;

        Integer[][] mst = kruskal(graph);
        System.out.println("--- Zero for No path ---");
        for (int s = 0; s < mst.length; s++) {
            for (int d = 0; d < mst.length; d++) {
                System.out.print((mst[s][d] == null ? 0 : mst[s][d]) + "\t\t");
            }
            System.out.println();
        }
    }

}
