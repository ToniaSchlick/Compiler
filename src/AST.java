import java.util.ArrayList;

class AST {
    ASTNode root;
    ASTNode currentNode;
    static int numTemp = 0;
    TableTree table;

    AST(TableTree s) {
        root = new ASTNode("GLOBAL", "r", null);
        currentNode = root;
        table = s;
        /*
        structure = s;
        root = new ASTNode();
        if(structure.equals("assign")) {
            root.value = ":=";
        } else if (structure.equals("while")) {
            root.value = "while_stmt";
        } else if (structure.equals("if")) {
            root.value = "if_stmt";
        }
        */
    }

    ArrayList<String> buildCode() {
        ArrayList<String> code = new ArrayList<>();

        return code;
    }

    static String newTemp() {
        numTemp++;
        return "T" + numTemp;
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

    ASTNode nextChild() {
        if (currentChild + 1 < children.size()) {
            currentChild++;
            return children.get(currentChild);
        } else {
            return null;
        }
    }

    ASTNode previousChild() {
        if (currentChild - 1 >= 0) {
            currentChild--;
            return children.get(currentChild);
        } else {
            return null;
        }
    }
}