package org.erlide.erlang;

import com.google.inject.Inject;
import java.util.Collection;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.junit4.util.ParseHelper;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.erlide.ErlangInjectorProvider;
import org.erlide.erlang.Attribute;
import org.erlide.erlang.CustomAttribute;
import org.erlide.erlang.FunRef;
import org.erlide.erlang.Function;
import org.erlide.erlang.IsFunRefMatcher;
import org.erlide.erlang.ModelExtensions;
import org.erlide.erlang.Module;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(value = XtextRunner.class)
@InjectWith(value = ErlangInjectorProvider.class)
@SuppressWarnings("all")
public class ModuleExtensionsTest {
  @Inject
  private ParseHelper<Module> parser;
  
  @Test
  public void moduleName() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("-module(x).");
      _builder.newLine();
      final Module module = this.parser.parse(_builder);
      final String myname = ModelExtensions.getName(module);
      Matcher<? super String> _is = Matchers.<String>is("x");
      MatcherAssert.<String>assertThat(myname, _is);
    } catch (Exception _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void moduleNameWithComments() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("%% comment");
      _builder.newLine();
      _builder.append("-module(x).");
      _builder.newLine();
      final Module module = this.parser.parse(_builder);
      final String myname = ModelExtensions.getName(module);
      Matcher<? super String> _is = Matchers.<String>is("x");
      MatcherAssert.<String>assertThat(myname, _is);
    } catch (Exception _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void noModuleName() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("-include(\"x\").");
      _builder.newLine();
      final Module module = this.parser.parse(_builder);
      final String myname = ModelExtensions.getName(module);
      Matcher<String> _nullValue = Matchers.<String>nullValue();
      Matcher<String> _is = Matchers.<String>is(_nullValue);
      MatcherAssert.<String>assertThat(myname, _is);
    } catch (Exception _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void headerHasNoName() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("-include(\"x\").");
      _builder.newLine();
      final Module module = this.parser.parse(_builder);
      boolean _isHeader = ModelExtensions.isHeader(module);
      Matcher<? super Boolean> _is = Matchers.<Boolean>is(Boolean.valueOf(true));
      MatcherAssert.<Boolean>assertThat(Boolean.valueOf(_isHeader), _is);
    } catch (Exception _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void fullModuleHasName() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("-module(x).");
      _builder.newLine();
      final Module module = this.parser.parse(_builder);
      boolean _isHeader = ModelExtensions.isHeader(module);
      Matcher<? super Boolean> _is = Matchers.<Boolean>is(Boolean.valueOf(false));
      MatcherAssert.<Boolean>assertThat(Boolean.valueOf(_isHeader), _is);
    } catch (Exception _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void getAttributes() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("-module(x).");
      _builder.newLine();
      _builder.append("-define(X, ok).");
      _builder.newLine();
      _builder.append("foo() -> ok.");
      _builder.newLine();
      _builder.append("-foo(bar).");
      _builder.newLine();
      final Module module = this.parser.parse(_builder);
      final Collection<Attribute> attrs = ModelExtensions.getAttributes(module);
      int _size = attrs.size();
      Matcher<? super Integer> _is = Matchers.<Integer>is(Integer.valueOf(3));
      MatcherAssert.<Integer>assertThat(Integer.valueOf(_size), _is);
    } catch (Exception _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void getCustomAttributesByTag() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("-module(x).");
      _builder.newLine();
      _builder.append("-foo(baz).");
      _builder.newLine();
      _builder.append("-define(X, ok).");
      _builder.newLine();
      _builder.append("foo() -> ok.");
      _builder.newLine();
      _builder.append("-foo(bar).");
      _builder.newLine();
      final Module module = this.parser.parse(_builder);
      final Collection<CustomAttribute> attrs = ModelExtensions.getCustomAttributesWithTag(module, "foo");
      int _size = attrs.size();
      Matcher<? super Integer> _is = Matchers.<Integer>is(Integer.valueOf(2));
      MatcherAssert.<Integer>assertThat(Integer.valueOf(_size), _is);
      CustomAttribute _head = IterableExtensions.<CustomAttribute>head(attrs);
      String _tag = _head.getTag();
      Matcher<? super String> _is_1 = Matchers.<String>is("foo");
      MatcherAssert.<String>assertThat(_tag, _is_1);
      Iterable<CustomAttribute> _tail = IterableExtensions.<CustomAttribute>tail(attrs);
      CustomAttribute _head_1 = IterableExtensions.<CustomAttribute>head(_tail);
      String _tag_1 = _head_1.getTag();
      Matcher<? super String> _is_2 = Matchers.<String>is("foo");
      MatcherAssert.<String>assertThat(_tag_1, _is_2);
    } catch (Exception _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void getExportFunctions() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("-module(x).");
      _builder.newLine();
      _builder.append("-export([bar/0, baz/1]).");
      _builder.newLine();
      _builder.append("-export([foo/0]).");
      _builder.newLine();
      _builder.append("bar()->ok.");
      _builder.newLine();
      _builder.append("baz(X)->ok.");
      _builder.newLine();
      _builder.append("foo()->ok.");
      _builder.newLine();
      final Module module = this.parser.parse(_builder);
      final Collection<Function> attrs = ModelExtensions.getExportedFunctions(module);
      int _size = attrs.size();
      Matcher<? super Integer> _is = Matchers.<Integer>is(Integer.valueOf(3));
      MatcherAssert.<Integer>assertThat(Integer.valueOf(_size), _is);
      Function _head = IterableExtensions.<Function>head(attrs);
      Matcher<Function> _isFunction = IsFunRefMatcher.isFunction("bar", 0);
      MatcherAssert.<Function>assertThat(_head, _isFunction);
      Iterable<Function> _tail = IterableExtensions.<Function>tail(attrs);
      Function _head_1 = IterableExtensions.<Function>head(_tail);
      Matcher<Function> _isFunction_1 = IsFunRefMatcher.isFunction("baz", 1);
      MatcherAssert.<Function>assertThat(_head_1, _isFunction_1);
      Iterable<Function> _tail_1 = IterableExtensions.<Function>tail(attrs);
      Iterable<Function> _tail_2 = IterableExtensions.<Function>tail(_tail_1);
      Function _head_2 = IterableExtensions.<Function>head(_tail_2);
      Matcher<Function> _isFunction_2 = IsFunRefMatcher.isFunction("foo", 0);
      MatcherAssert.<Function>assertThat(_head_2, _isFunction_2);
    } catch (Exception _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void getExportFunRefs() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("-module(x).");
      _builder.newLine();
      _builder.append("-export([bar/0, baz/1]).");
      _builder.newLine();
      _builder.append("-export([foo/0]).");
      _builder.newLine();
      final Module module = this.parser.parse(_builder);
      final Collection<FunRef> attrs = ModelExtensions.getExportedFunRefs(module);
      int _size = attrs.size();
      Matcher<? super Integer> _is = Matchers.<Integer>is(Integer.valueOf(3));
      MatcherAssert.<Integer>assertThat(Integer.valueOf(_size), _is);
      FunRef _head = IterableExtensions.<FunRef>head(attrs);
      Matcher<FunRef> _isFunRef = IsFunRefMatcher.isFunRef("bar", 0);
      MatcherAssert.<FunRef>assertThat(_head, _isFunRef);
      Iterable<FunRef> _tail = IterableExtensions.<FunRef>tail(attrs);
      FunRef _head_1 = IterableExtensions.<FunRef>head(_tail);
      Matcher<FunRef> _isFunRef_1 = IsFunRefMatcher.isFunRef("baz", 1);
      MatcherAssert.<FunRef>assertThat(_head_1, _isFunRef_1);
      Iterable<FunRef> _tail_1 = IterableExtensions.<FunRef>tail(attrs);
      Iterable<FunRef> _tail_2 = IterableExtensions.<FunRef>tail(_tail_1);
      FunRef _head_2 = IterableExtensions.<FunRef>head(_tail_2);
      Matcher<FunRef> _isFunRef_2 = IsFunRefMatcher.isFunRef("foo", 0);
      MatcherAssert.<FunRef>assertThat(_head_2, _isFunRef_2);
    } catch (Exception _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void getFunction() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("-module(x).");
      _builder.newLine();
      _builder.append("bar()->ok.");
      _builder.newLine();
      _builder.append("baz(X)->ok.");
      _builder.newLine();
      _builder.append("foo()->ok.");
      _builder.newLine();
      final Module module = this.parser.parse(_builder);
      final Function bar = ModelExtensions.getFunction(module, "bar", 0);
      Matcher<Function> _notNullValue = Matchers.<Function>notNullValue();
      Matcher<Function> _is = Matchers.<Function>is(_notNullValue);
      MatcherAssert.<Function>assertThat(bar, _is);
      Matcher<Function> _isFunction = IsFunRefMatcher.isFunction("bar", 0);
      MatcherAssert.<Function>assertThat(bar, _isFunction);
    } catch (Exception _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void getUndefinedFunction() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("-module(x).");
      _builder.newLine();
      _builder.append("bar()->ok.");
      _builder.newLine();
      _builder.append("baz(X)->ok.");
      _builder.newLine();
      _builder.append("foo()->ok.");
      _builder.newLine();
      final Module module = this.parser.parse(_builder);
      final Function bar1 = ModelExtensions.getFunction(module, "bar", 1);
      Matcher<Function> _nullValue = Matchers.<Function>nullValue();
      Matcher<Function> _is = Matchers.<Function>is(_nullValue);
      MatcherAssert.<Function>assertThat(bar1, _is);
    } catch (Exception _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
