// Generated from D:/Users/Jase/IdeaProjects/Compiler\Compilers.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CompilersParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CompilersVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link CompilersParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(CompilersParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#id}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId(CompilersParser.IdContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#pgm_body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPgm_body(CompilersParser.Pgm_bodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecl(CompilersParser.DeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#string_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString_decl(CompilersParser.String_declContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#str}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStr(CompilersParser.StrContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#var_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_decl(CompilersParser.Var_declContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#var_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_type(CompilersParser.Var_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#any_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAny_type(CompilersParser.Any_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#id_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId_list(CompilersParser.Id_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#id_tail}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId_tail(CompilersParser.Id_tailContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#param_decl_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam_decl_list(CompilersParser.Param_decl_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#param_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam_decl(CompilersParser.Param_declContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#param_decl_tail}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam_decl_tail(CompilersParser.Param_decl_tailContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#func_declarations}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunc_declarations(CompilersParser.Func_declarationsContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#func_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunc_decl(CompilersParser.Func_declContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#func_body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunc_body(CompilersParser.Func_bodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#stmt_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmt_list(CompilersParser.Stmt_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmt(CompilersParser.StmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#base_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBase_stmt(CompilersParser.Base_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#assign_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign_stmt(CompilersParser.Assign_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#assign_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign_expr(CompilersParser.Assign_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#read_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRead_stmt(CompilersParser.Read_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#write_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWrite_stmt(CompilersParser.Write_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#return_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn_stmt(CompilersParser.Return_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(CompilersParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#expr_prefix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_prefix(CompilersParser.Expr_prefixContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactor(CompilersParser.FactorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#factor_prefix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactor_prefix(CompilersParser.Factor_prefixContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#postfix_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfix_expr(CompilersParser.Postfix_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#call_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCall_expr(CompilersParser.Call_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#expr_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_list(CompilersParser.Expr_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#expr_list_tail}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_list_tail(CompilersParser.Expr_list_tailContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(CompilersParser.PrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#addop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddop(CompilersParser.AddopContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#mulop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulop(CompilersParser.MulopContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#if_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_stmt(CompilersParser.If_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#else_part}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElse_part(CompilersParser.Else_partContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCond(CompilersParser.CondContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#compop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompop(CompilersParser.CompopContext ctx);
	/**
	 * Visit a parse tree produced by {@link CompilersParser#while_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile_stmt(CompilersParser.While_stmtContext ctx);
}