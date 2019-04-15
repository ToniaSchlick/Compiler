import org.antlr.v4.runtime.misc.ParseCancellationException;

import java.util.Stack;

public class Listener extends CompilersBaseListener {
    private TableTree tree;
    private int blockCount = 0;
    private String curVarType;
    private boolean varDeclaration = false;
    private AST ast;
    private Stack<ASTNode> roots = new Stack<>();
    private ASTNode nodePointer = null;
    private ASTNode previousPointer = null;

    Listener(TableTree t, AST a) {
        tree = t;
        ast = a;
    }

    /*Functions to enter and exit scope*/
    @Override
    public void enterProgram(CompilersParser.ProgramContext ctx) {
        tree.newScope("GLOBAL");
        roots.push(ast.root);
    }

    @Override
    public void exitProgram(CompilersParser.ProgramContext ctx) {
        tree.exitCurrentScope();
        roots.pop();
    }

    @Override
    public void enterFunc_decl(CompilersParser.Func_declContext ctx) {
        tree.newScope(ctx.id().IDENTIFIER().toString());
        ast.currentNode = ast.currentNode.addNode(ctx.id().IDENTIFIER().toString(), "r", "func_decl");
        roots.push(ast.currentNode);
    }

    @Override
    public void exitFunc_decl(CompilersParser.Func_declContext ctx) {
        tree.exitCurrentScope();
        ast.currentNode = roots.pop().parent;
    }

    @Override
    public void enterIf_stmt(CompilersParser.If_stmtContext ctx) {
        //System.out.println("Entering IF");
        blockCount++;
        tree.newScope("BLOCK " + blockCount);
        ast.currentNode = ast.currentNode.addNode(null, "r", "if_stmt");
        roots.push(ast.currentNode);
    }

    @Override
    public void exitIf_stmt(CompilersParser.If_stmtContext ctx) {
        tree.exitCurrentScope();
        ast.currentNode = roots.pop();
        if (ast.currentNode.children.get(2).children.size() == 0) {
            ast.currentNode.children.remove(2);
        }
        ast.currentNode = ast.currentNode.parent;
    }

    @Override
    public void enterElse_part(CompilersParser.Else_partContext ctx) {
        //only declare a new scope for the else if an actual else block exists
        if (ctx.decl() != null) {
            blockCount++;
            tree.newScope("BLOCK " + blockCount);
        }
        ast.currentNode = ast.currentNode.addNode(null, "r", "else_stmt");
        roots.push(ast.currentNode);
    }

    @Override
    public void exitElse_part(CompilersParser.Else_partContext ctx) {
        //only exit the current scope if a scope for the else block was created
        if (ctx.decl() != null) {
            tree.exitCurrentScope();
        }
        ast.currentNode = roots.pop().parent;
    }

    @Override
    public void enterCond(CompilersParser.CondContext ctx) {
        ast.currentNode = ast.currentNode.addNode(null, "r", "cond");
        roots.push(ast.currentNode);
    }

    @Override
    public void exitCond(CompilersParser.CondContext ctx) {
        ast.currentNode = roots.pop();
        ast.currentNode.children.get(1).children.add(ast.currentNode.children.get(0));
        ast.currentNode.children.get(1).children.add(ast.currentNode.children.get(2));
        ast.currentNode.parent.replace(ast.currentNode, ast.currentNode.children.get(1));
        ast.currentNode = ast.currentNode.parent;
    }

    @Override
    public void enterCompop(CompilersParser.CompopContext ctx) {
        ast.currentNode.addNode(ctx.getText(), "r", "comp");
    }

    @Override
    public void enterWhile_stmt(CompilersParser.While_stmtContext ctx) {
        blockCount++;
        tree.newScope("BLOCK " + blockCount);
        ast.currentNode = ast.currentNode.addNode(null, "r", "while_stmt");
        roots.push(ast.currentNode);
    }

    @Override
    public void exitWhile_stmt(CompilersParser.While_stmtContext ctx) {
        tree.exitCurrentScope();
        ast.currentNode = roots.pop().parent;
    }

    /*Functions to ensure variables are actually being declared*/
    @Override
    public void enterVar_decl(CompilersParser.Var_declContext ctx) {
        curVarType = ctx.var_type().getText();
        varDeclaration = true;
    }

    @Override
    public void exitVar_decl(CompilersParser.Var_declContext ctx) {
        curVarType = null;
        varDeclaration = false;
    }

    /*Functions to handle adding variables to the symbol table*/
    @Override
    public void enterString_decl(CompilersParser.String_declContext ctx) throws ParseCancellationException {
        SymbolTable table = tree.getCurrentTable();
        boolean result = table.add(ctx.id().IDENTIFIER().toString(), "STRING", ctx.str().STRINGLITERAL().toString());

        //if the variable already exists in scope, print the name of the offending variable
        //and throw a ParseCancellationException
        if(!result) {
            System.out.printf("DECLARATION ERROR %s", ctx.id().IDENTIFIER().toString());
            throw new ParseCancellationException();
        }
        ast.currentNode = ast.currentNode.addNode(":=", "r", "assign");
        roots.push(ast.currentNode);
    }

    @Override
    public void exitString_decl(CompilersParser.String_declContext ctx) {
        ast.currentNode = roots.pop();
        ast.currentNode.addNode(ctx.str().STRINGLITERAL().toString(), "c", "literal");
        ast.currentNode = ast.currentNode.parent;
    }

    @Override
    public void enterId_list(CompilersParser.Id_listContext ctx) throws ParseCancellationException {
        if (varDeclaration) {
            SymbolTable table = tree.getCurrentTable();
            boolean result = table.add(ctx.id().IDENTIFIER().toString(), curVarType);

            //if the variable already exists in scope, print the name of the offending variable
            //and throw a ParseCancellationException
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

            //if the variable already exists in scope, print the name of the offending variable
            //and throw a ParseCancellationException
            if(!result) {
                System.out.printf("DECLARATION ERROR %s", ctx.id().IDENTIFIER().toString());
                throw new ParseCancellationException();
            }
        }
    }

    @Override
    public void enterParam_decl(CompilersParser.Param_declContext ctx) throws ParseCancellationException {
        SymbolTable table = tree.getCurrentTable();
        boolean result = table.add(ctx.id().IDENTIFIER().toString(), ctx.var_type().getText());

        //if the variable already exists in scope, print the name of the offending variable
        //and throw a ParseCancellationException
        if(!result) {
            System.out.printf("DECLARATION ERROR %s", ctx.id().IDENTIFIER().toString());
            throw new ParseCancellationException();
        }
    }

    @Override
    public void enterWrite_stmt(CompilersParser.Write_stmtContext ctx) {
        ast.currentNode = ast.currentNode.addNode("Write", "r", "write");
        roots.push(ast.currentNode);
    }

    @Override
    public void exitWrite_stmt(CompilersParser.Write_stmtContext ctx) {
        ast.currentNode = roots.pop().parent;
    }

    @Override
    public void enterRead_stmt(CompilersParser.Read_stmtContext ctx) {
        ast.currentNode = ast.currentNode.addNode("Read", "r", "read");
        roots.push(ast.currentNode);
    }

    @Override
    public void exitRead_stmt(CompilersParser.Read_stmtContext ctx) {
        ast.currentNode = roots.pop().parent;
    }

    @Override
    public void enterAssign_expr(CompilersParser.Assign_exprContext ctx) {
        ast.currentNode = ast.currentNode.addNode(":=", "r", "assign");
        roots.push(ast.currentNode);
        //ast.currentNode.addNode(ctx.id().IDENTIFIER().toString(), "l", "id");
    }

    @Override
    public void exitAssign_expr(CompilersParser.Assign_exprContext ctx) {
        ast.currentNode = roots.pop();
        System.out.println(ctx.getText());
        ast.currentNode = ast.currentNode.parent;
    }

    @Override
    public void enterId(CompilersParser.IdContext ctx) {
        ast.currentNode.addNode(ctx.IDENTIFIER().toString(), "l", "id");
    }

    @Override
    public void enterExpr(CompilersParser.ExprContext ctx) {
        ast.currentNode = ast.currentNode.addNode(null, "r", "expr");
        roots.push(ast.currentNode);
    }

    @Override
    public void exitExpr(CompilersParser.ExprContext ctx) {
        ast.currentNode = roots.pop();
        if (ast.currentNode.children.get(0).children.size() == 0) {
            ast.currentNode.parent.replace(ast.currentNode, ast.currentNode.children.get(1));
        } else {
            ast.currentNode.children.get(0).children.add(ast.currentNode.children.get(1));
            ast.currentNode.parent.replace(ast.currentNode, ast.currentNode.children.get(0));
        }
        ast.currentNode = ast.currentNode.parent;
    }

    @Override
    public void enterExpr_prefix(CompilersParser.Expr_prefixContext ctx) {
        ast.currentNode = ast.currentNode.addNode(null, "r", "expr_prefix");
        roots.push(ast.currentNode);
    }

    @Override
    public void exitExpr_prefix(CompilersParser.Expr_prefixContext ctx) {
        ast.currentNode = roots.pop();
        if (ctx.addop() != null) {
            ASTNode addop = ast.currentNode.children.get(2);
            if (ast.currentNode.children.get(0).children.size() == 0) {
                addop.children.add(ast.currentNode.children.get(1));
            } else {
                ast.currentNode.children.get(0).children.add(ast.currentNode.children.get(1));
                addop.children.add(ast.currentNode.children.get(0));
            }
            ast.currentNode.parent.replace(ast.currentNode, addop);
        }
        ast.currentNode = ast.currentNode.parent;
    }

    @Override
    public void enterFactor(CompilersParser.FactorContext ctx) {
        ast.currentNode = ast.currentNode.addNode(null, "r", "factor");
        roots.push(ast.currentNode);
    }

    @Override
    public void exitFactor(CompilersParser.FactorContext ctx) {
        ast.currentNode = roots.pop();
        if (ast.currentNode.children.get(0).children.size() == 0) {
            ast.currentNode.parent.replace(ast.currentNode, ast.currentNode.children.get(1));
        } else {
            ast.currentNode.children.get(0).children.add(ast.currentNode.children.get(1));
            ast.currentNode.parent.replace(ast.currentNode, ast.currentNode.children.get(0));
        }
        ast.currentNode = ast.currentNode.parent;
    }

    @Override
    public void enterFactor_prefix(CompilersParser.Factor_prefixContext ctx) {
        ast.currentNode = ast.currentNode.addNode(null, "r", "factor_prefix");
        roots.push(ast.currentNode);
    }

    @Override
    public void exitFactor_prefix(CompilersParser.Factor_prefixContext ctx) {
        ast.currentNode = roots.pop();
        if (ctx.mulop() != null) {
            ASTNode mulop = ast.currentNode.children.get(2);
            if (ast.currentNode.children.get(0).children.size() == 0) {
                mulop.children.add(ast.currentNode.children.get(1));
            } else {
                ast.currentNode.children.get(0).children.add(ast.currentNode.children.get(1));
                mulop.children.add(ast.currentNode.children.get(0));
            }
            ast.currentNode.parent.replace(ast.currentNode, mulop);
        }
        ast.currentNode = ast.currentNode.parent;
    }

    @Override
    public void enterPostfix_expr(CompilersParser.Postfix_exprContext ctx) {
        ast.currentNode = ast.currentNode.addNode(null, "r", "postfix_expr");
        roots.push(ast.currentNode);
    }

    @Override
    public void exitPostfix_expr(CompilersParser.Postfix_exprContext ctx) {
        ast.currentNode = roots.pop();
        ast.currentNode.parent.replace(ast.currentNode, ast.currentNode.children.get(0));
        ast.currentNode = ast.currentNode.parent;
    }

    @Override
    public void enterPrimary(CompilersParser.PrimaryContext ctx) {
        if (ctx.FLOATLITERAL() != null) {
            ast.currentNode = ast.currentNode.addNode(ctx.FLOATLITERAL().toString(), "c", "literal");
        } else if (ctx.INTLITERAL() != null) {
            ast.currentNode = ast.currentNode.addNode(ctx.INTLITERAL().toString(), "c", "literal");
        } else {
            ast.currentNode = ast.currentNode.addNode(null, "r", "primary");
        }
        roots.push(ast.currentNode);
    }

    @Override
    public void exitPrimary(CompilersParser.PrimaryContext ctx) {
        ast.currentNode = roots.pop();
        if (!ast.currentNode.rule.equals("literal")) {
            ast.currentNode.parent.replace(ast.currentNode, ast.currentNode.children.get(0));
        }
        ast.currentNode = ast.currentNode.parent;
    }

    @Override
    public void enterMulop(CompilersParser.MulopContext ctx) {
        ast.currentNode.addNode(ctx.getText(), "r", "mulop");
    }

    @Override
    public void enterAddop(CompilersParser.AddopContext ctx) {
        ast.currentNode.addNode(ctx.getText(), "r", "addop");
    }
}