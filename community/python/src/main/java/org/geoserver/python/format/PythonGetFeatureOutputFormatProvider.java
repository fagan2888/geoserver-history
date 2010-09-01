package org.geoserver.python.format;

import org.geoserver.python.Python;
import org.geoserver.wfs.WFSGetFeatureOutputFormat;

public class PythonGetFeatureOutputFormatProvider extends PythonVectorOutputFormatProvider<WFSGetFeatureOutputFormat> {

    public PythonGetFeatureOutputFormatProvider(Python py) {
        super(py);
    }
    
    public Class<WFSGetFeatureOutputFormat> getExtensionPoint() {
        return WFSGetFeatureOutputFormat.class;
    }
    
    @Override
    protected WFSGetFeatureOutputFormat createOutputFormat(PythonVectorFormatAdapter adapter) {
        return new PythonGetFeatureOutputFormat(adapter);
    }

}
