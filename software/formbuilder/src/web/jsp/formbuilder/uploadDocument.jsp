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
<HTML>
  <HEAD>
    <TITLE>Welcome to Form Builder..</TITLE>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
    <LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">
    <SCRIPT LANGUAGE="JavaScript">
      function submitForm(methodName) {
         var f = document.forms[0];
         document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
         f.submit();
      }      
    </SCRIPT>

<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/ui-lightness/jquery-ui-1.10.3.custom.min.css">
<script src="./js/jquery-1.9.1.js"></SCRIPT>
<script src="./js/jquery-ui-1.10.3.custom.min.js"></SCRIPT>
<script src="./js/jquery.cookie.js"></SCRIPT>
<script src="./js/jquery.marquee.js"></script>
<script src="./js/formbuilderJQ.js"></script>

<script type="text/javascript">

$(document).ready(function()
{
	setupUser();
});

</script>	

    
  </HEAD>
  <BODY topmargin=0 bgcolor="#ffffff">
    <% String urlPrefix = "";
      String pageUrl = "&PageId=DataElementsGroup";
      String protoLOVUrl= 
      "javascript:newWin('/formbuilder/formLOVAction.do?method=getProtocolsLOV&idVar=" + FormConstants.PROTOCOLS_LOV_ID_FIELD + "&nameVar=" + FormConstants.PROTOCOLS_LOV_NAME_FIELD +pageUrl+"','protoLOV',1200,900)";
      %>
    <%@ include file="../common/in_process_common_header_inc.jsp"%>
    <jsp:include page="../common/tab_inc.jsp" flush="true">
      <jsp:param name="label" value="Maintain&nbsp;Reference&nbsp;Documents"/>
      <jsp:param name="urlPrefix" value=""/>
    </jsp:include>

    <%@ include file="showMessages.jsp"%>


      <table cellpadding="0" cellspacing="0" width="80%" align="center">
        <tr >
          <td >
            &nbsp;
          </td>
        </tr>         
        <tr>
          <td class="OraHeaderSubSub" width="100%">Upload Document</td>
        </tr>
        <tr>
          <td><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
        </tr>
      </table>
      
      <html:form action="/uploadDocument.do" enctype="multipart/form-data">
      <html:hidden property="selectedRefDocId" />
      <html:hidden value="" property="<%=NavigationConstants.METHOD_PARAM%>"/>
       
       <table cellpadding="0" cellspacing="0" width="80%" align="center">
        <tr>
          <td>
            &nbsp;
          </td>
        </tr>        
        <tr>
          <td>
            If document size is larger than 2MB, the upload time will be significantly increased.
          </td>
        </tr>        
        <tr>
          <td>
            &nbsp;
          </td>
        </tr>        
        <tr >
          <td>
            <html:file size="50%" property="uploadedFile" />
          </td>
        </tr>  
        <tr >
          <td >
            &nbsp;
          </td>
        </tr>        
        <tr >

      <td>
      <table width="20%" align="left" cellpadding="1" cellspacing="1" border="0" >
        <tr >
         <td>
            <a href="javascript:submitForm('<%="uploadDocuments"%>')">
               <html:img src='<%="i/add_button.gif"%>' border="0" alt="Add Attachment"/>
            </a>
          </td>          
              
          <td > 
            <a href="javascript:submitForm('<%="cancelUploadAttachement"%>')">
         		<html:img src='<%=urlPrefix+"i/back.gif"%>' border="0" alt="Done"/>           
            </a>

          </td>                    
             
        </tr> 
                
      </table>
      </td>
           
        </tr>          
      </table>      
     </html:form>         
  
<%@ include file="../common/common_bottom_border.jsp"%>
</BODY>
</HTML>
