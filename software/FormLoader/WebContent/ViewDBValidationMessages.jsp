<%--L
  Copyright Oracle Inc, ScenPro Inc, SAIC-F

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-formbuilder/LICENSE.txt for details.
L--%>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<LINK REL=STYLESHEET TYPE="text/css" HREF="/FormLoader/css/blaf.css">

<style type="text/css">
@import url(css/style.css);
</style>

<title>View Form's DB Validation Messages</title>

</head>

<div style="padding-left: 50px; padding-right: 50px;">

<body>
<div class="content">
<table class="fileTable">
<tr>
<td align="left"><b>Form Public Id in Xml:</b></td>
<td>&nbsp;</td>
<td valign="bottom"><b><s:property value="publicId" /></b></td>
</tr>
<tr>
<td align="left"><b>Form Version in Xml:</b></td>
<td>&nbsp;</td>
<td ><b><s:property value="version" /></b></td>
</tr>
<tr/>
<tr/>
<tr/>
<tr><td colspan="3">The following are messages generated by Form Loader during DB validation:</td></tr>
</table>
</div>
<br><br>

<s:actionerror />


<s:if test="formMessages.size() > 0">
<div class="content">
		<table class="userTable">
	
	<s:iterator value="formMessages" var="msg" status="status">	
	<tr class="<s:if test="#status.odd == true ">odd</s:if> <s:else>even</s:else>">
	<td>
		<ul><s:property value="msg" /></ul>
		</td>
		</tr>
	</s:iterator>
	
	</table>
	</div>
</s:if>	

<s:if test="questionMessages.size() > 0">
<div class="content">
		<table class="userTable">
	
	<s:iterator value="questionMessages" var="msg" status="status">	
	<tr class="<s:if test="#status.odd == true ">odd</s:if> <s:else>even</s:else>">
	<td>
		<ul><s:property value="msg" /></ul>
		</td>
		</tr>
	</s:iterator>
	
	</table>
	</div>
</s:if>	
		

</body>
</div>
</html>