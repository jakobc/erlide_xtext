package org.erlide.ui.outline;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.xtext.ui.PluginImageHelper;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.actions.AbstractFilterOutlineContribution;
import org.eclipse.xtext.ui.editor.outline.impl.EObjectNode;
import org.erlide.erlang.Attribute;

import com.google.inject.Inject;

public class FilterOperationsContribution extends
		AbstractFilterOutlineContribution {

	  public static final String PREFERENCE_KEY = 
			    "ui.outline.filterOperations";
			 
			  @Inject
			  private PluginImageHelper imageHelper;
			  
			  @Override
			  protected boolean apply(IOutlineNode node) {
			    return !(node instanceof EObjectNode)
			        || !((EObjectNode) node).getEClass()
			          .equals(Attribute.class);
			  }
			 
			  @Override
			  public String getPreferenceKey() {
			    return PREFERENCE_KEY;
			  }
			 
			  @Override
			  protected void configureAction(Action action) {
			    action.setText("Hide attributes");
			    action.setDescription("Hide attributes");
			    action.setToolTipText("Hide attributes");
			    action.setImageDescriptor(getImageDescriptor("icons/commands/hide_attributes.gif"));
			  }
			 
			  protected ImageDescriptor getImageDescriptor(String imagePath) {
			    return ImageDescriptor.createFromImage(
			      imageHelper.getImage(imagePath));
			  }

}
