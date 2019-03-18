import java.util.ArrayList;

public class TableTree {
    ArrayList<SymbolTable> tables = new ArrayList<>();
    ArrayList<Integer> depth = new ArrayList<>();
    TableTree () {

    }

    public void newScope(String scope) {
        SymbolTable table = new SymbolTable(scope);
        tables.add(table);
        depth.add(tables.indexOf(table));
    }

    public void exitCurrentScope(){
        int end = depth.size()-1;
        depth.remove(end);
    }

    public SymbolTable getCurrentTable(){
        int end = depth.size()-1;
        return tables.get(depth.get(end));
    }

    //prints all the tables, without an extra line after the final table
    public void printTables(){
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
