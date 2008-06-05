package org.hl7.CTSMAPI;


/**
* org/hl7/CTSMAPI/ValueSetDescription.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from idl/CTSMAPI.idl
* Monday, April 21, 2003 10:17:09 AM EDT
*/


/** 
 *<PRE>Value Set Description
 *	idAndName		- value set id and name (if any)
 *      description     	- description of the value set and its use
 *      definingExpression 	- a semi-formalized expression that defines the set contents (if any)
 *      basedOnCodeSystem	- base code system id, name and version used to construct the set

 *      allCodes        	- true means that the set includes all codes within the named code system
 *                        	  false means a selected subset
 *	head_code		- the concept code from the codeSystem that represents the entire value set (if any)
 </PRE>
 */
public final class ValueSetDescription implements org.omg.CORBA.portable.IDLEntity
{
  public org.hl7.CTSMAPI.ValueSetDescriptor idAndName = null;
  public org.hl7.types.ST description = null;
  public org.hl7.types.ST definingExpression = null;
  public org.hl7.CTSMAPI.CodeSystemDescriptor basedOnCodeSystem = null;
  public org.hl7.types.BL allCodes = null;
  public org.hl7.types.ST head_code = null;

  public ValueSetDescription ()
  {
  } // ctor

  public ValueSetDescription (org.hl7.CTSMAPI.ValueSetDescriptor _idAndName, org.hl7.types.ST _description, org.hl7.types.ST _definingExpression, org.hl7.CTSMAPI.CodeSystemDescriptor _basedOnCodeSystem, org.hl7.types.BL _allCodes, org.hl7.types.ST _head_code)
  {
    idAndName = _idAndName;
    description = _description;
    definingExpression = _definingExpression;
    basedOnCodeSystem = _basedOnCodeSystem;
    allCodes = _allCodes;
    head_code = _head_code;
  } // ctor

} // class ValueSetDescription