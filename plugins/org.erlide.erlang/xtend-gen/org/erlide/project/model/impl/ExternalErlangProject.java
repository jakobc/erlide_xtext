package org.erlide.project.model.impl;

import org.erlide.project.model.IErlangModel;
import org.erlide.project.model.IExternalErlangProject;
import org.erlide.project.model.impl.ErlangProject;

@SuppressWarnings("all")
public class ExternalErlangProject extends ErlangProject implements IExternalErlangProject {
  public ExternalErlangProject(final IErlangModel model) {
    super(model, null);
  }
}
