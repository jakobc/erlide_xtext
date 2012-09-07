package org.erlide.builder;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class ProjectBuilderExtensions {
  public static IResource getLinkedContent(final IProject project) {
    try {
      IResource[] _members = project.members();
      final Function1<IResource,Boolean> _function = new Function1<IResource,Boolean>() {
          public Boolean apply(final IResource it) {
            boolean _isLinked = it.isLinked();
            return Boolean.valueOf(_isLinked);
          }
        };
      final Iterable<IResource> links = IterableExtensions.<IResource>filter(((Iterable<IResource>)Conversions.doWrapArray(_members)), _function);
      int _size = IterableExtensions.size(links);
      boolean _greaterThan = (_size > 1);
      if (_greaterThan) {
        InputOutput.<String>println("too many...");
      }
      return IterableExtensions.<IResource>head(links);
    } catch (Exception _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public static IPath getPathInProject(final IProject project, final IPath path) {
    IPath _xblockexpression = null;
    {
      final IResource link = ProjectBuilderExtensions.getLinkedContent(project);
      String _name = link.getName();
      Path _path = new Path(_name);
      IPath _append = _path.append(path);
      _xblockexpression = (_append);
    }
    return _xblockexpression;
  }
}
