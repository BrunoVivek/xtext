/*******************************************************************************
 * Copyright (c) 2015 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.idea.resource.impl

import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.Delegate
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtext.psi.impl.BaseXtextFile
import org.eclipse.xtext.resource.IResourceDescription

@FinalFieldsConstructor
class PsiFileBasedResourceDescription implements IResourceDescription {

	@Accessors
	val BaseXtextFile xtextFile

	@Delegate
	val IResourceDescription resourceDescription

	new(BaseXtextFile xtextFile) {
		this.xtextFile = xtextFile
		val resource = xtextFile.resource
		val manager = resource.resourceServiceProvider.resourceDescriptionManager
		this.resourceDescription = manager.getResourceDescription(resource)
	}

	override toString() {
		class.name + ':' + resourceDescription
	}

}
