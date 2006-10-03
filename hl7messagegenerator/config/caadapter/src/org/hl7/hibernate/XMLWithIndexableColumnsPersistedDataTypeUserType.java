package org.hl7.hibernate;

import org.hl7.xml.parser.StandaloneDataTypeContentHandler;

import org.hl7.meta.Datatype;
import org.hl7.meta.UnknownDatatypeException;
import org.hl7.meta.ParametrizedDatatype;
import org.hl7.meta.impl.DatatypeMetadataFactoryImpl;
import org.hl7.types.ANY;
import org.hl7.types.IVL;
import org.hl7.types.PIVL;
import org.hl7.types.QSET;
import org.hl7.types.impl.NullFlavorImpl;
import org.hl7.types.impl.ANYnull;
import org.hl7.types.ValueFactory;
import org.hl7.types.ValueFactoryException;
import org.hl7.xml.builder.RimGraphXMLSpeaker;

import org.hibernate.usertype.CompositeUserType;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.type.Type;
import org.hibernate.type.TypeFactory;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.Properties;
import java.util.Iterator;
import java.util.Map;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.logging.Logger;

/** <p>A Hibernate UserType that persists HL7 data type values through their XML serialization and
    also breaks out some data into separate columns that are then be queriable and indexable in
    SQL.</p>

    <p>This is a generic approach, where one mapper class does it all. It uses the ParameterizedType
    interface of hibernate.</p>

    <pre>
		<property name="code">
		<type class="org.hl7.hibernate.XMLWithIndexableColumnsPersistedDataTypeUserType"> 
		<param name="type">CE</param>
	  <param name="properties">2</param>
	  <param name="property.0.name">code</param>
	  <param name="property.0.type">string</param>
	  <param name="property.1.name">codeSystem</param>
	  <param name="property.1.type">string</param>	  
	  <param name="property.1.name">extraStuff</param>
	  <param name="property.1.type">string</param>	  
	  <param name="property.1.accessor">getExtraStuff</param>
		</type>
		</property> 
		n    </pre>

    <p>Properties will be those properties that are explicitly mapped to separate columns. 
    These columns must have a name and a hibernate Type defined.</p>

    <p>Unlike the MultiColumnPersistedDataTypeUserType, the values are always reconstituted
    from the XML and never from the variables. So, if the separate columns were to be 
    updated, the reconstituted value would not change. Thus, these columns should be 
    "read only" (if something like this existed in SQL.)</p>

    <p>Presently we can only use the name to get properties from data values, we cannot use 
    general expressions to navigate or subselect properties.</p>

    @author Gunther Schadow
    @version $Id: XMLWithIndexableColumnsPersistedDataTypeUserType.java,v 1.1 2006-10-03 17:38:44 marwahah Exp $
*/
public class XMLWithIndexableColumnsPersistedDataTypeUserType extends AbstractDataTypeCompositeUserType implements ParameterizedType, CompositeUserType {
  private Datatype _datatype = null;
  private Class _interface = null;
  private final String DEFAULT_TAG = "a";

	private static final Logger LOGGER = Logger.getLogger("org.hl7.hibernate");

  /** A structure that keeps all the metadata of the properties. */
  private static class Property {
    Property() { }
    Property(String propertyName, Type hibernateType, String accessorName) {
      _propertyName = propertyName;
      _hibernateType = hibernateType;
      if(accessorName == null)
				_accessorName = _propertyName;
      else
				_accessorName = accessorName;
    }
    public String _propertyName = null;
    public String _accessorName = null;
    public String _accessorTool = null;
    public String _accessorMethod = null;
    public Type _hibernateType = null;
    public String _type=null;
  }

  /** All the properties exposed to hibernate mapping to different properties. */
  private Property _properties[];
 
  /** The names of the properties that can be mapped to different properties. */
  private String _propertyNames[] = null;
  /** The hibernate types of the properties that can be mapped to different properties. */
  private Type _propertyTypes[] = null;
 
  /** Gets called by Hibernate to pass the configured type parameters to the implementation. */
  public void setParameterValues(Properties parameters) {
    String typeSpec = (String)parameters.get("type");
    try {      
      _datatype = DatatypeMetadataFactoryImpl.instance().create(typeSpec);
      if (_datatype instanceof ParametrizedDatatype)
				_interface = Class.forName("org.hl7.types." + ((ParametrizedDatatype)_datatype).getType());
      else
				_interface = Class.forName("org.hl7.types." + _datatype.getFullName());

      // requiring this makes our job of analyzing the parameters easier for now
      int propertyCount = Integer.parseInt((String)parameters.get("properties"));
      
      _properties = new Property[propertyCount];
      _propertyNames = new String[propertyCount + 1];
      _propertyTypes = new Type[propertyCount + 1];
      
      for(int i = 0; i < propertyCount; i++) {

				Property property = _properties[i] = new Property();
				property._propertyName=(String)parameters.get("property." + i +	".name");
				property._hibernateType=TypeFactory.basic((String)parameters.get("property." + i + ".type"));
				property._accessorName=(String)parameters.get("property." + i + ".accessor");
				if(property._accessorName == null) property._accessorName = property._propertyName;
				property._accessorTool=(String)parameters.get("property." + i +	".accessorTool");
				property._accessorMethod=(String)parameters.get("property." + i + ".accessorMethod");

				property._type = (String)parameters.get("property." + i + ".type");

				if (property._propertyName==null || property._type == null) ;
				//throw new Error ("must specify a name AND a type");

				String params = (String)parameters.get("property."+i+".type.params");
				if(params != null) {
					Properties propertyParams = new Properties();
					Iterator<Map.Entry<Object,Object>> paramIter = parameters.entrySet().iterator();
					String prefix = "property."+i+".type.param.";
					while(paramIter.hasNext()) {
						Map.Entry<Object,Object> entry = paramIter.next();
						String paramKey = (String)entry.getKey();
						if(paramKey.startsWith(prefix)) {
							propertyParams.put("type",entry.getValue());
						}
					}
					String  typeParam = (String)parameters.get("property." + i + ".type");
					if (typeParam != null) {
						property._hibernateType = TypeFactory.heuristicType(typeParam, propertyParams);
					} else
						throw new Error("property type must be set");
				} else {
					property._hibernateType = TypeFactory.heuristicType(property._type);
				}
	
	
				_propertyNames[i] = property._propertyName;
				_propertyTypes[i] = property._hibernateType;
      }

      _propertyNames[propertyCount] = "xml";
      _propertyTypes[propertyCount] = Hibernate.TEXT;
      
    } catch(ClassNotFoundException x) {
      throw new Error("data type interface not found", x);
    } catch(UnknownDatatypeException x) {
      throw new Error(x);
    }
  }
          
  /** The class returned by nullSafeGet(). */
  public Class returnedClass() {
    return _interface;
  }

  /** Return the hibernate types for the properties mapped by this type. */
  public Type[] getPropertyTypes() { return _propertyTypes; }

  /** Return the hibernate types for the properties mapped by this type. */
  public String[] getPropertyNames() { return _propertyNames; }

  /* Get the value of a property. */
  public Object getPropertyValue(Object rawValue, int propertyIndex) throws HibernateException {
    try {
      if(propertyIndex < _properties.length) {
				if(rawValue!=null) {
					ANY value = (ANY)rawValue;
					Property property = _properties[propertyIndex];
					
					/** If a DatatypeTool and and Method have been specified, use them, 
							else use the accessorName **/
					
					if (property._accessorTool!=null) {
						if (property._accessorMethod==null) 
							throw new Error("Must specify both accessorTool AND accesorMethod");
						Class clazz=Class.forName(property._accessorTool);
						Method[] methods=clazz.getMethods();
						Method getter=null;
						for (Method m : methods) {
							if (m.getName().equals(property._accessorMethod))
								getter=m;
						}
						if (getter==null) 
							throw new Error("Method "+property._accessorMethod+" not found!");
						Class[] paramTypes=getter.getParameterTypes();
						if (paramTypes==null) 
							throw new Error("Method "+getter+"has no args!");
						Class valueClazz=paramTypes[0];
						return getter.invoke(null,new Object[] {valueClazz.cast(value)});
						
					} else {
						return value.getClass().getMethod(property._accessorName).invoke(value);
					}
					
				}
      }
      return null;

    } catch(NoSuchMethodException ex) {
      throw new HibernateException(ex);
    } catch(InvocationTargetException ex) {
      throw new HibernateException(ex);
    } catch(IllegalAccessException ex) {
      throw new HibernateException(ex);
    } catch (ClassNotFoundException ex) {
      throw new HibernateException(ex);
    }
  }
  
  /** Retrieve an instance of the mapped class from a JDBC resultset. */
  public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException {
    try {
      InputStream is = rs.getAsciiStream(names[_properties.length]);
      if(!rs.wasNull()) {
				return StandaloneDataTypeContentHandler.parseValue(is, _datatype);
      } else
				if (_datatype instanceof ParametrizedDatatype)
					return ValueFactory.getInstance().nullValueOf(((ParametrizedDatatype)_datatype).getType(),"NI");
				else
					return ValueFactory.getInstance().nullValueOf(_datatype.getFullName(),"NI");

      // TODO: could check for inconsistencies or override

    } catch(SQLException ex) {
      throw new HibernateException(ex);
    } catch(SAXException ex) {
			String offendingText = null;
			try {
				offendingText = rs.getString(names[_properties.length]);
			} catch(SQLException why) { }
			throw new HibernateException(ex + ": " + offendingText);
    } catch(ValueFactoryException ex) {
      throw new HibernateException(ex);
    }
  }

  public static final TransformerFactory _transformerFactory = TransformerFactory.newInstance();

  /** Write an instance of the mapped class to a prepared statement. */
  public void nullSafeSet(PreparedStatement st, Object rawValue, int index, SessionImplementor session) throws HibernateException {
    try {
      if(rawValue!=null) {
				final ANY value = (ANY)rawValue;
				for(int i = 0; i < _properties.length; i++) {
					Property property = _properties[i];
					ANY propertyValue = (ANY)getPropertyValue(value,i);
					if(propertyValue != null && propertyValue.nonNull().isTrue()) {
						// FIXME: we can't assume those are strings
						String propertyLiteral = propertyValue.toString();
						property._hibernateType.nullSafeSet(st, propertyLiteral, index + i, session);
					} else {
						property._hibernateType.nullSafeSet(st, null, index + i, session);
					}
				}
				
				//if(value.nonNull().or(value.nullFlavor().equal(NullFlavorImpl.NI).not()).isTrue() || !value.getClass().equals(ANYnull.class)) {	
					Transformer transformer = _transformerFactory.newTransformer();
					transformer.setOutputProperty(OutputKeys.INDENT, "no");
					transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
					StringWriter sw = new StringWriter();
					transformer.transform(new SAXSource(new RimGraphXMLSpeaker(), new RimGraphXMLSpeaker.DataValueInputSource(value, DEFAULT_TAG, _datatype)), new StreamResult(sw));

					/*
					 * iff we have a QSET, we must wrap the QSET in an extra root
					 * element to keep the XML well formed.  The
					 * StandaloneDatatypeContentHandler knows this happens, and it looks
					 * for the QSET when it is called
					 */
	  
					String string = sw.toString();

					if(string.length() > 0) {
						if (value.isNull().isFalse() && (value instanceof QSET) && !(value instanceof IVL) &&	!(value instanceof PIVL))
							Hibernate.TEXT.nullSafeSet(st,"<matt xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"+ string +"</matt>",index + _properties.length);
						else
							Hibernate.TEXT.nullSafeSet(st, string, index + _properties.length);

						return;
					} // else 
					// LOGGER.warning("no output for: " + rawValue.getClass() + ": " + rawValue);
          // no need for this warning. Empty strings come about when collections are empty or null
				//}
      } 
      
      // don't ever forget to set this to null explicitly or 
      // else the value from the previous insert might be used!
      
      // for i from index to index+_properties.length
      for (int i=0;i<_properties.length;i++)
				Hibernate.TEXT.nullSafeSet(st, null, index + i);
      Hibernate.TEXT.nullSafeSet(st, null, index + _properties.length);
 
    } catch(TransformerConfigurationException ex) {
      throw new HibernateException(ex);
    } catch(TransformerException ex) {
      throw new HibernateException(ex);
    } catch(SQLException ex) {
      throw new HibernateException(ex);
    }
  }
}
