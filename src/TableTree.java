import java.util.ArrayList;

class TableTree {
    private ArrayList<SymbolTable> tables = new ArrayList<>();
    private ArrayList<Integer> depth = new ArrayList<>();
    TableTree () {

    }

    void newScope(String scope) {
        SymbolTable table = new SymbolTable(scope);
        tables.add(table);
        depth.add(tables.indexOf(table));
    }

    void exitCurrentScope(){
        int end = depth.size()-1;
        depth.remove(end);
    }

    SymbolTable getCurrentTable(){
        int end = depth.size()-1;
        return tables.get(depth.get(end));
    }

    //prints all the tables, without an extra line after the final table
    void printTables(){
        for (SymbolTable table : tables) {
            if(tables.indexOf(table) != tables.size()-1) {
                table.printTable(false);
                System.out.println();
            } else {
                table.printTable(true);
            }
        }
    }
}
