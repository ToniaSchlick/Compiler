import java.util.ArrayList;

class SymbolTable {
    private String scope;
    private ArrayList<Entry> entries = new ArrayList<>();
    SymbolTable(String s) {
        scope = s;
    }

    //adds a string to the symbol table
    //returns false if variable name already exists in scope
    boolean add(String n, String t, String v) {

        for(Entry entry : entries) {
            if(entry.name.equals(n)) {
                return false;
            }
        }
        entries.add(new Entry(n, t, v));
        return true;
    }

    //adds any other type of variable to the symbol table
    //returns false if variable name already exists in scope
    boolean add(String n,String t) {
        for(Entry entry : entries) {
            if(entry.name.equals(n)) {
                return false;
            }
        }
        entries.add(new Entry(n, t, null));
        return true;
    }

    //prints the values in the symbol table, without an extra line after the final entry
    void printTable(boolean last){
        System.out.printf("Symbol table %s\n", scope);
        for(Entry entry : entries) {
            System.out.printf("name %s type %s", entry.name, entry.type);
            if (entry.value != null) {
                if (last && entries.indexOf(entry) == entries.size()-1) {
                    System.out.printf(" value %s", entry.value);
                } else {
                    System.out.printf(" value %s\n", entry.value);
                }
            } else {
                if(!last && entries.indexOf(entry) != entries.size()-1) {
                    System.out.println();
                }
            }
        }
    }

    String findType(String var) {
        for (Entry entry : entries) {
            if (entry.name.equals(var)) {
                if (entry.type.equals("INT")) {
                    return "I";
                } else if (entry.type.equals("FLOAT")) {
                    return "F";
                } else {
                    return "S";
                }
            }
        }
        return "none";
    }
}

//container class to hold entry values
class Entry {
    String name, type, value;
    Entry(String n, String t, String v) {
        name = n;
        type = t;
        value = v;
    }
}
