package org.hl7.CTSMAPI;


/**
* org/hl7/CTSMAPI/SubsumptionNotSupported.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from idl/CTSMAPI.idl
* Monday, April 21, 2003 10:17:10 AM EDT
*/

public final class SubsumptionNotSupported extends java.lang.Exception
{
  public org.hl7.types.UID codeSystem_id = null;

  public SubsumptionNotSupported ()
  {
    // super(SubsumptionNotSupportedHelper.id());
  } // ctor

  public SubsumptionNotSupported (org.hl7.types.UID _codeSystem_id)
  {
    // super(SubsumptionNotSupportedHelper.id());
    codeSystem_id = _codeSystem_id;
  } // ctor


  public SubsumptionNotSupported (String $reason, org.hl7.types.UID _codeSystem_id)
  {
    // super(SubsumptionNotSupportedHelper.id() + "  " + $reason);
    codeSystem_id = _codeSystem_id;
  } // ctor

} // class SubsumptionNotSupported