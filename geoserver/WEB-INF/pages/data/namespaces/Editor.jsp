<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<table border=0 width=100%>

	<html:form action="/config/data/namespaceSubmit">
	
	<tr><td align="right">
		<bean:message key="label.default"/>:
	</td><td colspan=2 align="left">
		<html:checkbox property="_default"/>
	</td></tr>
	
	<tr><td align="right">
		<bean:message key="label.URI"/>
	</td><td colspan=2 align="left">
		<html:text property="URI" size="60"/>
	</td></tr>
	
	<tr><td align="right">
		<bean:message key="label.prefix"/>
	</td><td colspan=2 align="left">
		<html:text property="prefix" size="60"/>
	</td></tr>
	
	<tr><td align="right">&nbsp;</td><td colspan=2>
		<html:submit property="action">
			<bean:message key="label.submit"/>
		</html:submit>
		
		<html:reset>
			<bean:message key="label.reset"/>
		</html:reset>
	</td></tr>						
	
	</html:form>
</table>