/*
 * ============================================================================
 *                   The JasperReports License, Version 1.0
 * ============================================================================
 * 
 * Copyright (C) 2001-2004 Teodor Danciu (teodord@users.sourceforge.net). All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. The end-user documentation included with the redistribution, if any, must
 *    include the following acknowledgment: "This product includes software
 *    developed by Teodor Danciu (http://jasperreports.sourceforge.net)."
 *    Alternately, this acknowledgment may appear in the software itself, if
 *    and wherever such third-party acknowledgments normally appear.
 * 
 * 4. The name "JasperReports" must not be used to endorse or promote products 
 *    derived from this software without prior written permission. For written 
 *    permission, please contact teodord@users.sourceforge.net.
 * 
 * 5. Products derived from this software may not be called "JasperReports", nor 
 *    may "JasperReports" appear in their name, without prior written permission
 *    of Teodor Danciu.
 * 
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS  FOR A PARTICULAR  PURPOSE ARE  DISCLAIMED.  IN NO  EVENT SHALL  THE
 * APACHE SOFTWARE  FOUNDATION  OR ITS CONTRIBUTORS  BE LIABLE FOR  ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL,  EXEMPLARY, OR CONSEQUENTIAL  DAMAGES (INCLU-
 * DING, BUT NOT LIMITED TO, PROCUREMENT  OF SUBSTITUTE GOODS OR SERVICES; LOSS
 * OF USE, DATA, OR  PROFITS; OR BUSINESS  INTERRUPTION)  HOWEVER CAUSED AND ON
 * ANY  THEORY OF LIABILITY,  WHETHER  IN CONTRACT,  STRICT LIABILITY,  OR TORT
 * (INCLUDING  NEGLIGENCE OR  OTHERWISE) ARISING IN  ANY WAY OUT OF THE  USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/*
 * ============================================================================
 *                   GNU Lesser General Public License
 * ============================================================================
 *
 * JasperReports - Free Java report-generating library.
 * Copyright (C) 2001-2004 Teodor Danciu teodord@users.sourceforge.net
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307, USA.
 * 
 * Teodor Danciu
 * 173, Calea Calarasilor, Bl. 42, Sc. 1, Ap. 18
 * Postal code 030615, Sector 3
 * Bucharest, ROMANIA
 * Email: teodord@users.sourceforge.net
 */
package com.develog.utils.report.engine.util;

import java.awt.Color;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.io.StringReader;
import java.text.AttributedCharacterIterator;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.develog.utils.report.engine.JRRuntimeException;
import com.develog.utils.report.engine.xml.JRXmlConstants;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRStyledTextParser.java,v 1.1 2005/10/10 14:18:20 nahuel Exp $
 */
public class JRStyledTextParser
{

	/**
	 *
	 */
	private static final String ROOT_START = "<st>";
	private static final String ROOT_END = "</st>";
	private static final String NODE_style = "style";
	private static final String ATTRIBUTE_fontName = "fontName";
	private static final String ATTRIBUTE_size = "size";
	private static final String ATTRIBUTE_isBold = "isBold";
	private static final String ATTRIBUTE_isItalic = "isItalic";
	private static final String ATTRIBUTE_isUnderline = "isUnderline";
	private static final String ATTRIBUTE_isStrikeThrough = "isStrikeThrough";
	private static final String ATTRIBUTE_forecolor = "forecolor";
	private static final String ATTRIBUTE_backcolor = "backcolor";
	private static final String ATTRIBUTE_pdfFontName = "pdfFontName";
	private static final String ATTRIBUTE_pdfEncoding = "pdfEncoding";
	private static final String ATTRIBUTE_isPdfEmbedded = "isPdfEmbedded";

	private static final String SPACE = " ";
	private static final String EQUAL_QUOTE = "=\"";
	private static final String QUOTE = "\"";
	private static final String SHARP = "#";
	private static final String LESS = "<";
	private static final String LESS_SLASH = "</";
	private static final String GREATER = ">";
	private static final String SIX_ZEROS = "000000";
	private static final int colorMask = Integer.parseInt("FFFFFF", 16);

	/**
	 *
	 */
	private DocumentBuilder documentBuilder = null;


	/**
	 *
	 */
	public JRStyledTextParser()
	{
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			documentBuilder = factory.newDocumentBuilder();
		}
		catch (ParserConfigurationException e)
		{
			throw new JRRuntimeException(e);
		}
	}


	/**
	 *
	 */
	public JRStyledText parse(Map attributes, String text) throws SAXException
	{
		JRStyledText styledText = new JRStyledText();
		
		Document document = null;

		try
		{
			document = documentBuilder.parse(new InputSource(new StringReader(ROOT_START + text + ROOT_END)));
		}
		catch (IOException e)
		{
			throw new JRRuntimeException(e);
		}
		
		parseStyle(styledText, document.getDocumentElement());
		
		styledText.addRun(new JRStyledText.Run(attributes, 0, styledText.length()));
		
		return styledText;
	}

	/**
	 *
	 */
	public String write(Map parentAttrs, AttributedCharacterIterator iterator, String text)
	{
		StringBuffer sbuffer = new StringBuffer();
		
		int runLimit = 0;

		while(runLimit < iterator.getEndIndex() && (runLimit = iterator.getRunLimit()) <= iterator.getEndIndex())
		{
			String chunk = text.substring(iterator.getIndex(), runLimit);
			Map attrs = iterator.getAttributes();
			
			StringBuffer styleBuffer = writeStyleAttributes(parentAttrs, attrs);
			if (styleBuffer.length() > 0)
			{
				sbuffer.append(LESS);
				sbuffer.append(NODE_style);
				sbuffer.append(styleBuffer);
				sbuffer.append(GREATER);
				sbuffer.append(chunk);
				sbuffer.append(LESS_SLASH);
				sbuffer.append(NODE_style);
				sbuffer.append(GREATER);
			}
			else
			{
				sbuffer.append(chunk);
			}

			iterator.setIndex(runLimit);
		}
		
		return sbuffer.toString();
	}

	/**
	 *
	 */
	private void parseStyle(JRStyledText styledText, Node parentNode)
	{
		NodeList nodeList = parentNode.getChildNodes();
		for(int i = 0; i < nodeList.getLength(); i++)
		{
			Node node = nodeList.item(i);
			if (node.getNodeType() == Node.TEXT_NODE)
			{
				styledText.append(node.getNodeValue());
			}
			else if (
				node.getNodeType() == Node.ELEMENT_NODE
				&& NODE_style.equals(node.getNodeName())
				)
			{
				NamedNodeMap nodeAttrs = node.getAttributes();

				Map styleAttrs = new HashMap();

				if (nodeAttrs.getNamedItem(ATTRIBUTE_fontName) != null)
				{
					styleAttrs.put(
						TextAttribute.FAMILY, 
						nodeAttrs.getNamedItem(ATTRIBUTE_fontName).getNodeValue()
						);
				}

				if (nodeAttrs.getNamedItem(ATTRIBUTE_isBold) != null)
				{
					styleAttrs.put(
						TextAttribute.WEIGHT, 
						Boolean.valueOf(nodeAttrs.getNamedItem(ATTRIBUTE_isBold).getNodeValue()).booleanValue() 
							? TextAttribute.WEIGHT_BOLD : TextAttribute.WEIGHT_REGULAR
						);
				}

				if (nodeAttrs.getNamedItem(ATTRIBUTE_isItalic) != null)
				{
					styleAttrs.put(
						TextAttribute.POSTURE, 
						Boolean.valueOf(nodeAttrs.getNamedItem(ATTRIBUTE_isItalic).getNodeValue()).booleanValue() 
							? TextAttribute.POSTURE_OBLIQUE : TextAttribute.POSTURE_REGULAR
						);
				}

				if (nodeAttrs.getNamedItem(ATTRIBUTE_isUnderline) != null)
				{
					styleAttrs.put(
						TextAttribute.UNDERLINE, 
						Boolean.valueOf(nodeAttrs.getNamedItem(ATTRIBUTE_isUnderline).getNodeValue()).booleanValue() 
							? TextAttribute.UNDERLINE_ON : null
						);
				}

				if (nodeAttrs.getNamedItem(ATTRIBUTE_isStrikeThrough) != null)
				{
					styleAttrs.put(
						TextAttribute.STRIKETHROUGH, 
						Boolean.valueOf(nodeAttrs.getNamedItem(ATTRIBUTE_isStrikeThrough).getNodeValue()).booleanValue() 
							? TextAttribute.STRIKETHROUGH_ON : null
						);
				}

				if (nodeAttrs.getNamedItem(ATTRIBUTE_size) != null)
				{
					styleAttrs.put(
						TextAttribute.SIZE, 
						new Float(nodeAttrs.getNamedItem(ATTRIBUTE_size).getNodeValue()) 
						);
				}

				if (nodeAttrs.getNamedItem(ATTRIBUTE_pdfFontName) != null)
				{
					styleAttrs.put(
						JRTextAttribute.PDF_FONT_NAME, 
						nodeAttrs.getNamedItem(ATTRIBUTE_pdfFontName).getNodeValue()
						);
				}

				if (nodeAttrs.getNamedItem(ATTRIBUTE_pdfEncoding) != null)
				{
					styleAttrs.put(
						JRTextAttribute.PDF_ENCODING, 
						nodeAttrs.getNamedItem(ATTRIBUTE_pdfEncoding).getNodeValue()
						);
				}

				if (nodeAttrs.getNamedItem(ATTRIBUTE_isPdfEmbedded) != null)
				{
					styleAttrs.put(
						JRTextAttribute.IS_PDF_EMBEDDED, 
						Boolean.valueOf(nodeAttrs.getNamedItem(ATTRIBUTE_isPdfEmbedded).getNodeValue()) 
						);
				}
				
				if (nodeAttrs.getNamedItem(ATTRIBUTE_forecolor) != null)
				{
					Color color = null;
					String colorStr = nodeAttrs.getNamedItem(ATTRIBUTE_forecolor).getNodeValue();
					if (colorStr != null && colorStr.length() > 0)
					{
						char firstChar = colorStr.charAt(0);
						if (firstChar == '#')
						{
							color = new Color(Integer.parseInt(colorStr.substring(1), 16));
						}
						else if ('0' <= firstChar && firstChar <= '9')
						{
							color = new Color(Integer.parseInt(colorStr));
						}
						else
						{
							if (JRXmlConstants.getColorMap().containsKey(colorStr))
							{
								color = (Color)JRXmlConstants.getColorMap().get(colorStr);
							}
							else
							{
								color = Color.black;
							}
						}
					}
					styleAttrs.put(
						TextAttribute.FOREGROUND, 
						color 
						);
				}
				
				if (nodeAttrs.getNamedItem(ATTRIBUTE_backcolor) != null)
				{
					Color color = null;
					String colorStr = nodeAttrs.getNamedItem(ATTRIBUTE_backcolor).getNodeValue();
					if (colorStr != null && colorStr.length() > 0)
					{
						char firstChar = colorStr.charAt(0);
						if (firstChar == '#')
						{
							color = new Color(Integer.parseInt(colorStr.substring(1), 16));
						}
						else if ('0' <= firstChar && firstChar <= '9')
						{
							color = new Color(Integer.parseInt(colorStr));
						}
						else
						{
							if (JRXmlConstants.getColorMap().containsKey(colorStr))
							{
								color = (Color)JRXmlConstants.getColorMap().get(colorStr);
							}
							else
							{
								color = Color.black;
							}
						}
					}
					styleAttrs.put(
						TextAttribute.BACKGROUND, 
						color 
						);
				}
				
				int startIndex = styledText.length();

				parseStyle(styledText, node);
				
				styledText.addRun(new JRStyledText.Run(styleAttrs, startIndex, styledText.length()));
			}
		}
	}


	/**
	 *
	 */
	private StringBuffer writeStyleAttributes(Map parentAttrs,  Map attrs)
	{
		StringBuffer sbuffer = new StringBuffer();
		
		Object value = attrs.get(TextAttribute.FAMILY);
		Object oldValue = parentAttrs.get(TextAttribute.FAMILY);
		
		if (value != null && !value.equals(oldValue))
		{
			sbuffer.append(SPACE);
			sbuffer.append(ATTRIBUTE_fontName);
			sbuffer.append(EQUAL_QUOTE);
			sbuffer.append(value);
			sbuffer.append(QUOTE);
		}

		value = attrs.get(TextAttribute.WEIGHT);
		oldValue = parentAttrs.get(TextAttribute.WEIGHT);

		if (value != null && !value.equals(oldValue))
		{
			sbuffer.append(SPACE);
			sbuffer.append(ATTRIBUTE_isBold);
			sbuffer.append(EQUAL_QUOTE);
			sbuffer.append(value.equals(TextAttribute.WEIGHT_BOLD));
			sbuffer.append(QUOTE);
		}

		value = attrs.get(TextAttribute.POSTURE);
		oldValue = parentAttrs.get(TextAttribute.POSTURE);

		if (value != null && !value.equals(oldValue))
		{
			sbuffer.append(SPACE);
			sbuffer.append(ATTRIBUTE_isItalic);
			sbuffer.append(EQUAL_QUOTE);
			sbuffer.append(value.equals(TextAttribute.POSTURE_OBLIQUE));
			sbuffer.append(QUOTE);
		}

		value = attrs.get(TextAttribute.UNDERLINE);
		oldValue = parentAttrs.get(TextAttribute.UNDERLINE);

		if (
			(value == null && oldValue != null)
			|| (value != null && !value.equals(oldValue))
			)
		{
			sbuffer.append(SPACE);
			sbuffer.append(ATTRIBUTE_isUnderline);
			sbuffer.append(EQUAL_QUOTE);
			sbuffer.append(value != null);
			sbuffer.append(QUOTE);
		}

		value = attrs.get(TextAttribute.STRIKETHROUGH);
		oldValue = parentAttrs.get(TextAttribute.STRIKETHROUGH);

		if (
			(value == null && oldValue != null)
			|| (value != null && !value.equals(oldValue))
			)
		{
			sbuffer.append(SPACE);
			sbuffer.append(ATTRIBUTE_isStrikeThrough);
			sbuffer.append(EQUAL_QUOTE);
			sbuffer.append(value != null);
			sbuffer.append(QUOTE);
		}

		value = attrs.get(TextAttribute.SIZE);
		oldValue = parentAttrs.get(TextAttribute.SIZE);

		if (value != null && !value.equals(oldValue))
		{
			sbuffer.append(SPACE);
			sbuffer.append(ATTRIBUTE_size);
			sbuffer.append(EQUAL_QUOTE);
			sbuffer.append(((Float)value).intValue());
			sbuffer.append(QUOTE);
		}

		value = attrs.get(JRTextAttribute.PDF_FONT_NAME);
		oldValue = parentAttrs.get(JRTextAttribute.PDF_FONT_NAME);

		if (value != null && !value.equals(oldValue))
		{
			sbuffer.append(SPACE);
			sbuffer.append(ATTRIBUTE_pdfFontName);
			sbuffer.append(EQUAL_QUOTE);
			sbuffer.append(value);
			sbuffer.append(QUOTE);
		}

		value = attrs.get(JRTextAttribute.PDF_ENCODING);
		oldValue = parentAttrs.get(JRTextAttribute.PDF_ENCODING);

		if (value != null && !value.equals(oldValue))
		{
			sbuffer.append(SPACE);
			sbuffer.append(ATTRIBUTE_pdfEncoding);
			sbuffer.append(EQUAL_QUOTE);
			sbuffer.append(value);
			sbuffer.append(QUOTE);
		}

		value = attrs.get(JRTextAttribute.IS_PDF_EMBEDDED);
		oldValue = parentAttrs.get(JRTextAttribute.IS_PDF_EMBEDDED);

		if (value != null && !value.equals(oldValue))
		{
			sbuffer.append(SPACE);
			sbuffer.append(ATTRIBUTE_isPdfEmbedded);
			sbuffer.append(EQUAL_QUOTE);
			sbuffer.append(value);
			sbuffer.append(QUOTE);
		}

		value = attrs.get(TextAttribute.FOREGROUND);
		oldValue = parentAttrs.get(TextAttribute.FOREGROUND);

		if (value != null && !value.equals(oldValue))
		{
			sbuffer.append(SPACE);
			sbuffer.append(ATTRIBUTE_forecolor);
			sbuffer.append(EQUAL_QUOTE);
			sbuffer.append(SHARP);
			String hexa = Integer.toHexString(((Color)value).getRGB() & colorMask).toUpperCase();
			sbuffer.append(SIX_ZEROS + hexa).substring(hexa.length());
			sbuffer.append(QUOTE);
		}

		value = attrs.get(TextAttribute.BACKGROUND);
		oldValue = parentAttrs.get(TextAttribute.BACKGROUND);

		if (value != null && !value.equals(oldValue))
		{
			sbuffer.append(SPACE);
			sbuffer.append(ATTRIBUTE_backcolor);
			sbuffer.append(EQUAL_QUOTE);
			sbuffer.append(SHARP);
			String hexa = Integer.toHexString(((Color)value).getRGB() & colorMask).toUpperCase();
			sbuffer.append(SIX_ZEROS + hexa).substring(hexa.length());
			sbuffer.append(QUOTE);
		}
		
		return sbuffer;
	}

}
