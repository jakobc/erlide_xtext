package org.erlide.builder

import org.eclipse.core.resources.IProject

import static extension org.erlide.builder.ProjectBuilderExtensions.*

class SgsnBuilder extends ExternalBuilder {
	
	public static val String ID = "org.erlide.builder.sgsnbuilder"
	
	new() {
		super()
		setupCommands()
	}

	new(IProject project, BuilderExecutor executor) {
		super(project, executor, null)
		setupCommands()
		loadConfiguration
	}
	
	def setupCommands() {
		cleanCmdLine = newArrayList("rm", "-rf", "do3/erlang/")
		fullCmdLine = newArrayList("make", "beam")
		singleCmdLine = newArrayList("make", "-W", "$file")
	}
	
}