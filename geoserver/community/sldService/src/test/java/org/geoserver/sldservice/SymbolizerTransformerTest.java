/**
 * 
 */
package org.geoserver.sldservice;

import java.io.File;

import junit.framework.TestCase;

import org.geotools.styling.NamedLayer;
import org.geotools.styling.SLDTransformer;
import org.geotools.styling.StyledLayerDescriptor;
import org.geotools.styling.Symbolizer;

/**
 * @author kappu
 *
 */
public class SymbolizerTransformerTest extends TestCase {

	/**
	 * Test method for {@link org.geoserver.sldservice.utils.SymbolizerTransformer#SymbolizerTransformer()}.
	 */
	public void testSymbolizerTransformer() {
		SldDoc sldDoc= new SldDoc();
		//SymbolizerTransformer sT=new SymbolizerTransformer();
		SLDTransformer sT=new SLDTransformer();
		File f = new File("src/test/resources/Lakes.sld");
		try {
			StyledLayerDescriptor s= sldDoc.loadFile(f);
			NamedLayer st = (NamedLayer) s.getStyledLayers()[0];
		    Symbolizer[] sm = st.getStyles()[0].getFeatureTypeStyles()[0].getRules()[0].getSymbolizers();
		    String xml = sT.transform(sm[0]);
		    System.out.print(xml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	/**
	 * Test method for {@link org.geoserver.sldservice.utils.SymbolizerTransformer#SymbolizerTransformer(java.util.Map)}.
	 */
	public void testSymbolizerTransformerMap() {
	
	}

	/**
	 * Test method for {@link org.geoserver.sldservice.utils.SymbolizerTransformer#createTranslator(org.xml.sax.ContentHandler)}.
	 */
	public void testCreateTranslatorContentHandler() {
		
	}

}
