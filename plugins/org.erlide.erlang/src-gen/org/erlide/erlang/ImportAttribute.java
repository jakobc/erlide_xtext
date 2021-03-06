/**
 * <copyright>
 * </copyright>
 *
 */
package org.erlide.erlang;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Import Attribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.erlide.erlang.ImportAttribute#getModule <em>Module</em>}</li>
 *   <li>{@link org.erlide.erlang.ImportAttribute#getFuns <em>Funs</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.erlide.erlang.ErlangPackage#getImportAttribute()
 * @model
 * @generated
 */
public interface ImportAttribute extends Attribute
{
  /**
   * Returns the value of the '<em><b>Module</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Module</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Module</em>' containment reference.
   * @see #setModule(Expression)
   * @see org.erlide.erlang.ErlangPackage#getImportAttribute_Module()
   * @model containment="true"
   * @generated
   */
  Expression getModule();

  /**
   * Sets the value of the '{@link org.erlide.erlang.ImportAttribute#getModule <em>Module</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Module</em>' containment reference.
   * @see #getModule()
   * @generated
   */
  void setModule(Expression value);

  /**
   * Returns the value of the '<em><b>Funs</b></em>' containment reference list.
   * The list contents are of type {@link org.erlide.erlang.FunRef}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Funs</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Funs</em>' containment reference list.
   * @see org.erlide.erlang.ErlangPackage#getImportAttribute_Funs()
   * @model containment="true"
   * @generated
   */
  EList<FunRef> getFuns();

} // ImportAttribute
