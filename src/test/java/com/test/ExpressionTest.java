package com.test;

import org.junit.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class ExpressionTest extends  AopTest{
    @Test
    public void expressionTest(){
        ExpressionParser expressionParser = new SpelExpressionParser();
        EvaluationContext evaluationContext = new StandardEvaluationContext();
        System.out.println(expressionParser.parseExpression("#employer.id").getValue(evaluationContext));
    }
}
