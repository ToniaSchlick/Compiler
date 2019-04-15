import java.util.ArrayList;

class AST {
    ASTNode root;
    ASTNode currentNode;
    TableTree table;

    //constants to be used for code generation
    static int numTemp = 0;
    static final String FUNC = "func_decl";
    static final String WHILE = "while_stmt";
    static final String IF = "if_stmt";
    static final String ELSE = "else_stmt";
    static final String COMP = "comp";
    static final String ASSIGN = "assign";
    static final String LITERAL = "literal";
    static final String WRITE = "write";
    static final String READ = "read";
    static final String ID = "id";
    static final String MULOP = "mulop";
    static final String ADDOP = "addop";

    AST(TableTree s) {
        //create a global root node for the AST
        root = new ASTNode("GLOBAL", "r", null);
        //set that node as the current node
        currentNode = root;
        //initialize the symbol table tree for the class
        table = s;
    }

    //begin building the code
    ArrayList<String> buildCode() {
        ArrayList<String> code = new ArrayList<>();

        return code;
    }

    //automatically increments and returns a new temporary for use
    static String newTemp() {
        numTemp++;
        return "$T" + numTemp;
    }
}

class ASTNode {
    //the temporary value for the node when building the code
    String temp;
    //what type the node is: 'r', 'l', 'c'
    String type;
    //the actual value stored in the node, can be null
    String value;
    //what rule the node was created from
    String rule;
    //the parent node for current node
    ASTNode parent;
    //list of all the node's children
    ArrayList<ASTNode> children = new ArrayList<>();
    //each entry in the code as a 3AC command
    ArrayList<String> code = new ArrayList<>();

    //constructor to initialize stuff
    ASTNode(String v, String t, String r) {
        value = v;
        type = t;
        rule = r;
    }

    //recursively build the code using a post-order walk of the AST
    ArrayList<String> buildCode() {
        ArrayList<String> code = new ArrayList<>();
        for (ASTNode child : children) {
            code.addAll(child.buildCode());
        }

        return code;
    }

    //creates and adds a new node to the current node's children
    ASTNode addNode(String v, String t, String r) {
        ASTNode newNode = new ASTNode(v, t, r);
        children.add(newNode);
        newNode.parent = this;
        return newNode;
    }

    //replaces the old node in the parent's children with a new node
    //used for cutting out unnecessary nodes when bulding the AST
    void replace(ASTNode old, ASTNode newer) {
        newer.parent = this;
        children.set(children.indexOf(old), newer);
    }

    //prints all AST nodes under and including the current node
    void printSubTree() {
        for (ASTNode child : children) {
            child.printSubTree();
        }
        System.out.printf("Value: %s; Rule: %s\n", value, rule);
    }
}