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
        root = new ASTNode("GLOBAL", "r", null);
        currentNode = root;
        table = s;
    }

    ArrayList<String> buildCode() {
        ArrayList<String> code = new ArrayList<>();

        return code;
    }

    static String newTemp() {
        numTemp++;
        return "$T" + numTemp;
    }
}

class ASTNode {
    String temp;
    String type;
    String value;
    String rule;
    ASTNode parent;
    private int currentChild;
    ArrayList<ASTNode> children = new ArrayList<>();
    ArrayList<String> code = new ArrayList<>();

    ASTNode(String v, String t, String r) {
        value = v;
        type = t;
        rule = r;
    }

    ArrayList<String> buildCode() {
        ArrayList<String> code = new ArrayList<>();
        for (ASTNode child : children) {
            code.addAll(child.buildCode());
        }

        return code;
    }

    ASTNode addNode(String v, String t, String r) {
        ASTNode newNode = new ASTNode(v, t, r);
        if (children.size() == 0) {
            children.add(newNode);
            newNode.parent = this;
            currentChild = 0;
        } else {
            children.add(newNode);
            newNode.parent = this;
            currentChild++;
        }
        return newNode;
    }

    void replace(ASTNode old, ASTNode newer) {
        newer.parent = this;
        children.set(children.indexOf(old), newer);
    }

    void printSubTree() {
        for (ASTNode child : children) {
            child.printSubTree();
        }
        System.out.printf("Value: %s; Rule: %s\n", value, rule);
    }
}