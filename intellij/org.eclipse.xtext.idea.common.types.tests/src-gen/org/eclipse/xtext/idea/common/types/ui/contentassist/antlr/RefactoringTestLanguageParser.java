/*
* generated by Xtext
*/
package org.eclipse.xtext.idea.common.types.ui.contentassist.antlr;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.RecognitionException;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.ui.editor.contentassist.antlr.AbstractContentAssistParser;
import org.eclipse.xtext.ui.editor.contentassist.antlr.FollowElement;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;

import com.google.inject.Inject;

import org.eclipse.xtext.idea.common.types.services.RefactoringTestLanguageGrammarAccess;

public class RefactoringTestLanguageParser extends AbstractContentAssistParser {
	
	@Inject
	private RefactoringTestLanguageGrammarAccess grammarAccess;
	
	private Map<AbstractElement, String> nameMappings;
	
	@Override
	protected org.eclipse.xtext.idea.common.types.ui.contentassist.antlr.internal.InternalRefactoringTestLanguageParser createParser() {
		org.eclipse.xtext.idea.common.types.ui.contentassist.antlr.internal.InternalRefactoringTestLanguageParser result = new org.eclipse.xtext.idea.common.types.ui.contentassist.antlr.internal.InternalRefactoringTestLanguageParser(null);
		result.setGrammarAccess(grammarAccess);
		return result;
	}
	
	@Override
	protected String getRuleName(AbstractElement element) {
		if (nameMappings == null) {
			nameMappings = new HashMap<AbstractElement, String>() {
				private static final long serialVersionUID = 1L;
				{
					put(grammarAccess.getReferenceHolderAccess().getGroup(), "rule__ReferenceHolder__Group__0");
					put(grammarAccess.getFQNAccess().getGroup(), "rule__FQN__Group__0");
					put(grammarAccess.getFQNAccess().getGroup_1(), "rule__FQN__Group_1__0");
					put(grammarAccess.getFQNAccess().getGroup_2(), "rule__FQN__Group_2__0");
					put(grammarAccess.getModelAccess().getReferenceHolderAssignment(), "rule__Model__ReferenceHolderAssignment");
					put(grammarAccess.getReferenceHolderAccess().getNameAssignment_0(), "rule__ReferenceHolder__NameAssignment_0");
					put(grammarAccess.getReferenceHolderAccess().getDefaultReferenceAssignment_1(), "rule__ReferenceHolder__DefaultReferenceAssignment_1");
				}
			};
		}
		return nameMappings.get(element);
	}
	
	@Override
	protected Collection<FollowElement> getFollowElements(AbstractInternalContentAssistParser parser) {
		try {
			org.eclipse.xtext.idea.common.types.ui.contentassist.antlr.internal.InternalRefactoringTestLanguageParser typedParser = (org.eclipse.xtext.idea.common.types.ui.contentassist.antlr.internal.InternalRefactoringTestLanguageParser) parser;
			typedParser.entryRuleModel();
			return typedParser.getFollowElements();
		} catch(RecognitionException ex) {
			throw new RuntimeException(ex);
		}		
	}
	
	@Override
	protected String[] getInitialHiddenTokens() {
		return new String[] { "RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT" };
	}
	
	public RefactoringTestLanguageGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}
	
	public void setGrammarAccess(RefactoringTestLanguageGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}