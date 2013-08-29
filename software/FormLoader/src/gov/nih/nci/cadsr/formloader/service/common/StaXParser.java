package gov.nih.nci.cadsr.formloader.service.common;

import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.ncicb.cadsr.common.dto.DefinitionTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.DesignationTransferObjectExt;
import gov.nih.nci.ncicb.cadsr.common.dto.ReferenceDocumentTransferObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.log4j.Logger;

public class StaXParser {
	
	private static Logger logger = Logger.getLogger(StaXParser.class.getName());
	
	protected static final Map<String, String> MAP_XML_ELEM_TO_DTO_PROPERTY = createMap();
	
	protected static final String PREFIX_CDE = "cde";

	//Method names that are not in this format: set<NodeName>
    protected static Map<String, String> createMap() {
        Map<String, String> mapping = new HashMap<String, String>();
        mapping.put("publicID", "publicId");
        mapping.put(PREFIX_CDE+"publicID", "cdePublicId");
        mapping.put(PREFIX_CDE+"version", "cdeVersion");
        mapping.put("isEditable", "editable");
        mapping.put("isMandatory", "mandatory");
       return Collections.unmodifiableMap(mapping);
    }

	//Mapping node name in xml
	protected static final String FORM = "form";
	protected static final String PUBLIC_ID = "publicID";
	protected static final String LONG_NAME = "longName";
	protected static final String VERSION = "version";
	protected static final String CONTEXT = "context";
	protected static final String WORKFLOW_STATUS = "workflowStatusName";
	protected static final String TYPE = "type";
	protected static final String PROTOCOL = "protocol";
	protected static final String PROTOCOL_ID = "protocolID";
	protected static final String MODULE = "module";
	protected static final String MODULE_MAX_REPEAT = "maximumModuleRepeat";
	
	//Form mapping pass 2	
	protected static final String CREATED_BY = "createdBy";
	protected static final String MODIFIED_BY = "modifiedBy";
	protected static final String CHANGE_NOTE = "changeNote";
	protected static final String PREFERRED_DEFINITION = "preferredDefinition";
	protected static final String REGISTRATION_STATUS = "registrationStatus";
	protected static final String HEADER_INSTRUCTION = "headerInstruction";
	protected static final String FOOTER_INSTRUCTION = "footerInstruction";
	protected static final String CATEGORY_NAME = "categoryName";
	protected static final String REFERENCE_DOCUMENT = "referenceDocument";
	protected static final String REFERENCE_DOCUMENT_TYPE = "type";
	protected static final String REFERENCE_DOCUMENT_DOCTEXT = "doctext";
	protected static final String DEFINITION = "definition";
	protected static final String DESIGNATION = "designation";
	protected static final String NAME = "name";
	protected static final String LANGUAGE_NAME = "languageName";
	
	
	//Question 
	protected static final String QUESTION = "question";
	protected static final String QUESTION_TEXT = "questionText";
	protected static final String DEFAULT_VALUE = "defaultValue";
	protected static final String INSTRUCTION = "instruction";
	protected static final String TEXT = "text";
	protected static final String VALID_VALUE = "validValue";
	protected static final String VALUE = "value";
	protected static final String MEANING_TEXT = "meaningText";
	protected static final String DESCRIPTION = "description";
	protected static final String IS_EDITABLE = "isEditable";
	protected static final String IS_MANDATORY = "isMandatory";
	
	protected static final String DATA_ELEMENT = "dataElement";
	protected static final String VALUE_DOMAIN = "valueDomain";
	
	
	
	
	List<ReferenceDocumentTransferObject> refdocs;
	List<DefinitionTransferObject> definitions;
	List<String> protocolIds;
	List<DesignationTransferObjectExt> designations;
	
	
	/**
	 * For the first pass of the collection xml, to get the forms' basic header info
	 * @param xmlPathName
	 * @return
	 */
	public List<FormDescriptor> parseFormHeaders(String xmlPathName) {
		List<FormDescriptor> forms = new ArrayList<FormDescriptor>();
		ParserHandler handler = new FormParserHandler(forms);
		return parseFormHeaders(xmlPathName, handler);
	}
	
	/**
	 * For the second pass of the xml, to get question details
	 * @param xmlPathName
	 * @param forms
	 * @return
	 */
	public List<FormDescriptor> parseFormQuestions(String xmlPathName, List<FormDescriptor> forms) {
		if (forms == null || forms.size() == 0) {
			logger.error("No forms to work on. Should call parseFormHeaders() first");
			return null;
		}
		
		ParserHandler handler = new FormQuestionParserHandler(forms);
		return parseFormHeaders(xmlPathName, handler);
	}
	
	/**
	 * For the 3rd pass of the xml, to get form's other info: ref docs, definitions, designations, etc.
	 * @param xmlPathName
	 * @param forms
	 * @return
	 */
	public FormDescriptor parseFormDetails(String xmlPathName, FormDescriptor currForm, int currFormIdx) {
		if (currForm == null) {
			logger.error("Form objec invalid. Should call parseFormHeaders() first");
			return null;
		}
		
		ParserHandler handler = new FormDetailsParserHandler(currForm, currFormIdx);
		parseFormHeaders(xmlPathName, handler);
		//get the generatted list
		this.setProtocolIds(((FormDetailsParserHandler)handler).getProtocolIds());
		this.setRefdocs(((FormDetailsParserHandler)handler).getRefdocs());
		this.setDefinitions(((FormDetailsParserHandler)handler).getDefinitions());
		this.setDesignations(((FormDetailsParserHandler)handler).getDesignations());
		return currForm;
	}

	public List<FormDescriptor> parseFormHeaders(String xmlPathName, ParserHandler handler) {
		
		FileReader fReader = null;
		BufferedReader bReader = null;
		XMLStreamReader xmlreader = null;
		
		try {
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			bReader = new BufferedReader(new FileReader(xmlPathName)); 
			xmlreader = inputFactory.createXMLStreamReader(bReader);
			
			while(xmlreader.hasNext()){
				
			      readNext(xmlreader, handler);
			      xmlreader.next();
			}
			      
		}  catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fReader != null)
					fReader.close();
				if (xmlreader != null)
					xmlreader.close();
			} catch (IOException e) {
				logger.error("Error while closing FileReader handle in parseFormCollection()");
			} catch (XMLStreamException xmle) {
				logger.error("Error while closing xml reader handle in parseFormCollection()");
			}
		}
		
		List<FormDescriptor> generatedForms = handler.getFormList();
		
		//for (int i = 0; i < generatedForms.size(); i++) 
		//	logger.debug(generatedForms.get(i).toString());
		return generatedForms;
	}
	
	protected void readNext(XMLStreamReader xmlreader,ParserHandler handler) {
		if (xmlreader == null) {
			logger.debug("xmlread is null - this should never happen.");
			return;
		}

		switch (xmlreader.getEventType()) {

		case XMLStreamConstants.START_ELEMENT:
			handler.handleStartElement(xmlreader);
			break;
		case XMLStreamConstants.END_ELEMENT:
			handler.handleEndElement(xmlreader);
			break;
		case XMLStreamConstants.CHARACTERS:
			handler.handleCharacterElement(xmlreader);
			break;
		case XMLStreamConstants.PROCESSING_INSTRUCTION:
			System.out.print("Processing Instrcutions\n");
			break;
		case XMLStreamConstants.CDATA:
			System.out.print("CDATA\n");
			break;
		case XMLStreamConstants.COMMENT:
			System.out.print("Comment\n");
			break;
		case XMLStreamConstants.DTD:
			System.out.print("DTD\n");
			break;
		case XMLStreamConstants.ENTITY_REFERENCE:
			System.out.print("Entity Reference\n");
			break;
		case XMLStreamConstants.ENTITY_DECLARATION:
			System.out.print("Entity Declaration\n");
			break;
		case XMLStreamConstants.START_DOCUMENT:
			System.out.print("Start Document\n");
			break;
		case XMLStreamConstants.END_DOCUMENT:
			System.out.print("End Document\n");
			break;
		case XMLStreamConstants.SPACE:
			System.out.print("Space\n");
			break;
		}

	}

	public List<ReferenceDocumentTransferObject> getRefdocs() {
		return refdocs;
	}

	public void setRefdocs(List<ReferenceDocumentTransferObject> refdocs) {
		this.refdocs = refdocs;
	}

	public List<DefinitionTransferObject> getDefinitions() {
		return definitions;
	}

	public void setDefinitions(List<DefinitionTransferObject> definitions) {
		this.definitions = definitions;
	}

	public List<String> getProtocolIds() {
		return protocolIds;
	}

	public void setProtocolIds(List<String> protocolIds) {
		this.protocolIds = protocolIds;
	}

	public List<DesignationTransferObjectExt> getDesignations() {
		return designations;
	}

	public void setDesignations(List<DesignationTransferObjectExt> designations) {
		this.designations = designations;
	}


}
