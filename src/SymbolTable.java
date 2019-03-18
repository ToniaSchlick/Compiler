import java.util.ArrayList;

public class SymbolTable {
    String scope;
    ArrayList<Entry> entries = new ArrayList<>();
    SymbolTable(String s) {
        scope = s;
    }

    //adds a string to the symbol table
    public void add(String n, String t, String v) {
        entries.add(new Entry(n, t, v));
    }

    //adds any other type of variable to the symbol table
    public void add(String n,String t) {
        entries.add(new Entry(n, t, null));
    }

    //prints the values in the symbol table, without an extra line after the final entry
    public void printTable(boolean last){
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
}

class Entry {
    String name, type, value;
    Entry(String n, String t, String v) {
        name = n;
        type = t;
        value = v;
    }
}
