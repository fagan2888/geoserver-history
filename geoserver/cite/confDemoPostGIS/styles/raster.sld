 <?xml version="1.0" encoding="UTF-8"?>
<StyledLayerDescriptor xmlns="http://www.opengis.net/sld" xmlns:ogc="http://www.opengis.net/ogc" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.opengis.net/sld
http://schemas.opengis.net/sld/1.0.0/StyledLayerDescriptor.xsd" version="1.0.0">
<UserLayer>
	<Name>raster_layer</Name>
	<UserStyle>
		<Name>raster</Name>
		<Title>A boring default style</Title>
		<Abstract>A sample style for rasters</Abstract>
		<FeatureTypeStyle>
	        <FeatureTypeName>Feature</FeatureTypeName>
			<Rule>
				<RasterSymbolizer>
				    <Opacity>1.0</Opacity>
				    <ColorMap>
				       <ColorMapEntry color="#00ff00" quantity="-500"/>
				       <ColorMapEntry color="#00fa00" quantity="-417"/>
				       <ColorMapEntry color="#14f500" quantity="-333"/>
				       <ColorMapEntry color="#28f502" quantity="-250"/>
				       <ColorMapEntry color="#3cf505" quantity="-167"/>
				       <ColorMapEntry color="#50f50a" quantity="-83"/>
				       <ColorMapEntry color="#64f014" quantity="-1"/>
				       <ColorMapEntry color="#7deb32" quantity="0"/>
				       <ColorMapEntry color="#78c818" quantity="30"/>
				       <ColorMapEntry color="#38840c" quantity="105"/>
				       <ColorMapEntry color="#2c4b04" quantity="300"/>
				       <ColorMapEntry color="#ffff00" quantity="400"/>
				       <ColorMapEntry color="#dcdc00" quantity="700"/>
				       <ColorMapEntry color="#b47800" quantity="1200"/>
				       <ColorMapEntry color="#c85000" quantity="1400"/>
				       <ColorMapEntry color="#be4100" quantity="1600"/>
				       <ColorMapEntry color="#963000" quantity="2000"/>
				       <ColorMapEntry color="#3c0200" quantity="3000"/>
				       <ColorMapEntry color="#ffffff" quantity="5000"/>
				       <ColorMapEntry color="#ffffff" quantity="13000"/>
				    </ColorMap>
				    <OverlapBehavior>
				       <AVERAGE/>
				    </OverlapBehavior>
				    <ShadedRelief/>
				</RasterSymbolizer>
			</Rule>
		</FeatureTypeStyle>
	</UserStyle>
</UserLayer>
</StyledLayerDescriptor>