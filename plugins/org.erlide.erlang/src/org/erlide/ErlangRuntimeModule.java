/*
 * generated by Xtext
 */
package org.erlide;

import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.linking.ILinkingService;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.erlide.common.CommonModule;
import org.erlide.conversion.ErlangValueConverterService;
import org.erlide.naming.ErlangQualifiedNameConverter;
import org.erlide.naming.ErlangQualifiedNameProvider;
import org.erlide.project.ErlideProjectModule;
import org.erlide.scoping.ErlangLinkingService;

import com.google.inject.Binder;

/**
 * Use this class to register components to be used at runtime / without the
 * Equinox extension registry.
 */
public class ErlangRuntimeModule extends org.erlide.AbstractErlangRuntimeModule {
    @Override
    public void configure(final Binder binder) {
        super.configure(binder);
        binder.install(new CommonModule());
        binder.install(new ErlideProjectModule());
    }

    @Override
    public Class<? extends IValueConverterService> bindIValueConverterService() {
        return ErlangValueConverterService.class;
    }

    @Override
    public Class<? extends org.eclipse.xtext.naming.IQualifiedNameProvider> bindIQualifiedNameProvider() {
        return ErlangQualifiedNameProvider.class;
    }

    @Override
    public Class<? extends ILinkingService> bindILinkingService() {
        return ErlangLinkingService.class;
    }

    public Class<? extends IQualifiedNameConverter> bindIQualifiedNameConverter() {
        return ErlangQualifiedNameConverter.class;
    }

}
