<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/cdebrowser.tld" prefix="cde"%>
<%--@ page import="oracle.clex.process.jsp.GetInfoBean "--%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.html.* "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.util.* "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.FormConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.NavigationConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.resource.ReferenceDocument "%>
<HTML>
  <HEAD>
    <TITLE>Welcome to Form Builder..</TITLE>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
    <LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">
    <SCRIPT LANGUAGE="JavaScript">
 
 function submitForm(methodName) {
       var f = document.forms[0];
       document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
        if (methodName =="saveNewReferenceDoc" && document.forms[0].docName.value == ""){
          alert('Reference Document Name is required.');
       return;
  }
          f.submit();
      
  }      
      
    </SCRIPT>
  </HEAD>
  <BODY topmargin=0 bgcolor="#ffffff">

    <%@ include file="../common/in_process_common_header_inc.jsp"%>
    <jsp:include page="../common/tab_inc.jsp" flush="true">
      <jsp:param name="label" value="Maintain&nbsp;Reference&nbsp;Documents"/>
      <jsp:param name="urlPrefix" value=""/>
    </jsp:include>

    <%@ include file="showMessages.jsp"%>
     <%@ include file="refDocsCreateButton_inc.jsp"%>
     
    <html:form action="/createReferenceDoc.do">
      <html:hidden property="selectedRefDocId" />
      <html:hidden value="" property="<%=NavigationConstants.METHOD_PARAM%>"/>
      
      <table cellpadding="0" cellspacing="0" width="80%" align="center">
        <tr >
          <td >
            &nbsp;
          </td>
        </tr>         
        <tr>
          <td class="OraHeaderSubSub" width="100%">New Reference Document</td>
        </tr>
        <tr>
          <td><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
        </tr>
      </table>
      
        
      
            <table width="80%" align="center" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark">
              <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="20%" nowrap>
                    Name 
                  </td>
                  <td class="OraFieldText" nowrap>
                    <html:text size="80" property="docName" maxlength="<%= Integer.toString(FormConstants.LONG_NAME_MAX_LENGTH)%>">
                    </html:text>
                  </td>
               </tr>    

               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="20%" nowrap>
                    Type 
                  </td>
                  <td class="OraFieldText" nowrap>
                    <html:select styleClass="Dropdown" property="docType" style="width:30%">               
                    <html:option value="<%=ReferenceDocument.REF_DOC_TYPE_IMAGE%>"/>
                    <html:options name="<%=FormConstants.ALL_REFDOC_TYPES%>" /> 
                    </html:select>
                  </td>
               </tr>    
 
               
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="20%" nowrap>
                    <bean:message key="cadsr.formbuilder.form.context"/> 
                  </td>
                  <td class="OraFieldText" nowrap>
                    <html:select styleClass="Dropdown" property="contextIdSeq" style="width:20%">               
                      <html:options collection="<%=CaDSRConstants.USER_CONTEXTS%>" property="conteIdseq" labelProperty="name"/>
                    </html:select>
                  </td>
               </tr>  
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="20%" nowrap>
                    URL 
                  </td>
                  <td class="OraFieldText" nowrap>
                    <html:text size="80" property="url" maxlength="<%= Integer.toString(FormConstants.LONG_NAME_MAX_LENGTH)%>">
                    </html:text>
                  </td>
               </tr>    
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="20%" nowrap>
                    Description 
                  </td>
                  <td  class="OraFieldText" size="80%">
                    <html:textarea property="docText" cols="90%" rows="2">
                   </html:textarea>
                  </td>
               </tr>                 
             </table>
             
      <table width="80%" align="center" cellpadding="0" cellspacing="0" border="0" >
                  <tr class>
                    <td >
                      &nbsp;
                    </td>
                  </tr> 
      </table>                   
          <%@ include file="refDocsCreateButton_inc.jsp"%>
     </html:form>         
  <table width="80%" align="center" cellpadding="0" cellspacing="0" border="0" >
    <tr class>
      <td >
        &nbsp;
      </td>
    </tr> 
  </table>
  
<logic:notPresent name="<%=FormConstants.CRF%>">Selected form has been deleted by a diffrent user </logic:notPresent>
<%@ include file="../common/common_bottom_border.jsp"%>
</BODY>
</HTML>
