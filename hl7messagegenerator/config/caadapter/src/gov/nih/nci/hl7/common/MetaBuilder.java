/**
 * <!-- LICENSE_TEXT_START -->
 * $Header: /share/content/gforge/formbuilder/hl7messagegenerator/config/caadapter/src/gov/nih/nci/hl7/common/MetaBuilder.java,v 1.1 2006-10-03 17:38:25 marwahah Exp $
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


package gov.nih.nci.hl7.common;

import java.io.File;
import java.io.OutputStream;

/**
 * An interface for building meta based XML from in memory objects.
 *
 * @author OWNER: Eric Chen  Date: Jun 3, 2004
 * @author LAST UPDATE: $Author: marwahah $
 * @version $Revision: 1.1 $
 * @date $$Date: 2006-10-03 17:38:25 $
 * @since caAdapter v1.2
 */
public interface MetaBuilder {

    /**
     * @param outputStream
     * @param metaObject
     * @throws MetaException
     */
    public void build(OutputStream outputStream, MetaObject metaObject) throws MetaException;

    /**
     * Add the ability which will not earse the file content if exception is thrown
     *
     * @param file
     * @param metaObject
     * @throws MetaException
     */
    public void build(File file, MetaObject metaObject) throws MetaException;

    /**
     * HISTORY      : $Log: not supported by cvs2svn $
     * HISTORY      : Revision 1.4  2005/09/16 16:20:17  giordanm
     * HISTORY      : HL7V3 parser is not returning a result object not a just a meta object.
     * HISTORY      :
     * HISTORY      : Revision 1.3  2005/08/04 21:41:13  chene
     * HISTORY      : Support temporaly file saving
     * HISTORY      :
     * HISTORY      : Revision 1.2  2005/07/18 22:18:26  jiangsc
     * HISTORY      : First implementation of the Log functions.
     * HISTORY      :
     * HISTORY      : Revision 1.1  2005/06/03 22:43:03  chene
     * HISTORY      : Add HL7V3 Meta Builder
     * HISTORY      :
     */
}
