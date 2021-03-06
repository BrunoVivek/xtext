/*******************************************************************************
 * Copyright (c) 2015 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.web.server.test

import org.eclipse.xtext.web.server.validation.ValidationResult
import org.junit.Test

class ValidationTest extends AbstractWebServerTest {
	
	protected def assertValidationResult(String resourceContent, String expectedResult) {
		val sessionStore = new HashMapSessionStore
		val validate = dispatcher.getService('/validation', #{'fullText' -> resourceContent}, sessionStore)
		assertFalse(validate.hasSideEffects)
		assertTrue(validate.hasTextInput)
		val result = validate.service.apply() as ValidationResult
		assertEquals(expectedResult, result.toString)
	}
	
	@Test def testSyntaxError() {
		'entiti foo {}'.assertValidationResult('''
			ValidationResult [
			  entries = ArrayList (
			    Entry [
			      description = "missing EOF at 'entiti'"
			      severity = "error"
			      line = 1
			      startOffset = 0
			      endOffset = 6
			    ]
			  )
			]''')
	}
	
	@Test def testCrossRefError() {
		'entity foo { bar : Strink }'.assertValidationResult('''
				ValidationResult [
				  entries = ArrayList (
				    Entry [
				      description = "Couldn't resolve reference to JvmType 'Strink'."
				      severity = "error"
				      line = 1
				      startOffset = 19
				      endOffset = 25
				    ]
				  )
				]''')
	}
	
	@Test def testValidateFile() {
		val file = createFile('entiti foo {}')
		val sessionStore = new HashMapSessionStore
		val validate = dispatcher.getService('/validation', #{'resource' -> file.name}, sessionStore)
		assertFalse(validate.hasSideEffects)
		assertFalse(validate.hasTextInput)
		val result = validate.service.apply() as ValidationResult
		val String expectedResult = '''
			ValidationResult [
			  entries = ArrayList (
			    Entry [
			      description = "missing EOF at 'entiti'"
			      severity = "error"
			      line = 1
			      startOffset = 0
			      endOffset = 6
			    ]
			  )
			]'''
		assertEquals(expectedResult, result.toString)
	}
	
}
