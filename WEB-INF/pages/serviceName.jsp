<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<span class="site">
	<bean:write name="Config.WFS" property="title"/> |
	<bean:write name="Config.WMS" property="title"/>
</span>