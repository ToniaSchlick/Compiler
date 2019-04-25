import org.antlr.v4.runtime.misc.ParseCancellationException;

import java.util.Stack;

public class Listener extends CompilersBaseListener {
    private TableTree tree;
    private int blockCount = 0;
    private String curVarType;
    private boolean varDeclaration = false;
    private AST ast;
    private Stack<ASTNode> roots = new Stack<>();


    Listener(TableTree t, AST a) {
        tree = t;
        ast = a;
    }

    /*
        Whenever a node that has children is created, it is pushed onto a stack
        the ast.current node variable is always set to the node the tree is currently
        highlighting and is used to make sure that new nodes are added to the correct node.
        Always make sure to set the current node to the current node's parent when finished
        and walking back up the tree.

        Some nodes are necessary to keep track of where we are in the program, but are
        unnecessary for the creation of an actual AST. These nodes will be eliminated
        by replacing their position as a child of their parent with one of their children when
        we exit their rule.
    */

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
        //add a new function node and push it to the stack
        ast.currentNode = ast.currentNode.addNode(ctx.id().IDENTIFIER().toString(), "r", AST.FUNC);
        roots.push(ast.currentNode);
    }

    @Override
    public void exitFunc_decl(CompilersParser.Func_declContext ctx) {
        tree.exitCurrentScope();
        //pop the function node of the stack and set its parent as the current node
        ast.currentNode = roots.pop().parent;
    }

    @Override
    public void enterIf_stmt(CompilersParser.If_stmtContext ctx) {
        //System.out.println("Entering IF");
        blockCount++;
        tree.newScope("BLOCK " + blockCount);
        //create a new if node and push it to the stack
        ast.currentNode = ast.currentNode.addNode(null, "r", AST.IF);
        roots.push(ast.currentNode);
    }

    @Override
    public void exitIf_stmt(CompilersParser.If_stmtContext ctx) {
        tree.exitCurrentScope();
        //remove the else-node from the if statement if it is empty
        ast.currentNode = roots.pop();
        if (ast.currentNode.children.get(ast.currentNode.children.size()-1).children.size() == 0) {
            ast.currentNode.children.remove(ast.currentNode.children.size()-1);
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
        //create a new else node and push it to the stack
        ast.currentNode = ast.currentNode.addNode(null, "r", AST.ELSE);
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
        //create a condition node and add it to the stack
        ast.currentNode = ast.currentNode.addNode(null, "r", "cond");
        roots.push(ast.currentNode);
    }

    @Override
    public void exitCond(CompilersParser.CondContext ctx) {
        ast.currentNode = roots.pop();
        //the condition node itself is useless, we only need its comparison node
        //so set the condition node's two expression nodes as children of the
        ast.currentNode.children.get(1).children.add(ast.currentNode.children.get(0));
        ast.currentNode.children.get(1).children.add(ast.currentNode.children.get(2));
        //replace the condition node with the comparison node
        ast.currentNode.parent.replace(ast.currentNode, ast.currentNode.children.get(1));
        ast.currentNode = ast.currentNode.parent;
    }

    @Override
    public void enterCompop(CompilersParser.CompopContext ctx) {
        //adds a comparison node to the above condition node
        ast.currentNode.addNode(ctx.getText(), "r", AST.COMP);
    }

    @Override
    public void enterWhile_stmt(CompilersParser.While_stmtContext ctx) {
        blockCount++;
        tree.newScope("BLOCK " + blockCount);
        //create a while node and add it to the stack
        ast.currentNode = ast.currentNode.addNode(null, "r", AST.WHILE);
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
        //create a write node and add it to the stack
        ast.currentNode = ast.currentNode.addNode("Write", "r", AST.WRITE);
        roots.push(ast.currentNode);
    }

    @Override
    public void exitWrite_stmt(CompilersParser.Write_stmtContext ctx) {
        ast.currentNode = roots.pop().parent;
    }

    @Override
    public void enterRead_stmt(CompilersParser.Read_stmtContext ctx) {
        //create a read node and add it to the stack
        ast.currentNode = ast.currentNode.addNode("Read", "r", AST.READ);
        roots.push(ast.currentNode);
    }

    @Override
    public void exitRead_stmt(CompilersParser.Read_stmtContext ctx) {
        ast.currentNode = roots.pop().parent;
    }

    @Override
    public void enterAssign_expr(CompilersParser.Assign_exprContext ctx) {
        //create an assignment node and add it to the stack
        ast.currentNode = ast.currentNode.addNode(":=", "r", AST.ASSIGN);
        roots.push(ast.currentNode);
    }

    @Override
    public void exitAssign_expr(CompilersParser.Assign_exprContext ctx) {
        ast.currentNode = roots.pop().parent;
    }

    @Override
    public void enterId(CompilersParser.IdContext ctx) {
        //adds and id node to whatever node is above it unless that node is the global
        //node, in which case it is part of a variable declaration and is unnecessary
        if (!AST.GLOBAL.equals(ast.currentNode.value)) {
            ast.currentNode.addNode(ctx.IDENTIFIER().toString(), "l", AST.ID);
        }
    }

    @Override
    public void enterExpr(CompilersParser.ExprContext ctx) {
        //create and expression node and adds it to the stack
        ast.currentNode = ast.currentNode.addNode(null, "r", "expr");
        roots.push(ast.currentNode);
    }

    @Override
    public void exitExpr(CompilersParser.ExprContext ctx) {
        ast.currentNode = roots.pop();
        //if the expression_prefix node is empty, then we only need the result of the factor
        //node and we replace the unnecessary expression node with said result
        if (ast.currentNode.children.get(0).children.size() == 0) {
            ast.currentNode.parent.replace(ast.currentNode, ast.currentNode.children.get(1));

        //otherwise, we must add the result of the factor node as a child of the expr_prefix
        //node and replace the expression node with the expr_prefix node
        } else {
            ast.currentNode.children.get(0).children.add(ast.currentNode.children.get(1));
            ast.currentNode.parent.replace(ast.currentNode, ast.currentNode.children.get(0));
        }
        ast.currentNode = ast.currentNode.parent;
    }

    @Override
    public void enterExpr_prefix(CompilersParser.Expr_prefixContext ctx) {
        //add an expr_prefix node to the stack
        ast.currentNode = ast.currentNode.addNode(null, "r", "expr_prefix");
        roots.push(ast.currentNode);
    }

    @Override
    public void exitExpr_prefix(CompilersParser.Expr_prefixContext ctx) {
        ast.currentNode = roots.pop();
        //if the addop rule is not null, then there is some expression in this node we must
        //handle, otherwise this is just an empty node
        if (ctx.addop() != null) {
            ASTNode addop = ast.currentNode.children.get(2);
            //if this node's expr_prefix sub-node is empty, then we just add the result
            //of the factor node as a child of the operator
            if (ast.currentNode.children.get(0).children.size() == 0) {
                addop.children.add(ast.currentNode.children.get(1));

            //otherwise we add the factor result as a child of the expr_prefix node, then
            //make the expr_prefix node a child of the operator
            } else {
                ast.currentNode.children.get(0).children.add(ast.currentNode.children.get(1));
                addop.children.add(ast.currentNode.children.get(0));
            }
            //as the expr_prefix node is useless in an AST, replace this one with the
            //result of the addop node
            ast.currentNode.parent.replace(ast.currentNode, addop);
        }
        ast.currentNode = ast.currentNode.parent;
    }

    @Override
    public void enterFactor(CompilersParser.FactorContext ctx) {
        //create a new factor node and add it to the stack
        ast.currentNode = ast.currentNode.addNode(null, "r", "factor");
        roots.push(ast.currentNode);
    }

    @Override
    public void exitFactor(CompilersParser.FactorContext ctx) {
        ast.currentNode = roots.pop();
        //if the factor prefix node is empty, just replace this factor node with the result
        //of the postfix expression node
        if (ast.currentNode.children.get(0).children.size() == 0) {
            ast.currentNode.parent.replace(ast.currentNode, ast.currentNode.children.get(1));

        //otherwise, make the result of the postfix expression node a child of the factor
        //prefix node and replace this factor node with the result of the factor prefix
        } else {
            ast.currentNode.children.get(0).children.add(ast.currentNode.children.get(1));
            ast.currentNode.parent.replace(ast.currentNode, ast.currentNode.children.get(0));
        }
        ast.currentNode = ast.currentNode.parent;
    }

    @Override
    public void enterFactor_prefix(CompilersParser.Factor_prefixContext ctx) {
        //adds a factor prefix node and pushes it to the stack
        ast.currentNode = ast.currentNode.addNode(null, "r", "factor_prefix");
        roots.push(ast.currentNode);
    }

    @Override
    public void exitFactor_prefix(CompilersParser.Factor_prefixContext ctx) {
        ast.currentNode = roots.pop();
        //if the mulop node exists, there is an expression here we must handle, otherwise
        //this is just an empty node
        if (ctx.mulop() != null) {
            ASTNode mulop = ast.currentNode.children.get(2);
            //if this node's factor_prefix sub-node is empty, just add the postfix_expr
            //node as a child of the mulop node
            if (ast.currentNode.children.get(0).children.size() == 0) {
                mulop.children.add(ast.currentNode.children.get(1));

            //otherwise, add the postfix_expr node as a child of the factor_prefix node
            //and set that as the child of the mulop node
            } else {
                ast.currentNode.children.get(0).children.add(ast.currentNode.children.get(1));
                mulop.children.add(ast.currentNode.children.get(0));
            }
            //the factor_prefix node is unnecessary for an AST, so replace its position in the
            //parent node with the mulop node
            ast.currentNode.parent.replace(ast.currentNode, mulop);
        }
        ast.currentNode = ast.currentNode.parent;
    }

    @Override
    public void enterPostfix_expr(CompilersParser.Postfix_exprContext ctx) {
        //create a postfix_expr node and push it to the stack
        ast.currentNode = ast.currentNode.addNode(null, "r", "postfix_expr");
        roots.push(ast.currentNode);
    }

    @Override
    public void exitPostfix_expr(CompilersParser.Postfix_exprContext ctx) {
        ast.currentNode = roots.pop();
        //the postfix_expr node is unnecessary for an AST, so replace it with its child
        //in the parent node
        ast.currentNode.parent.replace(ast.currentNode, ast.currentNode.children.get(0));
        ast.currentNode = ast.currentNode.parent;
    }

    @Override
    public void enterPrimary(CompilersParser.PrimaryContext ctx) {
        //if the primary contains just a literal constant, then pass that up
        //otherwise, it contains and expr node and must be handled appropriately
        if (ctx.FLOATLITERAL() != null) {
            ast.currentNode = ast.currentNode.addNode(ctx.FLOATLITERAL().toString(), "c", AST.FLOATLITERAL);
        } else if (ctx.INTLITERAL() != null) {
            ast.currentNode = ast.currentNode.addNode(ctx.INTLITERAL().toString(), "c", AST.INTLITERAL);
        } else {
            ast.currentNode = ast.currentNode.addNode(null, "r", "primary");
        }
        //whatever node was created, push it to the stack
        roots.push(ast.currentNode);
    }

    @Override
    public void exitPrimary(CompilersParser.PrimaryContext ctx) {
        ast.currentNode = roots.pop();
        //if the primary node contained and expr node, replace the primary node with the result of
        //that expr node
        if (!ast.currentNode.rule.equals(AST.INTLITERAL) && !ast.currentNode.rule.equals(AST.FLOATLITERAL)) {
            ast.currentNode.parent.replace(ast.currentNode, ast.currentNode.children.get(0));
        }
        ast.currentNode = ast.currentNode.parent;
    }

    @Override
    public void enterMulop(CompilersParser.MulopContext ctx) {
        //add a simple node containing an operator
        ast.currentNode.addNode(ctx.getText(), "r", AST.MULOP);
    }

    @Override
    public void enterAddop(CompilersParser.AddopContext ctx) {
        //add a simple node containing an operator
        ast.currentNode.addNode(ctx.getText(), "r", AST.ADDOP);
    }
}