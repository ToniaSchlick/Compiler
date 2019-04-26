// Generated from Compilers.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CompilersParser}.
 */
public interface CompilersListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CompilersParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(CompilersParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(CompilersParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#id}.
	 * @param ctx the parse tree
	 */
	void enterId(CompilersParser.IdContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#id}.
	 * @param ctx the parse tree
	 */
	void exitId(CompilersParser.IdContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#pgm_body}.
	 * @param ctx the parse tree
	 */
	void enterPgm_body(CompilersParser.Pgm_bodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#pgm_body}.
	 * @param ctx the parse tree
	 */
	void exitPgm_body(CompilersParser.Pgm_bodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#decl}.
	 * @param ctx the parse tree
	 */
	void enterDecl(CompilersParser.DeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#decl}.
	 * @param ctx the parse tree
	 */
	void exitDecl(CompilersParser.DeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#string_decl}.
	 * @param ctx the parse tree
	 */
	void enterString_decl(CompilersParser.String_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#string_decl}.
	 * @param ctx the parse tree
	 */
	void exitString_decl(CompilersParser.String_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#str}.
	 * @param ctx the parse tree
	 */
	void enterStr(CompilersParser.StrContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#str}.
	 * @param ctx the parse tree
	 */
	void exitStr(CompilersParser.StrContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#var_decl}.
	 * @param ctx the parse tree
	 */
	void enterVar_decl(CompilersParser.Var_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#var_decl}.
	 * @param ctx the parse tree
	 */
	void exitVar_decl(CompilersParser.Var_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#var_type}.
	 * @param ctx the parse tree
	 */
	void enterVar_type(CompilersParser.Var_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#var_type}.
	 * @param ctx the parse tree
	 */
	void exitVar_type(CompilersParser.Var_typeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#any_type}.
	 * @param ctx the parse tree
	 */
	void enterAny_type(CompilersParser.Any_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#any_type}.
	 * @param ctx the parse tree
	 */
	void exitAny_type(CompilersParser.Any_typeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#id_list}.
	 * @param ctx the parse tree
	 */
	void enterId_list(CompilersParser.Id_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#id_list}.
	 * @param ctx the parse tree
	 */
	void exitId_list(CompilersParser.Id_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#id_tail}.
	 * @param ctx the parse tree
	 */
	void enterId_tail(CompilersParser.Id_tailContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#id_tail}.
	 * @param ctx the parse tree
	 */
	void exitId_tail(CompilersParser.Id_tailContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#param_decl_list}.
	 * @param ctx the parse tree
	 */
	void enterParam_decl_list(CompilersParser.Param_decl_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#param_decl_list}.
	 * @param ctx the parse tree
	 */
	void exitParam_decl_list(CompilersParser.Param_decl_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#param_decl}.
	 * @param ctx the parse tree
	 */
	void enterParam_decl(CompilersParser.Param_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#param_decl}.
	 * @param ctx the parse tree
	 */
	void exitParam_decl(CompilersParser.Param_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#param_decl_tail}.
	 * @param ctx the parse tree
	 */
	void enterParam_decl_tail(CompilersParser.Param_decl_tailContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#param_decl_tail}.
	 * @param ctx the parse tree
	 */
	void exitParam_decl_tail(CompilersParser.Param_decl_tailContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#func_declarations}.
	 * @param ctx the parse tree
	 */
	void enterFunc_declarations(CompilersParser.Func_declarationsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#func_declarations}.
	 * @param ctx the parse tree
	 */
	void exitFunc_declarations(CompilersParser.Func_declarationsContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#func_decl}.
	 * @param ctx the parse tree
	 */
	void enterFunc_decl(CompilersParser.Func_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#func_decl}.
	 * @param ctx the parse tree
	 */
	void exitFunc_decl(CompilersParser.Func_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#func_body}.
	 * @param ctx the parse tree
	 */
	void enterFunc_body(CompilersParser.Func_bodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#func_body}.
	 * @param ctx the parse tree
	 */
	void exitFunc_body(CompilersParser.Func_bodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#stmt_list}.
	 * @param ctx the parse tree
	 */
	void enterStmt_list(CompilersParser.Stmt_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#stmt_list}.
	 * @param ctx the parse tree
	 */
	void exitStmt_list(CompilersParser.Stmt_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterStmt(CompilersParser.StmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitStmt(CompilersParser.StmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#base_stmt}.
	 * @param ctx the parse tree
	 */
	void enterBase_stmt(CompilersParser.Base_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#base_stmt}.
	 * @param ctx the parse tree
	 */
	void exitBase_stmt(CompilersParser.Base_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#assign_stmt}.
	 * @param ctx the parse tree
	 */
	void enterAssign_stmt(CompilersParser.Assign_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#assign_stmt}.
	 * @param ctx the parse tree
	 */
	void exitAssign_stmt(CompilersParser.Assign_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#assign_expr}.
	 * @param ctx the parse tree
	 */
	void enterAssign_expr(CompilersParser.Assign_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#assign_expr}.
	 * @param ctx the parse tree
	 */
	void exitAssign_expr(CompilersParser.Assign_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#read_stmt}.
	 * @param ctx the parse tree
	 */
	void enterRead_stmt(CompilersParser.Read_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#read_stmt}.
	 * @param ctx the parse tree
	 */
	void exitRead_stmt(CompilersParser.Read_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#write_stmt}.
	 * @param ctx the parse tree
	 */
	void enterWrite_stmt(CompilersParser.Write_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#write_stmt}.
	 * @param ctx the parse tree
	 */
	void exitWrite_stmt(CompilersParser.Write_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#return_stmt}.
	 * @param ctx the parse tree
	 */
	void enterReturn_stmt(CompilersParser.Return_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#return_stmt}.
	 * @param ctx the parse tree
	 */
	void exitReturn_stmt(CompilersParser.Return_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(CompilersParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(CompilersParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#expr_prefix}.
	 * @param ctx the parse tree
	 */
	void enterExpr_prefix(CompilersParser.Expr_prefixContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#expr_prefix}.
	 * @param ctx the parse tree
	 */
	void exitExpr_prefix(CompilersParser.Expr_prefixContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactor(CompilersParser.FactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactor(CompilersParser.FactorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#factor_prefix}.
	 * @param ctx the parse tree
	 */
	void enterFactor_prefix(CompilersParser.Factor_prefixContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#factor_prefix}.
	 * @param ctx the parse tree
	 */
	void exitFactor_prefix(CompilersParser.Factor_prefixContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#postfix_expr}.
	 * @param ctx the parse tree
	 */
	void enterPostfix_expr(CompilersParser.Postfix_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#postfix_expr}.
	 * @param ctx the parse tree
	 */
	void exitPostfix_expr(CompilersParser.Postfix_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#call_expr}.
	 * @param ctx the parse tree
	 */
	void enterCall_expr(CompilersParser.Call_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#call_expr}.
	 * @param ctx the parse tree
	 */
	void exitCall_expr(CompilersParser.Call_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#expr_list}.
	 * @param ctx the parse tree
	 */
	void enterExpr_list(CompilersParser.Expr_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#expr_list}.
	 * @param ctx the parse tree
	 */
	void exitExpr_list(CompilersParser.Expr_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#expr_list_tail}.
	 * @param ctx the parse tree
	 */
	void enterExpr_list_tail(CompilersParser.Expr_list_tailContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#expr_list_tail}.
	 * @param ctx the parse tree
	 */
	void exitExpr_list_tail(CompilersParser.Expr_list_tailContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(CompilersParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(CompilersParser.PrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#addop}.
	 * @param ctx the parse tree
	 */
	void enterAddop(CompilersParser.AddopContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#addop}.
	 * @param ctx the parse tree
	 */
	void exitAddop(CompilersParser.AddopContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#mulop}.
	 * @param ctx the parse tree
	 */
	void enterMulop(CompilersParser.MulopContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#mulop}.
	 * @param ctx the parse tree
	 */
	void exitMulop(CompilersParser.MulopContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#if_stmt}.
	 * @param ctx the parse tree
	 */
	void enterIf_stmt(CompilersParser.If_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#if_stmt}.
	 * @param ctx the parse tree
	 */
	void exitIf_stmt(CompilersParser.If_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#else_part}.
	 * @param ctx the parse tree
	 */
	void enterElse_part(CompilersParser.Else_partContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#else_part}.
	 * @param ctx the parse tree
	 */
	void exitElse_part(CompilersParser.Else_partContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterCond(CompilersParser.CondContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitCond(CompilersParser.CondContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#compop}.
	 * @param ctx the parse tree
	 */
	void enterCompop(CompilersParser.CompopContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#compop}.
	 * @param ctx the parse tree
	 */
	void exitCompop(CompilersParser.CompopContext ctx);
	/**
	 * Enter a parse tree produced by {@link CompilersParser#while_stmt}.
	 * @param ctx the parse tree
	 */
	void enterWhile_stmt(CompilersParser.While_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CompilersParser#while_stmt}.
	 * @param ctx the parse tree
	 */
	void exitWhile_stmt(CompilersParser.While_stmtContext ctx);
}