package org.erlide.builder

import org.eclipse.core.resources.IProject

import static extension org.erlide.builder.ProjectBuilderExtensions.*

class MakeBuilder extends ExternalBuilder {

	new() {
		super()
		setupCommands()
	}

	new(IProject project, BuilderMarkerUpdater markerUpdater) {
		super(project, markerUpdater)
		setupCommands()
		setupConfig(project)
	} 
	
	new(IProject project, BuilderMarkerUpdater markerUpdater, BuilderExecutor executor) {
		super(project, markerUpdater, executor)
		setupCommands()
		setupConfig(project)
	}
	
	def setupCommands() {
		cleanCmdLine = newArrayList("make", "clean")
		fullCmdLine = newArrayList("make", "beam")
		singleCmdLine = newArrayList("make", "-W", "$file")
	}
	
	def setupConfig(IProject project) {
		if(project.location.toPortableString.startsWith("/vobs/gsn")) {
			workingDir = project.linkedContent?.location
			println("WD="+workingDir)
		}
	}

}