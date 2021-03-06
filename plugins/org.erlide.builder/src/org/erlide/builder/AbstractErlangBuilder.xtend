package org.erlide.builder

import com.google.common.eventbus.EventBus
import com.google.inject.Inject
import com.google.inject.name.Named
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.IResource
import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.IConfigurationElement
import org.eclipse.core.runtime.IExecutableExtension
import org.eclipse.xtend.lib.Property
import org.erlide.builder.markers.AddErlangMarkerEvent
import org.erlide.builder.markers.RemoveMarkersEvent

abstract class AbstractErlangBuilder implements IErlangBuilder, IExecutableExtension {
	@Property IProject project
	@Inject @Named("erlangBuilder") EventBus builderEventBus
	String id
	
	new()  {
		//BuilderPlugin::instance.injector.injectMembers(this)
	}

	new(IProject project, EventBus eventBus) {
		this()
		this._project = project
		this.builderEventBus = eventBus
	}
	
	override setInitializationData(IConfigurationElement config, String propertyName, Object data) throws CoreException {
		id = config.getAttribute("id")
	}
	
	override String getId() {
		id
	}
		
	override void loadConfiguration() {
		// do nothing by default
	}
	
	def removeMarkers(IResource resource, String type) {
		builderEventBus.post(new RemoveMarkersEvent(resource, type))
	}
	
	def addMarker(IFile file, CompilerProblem problem) {
		builderEventBus.post(new AddErlangMarkerEvent(file, problem))
	}
	
}