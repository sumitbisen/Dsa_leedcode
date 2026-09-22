class Solution {

    class Node {
        int prod;
        int[] pref;

        Node(int k) {
            pref = new int[k];
        }
    }

    int k;
    Node[] tree;

    Node merge(Node left, Node right) {
        Node res = new Node(k);

        // Complete segment product
        res.prod = (int)((long)left.prod * right.prod % k);

        // Prefixes completely inside left
        for (int r = 0; r < k; r++) {
            res.pref[r] += left.pref[r];
        }

        // Prefixes which contain all of left
        // and some prefix of right
        for (int r = 0; r < k; r++) {
            if (right.pref[r] == 0) continue;

            int rem = (int)((long)left.prod * r % k);

            res.pref[rem] += right.pref[r];
        }

        return res;
    }

    void build(int node, int l, int r, int[] nums) {

        if (l == r) {
            tree[node] = new Node(k);

            int rem = nums[l] % k;

            tree[node].prod = rem;
            tree[node].pref[rem] = 1;

            return;
        }

        int mid = (l + r) / 2;

        build(node * 2, l, mid, nums);
        build(node * 2 + 1, mid + 1, r, nums);

        tree[node] = merge(tree[node * 2],
                           tree[node * 2 + 1]);
    }

    void update(int node, int l, int r,
                int index, int value) {

        if (l == r) {
            tree[node] = new Node(k);

            int rem = value % k;

            tree[node].prod = rem;
            tree[node].pref[rem] = 1;

            return;
        }

        int mid = (l + r) / 2;

        if (index <= mid) {
            update(node * 2, l, mid, index, value);
        } else {
            update(node * 2 + 1, mid + 1, r, index, value);
        }

        tree[node] = merge(tree[node * 2],
                           tree[node * 2 + 1]);
    }

    Node query(int node, int l, int r,
               int ql, int qr) {

        if (ql <= l && r <= qr) {
            return tree[node];
        }

        int mid = (l + r) / 2;

        if (qr <= mid) {
            return query(node * 2, l, mid, ql, qr);
        }

        if (ql > mid) {
            return query(node * 2 + 1, mid + 1, r, ql, qr);
        }

        Node left = query(node * 2, l, mid, ql, qr);
        Node right = query(node * 2 + 1, mid + 1, r, ql, qr);

        return merge(left, right);
    }

    // IMPORTANT:
    // LeetCode expects this exact method
    public int[] resultArray(int[] nums,
                             int k,
                             int[][] queries) {

        this.k = k;

        int n = nums.length;

        tree = new Node[4 * n];

        // Build
        build(1, 0, n - 1, nums);

        int[] result = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {

            int index = queries[i][0];
            int value = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];

            // Update persists
            update(1, 0, n - 1, index, value);

            // Query range [start ... n-1]
            Node ans = query(
                1, 0, n - 1,
                start, n - 1
            );

            // Number of valid remaining arrays
            result[i] = ans.pref[x];
        }

        return result;
    }
}