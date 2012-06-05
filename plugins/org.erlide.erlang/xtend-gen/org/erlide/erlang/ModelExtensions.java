package org.erlide.erlang;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.erlide.erlang.Atom;
import org.erlide.erlang.Attribute;
import org.erlide.erlang.CompileAttribute;
import org.erlide.erlang.ConditionalAttribute;
import org.erlide.erlang.ConditionalFormBlock;
import org.erlide.erlang.CustomAttribute;
import org.erlide.erlang.ErlList;
import org.erlide.erlang.ErlTuple;
import org.erlide.erlang.ExportAttribute;
import org.erlide.erlang.Expression;
import org.erlide.erlang.Form;
import org.erlide.erlang.FunRef;
import org.erlide.erlang.FunType;
import org.erlide.erlang.Function;
import org.erlide.erlang.FunctionClause;
import org.erlide.erlang.ImportAttribute;
import org.erlide.erlang.IncludeAttribute;
import org.erlide.erlang.IncludeLibAttribute;
import org.erlide.erlang.Module;
import org.erlide.erlang.ModuleAttribute;
import org.erlide.erlang.SpecAttribute;
import org.erlide.erlang.SpecFun;
import org.erlide.erlang.TopType;
import org.erlide.erlang.TypeSig;

@SuppressWarnings("all")
public class ModelExtensions {
  public String getName(final Module module) {
    String _xblockexpression = null;
    {
      EList<Form> _forms = module.getForms();
      final Form attr = IterableExtensions.<Form>head(_forms);
      String _xifexpression = null;
      if ((attr instanceof ModuleAttribute)) {
        String _moduleName = ((ModuleAttribute) attr).getModuleName();
        String _replaceAll = _moduleName.replaceAll("\\.", "_");
        _xifexpression = _replaceAll;
      } else {
        _xifexpression = null;
      }
      _xblockexpression = (_xifexpression);
    }
    return _xblockexpression;
  }
  
  public boolean isHeader(final Module module) {
    String _name = this.getName(module);
    boolean _equals = Objects.equal(_name, null);
    return _equals;
  }
  
  public Collection<Attribute> getAttributes(final Module module) {
    EList<EObject> _eContents = module.eContents();
    Iterable<Attribute> _filter = Iterables.<Attribute>filter(_eContents, Attribute.class);
    List<Attribute> _list = IterableExtensions.<Attribute>toList(_filter);
    return _list;
  }
  
  public Collection<CustomAttribute> getCustomAttributesWithTag(final Module module, final String mytag) {
    EList<EObject> _eContents = module.eContents();
    Iterable<CustomAttribute> _filter = Iterables.<CustomAttribute>filter(_eContents, CustomAttribute.class);
    final Function1<CustomAttribute,Boolean> _function = new Function1<CustomAttribute,Boolean>() {
        public Boolean apply(final CustomAttribute it) {
          String _tag = it.getTag();
          boolean _equals = Objects.equal(_tag, mytag);
          return Boolean.valueOf(_equals);
        }
      };
    Iterable<CustomAttribute> _filter_1 = IterableExtensions.<CustomAttribute>filter(_filter, _function);
    List<CustomAttribute> _list = IterableExtensions.<CustomAttribute>toList(_filter_1);
    return _list;
  }
  
  public Collection<ExportAttribute> getExportAttributes(final Module module) {
    Collection<ExportAttribute> _attributes = ModelExtensions.<ExportAttribute>getAttributes(module, ExportAttribute.class);
    return _attributes;
  }
  
  public Collection<ImportAttribute> getImportAttributes(final Module module) {
    Collection<ImportAttribute> _attributes = ModelExtensions.<ImportAttribute>getAttributes(module, ImportAttribute.class);
    return _attributes;
  }
  
  public Collection<SpecAttribute> getSpecs(final Module module) {
    Collection<SpecAttribute> _attributes = ModelExtensions.<SpecAttribute>getAttributes(module, SpecAttribute.class);
    return _attributes;
  }
  
  public Collection<String> getIncludes(final Module module) {
    Collection<IncludeAttribute> _attributes = ModelExtensions.<IncludeAttribute>getAttributes(module, IncludeAttribute.class);
    final Function1<IncludeAttribute,String> _function = new Function1<IncludeAttribute,String>() {
        public String apply(final IncludeAttribute it) {
          String _importURI = it.getImportURI();
          return _importURI;
        }
      };
    Iterable<String> _map = IterableExtensions.<IncludeAttribute, String>map(_attributes, _function);
    List<String> _list = IterableExtensions.<String>toList(_map);
    return _list;
  }
  
  public Collection<String> getIncludeLibs(final Module module) {
    Collection<IncludeLibAttribute> _attributes = ModelExtensions.<IncludeLibAttribute>getAttributes(module, IncludeLibAttribute.class);
    final Function1<IncludeLibAttribute,String> _function = new Function1<IncludeLibAttribute,String>() {
        public String apply(final IncludeLibAttribute it) {
          String _importURI = it.getImportURI();
          return _importURI;
        }
      };
    Iterable<String> _map = IterableExtensions.<IncludeLibAttribute, String>map(_attributes, _function);
    List<String> _list = IterableExtensions.<String>toList(_map);
    return _list;
  }
  
  public boolean exportsFunction(final Module module, final Function function) {
    Collection<Function> _exportedFunctions = this.getExportedFunctions(module);
    boolean _contains = _exportedFunctions.contains(function);
    return _contains;
  }
  
  public boolean exports(final Module module, final FunRef function) {
    Collection<Function> _exportedFunctions = this.getExportedFunctions(module);
    boolean _contains = _exportedFunctions.contains(function);
    return _contains;
  }
  
  public Collection<Function> getExportedFunctions(final Module module) {
    List<Function> _xblockexpression = null;
    {
      final Collection<FunRef> exportedRefs = this.getExportedFunRefs(module);
      final Function1<FunRef,Function> _function = new Function1<FunRef,Function>() {
          public Function apply(final FunRef it) {
            Function _function = ModelExtensions.this.getFunction(module, it);
            return _function;
          }
        };
      Iterable<Function> _map = IterableExtensions.<FunRef, Function>map(exportedRefs, _function);
      List<Function> _list = IterableExtensions.<Function>toList(_map);
      _xblockexpression = (_list);
    }
    return _xblockexpression;
  }
  
  public Collection<FunRef> getExportedFunRefs(final Module module) {
    List<FunRef> _xblockexpression = null;
    {
      final Collection<ExportAttribute> exportAttributes = this.getExportAttributes(module);
      final Function1<ExportAttribute,EList<FunRef>> _function = new Function1<ExportAttribute,EList<FunRef>>() {
          public EList<FunRef> apply(final ExportAttribute it) {
            EList<FunRef> _funs = it.getFuns();
            return _funs;
          }
        };
      Iterable<EList<FunRef>> _map = IterableExtensions.<ExportAttribute, EList<FunRef>>map(exportAttributes, _function);
      Iterable<FunRef> _concat = Iterables.<FunRef>concat(_map);
      List<FunRef> _list = IterableExtensions.<FunRef>toList(_concat);
      _xblockexpression = (_list);
    }
    return _xblockexpression;
  }
  
  public Collection<EObject> getAllContents(final EObject module) {
    List<EObject> _xblockexpression = null;
    {
      final EList<EObject> result = module.eContents();
      final Function1<EObject,Collection<EObject>> _function = new Function1<EObject,Collection<EObject>>() {
          public Collection<EObject> apply(final EObject it) {
            Collection<EObject> _xifexpression = null;
            if ((it instanceof ConditionalFormBlock)) {
              Collection<EObject> _allContents = ModelExtensions.this.getAllContents(it);
              _xifexpression = _allContents;
            } else {
              ArrayList<EObject> _xifexpression_1 = null;
              if ((it instanceof ConditionalAttribute)) {
                ArrayList<EObject> _newArrayList = CollectionLiterals.<EObject>newArrayList();
                _xifexpression_1 = _newArrayList;
              } else {
                ArrayList<EObject> _newArrayList_1 = CollectionLiterals.<EObject>newArrayList(it);
                _xifexpression_1 = _newArrayList_1;
              }
              _xifexpression = _xifexpression_1;
            }
            return _xifexpression;
          }
        };
      List<Collection<EObject>> _map = ListExtensions.<EObject, Collection<EObject>>map(result, _function);
      Iterable<EObject> _flatten = Iterables.<EObject>concat(_map);
      List<EObject> _list = IterableExtensions.<EObject>toList(_flatten);
      _xblockexpression = (_list);
    }
    return _xblockexpression;
  }
  
  public Function getFunction(final Module module, final String fname, final int farity) {
    Collection<EObject> _allContents = this.getAllContents(module);
    Iterable<Function> _filter = Iterables.<Function>filter(_allContents, Function.class);
    final Function1<Function,Boolean> _function = new Function1<Function,Boolean>() {
        public Boolean apply(final Function it) {
          boolean _and = false;
          String _name = it.getName();
          boolean _equals = Objects.equal(_name, fname);
          if (!_equals) {
            _and = false;
          } else {
            int _arity = ModelExtensions.this.getArity(it);
            boolean _equals_1 = (_arity == farity);
            _and = (_equals && _equals_1);
          }
          return Boolean.valueOf(_and);
        }
      };
    Function _findFirst = IterableExtensions.<Function>findFirst(_filter, _function);
    return _findFirst;
  }
  
  public Function getFunction(final Module module, final FunRef ref) {
    String _function = ref.getFunction();
    String _arity = ref.getArity();
    int _parseInt = Integer.parseInt(_arity);
    Function _function_1 = this.getFunction(module, _function, _parseInt);
    return _function_1;
  }
  
  public Function getFunction(final Module module, final SpecFun ref) {
    String _function = ref.getFunction();
    String _arity = ref.getArity();
    int _parseInt = Integer.parseInt(_arity);
    Function _function_1 = this.getFunction(module, _function, _parseInt);
    return _function_1;
  }
  
  public boolean exportsAll(final Module module) {
    Collection<Expression> _compileOptions = this.getCompileOptions(module);
    final Function1<Expression,Boolean> _function = new Function1<Expression,Boolean>() {
        public Boolean apply(final Expression it) {
          boolean _hasAtom = ModelExtensions.this.hasAtom(it, "export_all");
          return Boolean.valueOf(_hasAtom);
        }
      };
    boolean _exists = IterableExtensions.<Expression>exists(_compileOptions, _function);
    return _exists;
  }
  
  public Collection<Atom> getParseTransforms(final Module module) {
    List<Atom> _xblockexpression = null;
    {
      final Collection<Expression> options = this.getCompileOptions(module);
      final Iterable<ErlTuple> tuples = Iterables.<ErlTuple>filter(options, ErlTuple.class);
      final Function1<ErlTuple,Boolean> _function = new Function1<ErlTuple,Boolean>() {
          public Boolean apply(final ErlTuple it) {
            boolean _parseTransformTuple = ModelExtensions.this.parseTransformTuple(it);
            return Boolean.valueOf(_parseTransformTuple);
          }
        };
      Iterable<ErlTuple> _filter = IterableExtensions.<ErlTuple>filter(tuples, _function);
      final Function1<ErlTuple,Atom> _function_1 = new Function1<ErlTuple,Atom>() {
          public Atom apply(final ErlTuple it) {
            EList<Expression> _elements = it.getElements();
            Iterable<Expression> _tail = IterableExtensions.<Expression>tail(_elements);
            Expression _head = IterableExtensions.<Expression>head(_tail);
            return ((Atom) _head);
          }
        };
      Iterable<Atom> _map = IterableExtensions.<ErlTuple, Atom>map(_filter, _function_1);
      List<Atom> _list = IterableExtensions.<Atom>toList(_map);
      _xblockexpression = (_list);
    }
    return _xblockexpression;
  }
  
  public SpecAttribute getSpec(final Module module, final String fname, final int farity) {
    SpecAttribute _xblockexpression = null;
    {
      final Collection<SpecAttribute> specs = this.getSpecs(module);
      final Function1<SpecAttribute,Boolean> _function = new Function1<SpecAttribute,Boolean>() {
          public Boolean apply(final SpecAttribute it) {
            boolean _and = false;
            SpecFun _ref = it.getRef();
            String _function = _ref.getFunction();
            boolean _equals = Objects.equal(_function, fname);
            if (!_equals) {
              _and = false;
            } else {
              int _specArity = ModelExtensions.this.getSpecArity(it);
              boolean _equals_1 = (_specArity == farity);
              _and = (_equals && _equals_1);
            }
            return Boolean.valueOf(_and);
          }
        };
      SpecAttribute _findFirst = IterableExtensions.<SpecAttribute>findFirst(specs, _function);
      _xblockexpression = (_findFirst);
    }
    return _xblockexpression;
  }
  
  public int getArity(final Function fun) {
    EList<FunctionClause> _clauses = fun.getClauses();
    FunctionClause _head = IterableExtensions.<FunctionClause>head(_clauses);
    EList<Expression> _params = _head==null?(EList<Expression>)null:_head.getParams();
    int _size = _params==null?0:_params.size();
    return _size;
  }
  
  public boolean isExported(final Function function) {
    Module _module = this.getModule(function);
    boolean _exportsFunction = this.exportsFunction(_module, function);
    return _exportsFunction;
  }
  
  public SpecAttribute getSpec(final Function function) {
    SpecAttribute _xblockexpression = null;
    {
      final Module module = this.getModule(function);
      final Collection<SpecAttribute> specs = this.getSpecs(module);
      final Function1<SpecAttribute,Boolean> _function = new Function1<SpecAttribute,Boolean>() {
          public Boolean apply(final SpecAttribute it) {
            boolean _and = false;
            SpecFun _ref = it.getRef();
            String _function = _ref.getFunction();
            String _name = function.getName();
            boolean _equals = Objects.equal(_function, _name);
            if (!_equals) {
              _and = false;
            } else {
              int _specArity = ModelExtensions.this.getSpecArity(it);
              int _arity = ModelExtensions.this.getArity(function);
              boolean _equals_1 = (_specArity == _arity);
              _and = (_equals && _equals_1);
            }
            return Boolean.valueOf(_and);
          }
        };
      SpecAttribute _findFirst = IterableExtensions.<SpecAttribute>findFirst(specs, _function);
      _xblockexpression = (_findFirst);
    }
    return _xblockexpression;
  }
  
  protected Module _getModule(final Module element) {
    return element;
  }
  
  protected Module _getModule(final EObject element) {
    EObject _eContainer = element.eContainer();
    Module _module = this.getModule(_eContainer);
    return _module;
  }
  
  public String getSourceText(final EObject obj) {
    String _xblockexpression = null;
    {
      final ICompositeNode node = NodeModelUtils.getNode(obj);
      boolean _equals = Objects.equal(node, null);
      if (_equals) {
        return null;
      }
      Iterable<ILeafNode> _leafNodes = node.getLeafNodes();
      final Function1<ILeafNode,Boolean> _function = new Function1<ILeafNode,Boolean>() {
          public Boolean apply(final ILeafNode it) {
            boolean _isHidden = it.isHidden();
            boolean _not = (!_isHidden);
            return Boolean.valueOf(_not);
          }
        };
      Iterable<ILeafNode> _filter = IterableExtensions.<ILeafNode>filter(_leafNodes, _function);
      final Function1<ILeafNode,String> _function_1 = new Function1<ILeafNode,String>() {
          public String apply(final ILeafNode it) {
            String _text = it.getText();
            return _text;
          }
        };
      Iterable<String> _map = IterableExtensions.<ILeafNode, String>map(_filter, _function_1);
      String _join = IterableExtensions.join(_map, " ");
      _xblockexpression = (_join);
    }
    return _xblockexpression;
  }
  
  public int getSpecArity(final SpecAttribute spec) {
    int _xifexpression = (int) 0;
    SpecFun _ref = spec.getRef();
    String _arity = _ref.getArity();
    boolean _notEquals = (!Objects.equal(_arity, null));
    if (_notEquals) {
      _xifexpression = 0;
    } else {
      EList<TypeSig> _signatures = spec.getSignatures();
      TypeSig _head = IterableExtensions.<TypeSig>head(_signatures);
      FunType _decl = _head.getDecl();
      EList<TopType> _args = _decl.getArgs();
      int _size = _args.size();
      _xifexpression = _size;
    }
    return _xifexpression;
  }
  
  public static <T extends Object> Collection<T> getAttributes(final Module module, final Class<T> type) {
    EList<EObject> _eContents = module.eContents();
    Iterable<T> _filter = Iterables.<T>filter(_eContents, type);
    List<T> _list = IterableExtensions.<T>toList(_filter);
    return _list;
  }
  
  public static Collection<Expression> getRawCompileOptions(final Module module) {
    Collection<CompileAttribute> _attributes = ModelExtensions.<CompileAttribute>getAttributes(module, CompileAttribute.class);
    final Function1<CompileAttribute,Expression> _function = new Function1<CompileAttribute,Expression>() {
        public Expression apply(final CompileAttribute it) {
          Expression _options = it.getOptions();
          return _options;
        }
      };
    Iterable<Expression> _map = IterableExtensions.<CompileAttribute, Expression>map(_attributes, _function);
    List<Expression> _list = IterableExtensions.<Expression>toList(_map);
    return _list;
  }
  
  private Set<Expression> _merge(final Set<Expression> acc, final ErlList x) {
    Set<Expression> _xblockexpression = null;
    {
      EList<Expression> _elements = x.getElements();
      acc.addAll(_elements);
      _xblockexpression = (acc);
    }
    return _xblockexpression;
  }
  
  private Set<Expression> _merge(final Set<Expression> acc, final Expression x) {
    Set<Expression> _xblockexpression = null;
    {
      acc.add(x);
      _xblockexpression = (acc);
    }
    return _xblockexpression;
  }
  
  public Collection<Expression> getCompileOptions(final Module module) {
    Set<Expression> _xblockexpression = null;
    {
      final Function2<Expression,Expression,Integer> _function = new Function2<Expression,Expression,Integer>() {
          public Integer apply(final Expression a, final Expression b) {
            String _sourceText = ModelExtensions.this.getSourceText(a);
            String _sourceText_1 = ModelExtensions.this.getSourceText(b);
            int _compareTo = _sourceText.compareTo(_sourceText_1);
            return _compareTo;
          }
        };
      final Set<Expression> seed = CollectionLiterals.<Expression>newTreeSet(new Comparator<Expression>() {
          public int compare(Expression o1,Expression o2) {
            return _function.apply(o1,o2);
          }
      });
      Collection<Expression> _rawCompileOptions = ModelExtensions.getRawCompileOptions(module);
      final Function2<Set<Expression>,Expression,Set<Expression>> _function_1 = new Function2<Set<Expression>,Expression,Set<Expression>>() {
          public Set<Expression> apply(final Set<Expression> acc, final Expression item) {
            Set<Expression> _merge = ModelExtensions.this.merge(acc, item);
            return _merge;
          }
        };
      Set<Expression> _fold = IterableExtensions.<Expression, Set<Expression>>fold(_rawCompileOptions, seed, _function_1);
      _xblockexpression = (_fold);
    }
    return _xblockexpression;
  }
  
  private boolean _hasAtom(final Atom atom, final String s) {
    String _sourceText = this.getSourceText(atom);
    boolean _equals = Objects.equal(_sourceText, s);
    return _equals;
  }
  
  private boolean _hasAtom(final ErlList list, final String s) {
    EList<Expression> _elements = list.getElements();
    final Function1<Expression,Boolean> _function = new Function1<Expression,Boolean>() {
        public Boolean apply(final Expression it) {
          boolean _hasAtom = ModelExtensions.this.hasAtom(it, s);
          return Boolean.valueOf(_hasAtom);
        }
      };
    boolean _exists = IterableExtensions.<Expression>exists(_elements, _function);
    return _exists;
  }
  
  private boolean _hasAtom(final Expression expr, final String s) {
    return false;
  }
  
  private boolean parseTransformTuple(final ErlTuple expr) {
    boolean _or = false;
    EList<Expression> _elements = expr.getElements();
    int _size = _elements.size();
    boolean _notEquals = (_size != 2);
    if (_notEquals) {
      _or = true;
    } else {
      EList<Expression> _elements_1 = expr.getElements();
      Expression _head = IterableExtensions.<Expression>head(_elements_1);
      boolean _not = (!(_head instanceof Atom));
      _or = (_notEquals || _not);
    }
    if (_or) {
      return false;
    }
    EList<Expression> _elements_2 = expr.getElements();
    Expression _head_1 = IterableExtensions.<Expression>head(_elements_2);
    final Atom hd = ((Atom) _head_1);
    boolean _and = false;
    String _sourceText = this.getSourceText(hd);
    boolean _equals = Objects.equal(_sourceText, "parse_transform");
    if (!_equals) {
      _and = false;
    } else {
      EList<Expression> _elements_3 = expr.getElements();
      Iterable<Expression> _tail = IterableExtensions.<Expression>tail(_elements_3);
      Expression _head_2 = IterableExtensions.<Expression>head(_tail);
      _and = (_equals && (_head_2 instanceof Atom));
    }
    return _and;
  }
  
  public Module getModule(final EObject element) {
    if (element instanceof Module) {
      return _getModule((Module)element);
    } else if (element != null) {
      return _getModule(element);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(element).toString());
    }
  }
  
  private Set<Expression> merge(final Set<Expression> acc, final Expression x) {
    if (x instanceof ErlList) {
      return _merge(acc, (ErlList)x);
    } else if (x != null) {
      return _merge(acc, x);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(acc, x).toString());
    }
  }
  
  private boolean hasAtom(final Expression atom, final String s) {
    if (atom instanceof Atom) {
      return _hasAtom((Atom)atom, s);
    } else if (atom instanceof ErlList) {
      return _hasAtom((ErlList)atom, s);
    } else if (atom != null) {
      return _hasAtom(atom, s);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(atom, s).toString());
    }
  }
}
