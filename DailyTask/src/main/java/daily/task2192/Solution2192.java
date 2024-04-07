package daily.task2192;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * 2192. 有向无环图中一个节点的所有祖先
 */
public class Solution2192 {
    public List<List<Integer>> getAncestors(int n, int[][] edges) {
        Set<Integer>[] anc = new Set[n];   // 存储每个节点祖先的辅助数组
        for (int i = 0; i < n; ++i) {
            anc[i] = new HashSet<Integer>();
        }
        List<Integer>[] e = new List[n];   // 邻接表
        for (int i = 0; i < n; ++i) {
            e[i] = new ArrayList<Integer>();
        }
        int[] indeg = new int[n];   // 入度表：几个节点指向该节点
        // 预处理
        for (int[] edge : edges) {
            // edge[0] --> edge[1]
            e[edge[0]].add(edge[1]);

            // edge[1] 入度++
            ++indeg[edge[1]];
        }

        // 广度优先搜索求解拓扑排序
        Queue<Integer> q = new ArrayDeque<Integer>();
        for (int i = 0; i < n; ++i) {
            if (indeg[i] == 0) {
                // 队列中加入 未被指向的 节点 （入度为0的节点）
                q.offer(i);
            }
        }

        while (!q.isEmpty()) {
            int u = q.poll();
            // u 指向了那个节点
            for (int v : e[u]) {
                // 更新子节点的祖先哈希表
                // u 指向了这些 v 节点
                anc[v].add(u);
                for (int i : anc[u]) {
                    // u 的所有父节点 放入 v 中
                    anc[v].add(i);
                }

                // 拓扑排序
                // v 的入度--
                --indeg[v];
                if (indeg[v] == 0) {
                    q.offer(v);
                }
            }
        }

        // 转化为答案数组
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        for (int i = 0; i < n; ++i) {
            res.add(new ArrayList<Integer>());
            for (int j : anc[i]) {
                res.get(i).add(j);
            }
            // Collections.sort(res.get(i));
        }
        return res;
    }

    public List<Integer> traverse(int n, int[][] edges) {
        ArrayList<Integer> res = new ArrayList<>();
        List<Integer> indegree = new ArrayList<>();
        List<List<Integer>> table = new ArrayList<>();

        // init
        for (int i = 0; i < n; i++) {
            table.add(new ArrayList<>());
            indegree.add(0);
        }

        for (int[] edge : edges) {
            // 邻接表
            table.get(edge[0]).add(edge[1]);

            // 入度
            indegree.set(edge[1], indegree.get(edge[1]) + 1);
        }

        ArrayDeque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < indegree.size(); i++) {
            if (indegree.get(i) == 0) {
                deque.offer(i);
            }
        }

        while (!deque.isEmpty()) {
            int size = deque.size();
            for (int i = 0; i < size; i++) {
                int index = deque.pop();
                res.add(index);

                // 指向的节点入度--
                for (Integer idx : table.get(index)) {
                    indegree.set(idx, indegree.get(idx) - 1);
                    if (indegree.get(idx) == 0) {
                        deque.offer(idx);
                    }
                }
            }
        }

        return res;
    }

    public static void main(String[] args) {
        // 输入：n = 8, edgeList = [[0,3],[0,4],[1,3],[2,4],[2,7],[3,5],[3,6],[3,7],[4,6]]
        // 输出：[[],[],[],[0,1],[0,2],[0,1,3],[0,1,2,3,4],[0,1,2,3]]
        List<Integer> traverse = new Solution2192().traverse(8, new int[][]{
                new int[]{0, 3},
                new int[]{0, 4},
                new int[]{1, 3},
                new int[]{2, 4},
                new int[]{2, 7},
                new int[]{3, 5},
                new int[]{3, 6},
                new int[]{3, 7},
                new int[]{4, 6}
        });

        System.out.println(traverse);
    }
}
