/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: DataElementTextImpl.java,v 1.1 2006-10-03 19:42:06 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;

/**
 * Language-specific texts associated with the Data Element.
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:06 $
 */
public class DataElementTextImpl implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * language in which the attributes are expressed in this class
     * instance.
     */
    private java.lang.String _language;

    /**
     * Prompt for this ItemDef, in the specified language. This
     * will serve as the default prompt for any references to this
     * def.
     */
    private java.lang.String _prompt;


      //----------------/
     //- Constructors -/
    //----------------/

    public DataElementTextImpl() 
     {
        super();
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.DataElementTextImpl()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'language'. The field 'language'
     * has the following description: language in which the
     * attributes are expressed in this class instance.
     * 
     * @return String
     * @return the value of field 'language'.
     */
    public java.lang.String getLanguage()
    {
        return this._language;
    } //-- java.lang.String getLanguage() 

    /**
     * Returns the value of field 'prompt'. The field 'prompt' has
     * the following description: Prompt for this ItemDef, in the
     * specified language. This will serve as the default prompt
     * for any references to this def.
     * 
     * @return String
     * @return the value of field 'prompt'.
     */
    public java.lang.String getPrompt()
    {
        return this._prompt;
    } //-- java.lang.String getPrompt() 

    /**
     * Sets the value of field 'language'. The field 'language' has
     * the following description: language in which the attributes
     * are expressed in this class instance.
     * 
     * @param language the value of field 'language'.
     */
    public void setLanguage(java.lang.String language)
    {
        this._language = language;
    } //-- void setLanguage(java.lang.String) 

    /**
     * Sets the value of field 'prompt'. The field 'prompt' has the
     * following description: Prompt for this ItemDef, in the
     * specified language. This will serve as the default prompt
     * for any references to this def.
     * 
     * @param prompt the value of field 'prompt'.
     */
    public void setPrompt(java.lang.String prompt)
    {
        this._prompt = prompt;
    } //-- void setPrompt(java.lang.String) 

}