/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.9</a>, using an XML
 * Schema.
 * $Id: ConstructedElementKind.java,v 1.1 2006-10-03 19:42:06 marwahah Exp $
 */

package gov.nih.nci.ncicb.cadsr.edci.domain.impl.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Indicates the type of element to construct.
 * 
 * @version $Revision: 1.1 $ $Date: 2006-10-03 19:42:06 $
 */
public class ConstructedElementKind implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The DatatypeSummary type
     */
    public static final int DATATYPESUMMARY_TYPE = 0;

    /**
     * The instance of the DatatypeSummary type
     */
    public static final ConstructedElementKind DATATYPESUMMARY = new ConstructedElementKind(DATATYPESUMMARY_TYPE, "DatatypeSummary");

    /**
     * Field _memberTable
     */
    private static java.util.Hashtable _memberTable = init();

    /**
     * Field type
     */
    private int type = -1;

    /**
     * Field stringValue
     */
    private java.lang.String stringValue = null;


      //----------------/
     //- Constructors -/
    //----------------/

    private ConstructedElementKind(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.ConstructedElementKind(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * ConstructedElementKind
     * 
     * @return Enumeration
     */
    public static java.util.Enumeration enumerate()
    {
        return _memberTable.elements();
    } //-- java.util.Enumeration enumerate() 

    /**
     * Method getType
     * 
     * Returns the type of this ConstructedElementKind
     * 
     * @return int
     */
    public int getType()
    {
        return this.type;
    } //-- int getType() 

    /**
     * Method init
     * 
     * 
     * 
     * @return Hashtable
     */
    private static java.util.Hashtable init()
    {
        Hashtable members = new Hashtable();
        members.put("DatatypeSummary", DATATYPESUMMARY);
        return members;
    } //-- java.util.Hashtable init() 

    /**
     * Method readResolve
     * 
     *  will be called during deserialization to replace the
     * deserialized object with the correct constant instance.
     * <br/>
     * 
     * @return Object
     */
    private java.lang.Object readResolve()
    {
        return valueOf(this.stringValue);
    } //-- java.lang.Object readResolve() 

    /**
     * Method toString
     * 
     * Returns the String representation of this
     * ConstructedElementKind
     * 
     * @return String
     */
    public java.lang.String toString()
    {
        return this.stringValue;
    } //-- java.lang.String toString() 

    /**
     * Method valueOf
     * 
     * Returns a new ConstructedElementKind based on the given
     * String value.
     * 
     * @param string
     * @return ConstructedElementKind
     */
    public static gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.ConstructedElementKind valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid ConstructedElementKind";
            throw new IllegalArgumentException(err);
        }
        return (ConstructedElementKind) obj;
    } //-- gov.nih.nci.ncicb.cadsr.edci.domain.impl.types.ConstructedElementKind valueOf(java.lang.String) 

}