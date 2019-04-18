import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

class AST {
    ASTNode root;
    ASTNode currentNode;

    //constants to be used for code generation
    private static int numTemp = 0;
    private static int numLabel = 0;
    static Stack<LinkedList<String>> labelStack = new Stack<>();
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
                //create a new label for the comparison to jump to
                String newlabel = AST.newLabel();
                LinkedList<String> labels = new LinkedList<>();
                //if this is part of an if, save the label for later
                if(parent.rule.equals(AST.IF)) {
                    labels.add(newlabel);
                    AST.labelStack.push(labels);
                }
                //if this is part of a while, place the label in front of the comparison and
                //create a new label to jump out of later
                if(parent.rule.equals(AST.WHILE)) {
                    code.add("LABEL " + newlabel);
                    labels.add(newlabel);
                    newlabel = AST.newLabel();
                    labels.add(newlabel);
                    AST.labelStack.push(labels);
                }
                //get the datatype of the operation and the left and right temporaries
                datatype = children.get(0).datatype;
                if (children.get(0).type.equals("1")) {
                    leftTemp = AST.newTemp();
                    code.add("STORE" + datatype + " " + children.get(0).value + " " + leftTemp);
                } else {
                    leftTemp = children.get(0).temp;
                }
                if (children.get(1).type.equals("1")) {
                    rightTemp = AST.newTemp();
                    code.add("STORE" + datatype + " " + children.get(0).value + " " + rightTemp);
                } else {
                    rightTemp = children.get(1).temp;
                }

                //determine what the jump logic will be, which is the opposite of the operator
                String op = "";
                switch(value) {
                    case "=":
                        op = "NE" + datatype;
                        break;
                    case "!=":
                        op = "EQ" + datatype;
                        break;
                    case "<=":
                        op = "GT" + datatype;
                        break;
                    case ">=":
                        op = "LT" + datatype;
                        break;
                    case "<":
                        op = "GE" + datatype;
                        break;
                    case ">":
                        op = "LE" + datatype;
                }
                code.add(op + " " + leftTemp + " " + rightTemp + " " + newlabel);
                break;
            case AST.FUNC:
                //add the function code based on the assumption that there is only one function in the program
                code.add(0, "LABEL " + value);
                code.add(1, "LINK");
                code.add("RET");
                break;
            case AST.ID:
                //find the variable's datatype by looking it up in the symbol table
                datatype = s.findType(value);
                temp = value;
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
                //creates a read statement for every child
                for (ASTNode child : children) {
                    code.add("READ" + child.datatype + " " + child.value);
                }
                break;
            case AST.WRITE:
                //creates a write statement for every child
                for (ASTNode child : children) {
                    code.add("WRITE" + child.datatype + " " + child.value);
                }
                break;
            case AST.IF:
                //adds the label to skip the if or else to the end of the if code
                LinkedList<String> iflabels = AST.labelStack.pop();
                code.add("LABEL " + iflabels.removeFirst());
                break;
            case AST.WHILE:
                //set the jump back to the beginning of the loop and the label to exit the loop
                LinkedList<String> labellist = AST.labelStack.pop();
                code.add("JUMP " + labellist.removeFirst());
                code.add("LABEL " + labellist.removeFirst());
                break;
            case AST.GLOBAL:
                return code;
            default:
                System.out.println("Unhandled rule: " + rule);
        }
        //if the current node is a statement just before the else-block of an if statement
        if (parent.rule.equals(AST.IF) && parent.children.indexOf(this) == parent.children.size()-2 &&
            parent.children.get(parent.children.indexOf(this) + 1).rule.equals(AST.ELSE)) {
            //place the jump over the else-block and set the label for the else-block itself
            LinkedList<String> labels = AST.labelStack.peek();
            String label = AST.newLabel();
            labels.add(label);
            code.add("JUMP " + label);
            code.add("LABEL " + labels.removeFirst());
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

    @SuppressWarnings({"WeakerAccess", "unused"})
    void printSubTree() {
        for (ASTNode child : children) {
            child.printSubTree();
        }
        printNode();
    }

    private void printNode() {
        System.out.printf("Value: %s; Rule: %s\n", value, rule);
    }
}