package org.erlide.ui.outline;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.xtext.ui.editor.outline.impl.EObjectNode;
import org.erlide.erlang.ErlangPackage;

public class HideAttributesFilterOperationsContribution extends
		ErlideFilterOperationsContribution {

	public HideAttributesFilterOperationsContribution() {
		super("Hide attributes", "hideAttributes", "hide_attributes.gif");
	}

	@Override
	protected boolean shouldShow(EObjectNode eObjectNode) {
		EClass eClass = eObjectNode.getEClass();
		EList<EClass> eSuperTypes = eClass.getESuperTypes();
		boolean attribute = eSuperTypes.contains(ErlangPackage.Literals.ATTRIBUTE);
		boolean record = eClass.equals(ErlangPackage.Literals.RECORD_ATTRIBUTE);
		boolean macro =eClass.equals(ErlangPackage.Literals.ABSTRACT_DEFINE_ATTRIBUTE);
		boolean type = eClass.equals(ErlangPackage.Literals.ABSTRACT_TYPE_ATTRIBUTE);
		return !attribute || record || macro || type;
	}
	
}
