/**
 */
package org.erlide.erlang;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Spec Fun</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.erlide.erlang.SpecFun#getModule <em>Module</em>}</li>
 *   <li>{@link org.erlide.erlang.SpecFun#getFunction <em>Function</em>}</li>
 *   <li>{@link org.erlide.erlang.SpecFun#getArity <em>Arity</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.erlide.erlang.ErlangPackage#getSpecFun()
 * @model
 * @generated
 */
public interface SpecFun extends EObject
{
  /**
   * Returns the value of the '<em><b>Module</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Module</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Module</em>' attribute.
   * @see #setModule(String)
   * @see org.erlide.erlang.ErlangPackage#getSpecFun_Module()
   * @model
   * @generated
   */
  String getModule();

  /**
   * Sets the value of the '{@link org.erlide.erlang.SpecFun#getModule <em>Module</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Module</em>' attribute.
   * @see #getModule()
   * @generated
   */
  void setModule(String value);

  /**
   * Returns the value of the '<em><b>Function</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Function</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Function</em>' attribute.
   * @see #setFunction(String)
   * @see org.erlide.erlang.ErlangPackage#getSpecFun_Function()
   * @model
   * @generated
   */
  String getFunction();

  /**
   * Sets the value of the '{@link org.erlide.erlang.SpecFun#getFunction <em>Function</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Function</em>' attribute.
   * @see #getFunction()
   * @generated
   */
  void setFunction(String value);

  /**
   * Returns the value of the '<em><b>Arity</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Arity</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Arity</em>' attribute.
   * @see #setArity(String)
   * @see org.erlide.erlang.ErlangPackage#getSpecFun_Arity()
   * @model
   * @generated
   */
  String getArity();

  /**
   * Sets the value of the '{@link org.erlide.erlang.SpecFun#getArity <em>Arity</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Arity</em>' attribute.
   * @see #getArity()
   * @generated
   */
  void setArity(String value);

} // SpecFun
