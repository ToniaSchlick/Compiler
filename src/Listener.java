import org.antlr.v4.runtime.misc.ParseCancellationException;

public class Listener extends CompilersBaseListener {
    private TableTree tree;
    private int blockCount = 0;
    private String curVarType;
    private boolean varDeclaration = false;

    Listener(TableTree t) {
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
    public void enterString_decl(CompilersParser.String_declContext ctx) throws ParseCancellationException {
        SymbolTable table = tree.getCurrentTable();
        boolean result = table.add(ctx.id().IDENTIFIER().toString(), "STRING", ctx.str().STRINGLITERAL().toString());
        if(!result) {
            System.out.printf("DECLARATION ERROR %s", ctx.id().IDENTIFIER().toString());
            throw new ParseCancellationException();
        }
    }

    @Override
    public void enterVar_decl(CompilersParser.Var_declContext ctx) {
        curVarType = ctx.var_type().getText();
        varDeclaration = true;
    }

    @Override
    public void enterId_list(CompilersParser.Id_listContext ctx) throws ParseCancellationException {
        if (varDeclaration) {
            SymbolTable table = tree.getCurrentTable();
            boolean result = table.add(ctx.id().IDENTIFIER().toString(), curVarType);
            if(!result) {
                System.out.printf("DECLARATION ERROR %s", ctx.id().IDENTIFIER().toString());
                throw new ParseCancellationException();
            }
        }
    }

    @Override
    public void enterId_tail(CompilersParser.Id_tailContext ctx) throws ParseCancellationException {
        if(varDeclaration && ctx.id() != null) {
            SymbolTable table = tree.getCurrentTable();
            boolean result = table.add(ctx.id().IDENTIFIER().toString(), curVarType);
            if(!result) {
                System.out.printf("DECLARATION ERROR %s", ctx.id().IDENTIFIER().toString());
                throw new ParseCancellationException();
            }
        }
    }

    @Override
    public void exitVar_decl(CompilersParser.Var_declContext ctx) {
        curVarType = null;
        varDeclaration = false;
    }

    @Override
    public void enterParam_decl(CompilersParser.Param_declContext ctx) throws ParseCancellationException {
        SymbolTable table = tree.getCurrentTable();
        boolean result = table.add(ctx.id().IDENTIFIER().toString(), ctx.var_type().getText());
        if(!result) {
            System.out.printf("DECLARATION ERROR %s", ctx.id().IDENTIFIER().toString());
            throw new ParseCancellationException();
        }
    }
}
