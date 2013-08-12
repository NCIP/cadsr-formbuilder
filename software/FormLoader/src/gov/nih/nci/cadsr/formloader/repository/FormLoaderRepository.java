package gov.nih.nci.cadsr.formloader.repository;

import gov.nih.nci.cadsr.formloader.domain.FormDescriptor;
import gov.nih.nci.ncicb.cadsr.common.dto.DataElementTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.PermissibleValueV2TransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.QuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ReferenceDocumentTransferObject;
import gov.nih.nci.ncicb.cadsr.common.resource.FormV2;
import gov.nih.nci.ncicb.cadsr.common.resource.PermissibleValueV2;
import gov.nih.nci.ncicb.cadsr.common.resource.ReferenceDocument;

import java.util.HashMap;
import java.util.List;

public interface FormLoaderRepository {
	public List<FormV2> getFormsForPublicIDs(List<String> pubicIDList);
	public List<QuestionTransferObject> getQuestionsByPublicId(String publicId);
	public List<DataElementTransferObject> getCDEByPublicId(String cdePublicId);
	public List<ReferenceDocumentTransferObject> getReferenceDocsForQuestionCde(String cdePublicId, String cdeVersion);
	public List<PermissibleValueV2TransferObject> getValueDomainPermissibleValuesByVdId(String vdSeqId);
	
	public List<QuestionTransferObject> getQuestionsByPublicIds(List<String> publicIds);
	public List<DataElementTransferObject> getCDEsByPublicIds(List<String> cdePublicIds);
	public HashMap<String, List<ReferenceDocumentTransferObject>> getReferenceDocsByCdePublicIds(List<String> cdePublicIds);
	public HashMap<String, List<PermissibleValueV2TransferObject>> getPermissibleValuesByVdIds(List<String> vdSeqIds);

	public String getContextSeqId(String contextName);
	
	public String createNewForm(FormDescriptor form, String userName, String xmlPathName, int formIdx);
}