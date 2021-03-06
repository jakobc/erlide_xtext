package org.erlide.builder

import com.google.common.eventbus.DeadEvent
import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import com.google.inject.name.Named
import java.util.Map
import javax.inject.Inject
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.IResource
import org.eclipse.core.resources.IncrementalProjectBuilder
import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.OperationCanceledException
import org.eclipse.core.runtime.SubMonitor
import org.erlide.common.NatureConstants
import org.erlide.common.util.ErlLogger
import org.erlide.builder.markers.BuilderMarkerUpdater
import org.erlide.builder.markers.RemoveMarkersEvent
import org.eclipse.core.runtime.NullProgressMonitor

class ErlangBuilder extends IncrementalProjectBuilder {

    public static String BUILDER_ID = "org.erlide.builder.erlangBuilder"
    public static String OLD_BUILDER_ID = "org.erlide.core.erlbuilder"
    public static String MARKER_TYPE = "org.erlide.builder.erlangBuildProblem"

	@Inject BuilderMarkerUpdater markerUpdater
	@Inject BuildersProvider builderProvider
	@Inject ErlLogger log
	@Inject @Named("erlangBuilder") EventBus builderEventBus

	new() {
		// we need this, can't use extensionFactory on builder
        BuilderPlugin::instance.injector.injectMembers(this);
        builderEventBus.register(this) // for dead event reporting
	}
	
	new(BuilderMarkerUpdater markerUpdater, BuildersProvider builderProvider, EventBus eventBus) {
		this.markerUpdater = markerUpdater
		this.builderProvider = builderProvider
		this.builderEventBus = eventBus
        builderEventBus.register(this)
	}
	
	override protected IProject[] build(int kind, Map<String,String> args, IProgressMonitor monitor) throws CoreException {
		val startTime = System::currentTimeMillis
		cleanXtextMarkers(project)
		println(monitor)
		val progress = SubMonitor::convert(monitor, 100)
		try {
	        val builder = getProjectBuilder(project)
	        if (builder==null) {
	        	log.warn("Project "+project+" has no Erlang builder")
	        } else {
	        	switch(kind) {
	        		case FULL_BUILD: 
			        	builder.fullBuild(progress.newChild(100))
			        default:
			        	builder.incrementalBuild(getDelta(project), progress.newChild(100))
	        	}
	        }
		} catch (CoreException e) {
			log.error(e)
			throw e
		} catch (OperationCanceledException e) {
			forgetLastBuiltState
			throw e
		} catch (Exception e) {
			log.error(e)
		} finally {
			monitor?.done
			log.info("Build " + getProject().getName() + " in " + (System::currentTimeMillis - startTime) + " ms")
		}
		
		return getProject().getReferencedProjects();
    }

	override protected clean(IProgressMonitor monitor) throws CoreException {
		cleanXtextMarkers(project)
        val builder = getProjectBuilder(project)
        try{
        	if (builder==null) {
	        	log.warn("Project "+project+" has no Erlang builder")
	        } else {
	        	builder.clean(new NullProgressMonitor())
	        }
	        builderEventBus.post(new RemoveMarkersEvent(project, MARKER_TYPE))
        } finally {
        	monitor?.done
        }
	}
	
	def getProjectBuilder(IProject project) {
		val bId = SgsnBuilder::ID // TODO get project's config
		val builder = builderProvider.get(bId)
        builder.project = project
        builder.loadConfiguration
        builder
	}
	
	def private cleanXtextMarkers(IProject project) {
		// TODO this removes all xtext markers, which are only created when file is open
		if(project.hasNature(NatureConstants::OLD_NATURE_ID)) {
			newArrayList("erlang.check.fast", 
						"erlang.check.normal", 
						"erlang.check.expensive").forEach [ 
				project.deleteMarkers("org.erlide.erlang.ui."+it, true, IResource::DEPTH_INFINITE)
			]
		}
	}
	
	@Subscribe
	def void noBuilderHandler(DeadEvent dead) {
		log.warn("No builder configured for project %s", project.name)
		log.debug("From: %s     Event %s", dead.source, dead.event)
	}	
}

