package edu.emory.cs.tree.balanced;

import edu.emory.cs.tree.BinaryNode;
import edu.emory.cs.tree.balanced.AbstractBalancedBinarySearchTree;

/** @author Jinho D. Choi */
public class BalancedBinarySearchTreeQuiz<T extends Comparable<T>> extends AbstractBalancedBinarySearchTree<T, BinaryNode<T>> {
    @Override
    public BinaryNode<T> createNode(T key) {
        return new BinaryNode<>(key);
    }

    @Override
    protected void balance(BinaryNode<T> node) {

        if (node.hasParent() && node.getParent().hasParent()) { //checks if node has parent and grandparents
            //checks if node is the only child and node's parent is the right child of node's grandparent
            BinaryNode<T> parent = node.getParent();
            BinaryNode<T> grandparent = node.getGrandParent();
            if (!parent.hasBothChildren() && grandparent.getRightChild() == parent) {
                BinaryNode<T> uncle = node.getUncle();
                if (!uncle.hasBothChildren() && (uncle.hasLeftChild() || uncle.hasRightChild())) {
                    // TODO: Rotation algorithm
                    if (parent.getRightChild() == node) {
                        rotateLeft(grandparent);
                    }
                    else {
                        rotateRight(parent);
                        rotateLeft(grandparent);
                    }

                    if (uncle.hasLeftChild()) {
                        rotateRight(grandparent);
                    }
                    else {
                        rotateLeft(uncle);
                        rotateRight(grandparent);
                    }
                }
            }
        }
    }


}