/* Copyright (c) 2001 - 2007 TOPP - www.openplans.org.  All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.vfny.geoserver.global.xml;

import org.vfny.geoserver.global.ConfigurationException;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;


/**
 * WriterUtils purpose.<p>Used to provide assitance writing xml to a
 * Writer.</p>
 *  <p></p>
 *
 * @author dzwiers, Refractions Research, Inc.
 * @version $Id: WriterHelper.java,v 1.9 2004/02/09 23:29:49 dmzwiers Exp $
 */
public class WriterHelper {
    /** Used internally to create log information to detect errors. */
    private static final Logger LOGGER = Logger.getLogger("org.vfny.geoserver.global");

    /** The output writer. */
    protected Writer writer;
    protected int indent;
    protected StringBuffer indentBuffer = new StringBuffer();

    /**
             * WriterUtils constructor.
             *
             * <p>
             * Should never be called.
             * </p>
             */
    protected WriterHelper() {
    }

    /**
             * WriterUtils constructor.
             *
             * <p>
             * Stores the specified writer to use for output.
             * </p>
             *
             * @param writer the writer which will be used for outputing the xml.
             */
    public WriterHelper(Writer writer) {
        this.writer = writer;
    }

    /**
    <<<<<<< .locale
     * write purpose.<p>Writes the String specified to the stored
     * output writer.</p>
     *
     * @param s The String to write.
     *
     * @throws ConfigurationException When an IO exception occurs.
     */
    public void write(String s) throws ConfigurationException {
        try {
            writer.write(s);
            writer.flush();
        } catch (IOException e) {
            throw new ConfigurationException("Write" + writer, e);
        }
    }

    /**
    =======
    >>>>>>> .merge-dx.r6523
     * writeln purpose.<p>Writes the String specified to the stored
     * output writer.</p>
     *
     * @param s The String to write.
     *
     * @throws ConfigurationException When an IO exception occurs.
     */
    public void writeln(String s) throws ConfigurationException {
        try {
            writer.write(indentBuffer.subSequence(0, indent) + s + "\n");
            writer.flush();
        } catch (IOException e) {
            throw new ConfigurationException("Writeln" + writer, e);
        }
    }

    private void increaseIndent() {
        indent += 2;
        indentBuffer.append("  ");
    }

    private void decreaseIndent() {
        if (indent > 0) {
            indent -= 2;
            indentBuffer.setLength(indentBuffer.length() - 2);
        }
    }

    /**
     * openTag purpose.<p>Writes an open xml tag with the name
     * specified to the stored output writer.</p>
     *
     * @param tagName The tag name to write.
     *
     * @throws ConfigurationException When an IO exception occurs.
     */
    public void openTag(String tagName) throws ConfigurationException {
        openTag(tagName, Collections.EMPTY_MAP);
    }

    /**
     * openTag purpose.<p>Writes an open xml tag with the name and
     * attributes specified to the stored output writer.</p>
     *
     * @param tagName The tag name to write.
     * @param attributes The tag attributes to write.
     *
     * @throws ConfigurationException When an IO exception occurs.
     */
    public void openTag(String tagName, Map attributes)
        throws ConfigurationException {
        StringBuffer sb = new StringBuffer();
        sb.append("<" + tagName + " ");

        Iterator i = attributes.keySet().iterator();

        while (i.hasNext()) {
            String s = (String) i.next();

            if (attributes.get(s) != null) {
                sb.append(s + " = " + "\"" + (attributes.get(s)).toString() + "\" ");
            }
        }

        sb.append(">");
        writeln(sb.toString());
        increaseIndent();
    }

    /**
     * closeTag purpose.<p>Writes an close xml tag with the name
     * specified to the stored output writer.</p>
     *
     * @param tagName The tag name to write.
     *
     * @throws ConfigurationException When an IO exception occurs.
     */
    public void closeTag(String tagName) throws ConfigurationException {
        decreaseIndent();
        writeln("</" + tagName + ">");
    }

    /**
     * valueTag purpose.<p>Writes an xml tag with the name and value
     * specified to the stored output writer.</p>
     *
     * @param tagName The tag name to write.
     * @param value The text data to write.
     *
     * @throws ConfigurationException When an IO exception occurs.
     */
    public void valueTag(String tagName, String value)
        throws ConfigurationException {
        writeln("<" + tagName + " value = \"" + value + "\" />");
    }

    /**
     * attrTag purpose.<p>Writes an xml tag with the name and
     * attributes specified to the stored output writer.</p>
     *
     * @param tagName The tag name to write.
     * @param attributes The tag attributes to write.
     *
     * @throws ConfigurationException When an IO exception occurs.
     */
    public void attrTag(String tagName, Map attributes)
        throws ConfigurationException {
        StringBuffer sb = new StringBuffer();
        sb.append("<" + tagName + " ");

        Iterator i = attributes.keySet().iterator();

        while (i.hasNext()) {
            String s = (String) i.next();

            if (attributes.get(s) != null) {
                sb.append(s + " = " + "\"" + (attributes.get(s)).toString() + "\" ");
            }
        }

        sb.append("/>");
        writeln(sb.toString());
    }

    /**
     * textTag purpose.<p>Writes an xml tag with the name, text and
     * attributes specified to the stored output writer.</p>
     *
     * <p>
     * Writes a text xml tag with the name and text specified to the stored
     * output writer.
     * </p>
     *
     * @param tagName The tag name to write.
     * @param data The text data to write.
     *
     * @throws ConfigurationException When an IO exception occurs.
     */
    public void textTag(String tagName, String data) throws ConfigurationException {
        textTag(tagName, Collections.EMPTY_MAP, data);
    }

    /**
     * textTag purpose.
     *
     * <p>
     * Writes an xml tag with the name, text and attributes specified to the
     * stored output writer.
     * </p>
     *
     * @param tagName The tag name to write.
     * @param attributes The tag attributes to write.
     * @param data The tag text to write.
     *
     * @throws ConfigurationException When an IO exception occurs.
     */
    public void textTag(String tagName, Map attributes, String data)
        throws ConfigurationException {
        StringBuffer sb = new StringBuffer();
        sb.append("<" + tagName + ((attributes.size() > 0) ? " " : ""));

        Iterator i = attributes.keySet().iterator();

        while (i.hasNext()) {
            String s = (String) i.next();

            if (attributes.get(s) != null) {
                sb.append(s + " = " + "\"" + (attributes.get(s)).toString() + "\" ");
            }
        }

        sb.append(">" + (data != null ? data : "") + "</" + tagName + ">");
        writeln(sb.toString());
    }

    /**
     * comment purpose.<p>Writes an xml comment with the text specified
     * to the stored output writer.</p>
     *
     * @param comment The comment text to write.
     *
     * @throws ConfigurationException When an IO exception occurs.
     */
    public void comment(String comment) throws ConfigurationException {
        writeln("<!--");
        increaseIndent();

        String ib = indentBuffer.substring(0, indent);
        comment = comment.trim();
        comment = comment.replaceAll("\n", "\n" + ib);
        writeln(comment);
        decreaseIndent();
        writeln("-->");
    }
}
