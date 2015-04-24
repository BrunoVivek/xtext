package org.eclipse.xtext.psi

import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.Condition
import com.intellij.openapi.vfs.VirtualFile
import org.eclipse.xtext.idea.lang.IXtextLanguage

class ProblemXtextFileHighlightFilter implements Condition<VirtualFile> {

	override value(VirtualFile file) {
		val fileType = file.fileType
		if (fileType instanceof LanguageFileType)
			return fileType.language instanceof IXtextLanguage
		else
			false
	}

}