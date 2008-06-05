/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/clone/data/CloneData.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $
 *
 * ******************************************************************
 * COPYRIGHT NOTICE
 * ******************************************************************
 *
 * The caAdapter Software License, Version 1.3
 * Copyright Notice.
 * 
 * Copyright 2006 SAIC. This software was developed in conjunction with the National Cancer Institute. To the extent government employees are co-authors, any rights in such works are subject to Title 17 of the United States Code, section 105. 
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met: 
 * 
 * 1. Redistributions of source code must retain the Copyright Notice above, this list of conditions, and the disclaimer of Article 3, below. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * 
 * 2. The end-user documentation included with the redistribution, if any, must include the following acknowledgment:
 * 
 * 
 * "This product includes software developed by the SAIC and the National Cancer Institute."
 * 
 * 
 * If no such end-user documentation is to be included, this acknowledgment shall appear in the software itself, wherever such third-party acknowledgments normally appear. 
 * 
 * 3. The names "The National Cancer Institute", "NCI" and "SAIC" must not be used to endorse or promote products derived from this software. 
 * 
 * 4. This license does not authorize the incorporation of this software into any third party proprietary programs. This license does not authorize the recipient to use any trademarks owned by either NCI or SAIC-Frederick. 
 * 
 * 5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE UNITED STATES GOVERNMENT, THE NATIONAL CANCER INSTITUTE, SAIC, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * <!-- LICENSE_TEXT_END -->
 */


package gov.nih.nci.hl7.clone.data;


import gov.nih.nci.hl7.common.DataObject;

import java.util.List;
import java.util.Map;

 /**
 * HL7 v3 clone data
 *
 * @author OWNER: Eric Chen  Date: Jun 8, 2005
 * @author LAST UPDATE: $Author: marwahah $

 * @since caAdapter v1.2
 * @version $Revision: 1.1 $
 * @date     $$Date: 2006-10-03 17:38:25 $
 */


public interface CloneData extends DataObject {

    /**
     * A list of Map related to the clone attribute name
     * @param attributeName
     * @return List of Map
     */
    public List<Map<String, Object>> getValueMapList(String attributeName);

    /**
     * Sub Class for that attribute with attribute name with the passing parameter for the specialization for ANY and QTY
     * @param attributeName
     * @return String
     */
    public String getSubClass(String attributeName);

    /**
     * Clone Class Attributes
     * @return List<CloneAttributeData>
     */
    public List<CloneAttributeData> getAttributes();

    /**
     * A list of clone attribute data related to attribute name
     * @param attributeName
     * @return
     */
    public List<CloneAttributeData> getAttributes(String attributeName);

    /**
     *
     * @param cloneAttribute
     */
    public void addAttribute(CloneAttributeData cloneAttribute);

    /**
     *
      * @param cloneAttributes
     */
    public void addAttributes(List<CloneAttributeData> cloneAttributes);


    /**
     * Look a list of ChildClone which clone name equals parameter
     * childCloneName
     *
     * @param childCloneName
     * @return List<CloneData>
     */
    public List<CloneData> getChildClones(String childCloneName);


    /**
     * Child Clone Classes
     * @return List<CloneData>
     */
    public List<CloneData> getChildClones();

    /**
     *
     * @param cloneData
     */
    public void addClone(CloneData cloneData);

    /**
     *
      * @param cloneDatas
     */
    public void addClones(List<CloneData> cloneDatas);


 }

/**
 * HISTORY      : $Log: not supported by cvs2svn $
 * HISTORY      : Revision 1.13  2006/08/02 18:44:19  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.12  2006/01/03 19:16:51  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.11  2006/01/03 18:27:13  jiangsc
 * HISTORY      : License Update
 * HISTORY      :
 * HISTORY      : Revision 1.10  2005/12/29 23:06:14  jiangsc
 * HISTORY      : Changed to latest project name.
 * HISTORY      :
 * HISTORY      : Revision 1.9  2005/12/28 20:52:22  chene
 * HISTORY      : Java Doc updated
 * HISTORY      :
 * HISTORY      : Revision 1.8  2005/12/28 17:54:12  chene
 * HISTORY      : Refactory JavaDOC
 * HISTORY      :
 * HISTORY      : Revision 1.7  2005/12/14 22:48:02  chene
 * HISTORY      : Promote backend org.hl7.xml.builder.AttributeCardinalityException: AuthorOrPerformer3.typeCode: HMD specifies cardinality 1..1, RIM object has 0 to validation message as well
 * HISTORY      :
 * HISTORY      : Revision 1.6  2005/07/19 20:23:45  giordanm
 * HISTORY      : More Map Processor work.
 * HISTORY      :
 * HISTORY      : Revision 1.5  2005/07/07 20:28:42  chene
 * HISTORY      : Change BaseComponenet Meta getData() to Meta getMeta
 * HISTORY      :
 * HISTORY      : Revision 1.4  2005/06/09 03:30:03  chene
 * HISTORY      : First Cut of RimGraphGen
 * HISTORY      :
 * HISTORY      : Revision 1.3  2005/06/09 02:16:58  chene
 * HISTORY      : First Cut of RimGraphGen
 * HISTORY      :
 * HISTORY      : Revision 1.2  2005/06/08 22:29:50  chene
 * HISTORY      : Update Clone Data implementation
 * HISTORY      :
 * HISTORY      : Revision 1.1  2005/06/08 17:30:01  chene
 * HISTORY      : First Cut of HL7 Data Objects
 * HISTORY      :
 */