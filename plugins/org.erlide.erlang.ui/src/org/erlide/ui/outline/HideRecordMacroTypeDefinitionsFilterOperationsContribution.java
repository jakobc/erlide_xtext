package org.erlide.ui.outline;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.xtext.ui.editor.outline.impl.EObjectNode;
import org.erlide.erlang.ErlangPackage;

public class HideRecordMacroTypeDefinitionsFilterOperationsContribution extends
		ErlideFilterOperationsContribution {

	public HideRecordMacroTypeDefinitionsFilterOperationsContribution() {
		super("Hide record, macro and type defintions",
				"hideRecordMacroTypeDefinitions", "hide_macro_record_defs.gif");
	}

	@Override
	protected boolean shouldShow(EObjectNode eObjectNode) {
		EClass eClass = eObjectNode.getEClass();
		boolean record = eClass.equals(ErlangPackage.Literals.RECORD_ATTRIBUTE);
		boolean macro = eClass
				.equals(ErlangPackage.Literals.ABSTRACT_DEFINE_ATTRIBUTE);
		boolean type = eClass
				.equals(ErlangPackage.Literals.ABSTRACT_TYPE_ATTRIBUTE);
		return !record && !macro && !type;
	}

}
