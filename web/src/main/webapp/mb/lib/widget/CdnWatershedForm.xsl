<?xml version="1.0"?>
<xsl:stylesheet xmlns:mb="http://mapbuilder.sourceforge.net/mapbuilder" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"><xsl:output method="xml" encoding="utf-8"/><xsl:param name="lang">en</xsl:param><xsl:param name="modelId"/><xsl:param name="widgetId"/><xsl:param name="watershedTitle">Canadian Watershed Lookup service</xsl:param><xsl:param name="watershedCode">Watershed code</xsl:param><xsl:param name="webServiceUrl">http://devgeo.cciw.ca:8080/WatershedLookupServlet/WatershedLookupServlet</xsl:param><xsl:param name="formName">WatershedForm</xsl:param><xsl:template match="/"><div><form name="{$formName}" id="{$formName}" method="get" action="{$webServiceUrl}"><input name="request" type="hidden" value="GetWatershed"/><input name="version" type="hidden" value="1.0.0"/><table><tr><th align="left" colspan="3"><xsl:value-of select="$watershedTitle"/></th></tr><tr><td><xsl:value-of select="$watershedCode"/></td><td><input name="code" type="text" size="10" value="01E"/></td><td><a href="javascript:config.objects.{$widgetId}.submitForm();">load web service data</a></td></tr></table></form></div></xsl:template></xsl:stylesheet>
