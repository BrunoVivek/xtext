/**
 * Copyright (c) 2015 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.xtext.idea.resource.impl;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtend.lib.annotations.Delegate;
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.psi.impl.BaseXtextFile;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IReferenceDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.xbase.lib.Pure;

@FinalFieldsConstructor
@SuppressWarnings("all")
public class PsiFileBasedResourceDescription implements IResourceDescription {
  @Accessors
  private final BaseXtextFile xtextFile;
  
  @Delegate
  private final IResourceDescription resourceDescription;
  
  public PsiFileBasedResourceDescription(final BaseXtextFile xtextFile) {
    this.xtextFile = xtextFile;
    final XtextResource resource = xtextFile.getResource();
    IResourceServiceProvider _resourceServiceProvider = resource.getResourceServiceProvider();
    final IResourceDescription.Manager manager = _resourceServiceProvider.getResourceDescriptionManager();
    IResourceDescription _resourceDescription = manager.getResourceDescription(resource);
    this.resourceDescription = _resourceDescription;
  }
  
  @Override
  public String toString() {
    Class<? extends PsiFileBasedResourceDescription> _class = this.getClass();
    String _name = _class.getName();
    String _plus = (_name + ":");
    return (_plus + this.resourceDescription);
  }
  
  public PsiFileBasedResourceDescription(final BaseXtextFile xtextFile, final IResourceDescription resourceDescription) {
    super();
    this.xtextFile = xtextFile;
    this.resourceDescription = resourceDescription;
  }
  
  @Pure
  public BaseXtextFile getXtextFile() {
    return this.xtextFile;
  }
  
  public Iterable<IEObjectDescription> getExportedObjects() {
    return this.resourceDescription.getExportedObjects();
  }
  
  public Iterable<QualifiedName> getImportedNames() {
    return this.resourceDescription.getImportedNames();
  }
  
  public Iterable<IReferenceDescription> getReferenceDescriptions() {
    return this.resourceDescription.getReferenceDescriptions();
  }
  
  public URI getURI() {
    return this.resourceDescription.getURI();
  }
  
  public Iterable<IEObjectDescription> getExportedObjects(final EClass type, final QualifiedName name, final boolean ignoreCase) {
    return this.resourceDescription.getExportedObjects(type, name, ignoreCase);
  }
  
  public Iterable<IEObjectDescription> getExportedObjectsByObject(final EObject object) {
    return this.resourceDescription.getExportedObjectsByObject(object);
  }
  
  public Iterable<IEObjectDescription> getExportedObjectsByType(final EClass type) {
    return this.resourceDescription.getExportedObjectsByType(type);
  }
  
  public boolean isEmpty() {
    return this.resourceDescription.isEmpty();
  }
}
