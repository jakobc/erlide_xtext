package org.erlide.erlang.completion

import org.eclipse.xtext.junit4.AbstractXtextTests
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.erlide.ErlangInjectorProvider
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(typeof(XtextRunner))
@InjectWith(typeof(ErlangInjectorProvider))
class CompletionTest extends AbstractXtextTests {
	
	@Test
	def void completeModule() {
		fail("not implemented yet")
	}
	
	@Test
	def void completeLocalFunction() {
		fail("not implemented yet")
	}
	
	@Test
	def void completeRemoteFunction() {
		fail("not implemented yet")
	}
	
	@Test
	def void completeRecord() {
		fail("not implemented yet")
	}
	
	@Test
	def void completeRecordField() {
		fail("not implemented yet")
	}
	
	@Test
	def void completeMacro() {
		fail("not implemented yet")
	}
	
	@Test
	def void completeVariable() {
		fail("not implemented yet")
	}
	
	
}
