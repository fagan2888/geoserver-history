package org.geoserver.wfs.http.kvp;

import net.opengis.wfs.ResultTypeType;

import org.geoserver.ows.http.KvpParser;

/**
 * Parses a kvp of the form resultType=<hits|results>.
 * <p>
 * Allowable values are "hits", and "results", which get parsed into 
 * the following respectivley.
 * <ul>
 * 	<li>{@link net.opengis.wfs.ResultTypeType#HITS_LITERAL}
 *  <li>{@link net.opengis.wfs.ResultTypeType#RESULTS_LITERAL}
 * </ul>
 * </p>
 * @author Justin Deoliveira, The Open Planning Project
 *
 */
public class ResultTypeKvpParser extends KvpParser {

	public ResultTypeKvpParser() {
		super( "resultType", ResultTypeType.class );
		
	}

	public Object parse(String value) throws Exception {
		if ( "hits".equalsIgnoreCase( value ) ) {
			return ResultTypeType.HITS_LITERAL;
		}
		
		if ( "results".equalsIgnoreCase( value ) )  {
			return ResultTypeType.RESULTS_LITERAL;
		}
		
		return null;
	}

}
