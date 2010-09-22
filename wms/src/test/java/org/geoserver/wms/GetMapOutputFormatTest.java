package org.geoserver.wms;

import java.util.List;
import java.util.Set;

import junit.framework.Test;

/**
 * An integration test for the GetMapOutputFormat implementations
 * 
 * @author Gabriel Roldan (TOPP)
 * @version $Id$
 */
public class GetMapOutputFormatTest extends WMSTestSupport {

    /**
     * This is a READ ONLY TEST so we can use one time setup
     */
    public static Test suite() {
        return new OneTimeTestSetup(new GetMapOutputFormatTest());
    }

    public void testGetOutputFormatNames() {
        List<GetMapOutputFormat> producers = WMSExtensions.findMapProducers(applicationContext);
        for (GetMapOutputFormat producer : producers) {
            Set<String> outputFormats = producer.getOutputFormatNames();
            assertNotNull(outputFormats);
            assertTrue(outputFormats.size() > 0);
            for (String oformat : outputFormats) {
                assertNotNull(oformat);
            }
        }
    }

    public void testGetOutputFormat() {
        List<GetMapOutputFormat> producers = WMSExtensions.findMapProducers(applicationContext);
        for (GetMapOutputFormat producer : producers) {
            assertNotNull(producer.getMimeType());
        }
    }

    public void testSetOutputFormat() {
        List<GetMapOutputFormat> producers = WMSExtensions.findMapProducers(applicationContext);
        for (GetMapOutputFormat producer : producers) {
            Set<String> outputFormats = producer.getOutputFormatNames();
            for (String outputFormat : outputFormats) {
                producer.setOutputFormat(outputFormat);
                String producerFormat = producer.getOutputFormat();
                String msg = producer.getClass().getName() + " output format not set";
                assertEquals(msg, outputFormat, producerFormat);
            }

            try {
                producer.setOutputFormat("not-a-valid-output-format");
                fail(producer.getClass().getName() + " didn't throw an IAE when an invalid "
                        + "output format was set");
            } catch (IllegalArgumentException e) {
                assertTrue(true);
            }
        }
    }

    public void testSetOutputFormatIsCaseInsensitive() {
        List<GetMapOutputFormat> producers = WMSExtensions.findMapProducers(applicationContext);
        for (GetMapOutputFormat producer : producers) {
            Set<String> outputFormats = producer.getOutputFormatNames();
            for (String outputFormat : outputFormats) {

                char caseChangedChar = outputFormat.charAt(0);
                if (Character.isUpperCase(caseChangedChar)) {
                    caseChangedChar = Character.toLowerCase(caseChangedChar);
                } else {
                    caseChangedChar = Character.toUpperCase(caseChangedChar);
                }
                String caseChangedFormatName;
                caseChangedFormatName = caseChangedChar + outputFormat.substring(1);

                producer.setOutputFormat(caseChangedFormatName);
                String producerFormat = producer.getOutputFormat();
                String msg = producer.getClass().getName() + " output format not set";
                assertTrue(msg, outputFormat.equalsIgnoreCase(producerFormat));
            }
        }
    }
}
