import java.util.ArrayList;

public class Listener extends CompilersBaseListener {
    TableTree tree;
    int blockCount = 0;
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
        blockCount++;
        tree.newScope("BLOCK " + blockCount);
    }

    @Override
    public void exitIf_stmt(CompilersParser.If_stmtContext ctx) {
        tree.exitCurrentScope();
    }

    @Override
    public void enterElse_part(CompilersParser.Else_partContext ctx) {
        blockCount++;
        tree.newScope("BLOCK " + blockCount);
    }

    @Override
    public void exitElse_part(CompilersParser.Else_partContext ctx) {
        tree.exitCurrentScope();
    }

    @Override
    public void enterWhile_stmt(CompilersParser.While_stmtContext ctx) {
        blockCount++;
        tree.newScope("BLOCK " + blockCount);
    }

    @Override
    public void exitWhile_stmt(CompilersParser.While_stmtContext ctx) {
        tree.exitCurrentScope();
    }
}
