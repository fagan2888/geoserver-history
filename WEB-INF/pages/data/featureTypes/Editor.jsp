<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<% try { %>
<html:form action="/config/data/typeEditorSubmit">
  <table class="info">
	<tr>
      <td class="label">
		<span class="help" title="<bean:message key="help.type.name"/>">
          <bean:message key="label.name"/>:
        </span>
      </td>
      <td class="datum">
		<bean:write name="typesEditorForm" property="name"/>
      </td>
    </tr>
	<tr>
      <td class="label">
		<span class="help" title="<bean:message key="help.type.style"/>">
          <bean:message key="label.style"/>:
        </span>
      </td>
      <td class="datum">
        <html:select property="styleId">
          <html:options property="styles"/>
        </html:select>
      </td>
    </tr>    
	<tr>
      <td class="label">
		<span class="help" title="<bean:message key="help.type.srs"/>">
          <bean:message key="label.SRS"/>:
        </span>
      </td>
	  <td class="datum">
		<html:text property="SRS" size="60"/>
	</td></tr>

    <tr>
      <td class="label">
		<span class="help" title="<bean:message key="help.type.title"/>">
          <bean:message key="label.title"/>:
        </span>
	  </td>
	  <td class="datum">
		<html:text property="title" size="60"/>
	</td></tr>

    <tr>
      <td class="label">
		<span class="help" title="<bean:message key="help.type.bbox"/>">
          <bean:message key="label.bbox"/>:
        </span>
	  </td>
	  <td class="datum">
        <table border=0>
          <tr>
            <td style="white-space: nowrap;">
              <span class="help" title="<bean:message key="help.type.minx"/>">
                <bean:message key="label.type.minx"/>:
              </span>
            </td>
            <td>
              <html:text property="minX" size="15"/>
            </td>
            <td style="white-space: nowrap;">
              <span class="help" title="<bean:message key="help.type.miny"/>">
                <bean:message key="label.type.miny"/>:
              </span>
            </td>
            <td>
              <html:text property="minY" size="15"/>
            </td>
          </tr>
          <tr>
            <td style="white-space: nowrap;">
              <span class="help" title="<bean:message key="help.type.maxx"/>">
                <bean:message key="label.type.maxx"/>:
              </span>
            </td>
            <td>
              <html:text property="maxX" size="15"/>
            </td>
            <td style="white-space: nowrap;">
              <span class="help" title="<bean:message key="help.type.maxy"/>">
                <bean:message key="label.type.maxy"/>:
              </span>
            </td>
            <td>
              <html:text property="maxX" size="15"/>
            </td>
          </tr>
        </table>
	  </td>
    </tr>

    <tr>
      <td class="label">
		<span class="help" title="<bean:message key="help.dataFeatureTypeKeywords"/>">
			<bean:message key="label.keywords"/>:
		</span>
	  </td>
	  <td class="datum">
		<html:textarea property="keywords" cols="60" rows="2"/>
	  </td>
    </tr>

    <tr>
      <td class="label">
		<span class="help" title="<bean:message key="help.dataFeatureTypeAbstract"/>">
			<bean:message key="label.abstract"/>:
		</span>
	  </td>
	  <td class="datum">
		<html:textarea property="abstract" cols="60" rows="3"/>
      </td>
    </tr>

    <tr>
      <td class="label">
		<span class="help" title="<bean:message key="help.type.base"/>">
			<bean:message key="label.base"/>:
		</span>
	  </td>
	  <td class="datum">
		<html:select property="schemaBase">
			<html:options property="allYourBase"/>
		</html:select>
		<html:submit property="action">
			Change
		</html:submit>
      </td>
    </tr>
<logic:iterate id="attribute" indexId="index" name="typesEditorForm" property="attributes">
	<tr>
      <td class="label">
		<bean:write name="attribute" property="name"/>:
	  </td>
	  <td class="datum"><% System.out.println("Hmmm.."); %>
        <table border=0 width="100%">
		<% if (attribute instanceof org.vfny.geoserver.form.data.AttributeDisplay) { %>
          <tr style="white-space: nowrap;">
            <td width="70%"><bean:write name="attribute" property="type"/></td>
            <td>nillable:<bean:write name="attribute" property="nillable"/></td>
            <td>min:<bean:write name="attribute" property="minOccurs"/></td>
            <td>max:<bean:write name="attribute" property="maxOccurs"/></td>
          </tr>
        <% } else { %>
          <tr style="white-space: nowrap;">
            <td width="70%">
            	<html:select property='<%= "attributes[" + index + "].type"%>'>
          			<html:options property='<%= "attributes[" + index + "].types"%>'/>
        		</html:select>
            </td>
            <td>nillable:<html:checkbox property='<%= "attributes[" + index + "].nillable" %>'/></td>
            <td>min:<html:text size="2" property='<%= "attributes[" + index + "].minOccurs"%>'/></td>
            <td>max:<html:text size="2" property='<%= "attributes[" + index + "].maxOccurs"%>'/></td>
          </tr>
        <% } %>
          <tr>
            <td>
              <pre><code><bean:write name="attribute" property="fragment"/></code></pre>
            </td>
          </tr>
        </table>		
      </td>
    </tr>
</logic:iterate>
    
    <tr>
      <td class="label">
        &nbsp;
      </td>
	  <td class="datum">

		<html:submit property="action">
			<bean:message key="label.submit"/>
		</html:submit>
		
		<html:reset>
			<bean:message key="label.reset"/>
		</html:reset>

	  </td>
    </tr>
  </table>
</html:form>

<% } catch (Throwable hate ){
   System.err.println( "FeatureType Editor problem:"+ hate );
   hate.printStackTrace();
   throw hate;
} %>