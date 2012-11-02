package org.erlide.scoping;

import com.google.inject.Inject;
import com.google.inject.Provider;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.junit4.AbstractXtextTests;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.xbase.lib.Pair;
import org.erlide.ErlangInjectorProvider;
import org.erlide.erlang.Atom;
import org.erlide.erlang.AtomRefTarget;
import org.erlide.erlang.DefineAttribute;
import org.erlide.erlang.ErlangTestExtensions;
import org.erlide.erlang.Macro;
import org.erlide.erlang.ModelExtensions;
import org.erlide.erlang.Module;
import org.erlide.erlang.util.ErlangTestingHelper;
import org.erlide.scoping.ErlangLinkingHelper;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(value = XtextRunner.class)
@InjectWith(value = ErlangInjectorProvider.class)
@SuppressWarnings("all")
public class ErlangLinkingTest extends AbstractXtextTests {
  @Inject
  private ErlangTestingHelper parser;
  
  @Inject
  private ModelExtensions _modelExtensions;
  
  @Inject
  private ErlangLinkingHelper _erlangLinkingHelper;
  
  @Inject
  private ErlangTestExtensions _erlangTestExtensions;
  
  @Inject
  private Provider<XtextResourceSet> resourceSetProvider;
  
  @Test
  public void resolve_noLink() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("-module(m).");
    _builder.newLine();
    _builder.append("f() ->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("[\u00DFok, 0].");
    _builder.newLine();
    final Pair<Module,List<Integer>> module = this.parser.parse(_builder.toString());
    EObject _objectAtMarker = this._erlangTestExtensions.getObjectAtMarker(module);
    final Atom atom = ((Atom) _objectAtMarker);
    AtomRefTarget _atomReference = this._erlangLinkingHelper.getAtomReference(atom);
    Matcher<AtomRefTarget> _nullValue = Matchers.<AtomRefTarget>nullValue();
    Matcher<AtomRefTarget> _is = Matchers.<AtomRefTarget>is(_nullValue);
    MatcherAssert.<AtomRefTarget>assertThat(_atomReference, _is);
  }
  
  @Test
  public void resolve_localCall() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("-module(m).");
    _builder.newLine();
    _builder.append("f() ->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\u00DFg(3),");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ok.");
    _builder.newLine();
    _builder.append("\u00DFg(X) -> X.");
    _builder.newLine();
    final Pair<Module,List<Integer>> module = this.parser.parse(_builder.toString());
    EObject _objectAtMarker = this._erlangTestExtensions.getObjectAtMarker(module);
    final Atom atom = ((Atom) _objectAtMarker);
    final EObject tgt = this._erlangTestExtensions.getObjectAtMarker(module, 1);
    AtomRefTarget _atomReference = this._erlangLinkingHelper.getAtomReference(atom);
    Matcher<? super EObject> _is = Matchers.<EObject>is(tgt);
    MatcherAssert.<AtomRefTarget>assertThat(_atomReference, _is);
  }
  
  @Test
  public void resolve_localCall_spec() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("-module(m).");
    _builder.newLine();
    _builder.append("-spec \u00DFf() -> \'ok\'.");
    _builder.newLine();
    _builder.append("\u00DFf() ->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("g(3),");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ok.");
    _builder.newLine();
    final Pair<Module,List<Integer>> module = this.parser.parse(_builder.toString());
    EObject _objectAtMarker = this._erlangTestExtensions.getObjectAtMarker(module);
    final Atom atom = ((Atom) _objectAtMarker);
    final EObject tgt = this._erlangTestExtensions.getObjectAtMarker(module, 1);
    AtomRefTarget _atomReference = this._erlangLinkingHelper.getAtomReference(atom);
    Matcher<? super EObject> _is = Matchers.<EObject>is(tgt);
    MatcherAssert.<AtomRefTarget>assertThat(_atomReference, _is);
  }
  
  @Test
  public void resolve_remoteCall() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("-module(m).");
    _builder.newLine();
    _builder.append("-export([g/1]).");
    _builder.newLine();
    _builder.append("f() ->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("m:\u00DFg(3),");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ok.");
    _builder.newLine();
    _builder.append("\u00DFg(X) -> ok.");
    _builder.newLine();
    final Pair<Module,List<Integer>> module = this.parser.parse(_builder.toString());
    EObject _objectAtMarker = this._erlangTestExtensions.getObjectAtMarker(module);
    final Atom atom = ((Atom) _objectAtMarker);
    final EObject tgt = this._erlangTestExtensions.getObjectAtMarker(module, 1);
    AtomRefTarget _atomReference = this._erlangLinkingHelper.getAtomReference(atom);
    Matcher<? super EObject> _is = Matchers.<EObject>is(tgt);
    MatcherAssert.<AtomRefTarget>assertThat(_atomReference, _is);
  }
  
  @Test
  public void resolve_remoteCall_2() {
    final XtextResourceSet rset = this.resourceSetProvider.get();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("-module(m).");
    _builder.newLine();
    _builder.append("f() ->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("w:\u00DFg(3),");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ok.");
    _builder.newLine();
    final Pair<Module,List<Integer>> module = this.parser.parse(_builder.toString(), "m.erl", rset);
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("-module(w).");
    _builder_1.newLine();
    _builder_1.append("-export([g/1]).");
    _builder_1.newLine();
    _builder_1.append("\u00DFg(X) -> ok.");
    _builder_1.newLine();
    final Pair<Module,List<Integer>> module2 = this.parser.parse(_builder_1.toString(), "w.erl", rset);
    EObject _objectAtMarker = this._erlangTestExtensions.getObjectAtMarker(module);
    final Atom atom = ((Atom) _objectAtMarker);
    final EObject tgt = this._erlangTestExtensions.getObjectAtMarker(module2);
    AtomRefTarget _atomReference = this._erlangLinkingHelper.getAtomReference(atom);
    Matcher<? super EObject> _is = Matchers.<EObject>is(tgt);
    MatcherAssert.<AtomRefTarget>assertThat(_atomReference, _is);
  }
  
  @Test
  public void resolve_remoteCall_bad_1() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("-module(m).");
    _builder.newLine();
    _builder.append("f() ->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("m:\u00DFg(3),");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ok.");
    _builder.newLine();
    _builder.append("g(X) -> ok.");
    _builder.newLine();
    final Pair<Module,List<Integer>> module = this.parser.parse(_builder.toString());
    EObject _objectAtMarker = this._erlangTestExtensions.getObjectAtMarker(module);
    final Atom atom = ((Atom) _objectAtMarker);
    AtomRefTarget _atomReference = this._erlangLinkingHelper.getAtomReference(atom);
    Matcher<AtomRefTarget> _nullValue = Matchers.<AtomRefTarget>nullValue();
    Matcher<AtomRefTarget> _is = Matchers.<AtomRefTarget>is(_nullValue);
    MatcherAssert.<AtomRefTarget>assertThat(_atomReference, _is);
  }
  
  @Test
  public void resolve_remoteCall_moduleMacro() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("-module(m).");
    _builder.newLine();
    _builder.append("-export([g/1]).");
    _builder.newLine();
    _builder.append("f() ->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("?MODULE:\u00DFg(3),");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ok.");
    _builder.newLine();
    _builder.append("\u00DFg(X) -> X.");
    _builder.newLine();
    final Pair<Module,List<Integer>> module = this.parser.parse(_builder.toString());
    EObject _objectAtMarker = this._erlangTestExtensions.getObjectAtMarker(module);
    final Atom atom = ((Atom) _objectAtMarker);
    final EObject tgt = this._erlangTestExtensions.getObjectAtMarker(module, 1);
    AtomRefTarget _atomReference = this._erlangLinkingHelper.getAtomReference(atom);
    Matcher<? super EObject> _is = Matchers.<EObject>is(tgt);
    MatcherAssert.<AtomRefTarget>assertThat(_atomReference, _is);
  }
  
  @Test
  public void resolve_remoteCall_bad() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("-module(m).");
    _builder.newLine();
    _builder.append("f() ->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("W:\u00DFg(3),");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ok.");
    _builder.newLine();
    final Pair<Module,List<Integer>> module = this.parser.parse(_builder.toString());
    EObject _objectAtMarker = this._erlangTestExtensions.getObjectAtMarker(module);
    final Atom atom = ((Atom) _objectAtMarker);
    AtomRefTarget _atomReference = this._erlangLinkingHelper.getAtomReference(atom);
    Matcher<AtomRefTarget> _nullValue = Matchers.<AtomRefTarget>nullValue();
    Matcher<AtomRefTarget> _is = Matchers.<AtomRefTarget>is(_nullValue);
    MatcherAssert.<AtomRefTarget>assertThat(_atomReference, _is);
  }
  
  @Test
  public void resolve_remoteCall_moduleMacro_bad() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("-module(m).");
    _builder.newLine();
    _builder.append("f() ->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("?MODULE:\u00DFg(3),");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ok.");
    _builder.newLine();
    _builder.append("\u00DFg(X) -> X.");
    _builder.newLine();
    final Pair<Module,List<Integer>> module = this.parser.parse(_builder.toString());
    EObject _objectAtMarker = this._erlangTestExtensions.getObjectAtMarker(module);
    final Atom atom = ((Atom) _objectAtMarker);
    AtomRefTarget _atomReference = this._erlangLinkingHelper.getAtomReference(atom);
    Matcher<AtomRefTarget> _nullValue = Matchers.<AtomRefTarget>nullValue();
    Matcher<AtomRefTarget> _is = Matchers.<AtomRefTarget>is(_nullValue);
    MatcherAssert.<AtomRefTarget>assertThat(_atomReference, _is);
  }
  
  @Test
  public void resolve_moduleCall() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("-module(m).");
    _builder.newLine();
    _builder.append("f() ->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\u00DFw:g(3),");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ok.");
    _builder.newLine();
    final Pair<Module,List<Integer>> module = this.parser.parse(_builder.toString());
    EObject _objectAtMarker = this._erlangTestExtensions.getObjectAtMarker(module);
    final Atom atom = ((Atom) _objectAtMarker);
    final EObject tgt = this._erlangTestExtensions.getObjectAtMarker(module, 1);
    AtomRefTarget _atomReference = this._erlangLinkingHelper.getAtomReference(atom);
    Matcher<AtomRefTarget> _nullValue = Matchers.<AtomRefTarget>nullValue();
    Matcher<AtomRefTarget> _is = Matchers.<AtomRefTarget>is(_nullValue);
    MatcherAssert.<AtomRefTarget>assertThat(_atomReference, _is);
  }
  
  @Test
  public void resolve_moduleCall_1() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("-module(m).");
    _builder.newLine();
    _builder.append("f() ->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\u00DFw:G(3),");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ok.");
    _builder.newLine();
    final Pair<Module,List<Integer>> module = this.parser.parse(_builder.toString());
    EObject _objectAtMarker = this._erlangTestExtensions.getObjectAtMarker(module);
    final Atom atom = ((Atom) _objectAtMarker);
    final EObject tgt = this._erlangTestExtensions.getObjectAtMarker(module, 1);
    AtomRefTarget _atomReference = this._erlangLinkingHelper.getAtomReference(atom);
    Matcher<AtomRefTarget> _nullValue = Matchers.<AtomRefTarget>nullValue();
    Matcher<AtomRefTarget> _is = Matchers.<AtomRefTarget>is(_nullValue);
    MatcherAssert.<AtomRefTarget>assertThat(_atomReference, _is);
  }
  
  @Test
  public void resolve_moduleRef() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("-module(m).");
    _builder.newLine();
    _builder.append("f() ->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("fun \u00DFw:g/3,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ok.");
    _builder.newLine();
    final Pair<Module,List<Integer>> module = this.parser.parse(_builder.toString());
    EObject _objectAtMarker = this._erlangTestExtensions.getObjectAtMarker(module);
    final Atom atom = ((Atom) _objectAtMarker);
    AtomRefTarget _atomReference = this._erlangLinkingHelper.getAtomReference(atom);
    Matcher<AtomRefTarget> _nullValue = Matchers.<AtomRefTarget>nullValue();
    Matcher<AtomRefTarget> _is = Matchers.<AtomRefTarget>is(_nullValue);
    MatcherAssert.<AtomRefTarget>assertThat(_atomReference, _is);
  }
  
  @Test
  public void resolve_functionRef_local() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("-module(m).");
    _builder.newLine();
    _builder.append("f() ->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("fun \u00DFg/2,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ok.");
    _builder.newLine();
    _builder.append("\u00DFg(X,Y) -> ok.");
    _builder.newLine();
    final Pair<Module,List<Integer>> module = this.parser.parse(_builder.toString());
    EObject _objectAtMarker = this._erlangTestExtensions.getObjectAtMarker(module);
    final Atom atom = ((Atom) _objectAtMarker);
    final EObject tgt = this._erlangTestExtensions.getObjectAtMarker(module, 1);
    AtomRefTarget _atomReference = this._erlangLinkingHelper.getAtomReference(atom);
    Matcher<? super EObject> _is = Matchers.<EObject>is(tgt);
    MatcherAssert.<AtomRefTarget>assertThat(_atomReference, _is);
  }
  
  @Test
  public void resolve_functionRef_remote() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("-module(m).");
    _builder.newLine();
    _builder.append("-export([g/2]).");
    _builder.newLine();
    _builder.append("f() ->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("fun m:\u00DFg/2,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ok.");
    _builder.newLine();
    _builder.append("\u00DFg(X,Y) -> ok.");
    _builder.newLine();
    final Pair<Module,List<Integer>> module = this.parser.parse(_builder.toString());
    EObject _objectAtMarker = this._erlangTestExtensions.getObjectAtMarker(module);
    final Atom atom = ((Atom) _objectAtMarker);
    final EObject tgt = this._erlangTestExtensions.getObjectAtMarker(module, 1);
    AtomRefTarget _atomReference = this._erlangLinkingHelper.getAtomReference(atom);
    Matcher<? super EObject> _is = Matchers.<EObject>is(tgt);
    MatcherAssert.<AtomRefTarget>assertThat(_atomReference, _is);
  }
  
  @Test
  public void resolve_functionRef_remote_bad() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("-module(m).");
    _builder.newLine();
    _builder.append("f() ->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("fun m:\u00DFg/2,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ok.");
    _builder.newLine();
    _builder.append("\u00DFg(X,Y) -> ok.");
    _builder.newLine();
    final Pair<Module,List<Integer>> module = this.parser.parse(_builder.toString());
    EObject _objectAtMarker = this._erlangTestExtensions.getObjectAtMarker(module);
    final Atom atom = ((Atom) _objectAtMarker);
    AtomRefTarget _atomReference = this._erlangLinkingHelper.getAtomReference(atom);
    Matcher<AtomRefTarget> _nullValue = Matchers.<AtomRefTarget>nullValue();
    Matcher<AtomRefTarget> _is = Matchers.<AtomRefTarget>is(_nullValue);
    MatcherAssert.<AtomRefTarget>assertThat(_atomReference, _is);
  }
  
  @Test
  public void resolve_functionRef_remote_2() {
    final XtextResourceSet rset = this.resourceSetProvider.get();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("-module(m).");
    _builder.newLine();
    _builder.append("f() ->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("fun w:\u00DFg/2,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ok.");
    _builder.newLine();
    final Pair<Module,List<Integer>> module = this.parser.parse(_builder.toString(), "m.erl", rset);
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("-module(w).");
    _builder_1.newLine();
    _builder_1.append("-export([g/2]).");
    _builder_1.newLine();
    _builder_1.append("\u00DFg(X,Y) -> ok.");
    _builder_1.newLine();
    final Pair<Module,List<Integer>> module2 = this.parser.parse(_builder_1.toString(), "w.erl", rset);
    EObject _objectAtMarker = this._erlangTestExtensions.getObjectAtMarker(module);
    final Atom atom = ((Atom) _objectAtMarker);
    final EObject tgt = this._erlangTestExtensions.getObjectAtMarker(module2);
    AtomRefTarget _atomReference = this._erlangLinkingHelper.getAtomReference(atom);
    Matcher<? super EObject> _is = Matchers.<EObject>is(tgt);
    MatcherAssert.<AtomRefTarget>assertThat(_atomReference, _is);
  }
  
  @Test
  public void resolve_functionRef_remote_2_bad() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("-module(m).");
    _builder.newLine();
    _builder.append("f() ->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("fun w:\u00DFg/2,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ok.");
    _builder.newLine();
    final Pair<Module,List<Integer>> module = this.parser.parse(_builder.toString());
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("-module(m).");
    _builder_1.newLine();
    _builder_1.append("\u00DFg(X,Y) -> ok.");
    _builder_1.newLine();
    final Pair<Module,List<Integer>> module2 = this.parser.parse(_builder_1.toString());
    EObject _objectAtMarker = this._erlangTestExtensions.getObjectAtMarker(module);
    final Atom atom = ((Atom) _objectAtMarker);
    AtomRefTarget _atomReference = this._erlangLinkingHelper.getAtomReference(atom);
    Matcher<AtomRefTarget> _nullValue = Matchers.<AtomRefTarget>nullValue();
    Matcher<AtomRefTarget> _is = Matchers.<AtomRefTarget>is(_nullValue);
    MatcherAssert.<AtomRefTarget>assertThat(_atomReference, _is);
  }
  
  @Test
  public void resolve_functionRef_moduleMacro() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("-module(m).");
    _builder.newLine();
    _builder.append("-export([g/2]).");
    _builder.newLine();
    _builder.append("f() ->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("fun ?MODULE:\u00DFg/2,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ok.");
    _builder.newLine();
    _builder.append("\u00DFg(X,Y) -> ok.");
    _builder.newLine();
    final Pair<Module,List<Integer>> module = this.parser.parse(_builder.toString());
    EObject _objectAtMarker = this._erlangTestExtensions.getObjectAtMarker(module);
    final Atom atom = ((Atom) _objectAtMarker);
    final EObject tgt = this._erlangTestExtensions.getObjectAtMarker(module, 1);
    AtomRefTarget _atomReference = this._erlangLinkingHelper.getAtomReference(atom);
    Matcher<? super EObject> _is = Matchers.<EObject>is(tgt);
    MatcherAssert.<AtomRefTarget>assertThat(_atomReference, _is);
  }
  
  @Test
  public void resolve_functionRef_bad() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("-module(m).");
    _builder.newLine();
    _builder.append("f() ->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("fun W:\u00DFg/2,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ok.");
    _builder.newLine();
    final Pair<Module,List<Integer>> module = this.parser.parse(_builder.toString());
    EObject _objectAtMarker = this._erlangTestExtensions.getObjectAtMarker(module);
    final Atom atom = ((Atom) _objectAtMarker);
    AtomRefTarget _atomReference = this._erlangLinkingHelper.getAtomReference(atom);
    Matcher<AtomRefTarget> _nullValue = Matchers.<AtomRefTarget>nullValue();
    Matcher<AtomRefTarget> _is = Matchers.<AtomRefTarget>is(_nullValue);
    MatcherAssert.<AtomRefTarget>assertThat(_atomReference, _is);
  }
  
  @Test
  public void resolve_record() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("-module(m).");
    _builder.newLine();
    _builder.append("\u00DF-record(myrec, {}).");
    _builder.newLine();
    _builder.append("f() ->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("#\u00DFmyrec{},");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ok.");
    _builder.newLine();
    final Pair<Module,List<Integer>> module = this.parser.parse(_builder.toString());
    EObject _objectAtMarker = this._erlangTestExtensions.getObjectAtMarker(module, 1);
    final Atom atom = ((Atom) _objectAtMarker);
    final EObject tgt = this._erlangTestExtensions.getObjectAtMarker(module, 0);
    AtomRefTarget _atomReference = this._erlangLinkingHelper.getAtomReference(atom);
    Matcher<? super EObject> _is = Matchers.<EObject>is(tgt);
    MatcherAssert.<AtomRefTarget>assertThat(_atomReference, _is);
  }
  
  @Test
  public void resolve_record_bad() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("-module(m).");
    _builder.newLine();
    _builder.append("f() ->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("#\u00DFmyrec{},");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ok.");
    _builder.newLine();
    final Pair<Module,List<Integer>> module = this.parser.parse(_builder.toString());
    EObject _objectAtMarker = this._erlangTestExtensions.getObjectAtMarker(module, 0);
    final Atom atom = ((Atom) _objectAtMarker);
    AtomRefTarget _atomReference = this._erlangLinkingHelper.getAtomReference(atom);
    Matcher<AtomRefTarget> _nullValue = Matchers.<AtomRefTarget>nullValue();
    Matcher<AtomRefTarget> _is = Matchers.<AtomRefTarget>is(_nullValue);
    MatcherAssert.<AtomRefTarget>assertThat(_atomReference, _is);
  }
  
  @Test
  public void resolve_record_1() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("-module(m).");
    _builder.newLine();
    _builder.append("\u00DF-record(myrec, {}).");
    _builder.newLine();
    _builder.append("f() ->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("#\u00DFmyrec.az,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ok.");
    _builder.newLine();
    final Pair<Module,List<Integer>> module = this.parser.parse(_builder.toString());
    EObject _objectAtMarker = this._erlangTestExtensions.getObjectAtMarker(module, 1);
    final Atom atom = ((Atom) _objectAtMarker);
    final EObject tgt = this._erlangTestExtensions.getObjectAtMarker(module, 0);
    AtomRefTarget _atomReference = this._erlangLinkingHelper.getAtomReference(atom);
    Matcher<? super EObject> _is = Matchers.<EObject>is(tgt);
    MatcherAssert.<AtomRefTarget>assertThat(_atomReference, _is);
  }
  
  @Test
  public void resolve_recordField() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("-module(m).");
    _builder.newLine();
    _builder.append("-record(myrec, {\u00DFff}).");
    _builder.newLine();
    _builder.append("f() ->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("#myrec.\u00DFff,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ok.");
    _builder.newLine();
    final Pair<Module,List<Integer>> module = this.parser.parse(_builder.toString());
    EObject _objectAtMarker = this._erlangTestExtensions.getObjectAtMarker(module, 1);
    final Atom atom = ((Atom) _objectAtMarker);
    final EObject tgt = this._erlangTestExtensions.getObjectAtMarker(module, 0);
    AtomRefTarget _atomReference = this._erlangLinkingHelper.getAtomReference(atom);
    Matcher<? super EObject> _is = Matchers.<EObject>is(tgt);
    MatcherAssert.<AtomRefTarget>assertThat(_atomReference, _is);
  }
  
  @Test
  public void resolve_recordField_bad() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("-module(m).");
    _builder.newLine();
    _builder.append("f() ->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("#Myrec.\u00DFff,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ok.");
    _builder.newLine();
    final Pair<Module,List<Integer>> module = this.parser.parse(_builder.toString());
    EObject _objectAtMarker = this._erlangTestExtensions.getObjectAtMarker(module);
    final Atom atom = ((Atom) _objectAtMarker);
    AtomRefTarget _atomReference = this._erlangLinkingHelper.getAtomReference(atom);
    Matcher<AtomRefTarget> _nullValue = Matchers.<AtomRefTarget>nullValue();
    Matcher<AtomRefTarget> _is = Matchers.<AtomRefTarget>is(_nullValue);
    MatcherAssert.<AtomRefTarget>assertThat(_atomReference, _is);
  }
  
  @Test
  public void resolve_recordField_bad_1() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("-module(m).");
    _builder.newLine();
    _builder.append("-record(myrec, {gg}).");
    _builder.newLine();
    _builder.append("f() ->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("#myrec.\u00DFff,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ok.");
    _builder.newLine();
    final Pair<Module,List<Integer>> module = this.parser.parse(_builder.toString());
    EObject _objectAtMarker = this._erlangTestExtensions.getObjectAtMarker(module);
    final Atom atom = ((Atom) _objectAtMarker);
    AtomRefTarget _atomReference = this._erlangLinkingHelper.getAtomReference(atom);
    Matcher<AtomRefTarget> _nullValue = Matchers.<AtomRefTarget>nullValue();
    Matcher<AtomRefTarget> _is = Matchers.<AtomRefTarget>is(_nullValue);
    MatcherAssert.<AtomRefTarget>assertThat(_atomReference, _is);
  }
  
  @Test
  public void resolve_recordField_1() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("-module(m).");
    _builder.newLine();
    _builder.append("-record(myrec, {\u00DFff}).");
    _builder.newLine();
    _builder.append("f() ->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("#myrec{\u00DFff=2},");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ok.");
    _builder.newLine();
    final Pair<Module,List<Integer>> module = this.parser.parse(_builder.toString());
    EObject _objectAtMarker = this._erlangTestExtensions.getObjectAtMarker(module, 1);
    final Atom atom = ((Atom) _objectAtMarker);
    final EObject tgt = this._erlangTestExtensions.getObjectAtMarker(module, 0);
    AtomRefTarget _atomReference = this._erlangLinkingHelper.getAtomReference(atom);
    Matcher<? super EObject> _is = Matchers.<EObject>is(tgt);
    MatcherAssert.<AtomRefTarget>assertThat(_atomReference, _is);
  }
  
  @Test
  public void resolve_recordField_1_bad() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("-module(m).");
    _builder.newLine();
    _builder.append("-record(myrec, {gg}).");
    _builder.newLine();
    _builder.append("f() ->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("#Myrec{\u00DFff=2},");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ok.");
    _builder.newLine();
    final Pair<Module,List<Integer>> module = this.parser.parse(_builder.toString());
    EObject _objectAtMarker = this._erlangTestExtensions.getObjectAtMarker(module);
    final Atom atom = ((Atom) _objectAtMarker);
    AtomRefTarget _atomReference = this._erlangLinkingHelper.getAtomReference(atom);
    Matcher<AtomRefTarget> _nullValue = Matchers.<AtomRefTarget>nullValue();
    Matcher<AtomRefTarget> _is = Matchers.<AtomRefTarget>is(_nullValue);
    MatcherAssert.<AtomRefTarget>assertThat(_atomReference, _is);
  }
  
  @Test
  public void resolve_macro() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("-module(m).");
    _builder.newLine();
    _builder.append("\u00DF-define(Z, zz).");
    _builder.newLine();
    _builder.append("f() ->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("?\u00DFZ,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ok.");
    _builder.newLine();
    final Pair<Module,List<Integer>> module = this.parser.parse(_builder.toString());
    EObject _objectAtMarker = this._erlangTestExtensions.getObjectAtMarker(module, 1);
    final Macro macro = ((Macro) _objectAtMarker);
    final EObject tgt = this._erlangTestExtensions.getObjectAtMarker(module, 0);
    DefineAttribute _macroReference = this._erlangLinkingHelper.getMacroReference(macro);
    Matcher<? super EObject> _is = Matchers.<EObject>is(tgt);
    MatcherAssert.<DefineAttribute>assertThat(_macroReference, _is);
  }
  
  @Test
  public void resolve_macro_atom() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("-module(m).");
    _builder.newLine();
    _builder.append("\u00DF-define(z, zz).");
    _builder.newLine();
    _builder.append("f() ->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("?\u00DFz,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ok.");
    _builder.newLine();
    final Pair<Module,List<Integer>> module = this.parser.parse(_builder.toString());
    EObject _objectAtMarker = this._erlangTestExtensions.getObjectAtMarker(module, 1);
    final Macro macro = ((Macro) _objectAtMarker);
    final EObject tgt = this._erlangTestExtensions.getObjectAtMarker(module, 0);
    DefineAttribute _macroReference = this._erlangLinkingHelper.getMacroReference(macro);
    Matcher<? super EObject> _is = Matchers.<EObject>is(tgt);
    MatcherAssert.<DefineAttribute>assertThat(_macroReference, _is);
  }
  
  @Test
  public void resolve_macro_1() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("-module(m).");
    _builder.newLine();
    _builder.append("\u00DF-define(Z, zz).");
    _builder.newLine();
    _builder.append("-ifdef(\u00DFZ).");
    _builder.newLine();
    _builder.append("-endif.");
    _builder.newLine();
    final Pair<Module,List<Integer>> module = this.parser.parse(_builder.toString());
    EObject _objectAtMarker = this._erlangTestExtensions.getObjectAtMarker(module, 1);
    final Macro macro = ((Macro) _objectAtMarker);
    final EObject tgt = this._erlangTestExtensions.getObjectAtMarker(module, 0);
    DefineAttribute _macroReference = this._erlangLinkingHelper.getMacroReference(macro);
    Matcher<? super EObject> _is = Matchers.<EObject>is(tgt);
    MatcherAssert.<DefineAttribute>assertThat(_macroReference, _is);
  }
  
  @Test
  public void resolve_macro_1_atom() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("-module(m).");
    _builder.newLine();
    _builder.append("\u00DF-define(z, zz).");
    _builder.newLine();
    _builder.append("-ifdef(\u00DFz).");
    _builder.newLine();
    _builder.append("-endif.");
    _builder.newLine();
    final Pair<Module,List<Integer>> module = this.parser.parse(_builder.toString());
    EObject _objectAtMarker = this._erlangTestExtensions.getObjectAtMarker(module, 1);
    final Macro macro = ((Macro) _objectAtMarker);
    final EObject tgt = this._erlangTestExtensions.getObjectAtMarker(module, 0);
    DefineAttribute _macroReference = this._erlangLinkingHelper.getMacroReference(macro);
    Matcher<? super EObject> _is = Matchers.<EObject>is(tgt);
    MatcherAssert.<DefineAttribute>assertThat(_macroReference, _is);
  }
}
