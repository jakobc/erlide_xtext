package org.erlide.ui.outline;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.ui.editor.outline.impl.EObjectNode;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;
import org.erlide.erlang.Function;
import org.erlide.erlang.ModelExtensions;

import com.google.inject.Inject;

public class HideLocalFunctionsFilterOperationsContribution extends
		ErlideFilterOperationsContribution {

	public HideLocalFunctionsFilterOperationsContribution() {
		super("Hide local functions", "hideLocalFunctions",
				"hide_local_functions.gif");
	}

	@Inject
	ModelExtensions modelExtensions;

	@Override
	protected boolean shouldShow(EObjectNode eObjectNode) {
		boolean shouldShow = eObjectNode
				.readOnly(new IUnitOfWork<Boolean, EObject>() {

					@Override
					public Boolean exec(EObject state) throws Exception {
						if (!(state instanceof Function))
							return true;
						Function function = (Function) state;
						return modelExtensions.isExported(function);
					}

				});
		return shouldShow;
	}

}
