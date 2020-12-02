/*
 * Copyright 2019-2119 gao_xianglong@sina.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.Arrays;
import java.util.Objects;

/**
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2020/11/30 10:59 下午
 */
public class RBTree<T> {
    enum Color {
        RED, BLACK;
    }

    static class Node<T> {
        int key;
        T value;
        Color color = Color.RED;
        Node<T> parent, left, right;

        Node(int key, T value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", value=" + value +
                    ", color=" + color +
                    ", parent=" + (Objects.nonNull(parent) ? parent.key : null) +
                    ", left=" + (Objects.nonNull(left) ? left.key : null) +
                    ", right=" + (Objects.nonNull(right) ? right.key : null) +
                    '}';
        }
    }

    /**
     * 根节点
     */
    Node<T> root;

    void insert(int key, T value) {
        var nn = new Node<>(key, value);
        if (Objects.isNull(root)) {
            root = nn;
        } else {
            insert(root, root.parent, nn);
        }
        i_fixup(nn);
    }

    void insert(Node<T> n1, Node<T> n2, Node<T> n3) {
        if (Objects.isNull(n1)) {
            if (n3.key < n2.key) {
                n2.left = n3;
            } else {
                n2.right = n3;
            }
            n3.parent = n2;
        } else {
            insert(n3.key < n1.key ? n1.left : n1.right, n1, n3);
        }
    }

    void i_fixup(Node<T> n) {
        if (root == n) {
            root.color = Color.BLACK;
            return;
        }
        if (Objects.isNull(n) || n.parent.color == Color.BLACK) {
            return;
        } else {
            var parent = n.parent;
            var gandfather = parent.parent;
            if (gandfather.left == parent) {
                var uncle = gandfather.right;
                if (Objects.nonNull(uncle) && uncle.color == Color.RED) {
                    parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    gandfather.color = Color.RED;
                    i_fixup(gandfather);
                } else {
                    if (parent.left == n) {
                        parent.color = Color.BLACK;
                        gandfather.color = Color.RED;
                        rightRotate(gandfather);
                    } else {
                        leftRotate(parent);
                        i_fixup(parent);
                    }
                }
            } else {
                var uncle = gandfather.left;
                if (Objects.nonNull(uncle) && uncle.color == Color.RED) {
                    parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    gandfather.color = Color.RED;
                    i_fixup(gandfather);
                } else {
                    if (parent.right == n) {
                        parent.color = Color.BLACK;
                        gandfather.color = Color.RED;
                        leftRotate(gandfather);
                    } else {
                        rightRotate(parent);
                        i_fixup(parent);
                    }
                }
            }
        }
    }

    void rightRotate(Node<T> n) {
        var left = n.left;
        n.left = left.right;
        if (Objects.nonNull(left.right)) {
            left.right.parent = n;
        }
        left.right = n;
        reset(left, n);
    }

    void leftRotate(Node<T> n) {
        var right = n.right;
        n.right = right.left;
        if (Objects.nonNull(right.left)) {
            right.left.parent = n;
        }
        right.left = n;
        reset(right, n);
    }

    void reset(Node<T> n1, Node<T> n2) {
        if (root == n2) {
            root = n1;
        } else if (n2.parent.left == n2) {
            n2.parent.left = n1;
        } else {
            n2.parent.right = n1;
        }
        n1.parent = n2.parent;
        n2.parent = n1;
    }


    void inOrder() {
        inOrder(root);
    }

    void inOrder(Node<T> n) {
        if (Objects.nonNull(n)) {
            inOrder(n.left);
            System.out.println(n);
            inOrder(n.right);
        }
    }

    Node<T> getNode(int key) {
        if (Objects.nonNull(root)) {
            return root.key == key ? root : getNode(key, root);
        }
        return null;
    }

    Node<T> getNode(int key, Node<T> n) {
        if (Objects.nonNull(n)) {
            if (key == n.key) {
                return n;
            }
            return getNode(key, key < n.key ? n.left : n.right);
        }
        return null;
    }

    Node<T> getSucceedNode(int key) {
        var n = getNode(key);
        if (Objects.nonNull(n)) {
            var sn = getSucceedNode(n.left);
            return Objects.nonNull(sn) ? sn : n.left;
        }
        return null;
    }

    Node<T> getSucceedNode(Node<T> n) {
        if (Objects.nonNull(n)) {
            var sn = getSucceedNode(n.right);
            return Objects.nonNull(sn) ? sn : n;
        }
        return null;
    }

    boolean delete(int key) {
        var n = getNode(key);
        if (Objects.nonNull(n)) {
            delete(n);
            return true;
        }
        return false;
    }

    void delete(Node<T> n) {
        Node<T> n1 = null, n2 = null;
        if (Objects.nonNull(n.left) && Objects.nonNull(n.right)) {
            var sn = getSucceedNode(n.key);
            n.key = sn.key;
            n.value = sn.value;
            n.left.parent = n;
            n.right.parent = n;
            delete(sn);
            return;
        } else {
            if (Objects.isNull(n.parent)) {
                root = Objects.nonNull(n.left) ? n.left : n.right;
                root.parent = null;
                n1 = root;
            } else {
                var c = Objects.nonNull(n.left) ? n.left : n.right;
                if (n.parent.left == n) {
                    n.parent.left = c;
                } else {
                    n.parent.right = c;
                }
                if (Objects.nonNull(c)) {
                    c.parent = n.parent;
                }
                n1 = c;
                n2 = n.parent;
            }
        }
        if (n.color == Color.BLACK) {
            d_fixup(n1, n2);
        }
    }

    void d_fixup(Node<T> n1, Node<T> n2) {
        Node<T> bro = null;
        while ((Objects.isNull(n1) || n1.color == Color.BLACK) && root != n1) {
            if (n2.left == n1) {
                bro = n2.right;
                if (bro.color == Color.RED) {
                    bro.color = Color.BLACK;
                    n2.color = Color.RED;
                    leftRotate(n2);
                    bro = n2.right;
                }
                if ((Objects.isNull(bro.left) || bro.left.color == Color.BLACK) && (Objects.isNull(bro.right) || bro.right.color == Color.BLACK)) {
                    if (n2.color == Color.RED) {
                        n2.color = Color.BLACK;
                        bro.color = Color.RED;
                        break;
                    } else {
                        bro.color = Color.RED;
                        n1 = n2;
                        n2 = n1.parent;
                    }
                } else {
                    if (Objects.nonNull(bro.left) && bro.left.color == Color.RED) {
                        bro.left.color = n2.color;
                        n2.color = Color.BLACK;
                        rightRotate(bro);
                        leftRotate(n2);
                    } else if (Objects.nonNull(bro.right) && bro.right.color == Color.RED) {
                        bro.color = n2.color;
                        n2.color = Color.BLACK;
                        bro.right.color = Color.BLACK;
                        leftRotate(n2);
                    }
                    break;
                }
            } else {
                bro = n2.left;
                if (bro.color == Color.RED) {
                    bro.color = Color.BLACK;
                    n2.color = Color.RED;
                    rightRotate(n2);
                    bro = n2.left;
                }
                if ((Objects.isNull(bro.left) || bro.left.color == Color.BLACK) && (Objects.isNull(bro.right) || bro.right.color == Color.BLACK)) {
                    if (n2.color == Color.RED) {
                        n2.color = Color.BLACK;
                        bro.color = Color.RED;
                        break;
                    } else {
                        bro.color = Color.RED;
                        n1 = n2;
                        n2 = n1.parent;
                    }
                } else {
                    if (Objects.nonNull(bro.right) && bro.right.color == Color.RED) {
                        bro.right.color = n2.color;
                        n2.color = Color.BLACK;
                        leftRotate(bro);
                        rightRotate(n2);
                    } else if (Objects.nonNull(bro.left) && bro.left.color == Color.RED) {
                        bro.color = n2.color;
                        n2.color = Color.BLACK;
                        bro.left.color = Color.BLACK;
                        rightRotate(n2);
                    }
                    break;
                }
            }
        }
        if (Objects.nonNull(n1)) {
            n1.color = Color.BLACK;
        }
    }

    void getBlackHeight() {
        getBlackHeight(root, root.parent, 0);
    }

    void getBlackHeight(Node<T> n1, Node<T> n2, int size) {
        if (Objects.nonNull(n1)) {
            getBlackHeight(n1.left, n1, n1.color == Color.BLACK ? size + 1 : size);
            getBlackHeight(n1.right, n1, n1.color == Color.BLACK ? size + 1 : size);
        } else {
            StringBuffer buf = new StringBuffer();
            while (Objects.nonNull(n2)) {
                buf.append(String.format("%s(%s)->", n2.key, n2.color));
                n2 = n2.parent;
            }
            var temp = buf.toString();
            temp = temp.substring(0, temp.length() - 2);
            System.out.printf("%s\tsize:%s\n", temp, size);
        }
    }

    int getSize() {
        return getSize(root);
    }

    int getSize(Node<T> n) {
        if (Objects.nonNull(n)) {
            return 1 + getSize(n.left) + getSize(n.right);
        }
        return 0;
    }

    int getHeight() {
        return getHeight(root);
    }

    int getHeight(Node<T> n) {
        if (Objects.nonNull(n)) {
            return 1 + Math.max(getHeight(n.left), getHeight(n.right));
        }
        return 0;
    }

    Integer getMin() {
        return getMin(root);
    }

    Integer getMin(Node<T> n) {
        if (Objects.nonNull(n)) {
            Integer result = getMin(n.left);
            return Objects.nonNull(result) ? result : n.key;
        }
        return null;
    }

    Integer getMax() {
        return getMax(root);
    }

    Integer getMax(Node<T> n) {
        if (Objects.nonNull(n)) {
            Integer result = getMax(n.right);
            return Objects.nonNull(result) ? result : n.key;
        }
        return null;
    }

    public static void main(String[] agrs) {
        Integer[] temp = {200, 100, 300, 10, 160, 250, 350, 5, 50, 150, 170, 180, 210};
        RBTree<Integer> tree = new RBTree<>();
        Arrays.asList(temp).forEach(x -> tree.insert(x, x));
        tree.inOrder();
        System.out.printf("min:%s,max:%s,size:%s,height:%s\n", tree.getMin(), tree.getMax(), tree.getSize(), tree.getHeight());
        System.out.printf("node:%s,succee:%s\n", tree.getNode(50), tree.getSucceedNode(10));
        System.out.printf("delete:%s\n", tree.delete(180));
        System.out.printf("delete:%s\n", tree.delete(200));
        System.out.printf("delete:%s\n", tree.delete(160));
        tree.inOrder();
        tree.getBlackHeight();

//        Integer[] temp = {200, 100, 300, 250, 400, 210};
//        RBTree<Integer> tree = new RBTree<>();
//        Arrays.asList(temp).forEach(x -> tree.insert(x, x));
//        tree.delete(210);
//        tree.delete(200);
//        tree.inOrder();
//        tree.getBlackHeight();

//        Integer[] temp = {200, 100, 300, 250};
//        RBTree<Integer> tree = new RBTree<>();
//        Arrays.asList(temp).forEach(x -> tree.insert(x, x));
//        tree.delete(100);
//        tree.inOrder();
//        tree.getBlackHeight();
    }
}
