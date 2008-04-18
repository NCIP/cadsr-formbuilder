package org.hl7.CTSMAPI;


/**
* org/hl7/CTSMAPI/ConceptId.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from idl/CTSMAPI.idl
* Monday, April 21, 2003 10:17:09 AM EDT
*/


/** 
 *<PRE>A unique concept identifier consisting of a code system id and concept code </PRE>
 */
public final class ConceptId implements org.omg.CORBA.portable.IDLEntity
{
  public org.hl7.types.UID codeSystem_id = null;
  public org.hl7.types.ST concept_code = null;

  public ConceptId ()
  {
  } // ctor

  public ConceptId (org.hl7.types.UID _codeSystem_id, org.hl7.types.ST _concept_code)
  {
    codeSystem_id = _codeSystem_id;
    concept_code = _concept_code;
  } // ctor

} // class ConceptId