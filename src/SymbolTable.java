import java.util.ArrayList;

public class SymbolTable {
    String scope;
    ArrayList<Entry> entries = new ArrayList<>();
    SymbolTable(String s) {
        scope = s;
    }

    public void add(String n, String t, String v) {
        entries.add(new Entry(n, t, v));
    }

    public void add(String n,String t) {
        entries.add(new Entry(n, t, null));
    }

    public void printTable(){
        System.out.printf("Symbol table %s\n", scope);
        for(Entry entry : entries) {
            System.out.printf("name %s type %s", entry.name, entry.type);
            if (entry.value != null) {
                System.out.printf("value %s\n", entry.value);
            } else {
                System.out.println();
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
