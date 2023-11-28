package HW4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;

import javax.swing.tree.TreeNode;

public class BinaryTree implements TreeNode, TreeStructure {
    private Integer value;
    private BinaryTree left;
    private BinaryTree right;
    private long timeStamp;


    public BinaryTree() {
        this.value = null;
        this.left = null;
        this.right = null;
        this.timeStamp = System.nanoTime();
    }

    public BinaryTree(Integer value) {
        this.value = value;
        this.left = null;
        this.right = null;
        this.timeStamp = System.nanoTime();
    }


    public void insert(Integer num) {
        if (num < value) {
            if (left == null) {
                left = new BinaryTree(num);
            } else {
                left.insert(num);
            }
        } else if (num > value) {
            if (right == null) {
                right = new BinaryTree(num);
            } else {
                right.insert(num);
            }
        }
    }

    public Boolean remove(Integer num) {
        BinaryTree parent = null;
        BinaryTree current = this;
        boolean isLeftChild = false;

        while (current != null && !current.value.equals(num)) {
            parent = current;
            if (num < current.value) {
                current = current.left;
                isLeftChild = true;
            } else {
                current = current.right;
                isLeftChild = false;
            }
        }

        if (current == null) {
            return false;
        }

        if (current.left == null && current.right == null) {
            if (parent == null) {
                value = null;
            } else {
                if (isLeftChild) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
            }
        }
        else if (current.right == null) {
            if (parent == null) {
                value = current.left.value;
                left = current.left.left;
                right = current.left.right;
            } else {
                if (isLeftChild) {
                    parent.left = current.left;
                } else {
                    parent.right = current.left;
                }
            }
        } else if (current.left == null) {
            if (parent == null) {
                value = current.right.value;
                left = current.right.left;
                right = current.right.right;
            } else {
                if (isLeftChild) {
                    parent.left = current.right;
                } else {
                    parent.right = current.right;
                }
            }
        }
        else {
            BinaryTree successor = findMin(current.right);
            current.remove(successor.value);
            current.value = successor.value;
        }

        return true;
    }

    private BinaryTree findMin(BinaryTree node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }


    public Long get(Integer num) {
        BinaryTree node = findNode(num);
        return (node != null) ? node.value.longValue() : null;
    }

    public Integer findMaxDepth() {
        return findMaxDepth(this);
    }

    public Integer findMinDepth() {
        return findMinDepth(this);
    }

    private BinaryTree findNode(Integer num) {
        if (num.equals(value)) {
            return this;
        } else if (num < value && left != null) {
            return left.findNode(num);
        } else if (num > value && right != null) {
            return right.findNode(num);
        }
        return null;
    }

    private static int findMaxDepth(BinaryTree node) {
        if (node == null) {
            return 0;
        }

        int leftDepth = findMaxDepth(node.left);
        int rightDepth = findMaxDepth(node.right);

        return Math.max(leftDepth, rightDepth) + 1;
    }

    private static int findMinDepth(BinaryTree node) {
        if (node == null) {
            return 0;
        }

        int leftDepth = findMinDepth(node.left);
        int rightDepth = findMinDepth(node.right);

        return Math.min(leftDepth, rightDepth) + 1;
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return (childIndex == 0) ? left : right;
    }

    @Override
    public int getChildCount() {
        int count = 0;
        if (left != null) count++;
        if (right != null) count++;
        return count;
    }

    @Override
    public TreeNode getParent() { // Unused method, but needed to implement
        return null;
    }

    @Override
    public int getIndex(TreeNode node) {
        return (node == left) ? 0 : (node == right) ? 1 : -1;
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public boolean isLeaf() {
        return (left == null && right == null);
    }

    @Override
    public Enumeration<TreeNode> children() {
        List<TreeNode> children = new ArrayList<>();
        if (left != null) children.add(left);
        if (right != null) children.add(right);
        return Collections.enumeration(children);
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {         
        File file = new File(args[0]);  
        FileReader fReader = new FileReader(file);  
        BufferedReader bufferedReader = new BufferedReader(fReader);  
        TreeStructure tree = new BinaryTree();  
        Random rng = new Random(42);  
        ArrayList<Integer> nodesToRemove = new ArrayList<>();  
        ArrayList<Integer> nodesToGet = new ArrayList<>();  
        String line = bufferedReader.readLine();         
        while (line != null) {  
            Integer lineInt = Integer.parseInt(line);             
            tree.insert(lineInt);  
            Integer rand = rng.nextInt(10);  
            if (rand < 5) nodesToRemove.add(lineInt);             
            else if (rand >= 5) nodesToGet.add(lineInt);             
                line = bufferedReader.readLine();  
        }  
        bufferedReader.close();         
        for (int i = 0; i < 10; i++) {  
            System.out.println(nodesToGet.get(i) + " inserted at " + tree.get(nodesToGet.get(i)));  
        }  
        System.out.println("Max depth: " + tree.findMaxDepth());         
        System.out.println("Min depth: " + tree.findMinDepth());         
        for (Integer num : nodesToRemove) {             
            tree.remove(num);  
        }  
        for (int i = 0; i < 10; i++) {  
            System.out.println(nodesToGet.get(i) + " inserted at " + tree.get(nodesToGet.get(i)));  
        }  
        System.out.println("Max depth: " + tree.findMaxDepth());  
        System.out.println("Min depth: " + tree.findMinDepth());  
    }
}