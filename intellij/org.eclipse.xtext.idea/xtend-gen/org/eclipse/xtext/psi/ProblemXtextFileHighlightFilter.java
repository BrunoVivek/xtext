package org.eclipse.xtext.psi;

import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.vfs.VirtualFile;
import org.eclipse.xtext.idea.lang.IXtextLanguage;

@SuppressWarnings("all")
public class ProblemXtextFileHighlightFilter implements Condition<VirtualFile> {
  @Override
  public boolean value(final VirtualFile file) {
    boolean _xblockexpression = false;
    {
      final FileType fileType = file.getFileType();
      boolean _xifexpression = false;
      if ((fileType instanceof LanguageFileType)) {
        Language _language = ((LanguageFileType)fileType).getLanguage();
        return (_language instanceof IXtextLanguage);
      } else {
        _xifexpression = false;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
}
