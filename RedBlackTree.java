// --== CS400 File Header Information ==--
// Name: Aristidis Giannopoulos
// Email: agiannopoulo@wisc.edu
// Team: NC
// TA: Daniel Finer
// Lecturer: Gary Dahl
// Notes to Grader: NONE
import java.util.LinkedList;

/**
 * Binary Search Tree implementation with a Node inner class for representing the nodes within a
 * binary search tree. You can use this class' insert method to build a binary search tree, and its
 * toString method to display the level order (breadth first) traversal of values in that tree.
 */
/**
 * 
 * @author Aristidis
 *
 * @param <T>
 */
public class RedBlackTree<T extends Comparable<T>> {

  /**
   * This class represents a node holding a single value within a binary tree the parent, left, and
   * right child references are always be maintained.
   */
  protected static class Node<T> {
    public T data;
    public Node<T> parent; // null for root node
    public Node<T> leftChild;
    public Node<T> rightChild;
    public boolean isBlack = false;

    public Node(T data) {
      this.data = data;
    }

    /**
     * @return true when this node has a parent and is the left child of that parent, otherwise
     *         return false
     */
    public boolean isLeftChild() {
      return parent != null && parent.leftChild == this;
    }

    /**
     * This method performs a level order traversal of the tree rooted at the current node. The
     * string representations of each data value within this tree are assembled into a comma
     * separated string within brackets (similar to many implementations of java.util.Collection).
     * 
     * @return string containing the values of this tree in level order
     */
    @Override
    public String toString() { // display subtree in order traversal
      String output = "[";
      LinkedList<Node<T>> q = new LinkedList<>();
      q.add(this);
      while (!q.isEmpty()) {
        Node<T> next = q.removeFirst();
        if (next.leftChild != null)
          q.add(next.leftChild);
        if (next.rightChild != null)
          q.add(next.rightChild);
        output += next.data.toString();
        if (!q.isEmpty())
          output += ", ";
      }
      return output + "]";
    }

    /**
     * This method confirms if a node is black or red.
     * 
     * @return true if the node is black, false otherwise
     */
    public boolean isBlack() {
      return isBlack == true;
    }
  }

  protected Node<T> root; // reference to root node of tree, null when empty

  /**
   * Performs a naive insertion into a binary search tree: adding the input data value to a new node
   * in a leaf position within the tree. After this insertion, no attempt is made to restructure or
   * balance the tree. This tree will not hold null references, nor duplicate data values.
   * 
   * @param data to be added into this binary search tree
   * @throws NullPointerException     when the provided data argument is null
   * @throws IllegalArgumentException when the tree already contains data
   */
  public void insert(T data) throws NullPointerException, IllegalArgumentException {
    // null references cannot be stored within this tree
    if (data == null)
      throw new NullPointerException("This RedBlackTree cannot store null references.");
    Node<T> newNode = new Node<>(data);
    if (root == null) {
      root = newNode;
    } // add first node to an empty tree
    else
      insertHelper(newNode, root); // recursively insert into subtree
    root.isBlack = true;
  }

  /**
   * Recursive helper method to find the subtree with a null reference in the position that the
   * newNode should be inserted, and then extend this tree by the newNode in that position.
   * 
   * @param newNode is the new node that is being added to this tree
   * @param subtree is the reference to a node within this tree which the newNode should be inserted
   *                as a descenedent beneath
   * @throws IllegalArgumentException when the newNode and subtree contain equal data references (as
   *                                  defined by Comparable.compareTo())
   */
  private void insertHelper(Node<T> newNode, Node<T> subtree) {
    int compare = newNode.data.compareTo(subtree.data);
    // do not allow duplicate values to be stored within this tree
    if (compare == 0)
      throw new IllegalArgumentException("This RedBlackTree already contains that value.");
    // store newNode within left subtree of subtree
    else if (compare < 0) {
      if (subtree.leftChild == null) { // left subtree empty, add here
        subtree.leftChild = newNode;
        newNode.parent = subtree;
        enforceRBTreeProptertiesAfterInsert(newNode);
        // otherwise continue recursive search for location to insert
      } else
        insertHelper(newNode, subtree.leftChild);
    }
    // store newNode within the right subtree of subtree
    else {
      if (subtree.rightChild == null) { // right subtree empty, add here
        subtree.rightChild = newNode;
        newNode.parent = subtree;
        enforceRBTreeProptertiesAfterInsert(newNode);
        // otherwise continue recursive search for location to insert
      } else
        insertHelper(newNode, subtree.rightChild);
    }
  }

  /**
   * This method performs a level order traversal of the tree. The string representations of each
   * data value within this tree are assembled into a comma separated string within brackets
   * (similar to many implementations of java.util.Collection, like java.util.ArrayList, LinkedList,
   * etc).
   * 
   * @return string containing the values of this tree in level order
   */
  @Override
  public String toString() {
    return root.toString();
  }

  /**
   * Performs the rotation operation on the provided nodes within this BST. When the provided child
   * is a leftChild of the provided parent, this method will perform a right rotation (sometimes
   * called a left-right rotation). When the provided child is a rightChild of the provided parent,
   * this method will perform a left rotation (sometimes called a right-left rotation). When the
   * provided nodes are not related in one of these ways, this method will throw an
   * IllegalArgumentException.
   * 
   * @param child  is the node being rotated from child to parent position (between these two node
   *               arguments)
   * @param parent is the node being rotated from parent to child position (between these two node
   *               arguments)
   * @throws IllegalArgumentException when the provided child and parent node references are not
   *                                  initially (pre-rotation) related that way
   */
  public void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
    if (child == null) {
      throw new IllegalArgumentException("RIP");
    }
    if (parent.rightChild == child) {
      child = parent.rightChild;
      parent.rightChild = child.leftChild;
      if (child.leftChild != null) {
        child.leftChild.parent = parent;
      }
      child.parent = parent.parent;
      if (parent.parent == null) {
        root = child;
      } else if (parent == parent.parent.leftChild) {
        parent.parent.leftChild = child;
      } else {
        parent.parent.rightChild = child;
      }
      child.leftChild = parent;
      parent.parent = child;
    } else if (parent.leftChild == child) {
      child = parent.leftChild;
      parent.leftChild = child.rightChild;
      if (child.rightChild != null) {
        child.rightChild.parent = parent;
      }
      child.parent = parent.parent;
      if (parent.parent == null) {
        root = child;
      } else if (parent == parent.parent.rightChild) {
        parent.parent.rightChild = child;
      } else {
        parent.parent.leftChild = child;
      }
      child.rightChild = parent;
      parent.parent = child;
    } else {
      throw new IllegalArgumentException();
    }
  }

  /**
   * This class enforces the properties of a red black tree
   * 
   * @param node
   */
  private void enforceRBTreeProptertiesAfterInsert(Node<T> node) {
    if (node == null || node.parent == null || node.parent.parent == null
        || node.parent.isBlack()) {
      return;
    }
    Node<T> parent = node.parent;
    Node<T> gp = parent.parent;
    if (parent.isBlack == false && node != root) {
      // If uncle is right and red
      if (gp.rightChild != null && !gp.rightChild.isBlack() && gp.rightChild != parent) {
        if (gp == root) {
          Boolean tempColor;
          tempColor = parent.isBlack;
          parent.isBlack = gp.isBlack;
          gp.isBlack = tempColor;
          gp.rightChild.isBlack = parent.isBlack;
          return;
        } else {
          Boolean tempColor;
          tempColor = parent.isBlack;
          parent.isBlack = gp.isBlack;
          gp.isBlack = tempColor;
          enforceRBTreeProptertiesAfterInsert(gp);
        }
      } else if (gp.leftChild != null && !gp.leftChild.isBlack() && gp.leftChild != parent) {
        if (gp == root) {
          Boolean tempColor;
          tempColor = parent.isBlack;
          parent.isBlack = gp.isBlack;
          gp.isBlack = tempColor;
          gp.leftChild.isBlack = parent.isBlack;
          return;
        } else {
          Boolean tempColor;
          tempColor = parent.isBlack;
          parent.isBlack = gp.isBlack;
          gp.isBlack = tempColor;
          enforceRBTreeProptertiesAfterInsert(gp);
        }
      }
      // If Uncle is black
      // If LL Case
      if (gp.leftChild == parent && parent.leftChild == node) {
        rotate(parent, gp);
        Boolean tempColor;
        tempColor = parent.isBlack;
        parent.isBlack = gp.isBlack;
        gp.isBlack = tempColor;
      }
      // If LR Case
      if (gp.leftChild == parent && parent.rightChild == node) {
        rotate(node, parent);
        rotate(node, gp);
        Boolean tempColor;
        tempColor = node.isBlack;
        node.isBlack = gp.isBlack;
        gp.isBlack = tempColor;
      }
      // If RR Case
      if (gp.rightChild == parent && parent.rightChild == node) {
        rotate(parent, gp);
        Boolean tempColor;
        tempColor = parent.isBlack;
        parent.isBlack = gp.isBlack;
        gp.isBlack = tempColor;
      }
      // If RL Case
      if (gp.rightChild == parent && parent.leftChild == node) {
        rotate(node, parent);
        rotate(node, gp);
        Boolean tempColor;
        tempColor = node.isBlack;
        node.isBlack = gp.isBlack;
        gp.isBlack = tempColor;
      }
    }
  }
}
