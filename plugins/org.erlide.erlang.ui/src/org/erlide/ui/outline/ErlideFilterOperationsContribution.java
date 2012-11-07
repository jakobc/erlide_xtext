package org.erlide.ui.outline;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.actions.AbstractFilterOutlineContribution;
import org.eclipse.xtext.ui.editor.outline.impl.EObjectNode;
import org.erlide.common.ui.ErlideImageHelper;

import com.google.inject.Inject;

public abstract class ErlideFilterOperationsContribution extends
		AbstractFilterOutlineContribution {

	public static final String PREFERENCE_KEY_ROOT = "ui.outline.filterOperations.";

	@Inject
	private ErlideImageHelper imageHelper;

	private String text;
	private final String preferenceKey;

	private final String imageName;

	public ErlideFilterOperationsContribution(String text,
			String preferenceKey, String imageName) {
		this.text = text;
		this.preferenceKey = preferenceKey;
		this.imageName = imageName;
	}

	@Override
	protected boolean apply(IOutlineNode node) {
		if (!(node instanceof EObjectNode))
			return true;
		EObjectNode eObjectNode = (EObjectNode) node;
		return shouldShow(eObjectNode);
	}

	protected abstract boolean shouldShow(EObjectNode eObjectNode);
	
	@Override
	public String getPreferenceKey() {
		return PREFERENCE_KEY_ROOT + preferenceKey;
	}

	@Override
	protected void configureAction(Action action) {
		action.setText(text);
		action.setDescription(text);
		action.setToolTipText(text);
		action.setImageDescriptor(getImageDescriptor("commands/" + imageName));
	}

	protected ImageDescriptor getImageDescriptor(String imagePath) {
		return ImageDescriptor.createFromImage(imageHelper.getImage(imagePath));
	}

}
