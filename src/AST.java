import java.util.ArrayList;

class AST {
    ASTNode root;
    ASTNode currentNode;

    //constants to be used for code generation
    private static int numTemp = 0;
    private static int numLabel = 0;
    static final String FUNC = "func_decl";
    static final String WHILE = "while_stmt";
    static final String IF = "if_stmt";
    static final String ELSE = "else_stmt";
    static final String COMP = "comp";
    static final String ASSIGN = "assign";
    static final String FLOATLITERAL = "floatliteral";
    static final String INTLITERAL = "intliteral";
    static final String STRINGLITERAL = "stringliteral";
    static final String WRITE = "write";
    static final String READ = "read";
    static final String ID = "id";
    static final String MULOP = "mulop";
    static final String ADDOP = "addop";
    static final String GLOBAL = "GLOBAL";

    AST() {
        //create a global root node for the AST
        root = new ASTNode("GLOBAL", "r", AST.GLOBAL);
        //set that node as the current node
        currentNode = root;
    }

    //begin building the code
    ArrayList<String> buildCode(SymbolTable s) {
        //ArrayList<String> code = new ArrayList<>();
        //code = root.buildCode();
        return root.buildCode(s);
    }

    //automatically increments and returns a new temporary for use
    static String newTemp() {
        numTemp++;
        return "$T" + numTemp;
    }

    //automatically increments and returns a new label
    static String newLabel() {
        numLabel++;
        return "label" + numLabel;
    }
}

class ASTNode {
    //the temporary value for the node when building the code
    private String temp;
    //what AST type the node is: 'r', 'l', 'c'
    private String type;
    //the actual value stored in the node, can be null, variable name, literal, or an operator
    String value;
    //what rule the node was created from
    String rule;
    //the parent node for current node
    ASTNode parent;
    //the actual datatype of the variable or result: 'F', 'I', or 'S'
    private String datatype = null;
    //list of all the node's children
    ArrayList<ASTNode> children = new ArrayList<>();

    //constructor to initialize stuff
    ASTNode(String v, String t, String r) {
        value = v;
        type = t;
        rule = r;
    }

    //recursively build the code using a post-order walk of the AST
    ArrayList<String> buildCode(SymbolTable s) {
        ArrayList<String> code = new ArrayList<>();
        for (ASTNode child : children) {
            code.addAll(child.buildCode(s));
        }

        String leftTemp;
        String rightTemp;
        //determine how to build the code for the node based on what rule it has
        switch (rule) {
            //if it is a literal, just initialize its datatype and set its temp as its actual value
            case AST.INTLITERAL:
                datatype = "I";
                temp = value;
                break;
            case AST.FLOATLITERAL:
                datatype = "F";
                temp = value;
                break;
            case AST.STRINGLITERAL:
                datatype = "S";
                temp = value;
                break;
            case AST.ASSIGN:
                //since we know operations are type safe, just determine datatype be left node
                datatype = children.get(0).datatype;
                code.add("STORE" + datatype + " " + children.get(1).temp + " " + children.get(0).value);
                break;
            case AST.ADDOP:
                //create a new temporary for the result to be stored in
                temp = AST.newTemp();
                //get datatype from the leftmost node
                datatype = children.get(0).datatype;

                //if the left node is a variable id, we should load it into a new temporary
                //otherwise, just use its already established temp
                if (children.get(0).type.equals("l")) {
                    leftTemp = AST.newTemp();
                    code.add("STORE" + datatype + " " + children.get(0).value + " " + leftTemp);
                } else {
                    leftTemp = children.get(0).temp;
                }

                //if the right node is a variable id, we should load it into a new temporary
                //otherwise, just use its already established temp
                if (children.get(1).type.equals("l")) {
                    rightTemp = AST.newTemp();
                    code.add("STORE" + datatype + " " + children.get(1).value + " " + rightTemp);
                } else {
                    rightTemp = children.get(1).temp;
                }

                //either add or subtract the two nodes
                if (value.equals("+")) {
                    code.add("ADD" + datatype + " " + leftTemp + " " + rightTemp + " " + temp);
                } else {
                    code.add("SUB" + datatype + " " + leftTemp + " " + rightTemp + " " + temp);
                }
                break;
            case AST.COMP:
                break;
            case AST.ELSE:
                break;
            case AST.FUNC:
                code.add(0, "LABEL " + value);
                code.add(1, "LINK");
                code.add("RET");
                break;
            case AST.ID:
                //find the variable's datatype by looking it up in the symbol table
                datatype = s.findType(value);
                break;
            case AST.MULOP:
                //create a new temporary to store the result
                temp = AST.newTemp();
                //get the datatype of the operation from the left node
                datatype = children.get(0).datatype;

                //if the left node is a variable id, we should load it into a new temporary
                //otherwise, just use its already established temp
                if (children.get(0).type.equals("l")) {
                    leftTemp = AST.newTemp();
                    code.add("STORE" + datatype + " " + children.get(0).value + " " + leftTemp);
                } else {
                    leftTemp = children.get(0).temp;
                }

                //if the right node is a variable id, we should load it into a new temporary
                //otherwise, just use its already established temp
                if (children.get(1).type.equals("l")) {
                    rightTemp = AST.newTemp();
                    code.add("STORE" + datatype + " " + children.get(1).value + " " + rightTemp);
                } else {
                    rightTemp = children.get(1).temp;
                }

                //either multiply or divide the two nodes
                if (value.equals("*")) {
                    code.add("MULT" + datatype + " " + leftTemp + " " + rightTemp + " " + temp);
                } else {
                    code.add("DIV" + datatype + " " + leftTemp + " " + rightTemp + " " + temp);
                }
                break;
            case AST.READ:
                for (ASTNode child : children) {
                    code.add("READ" + s.findType(child.value) + " " + child.value);
                }
                break;
            case AST.WRITE:
                for (ASTNode child : children) {
                    code.add("WRITE" + s.findType(child.value) + " " + child.value);
                }
                break;
            case AST.IF:
                break;
            case AST.WHILE:
                break;
            case AST.GLOBAL:
                break;
            default:
                System.out.println("Unhandled rule: " + rule);
        }
        return code;
    }

    //creates and adds a new node to the current node's children
    ASTNode addNode(String v, String t, String r) {
        ASTNode newNode = new ASTNode(v, t, r);
        if (children.size() == 0) {
            children.add(newNode);
            newNode.parent = this;
        } else {
            children.add(newNode);
            newNode.parent = this;
        }
        return newNode;
    }

    //replaces the old node in the parent's children with a new node
    //used for cutting out unnecessary nodes when building the AST
    void replace(ASTNode old, ASTNode newer) {
        newer.parent = this;
        children.set(children.indexOf(old), newer);
    }

    void printSubTree() {
        for (ASTNode child : children) {
            child.printSubTree();
        }
        printNode();
    }

    void printNode() {
        System.out.printf("Value: %s; Rule: %s\n", value, rule);
    }
}