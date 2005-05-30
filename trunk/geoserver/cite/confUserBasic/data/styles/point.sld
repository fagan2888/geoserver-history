<?xml version="1.0" encoding="ISO-8859-1"?>
<StyledLayerDescriptor version="1.0.0" xsi:schemaLocation="http://www.opengis.net/sld StyledLayerDescriptor.xsd" xmlns="http://www.opengis.net/sld" xmlns:ogc="http://www.opengis.net/ogc" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
<!-- a named layer is the basic building block of an sld document -->
<NamedLayer><Name>normal</Name>
<UserStyle>
    <!-- again they have names, titles and abstracts -->
  
  <Title>A boring default style</Title>
  <Abstract>A sample style that just prints out a black line for everything</Abstract>
    <!-- FeatureTypeStyles describe how to render different features -->
    <!-- a feature type for polygons -->
    <FeatureTypeStyle>
      <FeatureTypeName>Feature</FeatureTypeName>
      <Rule>
        <!-- like a linesymbolizer but with a fill too -->
        <PointSymbolizer>
       <Graphic>
       	<Mark>
       	<WellKnownName>circle</WellKnownName>
       	<Fill>
       	<CssParameter name="fill">#ff0000</CssParameter>
       	</Fill></Mark><Size>15.0</Size></Graphic>
        </PointSymbolizer>
      </Rule>
    </FeatureTypeStyle>
</UserStyle>
</NamedLayer>
</StyledLayerDescriptor>

