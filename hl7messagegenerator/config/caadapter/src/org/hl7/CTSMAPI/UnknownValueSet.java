package org.hl7.CTSMAPI;


/**
* org/hl7/CTSMAPI/UnknownValueSet.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from idl/CTSMAPI.idl
* Monday, April 21, 2003 10:17:10 AM EDT
*/

public final class UnknownValueSet extends java.lang.Exception
{
  public org.hl7.CTSMAPI.ValueSetDescriptor valueSet = null;

  public UnknownValueSet ()
  {
    // super(UnknownValueSetHelper.id());
  } // ctor

  public UnknownValueSet (org.hl7.CTSMAPI.ValueSetDescriptor _valueSet)
  {
    // super(UnknownValueSetHelper.id());
    valueSet = _valueSet;
  } // ctor


  public UnknownValueSet (String $reason, org.hl7.CTSMAPI.ValueSetDescriptor _valueSet)
  {
    // super(UnknownValueSetHelper.id() + "  " + $reason);
    valueSet = _valueSet;
  } // ctor

} // class UnknownValueSet