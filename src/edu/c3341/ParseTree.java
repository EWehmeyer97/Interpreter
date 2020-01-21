package edu.c3341;

/**
 * Parser for Core Interpreter project. (Note: by package-wide convention,
 * unless stated otherwise, all references are non-null.)
 *
 * @author Evan Wehmeyer
 *
 *
 */
interface ParseTree {

    /**
     * Java interfaces cannot contain constructors pertaining to the classes
     * that are to implement them; hence, here in this Javadoc comment
     * expectations for constructors are stated. Each class that implements
     * interface ParseTree is expected to follow the singleton pattern; hence,
     * each such class should have exactly one private constructor. To be a bit
     * more general about it, every constructor for each such class must be
     * private.
     *
     * In Java versions 7 and below, interfaces cannot contain static methods.
     * To remain compatible with these Java versions, this interface presents
     * the expected static methods here in a Javadoc comment.
     *
     * /** If no instance of ParseTree yet exists, create one; in any case,
     * return a reference to the single instance of the ParseTree.
     *
     * @return the single instance of the ParseTree
     * @updates p
     * @ensures <pre>[the reference create returned is the reference to the
     *                newly created and only instance of the class implementing
     *                the ParseTree interface]
     *       </pre> public static ParseTree create(Tokenizer t)
     *
     */

    /**
     * /** Return either null or the single instance of the ParseTree, if it
     * exists.
     *
     * @return either null or the single instance of the ParseTree, if it exists
     *
     *         public static ParseTree instance()
     *
     */

    /**
     * Moves cursor to the left child.
     *
     * @updates this
     * @requires the left child exists
     * @ensures <pre>The node goes to its left child and can return to the original node with goUp </pre>
     */
    void goDownLB();
    
    /**
     * Moves cursor to the middle child.
     *
     * @updates this
     * @requires the middle child exists
     * @ensures <pre>The node goes to its middle child and can return to the original node with goUp </pre>
     */
    void goDownMB();
    
    /**
     * Moves cursor to the right child.
     *
     * @updates this
     * @requires the right child exists
     * @ensures <pre>The node goes to its right child and can return to the original node with goUp </pre>
     */
    void goDownRB();
    
    /**
     * Creates a left child.
     *
     * @updates this
     * @requires Current node is not an Id or No
     * @ensures <pre>a left child is created for the current node </pre>
     */
    void createLB();
    
    /**
     * Creates a middle child.
     *
     * @updates this
     * @ensures <pre>a middle child is created for the current node </pre>
     */
    void createMB();
    
    /**
     * Creates a right child.
     *
     * @updates this
     * @ensures <pre>a right child is created for the current node </pre>
     */
    void createRB();
    
    /**
     * Moves cursor to the parent node.
     *
     * @updates this
     * @requires A parent node exists
     * @ensures <pre>The former node is a child of the parent node which can be returned to </pre>
     */
    void goUp();
    
    /**
     * Sets the NonTerminal int of the node.
     *
     * @updates this
     * @ensures <pre>The nonterminal value is equal to the assigned value </pre>
     */
    void setNTNo(int x);
    
    /**
     * Sets the Alternative int of the node.
     *
     * @updates this
     * @ensures <pre>The nonterminal value is equal to the assigned value</pre>
     */
    void setAlt(int x);

    /**
     * Return the Non Terminal value of the current node. (Restores
     * this.)
     *
     * @return the integer value of the current node
     * @requires NT has already been set
     * @ensures currentNTNo = the originally set NT int
     */
    int currentNTNo();
    
    /**
     * Return Alternative value of the current node. (Restores
     * this.)
     *
     * @return the integer value of the Alternative for the current node
     * @requires the Alternative has already been set
     * @ensures currentAlt = originally set Alt int
     */
    int currentAlt();
    
    /**
     * Return the integer value of the current Id node (Restores
     * this.)
     *
     * @return the integer value of the current Id.
     * @requires Current node to be an Id
     * @ensures currIdVal = recently set Id value
     */
    int currIdVal();
    
    /**
     * Sets the integer value of the current Id node
     *
     * @updates this
     * @requires Current node is an Id
     * @ensures currIdVal = x
     */
    void setIdVal(int x);
    
    /**
     * Adds the String x as a recognized ID name.
     *
     * @updates this
     * @requires Current node is an Id
     * @ensures Id node points to String location in table
     */
    void addId(String x);
    
    /**
     * Checks to ensure that String x matches an already recognized ID name and points the node as a reference to it.
     *
     * @updates this
     * @return the Id is in the table
     * @requires Current node is an Id
     * @ensures Id node points to String location in table
     */
    boolean findId(String x);
    
    /**
     * Stores int x in the No node
     *
     * @updates this
     * @requires Current node is a No
     * @ensures currNoVal = x
     */
    void setNoVal(int x);
    
    /**
     * Returns the int value in the No node
     *
     * @restores this
     * @return integer value of the No node
     * @requires Current node is a No
     * @ensures currNoVal = originally set value
     */
    int currNoVal();

    /**
     * Return the name of the Id node
     *
     * @restores this
     * @return the name of the Id node
     * @requires Current node is an Id
     * @ensures idName = originally set value
     */
    String idName();
}