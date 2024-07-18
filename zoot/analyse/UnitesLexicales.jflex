package zoot.analyse ;

import java_cup.runtime.*;
import zoot.exceptions.AnalyseLexicaleException;
      
%%
   
%class AnalyseurLexical
%public

%line
%column
    
%type Symbol
%eofval{
        return symbol(CodesLexicaux.EOF) ;
%eofval}

%cup

%{

  private StringBuilder chaine ;

  private Symbol symbol(int type) {
	return new Symbol(type, yyline, yycolumn) ;
  }

  private Symbol symbol(int type, Object value) {
	return new Symbol(type, yyline, yycolumn, value) ;
  }
%}

csteE = [0-9]+
boolE = (vrai|faux)
finDeLigne = \r|\n
espace = {finDeLigne}  | [ \t\f]
idf = [a-zA-Z][a-zA-Z0-9]*

%%
"//".*                                    { /* DO NOTHING */ }

"entier"               { return symbol(CodesLexicaux.ENTIER); }
"booleen"              { return symbol(CodesLexicaux.BOOL); }

"debut"                { return symbol(CodesLexicaux.DEBUT); }
"variables"            { return symbol(CodesLexicaux.VARIABLES); }
"fonctions"            { return symbol(CodesLexicaux.FONCTIONS); }
"fin"              	   { return symbol(CodesLexicaux.FIN); }



"ecrire"               { return symbol(CodesLexicaux.ECRIRE); }
"retourne"             { return symbol(CodesLexicaux.RETOURNER); }

";"                    { return symbol(CodesLexicaux.POINTVIRGULE); }
","                    { return symbol(CodesLexicaux.VIRGULE); }
"("                    { return symbol(CodesLexicaux.PAR_OUVR); }
")"                    { return symbol(CodesLexicaux.PAR_FERM); }

"si"                    { return symbol(CodesLexicaux.SI); }
"alors"                    { return symbol(CodesLexicaux.ALORS); }
"sinon"                    { return symbol(CodesLexicaux.SINON); }
"finsi"                    { return symbol(CodesLexicaux.FINSI); }

"repeter"                    { return symbol(CodesLexicaux.REPETER); }
"jusqua"                    { return symbol(CodesLexicaux.JUSQUA); }
"finrepeter"                    { return symbol(CodesLexicaux.FINREPETER); }

"="                    { return symbol(CodesLexicaux.EQUAL); }
"+"                    { return symbol(CodesLexicaux.PLUS); }
"-"                    { return symbol(CodesLexicaux.MOINS); }
"*"                    { return symbol(CodesLexicaux.MULT); }

"et"                    { return symbol(CodesLexicaux.ET); }
"ou"                    { return symbol(CodesLexicaux.OU); }
"non"                    { return symbol(CodesLexicaux.NON); }
"=="                    { return symbol(CodesLexicaux.DOUBLEEQUAL); }
"!="                    { return symbol(CodesLexicaux.DIFF); }
"<"                    { return symbol(CodesLexicaux.INF); }

{csteE}      	       { return symbol(CodesLexicaux.CSTENTIERE, yytext()); }

{boolE}      	       { return symbol(CodesLexicaux.CSTBOOL, yytext()); }

{espace}               { }

{idf}                  { return symbol(CodesLexicaux.IDF, yytext()); }

.                      { throw new AnalyseLexicaleException(yyline, yycolumn, yytext()) ; }

