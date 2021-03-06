package org.eclipse.xtext.parser.unorderedGroups.idea.lang.parser;

import org.eclipse.xtext.parser.unorderedGroups.idea.lang.BacktrackingBug325745TestLanguageElementTypeProvider;
import org.eclipse.xtext.parser.unorderedGroups.idea.lang.psi.impl.BacktrackingBug325745TestLanguageFileImpl;
import org.eclipse.xtext.idea.parser.AbstractXtextParserDefinition;

import com.google.inject.Inject;
import com.intellij.lang.ASTNode;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;

public class BacktrackingBug325745TestLanguageParserDefinition extends AbstractXtextParserDefinition {

	@Inject 
	private BacktrackingBug325745TestLanguageElementTypeProvider elementTypeProvider;

	@Override
	public PsiFile createFile(FileViewProvider viewProvider) {
		return new BacktrackingBug325745TestLanguageFileImpl(viewProvider);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public PsiElement createElement(ASTNode node) {
		return super.createElement(node);
	}

}
