lexer grammar AnimatorLexer;

ID : [a-zA-Z_][a-zA-Z_0-9]* ;
MUL : '*';
DIV : '/';
SUB : '-';
ADD : '+';
POW : '^';
COMMA : ',';
LCHEV : '<';
RCHEV : '>';
LPAREN : '(';
RPAREN : ')';
POINT : '.';
DIGIT : [0-9];
EQ : '=';
SEMI : ';';
FILEPATH_OPEN : '"' -> pushMode(FILEPATHMODE);
WS : [ \t\n\r]+ -> skip;

mode FILEPATHMODE;
FILEPATH_END : '"' -> popMode;
FILEPATH : (~[\n\t\r"])+;

