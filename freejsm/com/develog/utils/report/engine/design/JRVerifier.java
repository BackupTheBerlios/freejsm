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
package com.develog.utils.report.engine.design;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.develog.utils.report.engine.JRAnchor;
import com.develog.utils.report.engine.JRBand;
import com.develog.utils.report.engine.JRElement;
import com.develog.utils.report.engine.JRException;
import com.develog.utils.report.engine.JRExpression;
import com.develog.utils.report.engine.JRExpressionChunk;
import com.develog.utils.report.engine.JRField;
import com.develog.utils.report.engine.JRFont;
import com.develog.utils.report.engine.JRGroup;
import com.develog.utils.report.engine.JRHyperlink;
import com.develog.utils.report.engine.JRImage;
import com.develog.utils.report.engine.JRParameter;
import com.develog.utils.report.engine.JRQuery;
import com.develog.utils.report.engine.JRQueryChunk;
import com.develog.utils.report.engine.JRReportFont;
import com.develog.utils.report.engine.JRSubreport;
import com.develog.utils.report.engine.JRSubreportParameter;
import com.develog.utils.report.engine.JRTextElement;
import com.develog.utils.report.engine.JRTextField;
import com.develog.utils.report.engine.JRVariable;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRVerifier.java,v 1.1 2005/10/10 14:18:12 nahuel Exp $
 */
public class JRVerifier
{
	
	
	/**
	 *
	 */
	private static final Log log = LogFactory.getLog(JRVerifier.class);

	/**
	 *
	 */
	private static String[] queryParameterClassNames = null;
	private static String[] fieldClassNames = null;
	private static String[] textFieldClassNames = null;
	private static String[] imageClassNames = null;
	private static String[] subreportClassNames = null;

	/**
	 *
	 */
	private JasperDesign jasperDesign = null;
	private Collection brokenRules = null;


	/**
	 *
	 */
	protected JRVerifier(JasperDesign jrDesign)
	{
		jasperDesign = jrDesign;
		brokenRules = new ArrayList();
	}


	/**
	 *
	 */
	public static Collection verifyDesign(JasperDesign jasperDesign) throws JRException
	{
		JRVerifier verifier = new JRVerifier(jasperDesign);
		return verifier.verifyDesign();
	}

	/**
	 *
	 */
	protected Collection verifyDesign() throws JRException
	{
		/*   */
		this.verifyDesignAttributes();

		/*   */
		this.verifyExpressions();

		/*   */
		this.verifyReportFonts();

		/*   */
		this.verifyParameters();

		/*   */
		this.verifyQuery();

		/*   */
		this.verifyFields();

		/*   */
		this.verifyVariables();

		/*   */
		this.verifyGroups();

		/*   */
		this.verifyBand(jasperDesign.getBackground());
		this.verifyBand(jasperDesign.getTitle());
		this.verifyBand(jasperDesign.getPageHeader());
		this.verifyBand(jasperDesign.getColumnHeader());
		this.verifyBand(jasperDesign.getDetail());
		this.verifyBand(jasperDesign.getColumnFooter());
		this.verifyBand(jasperDesign.getPageFooter());
		this.verifyBand(jasperDesign.getSummary());

		return brokenRules;
	}


	/**
	 *
	 */
	private void verifyDesignAttributes() throws JRException
	{
		if (jasperDesign.getName() == null || jasperDesign.getName().trim().length() == 0)
		{
			brokenRules.add("Report name is missing.");
		}
		
		if (jasperDesign.getColumnCount() <= 0)
		{
			brokenRules.add("Column count must be greater than zero.");
		}

		if (jasperDesign.getPageWidth() < 0)
		{
			brokenRules.add("Page width must be positive.");
		}

		if (jasperDesign.getPageHeight() < 0)
		{
			brokenRules.add("Page height must be positive.");
		}

		if (jasperDesign.getColumnWidth() < 0)
		{
			brokenRules.add("Column width must be positive.");
		}

		if (jasperDesign.getColumnSpacing() < 0)
		{
			brokenRules.add("Column spacing must be positive.");
		}

		if (jasperDesign.getLeftMargin() < 0)
		{
			brokenRules.add("Left margin must be positive.");
		}

		if (jasperDesign.getRightMargin() < 0)
		{
			brokenRules.add("Right margin must be positive.");
		}

		if (jasperDesign.getTopMargin() < 0)
		{
			brokenRules.add("Top margin must be positive.");
		}

		if (jasperDesign.getBottomMargin() < 0)
		{
			brokenRules.add("Bottom margin must be positive.");
		}

		if (
			jasperDesign.getLeftMargin() +
			jasperDesign.getColumnCount() * jasperDesign.getColumnWidth() +
			(jasperDesign.getColumnCount() - 1) * jasperDesign.getColumnSpacing() +
			jasperDesign.getRightMargin() >
			jasperDesign.getPageWidth()
			)
		{
			brokenRules.add("The columns and the margins do not fit the page width.");
		}

		if (
			jasperDesign.getTopMargin() +
			(jasperDesign.getBackground() != null ? jasperDesign.getBackground().getHeight() : 0) +
			jasperDesign.getBottomMargin() >
			jasperDesign.getPageHeight()
			)
		{
			brokenRules.add("The background section and the margins do not fit the page height.");
		}

		if (jasperDesign.isTitleNewPage())
		{
			if (
				jasperDesign.getTopMargin() +
				(jasperDesign.getTitle() != null ? jasperDesign.getTitle().getHeight() : 0) +
				jasperDesign.getBottomMargin() >
				jasperDesign.getPageHeight()
				)
			{
				brokenRules.add("The title section and the margins do not fit the page height.");
			}
		}
		else
		{
			if (
				jasperDesign.getTopMargin() +
				(jasperDesign.getTitle() != null ? jasperDesign.getTitle().getHeight() : 0) +
				(jasperDesign.getPageHeader() != null ? jasperDesign.getPageHeader().getHeight() : 0) +
				(jasperDesign.getColumnHeader() != null ? jasperDesign.getColumnHeader().getHeight() : 0) +
				(jasperDesign.getColumnFooter() != null ? jasperDesign.getColumnFooter().getHeight() : 0) +
				(jasperDesign.getPageFooter() != null ? jasperDesign.getPageFooter().getHeight() : 0) +
				jasperDesign.getBottomMargin() >
				jasperDesign.getPageHeight()
				)
			{
				brokenRules.add("The title section, the page and column headers and footers and the margins do not fit the page height.");
			}
		}

		if (
			jasperDesign.getTopMargin() +
			(jasperDesign.getPageHeader() != null ? jasperDesign.getPageHeader().getHeight() : 0) +
			(jasperDesign.getColumnHeader() != null ? jasperDesign.getColumnHeader().getHeight() : 0) +
			(jasperDesign.getColumnFooter() != null ? jasperDesign.getColumnFooter().getHeight() : 0) +
			(jasperDesign.getPageFooter() != null ? jasperDesign.getPageFooter().getHeight() : 0) +
			jasperDesign.getBottomMargin() >
			jasperDesign.getPageHeight()
			)
		{
			brokenRules.add("The page and column headers and footers and the margins do not fit the page height.");
		}

		if (
			jasperDesign.getTopMargin() +
			(jasperDesign.getSummary() != null ? jasperDesign.getSummary().getHeight() : 0) +
			jasperDesign.getBottomMargin() >
			jasperDesign.getPageHeight()
			)
		{
			brokenRules.add("The summary section and the margins do not fit the page height.");
		}

		if (
			jasperDesign.getTopMargin() +
			(jasperDesign.getPageHeader() != null ? jasperDesign.getPageHeader().getHeight() : 0) +
			(jasperDesign.getColumnHeader() != null ? jasperDesign.getColumnHeader().getHeight() : 0) +
			(jasperDesign.getDetail() != null ? jasperDesign.getDetail().getHeight() : 0) +
			(jasperDesign.getColumnFooter() != null ? jasperDesign.getColumnFooter().getHeight() : 0) +
			(jasperDesign.getPageFooter() != null ? jasperDesign.getPageFooter().getHeight() : 0) +
			jasperDesign.getBottomMargin() >
			jasperDesign.getPageHeight()
			)
		{
			brokenRules.add("The detail section, the page and column headers and footers and the margins do not fit the page height.");
		}
	}


	/**
	 *
	 */
	private void verifyQuery() throws JRException
	{
		JRQuery query = jasperDesign.getQuery();
		if (query != null)
		{
			JRQueryChunk[] chunks = query.getChunks();
			if (chunks != null && chunks.length > 0)
			{
				JRQueryChunk queryChunk = null;
	
				Map parametersMap = jasperDesign.getParametersMap();
	
				JRParameter parameter = null;
				Class clazz = null;
				for(int j = 0; j < chunks.length; j++)
				{
					queryChunk = chunks[j];
					switch (queryChunk.getType())
					{
						case JRQueryChunk.TYPE_PARAMETER :
						{
							parameter = (JRParameter)parametersMap.get(queryChunk.getText());
							if ( parameter == null )
							{
								brokenRules.add("Query parameter not found : " + queryChunk.getText());
							}
							else 
							{
								if (Arrays.binarySearch(getQueryParameterClassNames(), parameter.getValueClassName()) < 0)
								{
									brokenRules.add("Parameter type not supported in query : " + queryChunk.getText() + " class " + clazz.getName());
								}
							}
	
							break;
						}
						case JRQueryChunk.TYPE_PARAMETER_CLAUSE :
						case JRQueryChunk.TYPE_TEXT :
						default :
						{
						}
					}
				}
			}
		}
	}


	/**
	 *
	 */
	private void verifyExpressions() throws JRException
	{
		Collection expressions = jasperDesign.getExpressions();
		if (expressions != null && expressions.size() > 0)
		{
			Map parametersMap = jasperDesign.getParametersMap();
			Map fieldsMap = jasperDesign.getFieldsMap();
			Map variablesMap = jasperDesign.getVariablesMap();

			JRExpression expression = null;
			JRExpressionChunk[] chunks = null;
			JRExpressionChunk expressionChunk = null;
			for(Iterator it = expressions.iterator(); it.hasNext();)
			{
				expression = (JRExpression)it.next();
				chunks = expression.getChunks();
				if (chunks != null && chunks.length > 0)
				{
					for(int j = 0; j < chunks.length; j++)
					{
						expressionChunk = chunks[j];
						switch (expressionChunk.getType())
						{
							case JRExpressionChunk.TYPE_VARIABLE :
							{
								if ( !variablesMap.containsKey(expressionChunk.getText()) )
								{
									brokenRules.add("Variable not found : " + expressionChunk.getText());
								}
								break;
							}
							case JRExpressionChunk.TYPE_FIELD :
							{
								if ( !fieldsMap.containsKey(expressionChunk.getText()) )
								{
									brokenRules.add("Field not found : " + expressionChunk.getText());
								}
								break;
							}
							case JRExpressionChunk.TYPE_PARAMETER :
							{
								if ( !parametersMap.containsKey(expressionChunk.getText()) )
								{
									brokenRules.add("Parameter not found : " + expressionChunk.getText());
								}
								break;
							}
							case JRExpressionChunk.TYPE_TEXT :
							default :
							{
							}
						}
					}
				}
			}
		}
	}


	/**
	 *
	 */
	private void verifyReportFonts() throws JRException
	{
		JRReportFont[] fonts = jasperDesign.getFonts();
		if (fonts != null && fonts.length > 0)
		{
			JRReportFont font = null;

			for(int index = 0; index < fonts.length; index++)
			{
				font = fonts[index];
				
				if (font.getName() == null || font.getName().trim().length() == 0)
				{
					brokenRules.add("Report font name missing.");
				}
			}
		}
	}


	/**
	 *
	 */
	private void verifyParameters() throws JRException
	{
		JRParameter[] parameters = jasperDesign.getParameters();
		if (parameters != null && parameters.length > 0)
		{
			JRParameter parameter = null;
			JRExpression expression = null;
			Class valueClass = null;

			for(int index = 0; index < parameters.length; index++)
			{
				parameter = parameters[index];
				
				if (parameter.getName() == null || parameter.getName().trim().length() == 0)
				{
					brokenRules.add("Parameter name missing.");
				}

				valueClass = parameter.getValueClass();

				if (valueClass == null)
				{
					brokenRules.add("Class not set for parameter : " + parameter.getName());
				}
				else
				{
					expression = parameter.getDefaultValueExpression();
					if (expression != null)
					{
						if (
							!valueClass.isAssignableFrom(
								expression.getValueClass()
								)
							)
						{
							brokenRules.add("The parameter default value expression class is not compatible with the parameter's class : " + parameter.getName());
						}
					}
				}
			}
		}
	}


	/**
	 *
	 */
	private void verifyFields() throws JRException
	{
		JRField[] fields = jasperDesign.getFields();
		if (fields != null && fields.length > 0)
		{
			for(int index = 0; index < fields.length; index++)
			{
				JRField field = fields[index];
				
				if (field.getName() == null || field.getName().trim().length() == 0)
				{
					brokenRules.add("Field name missing.");
				}

				String className = field.getValueClassName();

				if (className == null)
				{
					brokenRules.add("Class not set for field : " + field.getName());
				}
				else if (Arrays.binarySearch(getFieldClassNames(), className) < 0) 
				{
					brokenRules.add(
						"Class \"" + className + "\" not supported for field : " 
						+ field.getName() + ". Use java.lang.Object instead."
						);
				}
			}
		}
	}


	/**
	 *
	 */
	private void verifyVariables() throws JRException
	{
		JRVariable[] variables = jasperDesign.getVariables();
		if (variables != null && variables.length > 0)
		{
			JRVariable variable = null;
			JRExpression expression = null;
			Class valueClass = null;

			for(int index = 0; index < variables.length; index++)
			{
				variable = variables[index];
				
				if (variable.getName() == null || variable.getName().trim().length() == 0)
				{
					brokenRules.add("Variable name missing.");
				}

				valueClass = variable.getValueClass();

				if (valueClass == null)
				{
					brokenRules.add("Class not set for variable : " + variable.getName());
				}
				else
				{
					expression = variable.getExpression();
					if (expression != null)
					{
						if (
							variable.getCalculation() != JRVariable.CALCULATION_COUNT &&
							!valueClass.isAssignableFrom(
								expression.getValueClass()
								)
							)
						{
							brokenRules.add("The variable expression class is not compatible with the variable's class : " + variable.getName());
						}
					}
					
					if (variable.getInitialValueExpression() != null)
					{
						if (
							!valueClass.isAssignableFrom(
								variable.getInitialValueExpression().getValueClass()
								)
							)
						{
							brokenRules.add("The initial value class is not compatible with the variable's class : " + variable.getName());
						}
					}
				}
				
				if (variable.getResetType() == JRVariable.RESET_TYPE_GROUP)
				{
					if (variable.getResetGroup() == null)
					{
						brokenRules.add("Reset group missing for variable : " + variable.getName());
					}
					else
					{
						Map groupsMap = jasperDesign.getGroupsMap();
		
						if (!groupsMap.containsKey(variable.getResetGroup().getName()))
						{
							brokenRules.add("Reset group \"" + variable.getResetGroup().getName() + "\" not found for variable : " + variable.getName());
						}
					}
				}
			}
		}
	}


	/**
	 *
	 */
	private void verifyGroups() throws JRException
	{
		JRGroup[] groups = jasperDesign.getGroups();
		if (groups != null && groups.length > 0)
		{
			JRGroup group = null;
			JRExpression expression = null;
			Class clazz = null;

			for(int index = 0; index < groups.length; index++)
			{
				group = groups[index];

				if (group.getName() == null || group.getName().trim().length() == 0)
				{
					brokenRules.add("Group name missing.");
				}

				if (jasperDesign.isTitleNewPage())
				{
					if (
						jasperDesign.getTopMargin() +
						(jasperDesign.getPageHeader() != null ? jasperDesign.getPageHeader().getHeight() : 0) +
						(jasperDesign.getColumnHeader() != null ? jasperDesign.getColumnHeader().getHeight() : 0) +
						(group.getGroupHeader() != null ? group.getGroupHeader().getHeight() : 0) +
						(jasperDesign.getColumnFooter() != null ? jasperDesign.getColumnFooter().getHeight() : 0) +
						(jasperDesign.getPageFooter() != null ? jasperDesign.getPageFooter().getHeight() : 0) +
						jasperDesign.getBottomMargin() >
						jasperDesign.getPageHeight()
						)
					{
						brokenRules.add("The '" + group.getName() + "' group header section, the page and column headers and footers and the margins do not fit the page height.");
					}
	
					if (
						jasperDesign.getTopMargin() +
						(jasperDesign.getPageHeader() != null ? jasperDesign.getPageHeader().getHeight() : 0) +
						(jasperDesign.getColumnHeader() != null ?  jasperDesign.getColumnHeader().getHeight() : 0) +
						(group.getGroupFooter() != null ? group.getGroupFooter().getHeight() : 0) +
						(jasperDesign.getColumnFooter() != null ? jasperDesign.getColumnFooter().getHeight() : 0) +
						(jasperDesign.getPageFooter() != null ? jasperDesign.getPageFooter().getHeight() : 0) +
						jasperDesign.getBottomMargin() >
						jasperDesign.getPageHeight()
						)
					{
						brokenRules.add("The '" + group.getName() + "' group footer section, the page and column headers and footers and the margins do not fit the page height.");
					}
				}
				else
				{
					if (
						jasperDesign.getTopMargin() +
						(jasperDesign.getTitle() != null ? jasperDesign.getTitle().getHeight() : 0) +
						(jasperDesign.getPageHeader() != null ? jasperDesign.getPageHeader().getHeight() : 0) +
						(jasperDesign.getColumnHeader() != null ? jasperDesign.getColumnHeader().getHeight() : 0) +
						(group.getGroupHeader() != null ? group.getGroupHeader().getHeight() : 0) +
						(jasperDesign.getColumnFooter() != null ? jasperDesign.getColumnFooter().getHeight() : 0) +
						(jasperDesign.getPageFooter() != null ? jasperDesign.getPageFooter().getHeight() : 0) +
						jasperDesign.getBottomMargin() >
						jasperDesign.getPageHeight()
						)
					{
						brokenRules.add("The '" + group.getName() + "' group header section, the title, the page and column headers and footers and the margins do not fit the first page height.");
					}
	
					if (
						jasperDesign.getTopMargin() +
						(jasperDesign.getTitle() != null ? jasperDesign.getTitle().getHeight() : 0) +
						(jasperDesign.getPageHeader() != null ? jasperDesign.getPageHeader().getHeight() : 0) +
						(jasperDesign.getColumnHeader() != null ? jasperDesign.getColumnHeader().getHeight() : 0) +
						(group.getGroupFooter() != null ? group.getGroupFooter().getHeight() : 0) +
						(jasperDesign.getColumnFooter() != null ? jasperDesign.getColumnFooter().getHeight() : 0) +
						(jasperDesign.getPageFooter() != null ? jasperDesign.getPageFooter().getHeight() : 0) +
						jasperDesign.getBottomMargin() >
						jasperDesign.getPageHeight()
						)
					{
						brokenRules.add("The '" + group.getName() + "' group footer section, the title, the page and column headers and footers and the margins do not fit the first page height.");
					}
				}
				
				expression = group.getExpression();
				
				if (expression != null)
				{
					clazz = expression.getValueClass();
	
					if (clazz == null)
					{
						brokenRules.add("Class not set for group expression : " + group.getName());
					}
				}

				this.verifyBand(group.getGroupHeader());
				this.verifyBand(group.getGroupFooter());
			}
		}
	}


	/**
	 *
	 */
	private void verifyBand(JRBand band) throws JRException
	{
		if (band != null)
		{
			JRElement[] elements = band.getElements();
			if (elements != null && elements.length > 0)
			{
				JRExpression expression = band.getPrintWhenExpression();
				Class clazz = null;
				
				if (expression != null)
				{
					clazz = expression.getValueClass();
	
					if (clazz == null)
					{
						brokenRules.add("Class not set for band \"print when\" expression.");
					}
					else if (!java.lang.Boolean.class.isAssignableFrom(clazz))
					{
						brokenRules.add("Class " + clazz + " not supported for band \"print when\" expression. Use java.lang.Boolean instead.");
					}
				}

				JRElement element = null;
				for(int index = 0; index < elements.length; index++)
				{
					element = elements[index];
	
					expression = element.getPrintWhenExpression();
					
					if (expression != null)
					{
						clazz = expression.getValueClass();
		
						if (clazz == null)
						{
							brokenRules.add("Class not set for element \"print when\" expression.");
						}
						else if (!java.lang.Boolean.class.isAssignableFrom(clazz))
						{
							brokenRules.add("Class " + clazz + " not supported for element \"print when\" expression. Use java.lang.Boolean instead.");
						}
					}

					/*
					if (element.getY() < 0)
					{
						System.out.println(
							"Warning : Element placed outside band area : y=" + element.getY()
							);
						//brokenRules.add("Element placed outside band area.");
					}
					else if (element.getY() + element.getHeight() > band.getHeight())
					*/
					if (element.getY() + element.getHeight() > band.getHeight())
					{
						if (log.isWarnEnabled())
							log.warn(
								"Warning : Element bottom reaches outside band area : y=" + element.getY() + 
								" height=" + element.getHeight() + 
								" band-height=" + band.getHeight()
								);
						//brokenRules.add("Element placed outside band area.");
					}

					if (element instanceof JRTextField)
					{
						this.verifyTextField((JRTextField)element);
					}
					else if (element instanceof JRImage)
					{
						this.verifyImage((JRImage)element);
					}
					else if (element instanceof JRSubreport)
					{
						this.verifySubreport((JRSubreport)element);
					}
				}
			}
		}
	}
		

	/**
	 *
	 */
	private void verifyTextField(JRTextField textField) throws JRException
	{
		this.verifyTextElement(textField);
		this.verifyAnchor(textField);
		this.verifyHyperlink(textField);

		if (textField != null)
		{
			JRExpression expression = textField.getExpression();
			
			if (expression != null)
			{
				String className = expression.getValueClassName();

				if (className == null)
				{
					brokenRules.add("Class not set for text field expression.");
				}
				else if (Arrays.binarySearch(getTextFieldClassNames(), className) < 0) 
				{
					brokenRules.add("Class \"" + className + "\" not supported for text field expression.");
				}
			}
		}
	}
		

	/**
	 *
	 */
	private void verifyTextElement(JRTextElement textElement) throws JRException
	{
		if (textElement != null)
		{
			JRFont font = textElement.getFont();
			
			if (font != null)
			{
				JRReportFont reportFont = font.getReportFont();
				
				if (reportFont != null && reportFont.getName() != null)
				{
					Map fontsMap = jasperDesign.getFontsMap();
	
					if (!fontsMap.containsKey(reportFont.getName()))
					{
						brokenRules.add("Report font not found : " + reportFont.getName());
					}
				}
			}
		}
	}
		

	/**
	 *
	 */
	private void verifyAnchor(JRAnchor anchor) throws JRException
	{
		if (anchor != null)
		{
			JRExpression expression = anchor.getAnchorNameExpression();
			Class clazz = null;
			
			if (expression != null)
			{
				clazz = expression.getValueClass();

				if (clazz == null)
				{
					brokenRules.add("Class not set for anchor name expression.");
				}
				else if (!java.lang.String.class.isAssignableFrom(clazz))
				{
					brokenRules.add("Class " + clazz + " not supported for anchor name expression. Use java.lang.String instead.");
				}
			}
		}
	}
		

	/**
	 *
	 */
	private void verifyHyperlink(JRHyperlink hyperlink) throws JRException
	{
		if (hyperlink != null)
		{
			JRExpression expression = hyperlink.getHyperlinkReferenceExpression();
			Class clazz = null;
			
			if (expression != null)
			{
				clazz = expression.getValueClass();

				if (clazz == null)
				{
					brokenRules.add("Class not set for hyperlink reference expression.");
				}
				else if (!java.lang.String.class.isAssignableFrom(clazz))
				{
					brokenRules.add("Class " + clazz + " not supported for hyperlink reference expression. Use java.lang.String instead.");
				}
			}

			expression = hyperlink.getHyperlinkAnchorExpression();
			
			if (expression != null)
			{
				clazz = expression.getValueClass();

				if (clazz == null)
				{
					brokenRules.add("Class not set for hyperlink anchor expression.");
				}
				else if (!java.lang.String.class.isAssignableFrom(clazz))
				{
					brokenRules.add("Class " + clazz + " not supported for hyperlink anchor expression. Use java.lang.String instead.");
				}
			}

			expression = hyperlink.getHyperlinkPageExpression();
			
			if (expression != null)
			{
				clazz = expression.getValueClass();

				if (clazz == null)
				{
					brokenRules.add("Class not set for hyperlink page expression.");
				}
				else if (!java.lang.Integer.class.isAssignableFrom(clazz))
				{
					brokenRules.add("Class " + clazz + " not supported for hyperlink page expression. Use java.lang.Integer instead.");
				}
			}
		}
	}
		

	/**
	 *
	 */
	private void verifyImage(JRImage image) throws JRException
	{
		this.verifyAnchor(image);
		this.verifyHyperlink(image);

		if (image != null)
		{
			JRExpression expression = image.getExpression();
			
			if (expression != null)
			{
				String className = expression.getValueClassName();

				if (className == null)
				{
					brokenRules.add("Class not set for image expression.");
				}
				else if (Arrays.binarySearch(getImageClassNames(), className) < 0)
				{
					brokenRules.add("Class \"" + className + "\" not supported for image expression.");
				}
			}
		}
	}
		

	/**
	 *
	 */
	private void verifySubreport(JRSubreport subreport) throws JRException
	{
		if (subreport != null)
		{
			JRExpression expression = subreport.getExpression();
			Class clazz = null;
			
			if (expression != null)
			{
				String className = expression.getValueClassName();

				if (className == null)
				{
					brokenRules.add("Class not set for subreport expression.");
				}
				else if (Arrays.binarySearch(getSubreportClassNames(), className) < 0) 
				{
					brokenRules.add("Class \"" + className + "\" not supported for subreport expression.");
				}
			}

			expression = subreport.getParametersMapExpression();

			if (expression != null)
			{
				clazz = expression.getValueClass();

				if (clazz == null)
				{
					brokenRules.add("Class not set for subreport parameters map expression.");
				}
				else if (!java.util.Map.class.isAssignableFrom(clazz))
				{
					brokenRules.add("Class " + clazz + " not supported for subreport parameters map expression. Use java.util.Map instead.");
				}
			}

			JRSubreportParameter[] parameters = subreport.getParameters();
			if (parameters != null && parameters.length > 0)
			{
				for(int index = 0; index < parameters.length; index++)
				{
					JRSubreportParameter parameter = parameters[index];
	
					if (parameter.getName() == null || parameter.getName().trim().length() == 0)
					{
						brokenRules.add("Subreport parameter name missing.");
					}

					expression = parameter.getExpression();
					
					if (expression != null)
					{
						clazz = expression.getValueClass();
		
						if (clazz == null)
						{
							brokenRules.add("Class not set for subreport parameter expression : " + parameter.getName() + ". Use java.lang.Object class.");
						}
					}
				}
			}
			
			if (
				subreport.getConnectionExpression() != null &&
				subreport.getDataSourceExpression() != null
				)
			{
				brokenRules.add("Subreport cannot have both connection expresion and data source expression.");
			}
				
			expression = subreport.getConnectionExpression();

			if (expression != null)
			{
				clazz = expression.getValueClass();

				if (clazz == null)
				{
					brokenRules.add("Class not set for subreport connection expression.");
				}
				else if (!java.sql.Connection.class.isAssignableFrom(clazz))
				{
					brokenRules.add("Class " + clazz + " not supported for subreport connection expression. Use java.sql.Connection instead.");
				}
			}

			expression = subreport.getDataSourceExpression();

			if (expression != null)
			{
				clazz = expression.getValueClass();

				if (clazz == null)
				{
					brokenRules.add("Class not set for subreport data source expression.");
				}
				else if (!com.develog.utils.report.engine.JRDataSource.class.isAssignableFrom(clazz))
				{
					brokenRules.add("Class " + clazz + " not supported for subreport data source expression. Use com.develog.utils.report.engine.JRDataSource instead.");
				}
			}
		}
	}
		

	/**
	 *
	 */
	private static synchronized String[] getQueryParameterClassNames()
	{
		if (queryParameterClassNames == null)
		{
			queryParameterClassNames = new String[]
			{
				java.lang.Object.class.getName(),
				java.lang.Boolean.class.getName(),
				java.lang.Byte.class.getName(),
				java.lang.Double.class.getName(),
				java.lang.Float.class.getName(),
				java.lang.Integer.class.getName(),
				java.lang.Long.class.getName(),
				java.lang.Short.class.getName(),
				java.math.BigDecimal.class.getName(),
				java.lang.String.class.getName(),
				java.util.Date.class.getName(),
				java.sql.Timestamp.class.getName(),
				java.sql.Time.class.getName()
			};

			Arrays.sort(queryParameterClassNames);
		}
		
		return queryParameterClassNames;
	}


	/**
	 *
	 */
	private static synchronized String[] getFieldClassNames()
	{
		if (fieldClassNames == null)
		{
			fieldClassNames = new String[]
			{
				java.lang.Object.class.getName(),
				java.lang.Boolean.class.getName(),
				java.lang.Byte.class.getName(),
				java.util.Date.class.getName(),
				java.sql.Timestamp.class.getName(),
				java.sql.Time.class.getName(),
				java.lang.Double.class.getName(),
				java.lang.Float.class.getName(),
				java.lang.Integer.class.getName(),
				java.io.InputStream.class.getName(),
				java.lang.Long.class.getName(),
				java.lang.Short.class.getName(),
				java.math.BigDecimal.class.getName(),
				java.lang.String.class.getName()
			};

			Arrays.sort(fieldClassNames);
		}
		
		return fieldClassNames;
	}


	/**
	 *
	 */
	private static synchronized String[] getTextFieldClassNames()
	{
		if (textFieldClassNames == null)
		{
			textFieldClassNames = new String[]
			{
				java.lang.Boolean.class.getName(),
				java.lang.Byte.class.getName(),
				java.util.Date.class.getName(),
				java.sql.Timestamp.class.getName(),
				java.sql.Time.class.getName(),
				java.lang.Double.class.getName(),
				java.lang.Float.class.getName(),
				java.lang.Integer.class.getName(),
				java.lang.Long.class.getName(),
				java.lang.Short.class.getName(),
				java.math.BigDecimal.class.getName(),
				java.lang.Number.class.getName(),
				java.lang.String.class.getName()
			};

			Arrays.sort(textFieldClassNames);
		}
		
		return textFieldClassNames;
	}


	/**
	 *
	 */
	private static synchronized String[] getImageClassNames()
	{
		if (imageClassNames == null)
		{
			imageClassNames = new String[]
			{
				java.lang.String.class.getName(),
				java.io.File.class.getName(),
				java.net.URL.class.getName(),
				java.io.InputStream.class.getName(),
				java.awt.Image.class.getName()
			};

			Arrays.sort(imageClassNames);
		}
		
		return imageClassNames;
	}


	/**
	 *
	 */
	private static synchronized String[] getSubreportClassNames()
	{
		if (subreportClassNames == null)
		{
			subreportClassNames = new String[]
			{
				java.lang.String.class.getName(),
				java.io.File.class.getName(),
				java.net.URL.class.getName(),
				java.io.InputStream.class.getName(),
				com.develog.utils.report.engine.JasperReport.class.getName()
			};

			Arrays.sort(subreportClassNames);
		}
		
		return subreportClassNames;
	}


}
