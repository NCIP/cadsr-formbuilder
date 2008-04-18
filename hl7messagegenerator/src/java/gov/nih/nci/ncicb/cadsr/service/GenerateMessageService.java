package gov.nih.nci.ncicb.cadsr.service;


import gov.nih.nci.cadsr.domain.ReferenceDocument;
import gov.nih.nci.ncicb.cadsr.dao.EDCIDAOFactory;

import java.io.File;

import java.util.Collection;
import java.util.Date;


/**
 *  Methods for generating and downloading eDCI HL7 message.
 */
public interface GenerateMessageService {
   
   public static final String EDCI="edci";
   public static final String EDCI_WITHOUT_ATTACHMENT="edciwithoutattachment";
   public static final String GLOBAL_DEFINITIONS_MIF="globaldefinitionsMIF";

   /**
     * Generates the message for the given formIdSeq, user and messageType
     * messageType can be 
     *     -  EDCI
     *     -  EDCI_WITHOUT_ATTACHMENT
     * @param formIdSeq
     * @param user
     * @param messageType 
     * @return eDCI HL7 message with or without attachment or GlobalDefinitions MIF message.
     * @throws ServiceException
     */
   public String generateMessage(String formIdSeq, String user, String messageType ) throws ServiceException;
   /**
     * Gets an already generated message for the form, messageType.
     * messageType can be 
     *     -  EDCI
     *     -  EDCI_WITHOUT_ATTACHMENT
     * @param formIdSeq
     * @param generateDate
     * @param messageType
     * @return eDCI HL7 message with or without attachment or GlobalDefinitions MIF message.
     * @throws ServiceException
     */
   public String getMessage(String formIdSeq, Date generateDate, String messageType) throws ServiceException;
   /**
     * Gets an already generated message from the referenceDocument
     * @param formIdSeq
     * @param referenceDocumentName
     * @param messageType EDCI, EDCI_WITHOUT_ATTACHMENT, or GLOBAL_DEFINITIONS_MIF
     * @return Instrument HL7 message
     * @throws ServiceException
     */
   public String getMessage(String formIdSeq, String referenceDocumentName, String messageType) throws ServiceException;
   
   /**
     * Generates the message for the given formIdSeq, user and messageType
     * @param publicId
     * @param version
     * @param user
     * @param messageType
     * @return eDCI HL7 message with or without attachment or GlobalDefinitions MIF message.
     * @throws ServiceException
     */
    public String generateMessage(String publicId, String version, String user, String messageType ) throws ServiceException;
     /**
     * Gets an already generated message for the form, messageType.
     * @param publicId
     * @param version
     * @param generateDate 
     * @param messageType
     * @return eDCI HL7 message with or without attachment or GlobalDefinitions MIF message.
     * @throws ServiceException
     */
    public String getMessage(String publicId,String version,Date generateDate, String messageType) throws ServiceException;   
    /**
     * Queries message ReferenceDocument associated with a Form
     * @param publicId
     * @param version
     * @return collection of ReferenceDocument
     * @throws ServiceException
     */
    public Collection<ReferenceDocument> queryFormMessageReferenceDocuments(String publicId, String version) throws ServiceException;
    /**
     * Queries message ReferenceDocument associated with a Form
     * @param formIdSeq
     * @return
     * @throws ServiceException
     */
    public Collection<ReferenceDocument> queryFormMessageReferenceDocuments(String formIdSeq) throws ServiceException;
    
  
   
}