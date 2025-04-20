parser grammar AnimatorParser;
options { tokenVocab=AnimatorLexer; }

scene:
      variableAssignement* animation+ EOF
;

animation:
      imagePath lifetime? transformList SEMI
;

lifetime:
      LCHEV start=integerConstant (COMMA end=integerConstant)? RCHEV
;

variableAssignement:
      ID EQ interpolatorList SEMI
;

transformList:
      transform*
;

transform:
      name=ID LPAREN argumentList? RPAREN
;

imagePath :
      FILEPATH_OPEN path=FILEPATH FILEPATH_END
;

interpolatorList :
      interpolator+
;

interpolator:
      name=ID LCHEV duration=integerConstant? RCHEV LPAREN argumentList? RPAREN
;

argumentList:
      args+=expr (COMMA args+=expr)*
;

expr:
      number                                    #literal
    | ID                                        #varRead
    | LPAREN expr RPAREN                        #parenExpr
    | op=SUB right=expr                         #unOp
    | <assoc=right> left=expr op=POW right=expr #binOp
    | left=expr op=(MUL|DIV) right=expr         #binOp
    | left=expr op=(ADD|SUB) right=expr         #binOp
;

number :
      integerConstant
    | floatingPointConstant
;
integerConstant :
      DIGIT+
;
floatingPointConstant :
      DIGIT+? POINT DIGIT+
    | DIGIT+ POINT
    | DIGIT+
;
