package com.craftinginterpreters.lox;

public class AstPrinterPostfix implements Expr.Visitor<String> {
  String print(Expr expr) {
    return expr.accept(this);
  }

  @Override
  public String visitBinaryExpr(Expr.Binary expr) {
    return parenthesize(expr.left, expr.right, expr.operator.lexeme);
  }

  @Override
  public String visitGroupingExpr(Expr.Grouping expr) {
    return parenthesize(expr.expression, "group");
  }

  @Override
  public String visitLiteralExpr(Expr.Literal expr) {
    if (expr.value == null) return "nil";
    return expr.value.toString();
  }

  @Override
  public String visitUnaryExpr(Expr.Unary expr) {
    return parenthesize(expr.right, expr.operator.lexeme);
  }

  private String parenthesize(Expr expr, String name) {
    StringBuilder builder = new StringBuilder();

    builder
      .append("(")
      .append(expr.accept(this))
      .append(" ")
      .append(name)
      .append(")");

    return builder.toString();
  }

  private String parenthesize(Expr expr1, Expr expr2, String name) {
    StringBuilder builder = new StringBuilder();

    builder
        .append("(")
        .append(expr1.accept(this))
        .append(" ")
        .append(expr2.accept(this))
        .append(" ")
        .append(name)
        .append(")");

    return builder.toString();
  }

  // public static void main(String[] args) {
  //   Expr expression = new Expr.Binary(
  //       new Expr.Binary(
  //           new Expr.Literal(123), 
  //           new Token(TokenType.PLUS, "+", null, 0), 
  //           new Expr.Literal(456)
  //       ),
  //       new Token(TokenType.PLUS, "+", null, 1),
  //       new Expr.Unary(
  //           new Token(TokenType.MINUS, "-", null, 0), 
  //           new Expr.Literal(6456)
  //       )
  //   );

  //   System.out.println("\n");
  //   System.out.println(new AstPrinterPostfix().print(expression));
  //   System.out.println("\n");
  // }   
}
