package org.hl7.CTSMAPI;


/**
* org/hl7/CTSMAPI/ValueSetExpansion.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from idl/CTSMAPI.idl
* Monday, April 21, 2003 10:17:10 AM EDT
*/


/** 
 *<PRE>An expansion of a value set in a runtime mode
 *      pathLength      - an integer that identifies the distance from the root value set
 *	nodeType_code	- type of value set node (A, L or S)
 *      valueSet	- id and name (if any) of the value set associated with this node
 *      concept_id      - code system and concept code for this node (if any)
 *      displayName     - text associated with entry (if any)
 *      isExpandable    - true if this node can undergo further expansion
 *      expansionContext  - context (opaque) to use for further expansion if isExpandable is true
 </PRE>
 */
public final class ValueSetExpansion implements org.omg.CORBA.portable.IDLEntity
{
  public org.hl7.types.INT pathLength = null;
  public org.hl7.types.ST nodeType_code = null;
  public org.hl7.CTSMAPI.ValueSetDescriptor valueSet = null;
  public org.hl7.CTSMAPI.ConceptId concept_id = null;
  public org.hl7.types.ST displayName = null;
  public org.hl7.types.BL isExpandable = null;
  public byte expansionContext[] = null;

  public ValueSetExpansion ()
  {
  } // ctor

  public ValueSetExpansion (org.hl7.types.INT _pathLength, org.hl7.types.ST _nodeType_code, org.hl7.CTSMAPI.ValueSetDescriptor _valueSet, org.hl7.CTSMAPI.ConceptId _concept_id, org.hl7.types.ST _displayName, org.hl7.types.BL _isExpandable, byte[] _expansionContext)
  {
    pathLength = _pathLength;
    nodeType_code = _nodeType_code;
    valueSet = _valueSet;
    concept_id = _concept_id;
    displayName = _displayName;
    isExpandable = _isExpandable;
    expansionContext = _expansionContext;
  } // ctor

} // class ValueSetExpansion