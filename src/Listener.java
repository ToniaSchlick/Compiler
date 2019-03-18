import java.util.ArrayList;

public class Listener extends CompilersBaseListener {
    TableTree tree;
    int blockCount = 0;
    ArrayList<String> type = new ArrayList<>();
    ArrayList<Integer> typeDepth = new ArrayList<>();
    String curVarType;
    boolean varDeclaration = false;

    public Listener(TableTree t) {
        tree = t;
    }

    @Override
    public void enterProgram(CompilersParser.ProgramContext ctx) {
        tree.newScope("GLOBAL");
    }

    @Override
    public void exitProgram(CompilersParser.ProgramContext ctx) {
        tree.exitCurrentScope();
    }

    @Override
    public void enterFunc_decl(CompilersParser.Func_declContext ctx) {
        tree.newScope(ctx.id().IDENTIFIER().toString());
    }

    @Override
    public void exitFunc_decl(CompilersParser.Func_declContext ctx) {
        tree.exitCurrentScope();
    }

    @Override
    public void enterIf_stmt(CompilersParser.If_stmtContext ctx) {
        //System.out.println("Entering IF");
        blockCount++;
        tree.newScope("BLOCK " + blockCount);
    }

    @Override
    public void exitIf_stmt(CompilersParser.If_stmtContext ctx) {
        tree.exitCurrentScope();
    }

    @Override
    public void enterElse_part(CompilersParser.Else_partContext ctx) {
        //System.out.println("Entering ELSE");
        if (ctx.decl() != null) {
            blockCount++;
            tree.newScope("BLOCK " + blockCount);
        }
    }

    @Override
    public void exitElse_part(CompilersParser.Else_partContext ctx) {
        if (ctx.decl() != null) {
            tree.exitCurrentScope();
        }
    }

    @Override
    public void enterWhile_stmt(CompilersParser.While_stmtContext ctx) {
        //System.out.println("Entering WHILE");
        blockCount++;
        tree.newScope("BLOCK " + blockCount);
    }

    @Override
    public void exitWhile_stmt(CompilersParser.While_stmtContext ctx) {
        tree.exitCurrentScope();
    }

    @Override
    public void enterString_decl(CompilersParser.String_declContext ctx) {
        SymbolTable table = tree.getCurrentTable();
        table.add(ctx.id().IDENTIFIER().toString(), "STRING", ctx.str().STRINGLITERAL().toString());
    }

    @Override
    public void enterVar_decl(CompilersParser.Var_declContext ctx) {
        curVarType = ctx.var_type().getText();
        varDeclaration = true;
    }

    @Override
    public void enterId_list(CompilersParser.Id_listContext ctx) {
        if (varDeclaration) {
            SymbolTable table = tree.getCurrentTable();
            table.add(ctx.id().IDENTIFIER().toString(), curVarType);
        }
    }

    @Override
    public void enterId_tail(CompilersParser.Id_tailContext ctx) {
        if(varDeclaration && ctx.id() != null) {
            SymbolTable table = tree.getCurrentTable();
            table.add(ctx.id().IDENTIFIER().toString(), curVarType);
        }
    }

    @Override
    public void exitVar_decl(CompilersParser.Var_declContext ctx) {
        curVarType = null;
        varDeclaration = false;
    }

    @Override
    public void enterParam_decl(CompilersParser.Param_declContext ctx) {
        SymbolTable table = tree.getCurrentTable();
        table.add(ctx.id().IDENTIFIER().toString(), ctx.var_type().getText());
    }
}
