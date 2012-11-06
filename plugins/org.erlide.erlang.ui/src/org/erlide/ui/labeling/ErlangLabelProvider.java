/*
 * generated by Xtext
 */
package org.erlide.ui.labeling;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;

import com.google.inject.Inject;

/**
 * Provides labels for a EObjects.
 * 
 * see
 * http://www.eclipse.org/Xtext/documentation/latest/xtext.html#labelProvider
 */
public class ErlangLabelProvider extends ErlangLabelProviderBase {

    @Inject
    public ErlangLabelProvider(final AdapterFactoryLabelProvider delegate) {
        super(delegate);
    }

}
