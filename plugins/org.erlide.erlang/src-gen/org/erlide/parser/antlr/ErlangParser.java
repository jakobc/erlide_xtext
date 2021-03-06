/*
* generated by Xtext
*/
package org.erlide.parser.antlr;

import com.google.inject.Inject;

import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.erlide.services.ErlangGrammarAccess;

public class ErlangParser extends org.eclipse.xtext.parser.antlr.AbstractAntlrParser {
	
	@Inject
	private ErlangGrammarAccess grammarAccess;
	
	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_SL_COMMENT");
	}
	
	@Override
	protected org.erlide.parser.antlr.internal.InternalErlangParser createParser(XtextTokenStream stream) {
		return new org.erlide.parser.antlr.internal.InternalErlangParser(stream, getGrammarAccess());
	}
	
	@Override 
	protected String getDefaultRuleName() {
		return "Module";
	}
	
	public ErlangGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}
	
	public void setGrammarAccess(ErlangGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
	
}
