package com.github.data_structure;/*
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

import org.junit.Assert;

import java.io.ObjectStreamException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2020/11/30 10:59 下午
 */
public class RBTree<T> {
    static enum Color {
        RED, BLACK;
    }

    static class Node<T> {
        int key;
        T value;
        Node<T> parent, left, right;
        Color color = Color.RED;

        Node(int key, T value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", value=" + value +
                    ", parent=" + (Objects.nonNull(parent) ? parent.key : null) +
                    ", left=" + (Objects.nonNull(left) ? left.key : null) +
                    ", right=" + (Objects.nonNull(right) ? right.key : null) +
                    ", color=" + color +
                    '}';
        }
    }

    Node<T> root;

    void insert(int key, T value) {
        var n = new Node<T>(key, value);
        if (Objects.isNull(root)) {
            root = n;
        } else {
            insert(root, null, n);
        }
        i_fixup(n);
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
        }
        var parent = n.parent;
        var gand = parent.parent;
        if (gand.left == parent) {
            var uncle = gand.right;
            if (Objects.nonNull(uncle) && uncle.color == Color.RED) {
                gand.color = Color.RED;
                parent.color = Color.BLACK;
                uncle.color = Color.BLACK;
                i_fixup(gand);
            } else {
                if (parent.left == n) {
                    gand.color = Color.RED;
                    parent.color = Color.BLACK;
                    rightRotate(gand);
                } else {
                    leftRotate(parent);
                    i_fixup(parent);
                }
            }
        } else {
            var uncle = gand.left;
            if (Objects.nonNull(uncle) && uncle.color == Color.RED) {
                gand.color = Color.RED;
                parent.color = Color.BLACK;
                uncle.color = Color.BLACK;
                i_fixup(gand);
            } else {
                if (parent.right == n) {
                    gand.color = Color.RED;
                    parent.color = Color.BLACK;
                    leftRotate(gand);
                } else {
                    rightRotate(parent);
                    i_fixup(parent);
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

    Integer getMin() {
        return getMin(root);
    }

    Integer getMin(Node<T> n) {
        if (Objects.nonNull(n)) {
            var rlt = getMin(n.left);
            return Objects.nonNull(rlt) ? rlt : n.key;
        }
        return null;
    }

    Integer getMax() {
        return getMax(root);
    }

    Integer getMax(Node<T> n) {
        if (Objects.nonNull(n)) {
            var rlt = getMax(n.right);
            return Objects.nonNull(rlt) ? rlt : n.key;
        }
        return null;
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

    int getBlackSize() {
        return getBlackSize(root);
    }

    int getBlackSize(Node<T> n) {
        if (Objects.nonNull(n)) {
            return (n.color == Color.BLACK ? 1 : 0) + getBlackSize(n.left) + getBlackSize(n.right);
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

    void getBlackHeight() {
        getBlackHeight(root, null, 0);
    }

    void getBlackHeight(Node<T> n1, Node<T> n2, int size) {
        if (Objects.nonNull(n1)) {
            getBlackHeight(n1.left, n1, n1.color == Color.BLACK ? size + 1 : size);
            getBlackHeight(n1.right, n1, n1.color == Color.BLACK ? size + 1 : size);
        } else {
            var builder = new StringBuilder();
            while (Objects.nonNull(n2)) {
                builder.append(String.format("%s(%s)->", n2.key, n2.color));
                n2 = n2.parent;
            }
            var temp = builder.toString();
            System.out.printf("%s,size:%s\n", temp.substring(0, temp.lastIndexOf("->")), size);
        }
    }

    void inOrder() {
        inOrder(root);
        System.out.println();
    }

    void inOrder(Node<T> n) {
        if (Objects.isNull(n)) {
            return;
        }
        inOrder(n.left);
        System.out.println(n);
        inOrder(n.right);
    }

    Node<T> getNode(int key) {
        if (Objects.nonNull(root)) {
            return key == root.key ? root : getNode(key, root);
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
            return getSucceedNode(n.left);
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
                if (Objects.nonNull(root.parent)) {
                    root.parent = null;
                }
                n1 = root;
            } else {
                var cn = Objects.nonNull(n.left) ? n.left : n.right;
                if (n.parent.left == n) {
                    n.parent.left = cn;
                } else {
                    n.parent.right = cn;
                }
                if (Objects.nonNull(cn)) {
                    cn.parent = n.parent;
                }
                n1 = cn;
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
                if (Objects.nonNull(bro) && bro.color == Color.RED) {
                    n2.color = Color.RED;
                    bro.color = Color.BLACK;
                    leftRotate(n2);
                    bro = n2.right;
                }
                if ((Objects.isNull(bro.left) || bro.left.color == Color.BLACK) &&
                        (Objects.isNull(bro.right) || bro.right.color == Color.BLACK)) {
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
                    } else {
                        bro.color = n2.color;
                        n2.color = Color.BLACK;
                        bro.right.color = Color.BLACK;
                        leftRotate(n2);
                    }
                    break;
                }
            } else {
                bro = n2.left;
                if (Objects.nonNull(bro) && bro.color == Color.RED) {
                    n2.color = Color.RED;
                    bro.color = Color.BLACK;
                    rightRotate(n2);
                    bro = n2.left;
                }
                if ((Objects.isNull(bro.left) || bro.left.color == Color.BLACK) &&
                        (Objects.isNull(bro.right) || bro.right.color == Color.BLACK)) {
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
                    } else {
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

    void clear() {
        if (Objects.nonNull(root)) {
            root = null;
        }
    }

    public static void main(String[] agrs) {
        Integer[] temp = {200, 100, 300, 250, 400, 350};
        var tree = new RBTree<>();
        Arrays.asList(temp).forEach(x -> tree.insert(x, x));
        tree.inOrder();
        tree.getBlackHeight();
        Assert.assertEquals(4, tree.getBlackSize());
        Assert.assertEquals(6, tree.getSize());
        Assert.assertEquals(100, tree.getMin().intValue());
        Assert.assertEquals(400, tree.getMax().intValue());
        Assert.assertEquals(4, tree.getHeight());
        Assert.assertFalse(tree.delete(500));
        Assert.assertTrue(tree.delete(100));
        Assert.assertEquals(300, tree.root.key);
        Assert.assertEquals(200, tree.getMin().intValue());
        Assert.assertEquals(3, tree.getHeight());
        Assert.assertTrue(tree.delete(250));
        Assert.assertTrue(tree.delete(300));
        Assert.assertTrue(tree.delete(200));
        Assert.assertEquals(2, tree.getHeight());
        Assert.assertEquals(Color.RED, tree.root.right.color);
        Assert.assertEquals(1, tree.getBlackSize());

        tree.clear();
        Integer[] temp2 = {200, 100, 400, 250, 500};
        Arrays.asList(temp2).forEach(x -> tree.insert(x, x));
        Assert.assertEquals(3, tree.getBlackSize());
        tree.inOrder();
        tree.getBlackHeight();
        Assert.assertEquals(250, tree.getSucceedNode(400).key);
        Assert.assertTrue(tree.delete(250));
        Assert.assertTrue(tree.delete(500));
        tree.inOrder();
        Assert.assertEquals(2, tree.getHeight());
        Assert.assertTrue(tree.delete(400));
        Assert.assertEquals(2, tree.getSize());
        Assert.assertEquals(1, tree.getBlackSize());
        tree.inOrder();
        tree.getBlackHeight();
    }
}