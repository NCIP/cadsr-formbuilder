/*
THIS FILE IS GENERATED AUTOMATICALLY - DO NOT MODIFY.

The contents of this file are subject to the Health Level-7 Public
License Version 1.0 (the "License"); you may not use this file except
in compliance with the License. You may obtain a copy of the License
at http://www.hl7.org/HPL/

Software distributed under the License is distributed on an "AS IS"
basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
the License for the specific language governing rights and limitations
under the License.

The Original Code is all this file.

The Initial Developer of the Original Code is automatically generated
from HL7 copyrighted standards specification which may be subject to
different license. Portions created by Initial Developer are Copyright
(C) 2002-2004 Health Level Seven, Inc. All Rights Reserved.

THIS FILE IS GENERATED AUTOMATICALLY - DO NOT MODIFY.
*/
package org.hl7.rim.decorators;

import org.hl7.rim.QueryByParameter;
import org.hl7.rim.decorators.QuerySpecDecorator;

import org.hl7.rim.Parameter;
import /*org.hl7.rim.AssociationSet*/java.util.List;
import java.util.Collections;

/** Implementation of org.hl7.rim.QueryByParameter as an abstract decorator, i.e., a class that returns NULL/NA or nothing for all property accessprs amd that raises UnsupportedOperationExceptions for all mutators. This is used to adapt custom application classes to the RIM class interfaces and only bother about mapping those properties that actually apply to the application class. This can be done in one of two ways: (1) the client class can extend the decorator directly, and implement the applicable properties, or (2) the abstract decorator can be extend to a concrete decorator, which would hold a reference to the client object and method bodies to delegate and adapt the applicable properties.
 @see org.hl7.rim.QueryByParameter
  */
public abstract class QueryByParameterDecorator extends QuerySpecDecorator implements QueryByParameter {
  /** Property accessor, returns an empty collection if not overloaded.parameter.
      @see org.hl7.rim.QueryByParameter#getParameter
  */
  public /*AssociationSet*/List<Parameter> getParameter() { return Collections.EMPTY_LIST; }
  /** Property mutator, does nothing if not overloaded.parameter.
      @see org.hl7.rim.QueryByParameter#setParameter
  */
  public void setParameter(/*AssociationSet*/List<Parameter> parameter) { /* throw new UnsupportedOperationException(); */ }
  /** Association adder, throws UnsupportedOperationException if not overloaded parameter.
      @see org.hl7.rim.QueryByParameter#setParameter
  */
  public void addParameter(Parameter parameter) { throw new UnsupportedOperationException(); }
}