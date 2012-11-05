package org.erlide.ui.labeling

import com.google.inject.Inject
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider
import org.eclipse.jface.viewers.StyledString
import org.eclipse.jface.viewers.StyledString$Styler
import org.eclipse.swt.SWT
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.graphics.Font
import org.eclipse.swt.widgets.Display
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider
import org.erlide.erlang.Attribute
import org.erlide.erlang.CustomAttribute
import org.erlide.erlang.DefineAttribute
import org.erlide.erlang.Expression
import org.erlide.erlang.FunRef
import org.erlide.erlang.Function
import org.erlide.erlang.FunctionClause
import org.erlide.erlang.ModelExtensions
import org.erlide.erlang.Module
import org.erlide.erlang.RecordAttribute
import org.erlide.erlang.ModuleAttribute
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider
import org.erlide.erlang.FunRef
import org.erlide.erlang.ExportAttribute

/**
 * Provides labels for a EObjects.
 * 
 * see
 * http://www.eclipse.org/Xtext/documentation/latest/xtext.html#labelProvider
 */
public class ErlangLabelProviderBase extends DefaultEObjectLabelProvider {

    val Styler grayStyler = createStyler(null, Display::current.getSystemColor(SWT::COLOR_GRAY))

	@Inject
	extension ModelExtensions

	@Inject
	new(AdapterFactoryLabelProvider delegate) {
		super(delegate)
	}

    def Object text(Module module) {
        val StyledString s = new StyledString
        s.append("module ", grayStyler)
        s.append(module.name)
        return s
    }
    
    def Object text(DefineAttribute attribute) {
        val StyledString s = new StyledString
        s.append(attribute.tag, grayStyler)
        s.append(" ")
        s.append(attribute.macroName)
        return s
    }
    
    def Object text(RecordAttribute attribute) {
        val StyledString s = new StyledString
        s.append(attribute.tag, grayStyler)
        s.append(" ")
        s.append(attribute.name)
        return s
    }
    
    def Object text(CustomAttribute attribute) {
        val StyledString s = new StyledString
        s.append(attribute.tag, grayStyler)
        s.append(" ")
        s.append(attribute.value.toString)
        return s
    }
    
    def Object text(Function function) {
		if (function.getClauses() == null || function.getClauses().size() == 0) {
			return "???"
		}
        val StyledString s = new StyledString
        s.append("function", grayStyler)
        s.append(" ")
        s.append(function.name)
		val EList<Expression> params = function.clauses.head.params.exprs
		s.append("/" + (if(params == null) "0" else params.size()))
        return s
    }
    
    def Object text(FunctionClause clause) {
        val StyledString s = new StyledString
        val args = clause.params.sourceText
        s.append((clause.eContainer as Function).name, grayStyler)
        s.append(" (")
        s.append(args) 
        s.append(")")
        return s
    }
    
	def String text(Attribute ele) {
		val tag = ele.tag
		return "-" + tag + " -- " + ele.sourceText
	}
	
	def String text(ModuleAttribute ele) {
		return "module " + ele.moduleName
	}

	def String text(ExportAttribute ele) {
		"export: " + ele.funs.map[FunRef f | text(f)].join(", ")
	}

	def String text(FunRef c) {
		return c.function.sourceText + "/" + c.arity.sourceText
    }

	def String getListText(EList<Expression> list) {
		return list.map[sourceText].join(", ")
	}

    def String image(EObject element) {
        "full/obj16/skip.gif"
    }
    	
	def String image(Function ele) {
		if (ele.exported)
			"public_function.gif"
		else
			"private_function.gif" 
  	}
  
	def String image(FunctionClause element) {
		"function_clause.gif"
  	}
  	
  	def String image(Attribute ele) {
  		"attribute.gif"
  	}

    def private static Styler createStyler(Font font, Color color) {
        new ErlideStyler(font, color)
    }

	def String image(FunRef ele) {
		"function_ref.gif"
	}

}
