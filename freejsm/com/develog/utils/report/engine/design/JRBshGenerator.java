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

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.develog.utils.report.engine.JRException;
import com.develog.utils.report.engine.JRExpression;
import com.develog.utils.report.engine.JRExpressionChunk;
import com.develog.utils.report.engine.JRField;
import com.develog.utils.report.engine.JRParameter;
import com.develog.utils.report.engine.JRVariable;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRBshGenerator.java,v 1.1 2005/10/10 14:18:10 nahuel Exp $
 */
public class JRBshGenerator
{
	
	
	/**
	 *
	 */
	private JasperDesign jasperDesign = null;

	private static Map fieldPrefixMap = null;
	private static Map variablePrefixMap = null;
	private static Map methodSuffixMap = null;


	/**
	 *
	 */
	protected JRBshGenerator(JasperDesign jrDesign)
	{
		jasperDesign = jrDesign;

		fieldPrefixMap = new HashMap();
		fieldPrefixMap.put(new Byte(JRExpression.EVALUATION_OLD),       "Old");
		fieldPrefixMap.put(new Byte(JRExpression.EVALUATION_ESTIMATED), "");
		fieldPrefixMap.put(new Byte(JRExpression.EVALUATION_DEFAULT),   "");
		
		variablePrefixMap = new HashMap();
		variablePrefixMap.put(new Byte(JRExpression.EVALUATION_OLD),       "Old");
		variablePrefixMap.put(new Byte(JRExpression.EVALUATION_ESTIMATED), "Estimated");
		variablePrefixMap.put(new Byte(JRExpression.EVALUATION_DEFAULT),   "");
		
		methodSuffixMap = new HashMap();
		methodSuffixMap.put(new Byte(JRExpression.EVALUATION_OLD),       "Old");
		methodSuffixMap.put(new Byte(JRExpression.EVALUATION_ESTIMATED), "Estimated");
		methodSuffixMap.put(new Byte(JRExpression.EVALUATION_DEFAULT),   "");
	}


	/**
	 *
	 */
	public static String generateScript(JasperDesign jrDesign) throws JRException
	{
		JRBshGenerator generator = new JRBshGenerator(jrDesign);
		return generator.generateScript();
	}


	/**
	 *
	 */
	protected String generateScript() throws JRException
	{
		StringBuffer sbuffer = new StringBuffer();

		/*   */
		sbuffer.append(
			"//\n" +
			"// Generated by JasperReports - " + (new SimpleDateFormat()).format(new java.util.Date()) + "\n" +
			"//\n" +
			"import com.develog.utils.report.engine.*;\n" +
			"import com.develog.utils.report.engine.fill.*;\n" +
			"\n" +
			"import java.util.*;\n" +
			"import java.math.*;\n" +
			"import java.text.*;\n" +
			"import java.io.*;\n" +
			"import java.net.*;\n" +
			"\n" +
			"\n" +
			"createBshCalculator()\n" +
			"{\n" + 
			"\n" +
			"\n"
			);

		/*   */
		Map parametersMap = jasperDesign.getParametersMap();
		if (parametersMap != null && parametersMap.size() > 0)
		{
			Collection parameterNames = parametersMap.keySet();
			for (Iterator it = parameterNames.iterator(); it.hasNext();)
			{
				sbuffer.append("    JRFillParameter parameter_" + it.next() + " = null;\n");
			}
		}
		
		/*   */
		sbuffer.append(
			"\n"
			);

		/*   */
		Map fieldsMap = jasperDesign.getFieldsMap();
		if (fieldsMap != null && fieldsMap.size() > 0)
		{
			Collection fieldNames = fieldsMap.keySet();
			for (Iterator it = fieldNames.iterator(); it.hasNext();)
			{
				sbuffer.append("    JRFillField field_" + it.next() + " = null;\n");
			}
		}
		
		/*   */
		sbuffer.append(
			"\n"
			);

		/*   */
		JRVariable[] variables = jasperDesign.getVariables();
		if (variables != null && variables.length > 0)
		{
			for (int i = 0; i < variables.length; i++)
			{
				sbuffer.append("    JRFillVariable variable_" + variables[i].getName() + " = null;\n");
			}
		}

		/*   */
		sbuffer.append(
			"\n" +
			"\n" +
			"    init(\n" + 
			"        Map parsm,\n" + 
			"        Map fldsm,\n" + 
			"        Map varsm\n" + 
			"        )\n" +
			"    {\n"
			);

		/*   */
		parametersMap = jasperDesign.getParametersMap();
		if (parametersMap != null && parametersMap.size() > 0)
		{
			Collection parameterNames = parametersMap.keySet();
			String parameterName = null;
			for (Iterator it = parameterNames.iterator(); it.hasNext();)
			{
				parameterName = (String)it.next();
				sbuffer.append("        super.parameter_" + parameterName + " = (JRFillParameter)parsm.get(\"" + parameterName + "\");\n");
			}
		}
		
		/*   */
		sbuffer.append(
			"\n"
			);

		/*   */
		fieldsMap = jasperDesign.getFieldsMap();
		if (fieldsMap != null && fieldsMap.size() > 0)
		{
			Collection fieldNames = fieldsMap.keySet();
			String fieldName = null;
			for (Iterator it = fieldNames.iterator(); it.hasNext();)
			{
				fieldName = (String)it.next();
				sbuffer.append("        super.field_" + fieldName + " = (JRFillField)fldsm.get(\"" + fieldName + "\");\n");
			}
		}
		
		/*   */
		sbuffer.append(
			"\n"
			);

		/*   */
		variables = jasperDesign.getVariables();
		if (variables != null && variables.length > 0)
		{
			String variableName = null;
			for (int i = 0; i < variables.length; i++)
			{
				variableName = variables[i].getName();
				sbuffer.append("        super.variable_" + variableName + " = (JRFillVariable)varsm.get(\"" + variableName + "\");\n");
			}
		}

		/*   */
		sbuffer.append(
			"    }\n" +
			"\n" +
			"\n"
		//	"    /**\n" +
		//	"     * Test method\n" +
		//	"     */\n" +
		//	"    public static void helloJasper()\n" +
		//	"    {\n" +
		//	"        System.out.println(\"------------------------------\");\n" +
		//	"        System.out.println(\" Hello, Jasper!...\");\n" +
		//	"        System.out.println(\"------------------------------\");\n" +
		//	"    }\n" +
		//	"\n" +
		//	"\n"
			);

		sbuffer.append(this.generateMethod(JRExpression.EVALUATION_DEFAULT));
		sbuffer.append(this.generateMethod(JRExpression.EVALUATION_OLD));
		sbuffer.append(this.generateMethod(JRExpression.EVALUATION_ESTIMATED));

		sbuffer.append(
			"\n" + 
			"    return this;\n" +
			"}\n"
			);

		return sbuffer.toString();
	}		


	/**
	 *
	 *
	private String generateMethod(byte evaluationType) throws JRException
	{
		StringBuffer sbuffer = new StringBuffer();

		Collection expressions = jasperDesign.getExpressions();
		if (expressions != null && expressions.size() > 0)
		{
			JRExpression expression = null;
			for (Iterator it = expressions.iterator(); it.hasNext();)
			{
				expression = (JRExpression)it.next();
				
				/*   *
				sbuffer.append(
					"    Object evaluate" + (String)methodSuffixMap.get(new Byte(evaluationType)) + expression.getId() + "()\n" + 
					"    {\n" +
					"        // " + expression.getName() + "\n" +
					"        return (" + expression.getValueClass().getName() + ")("
					);
					
				sbuffer.append(
					this.generateExpression(expression, evaluationType)
					);
					
				sbuffer.append(
					");\n" +
					"    }\n" +
					"\n" +
					"\n"
					);
			}
		}
		
		return sbuffer.toString();
	}


	/**
	 *
	 */
	private String generateMethod(byte evaluationType) throws JRException
	{
		StringBuffer sbuffer = new StringBuffer();

		/*   */
		sbuffer.append(
			"    Object evaluate" + (String)methodSuffixMap.get(new Byte(evaluationType)) + "(int id)\n" +
			"    {\n" +
			"        Object value = null;\n" +
			"\n" +
			"        switch (id)\n" +
			"        {\n"
			);

		Collection expressions = jasperDesign.getExpressions();
		if (expressions != null && expressions.size() > 0)
		{
			JRExpression expression = null;
			for (Iterator it = expressions.iterator(); it.hasNext();)
			{
				expression = (JRExpression)it.next();
				
				sbuffer.append(
					"            case " + expression.getId() + " : // " + expression.getName() + "\n" +
					"            {\n" +
					"                value = (" + expression.getValueClassName() + ")("
					);
					
				sbuffer.append(
					this.generateExpression(expression, evaluationType)
					);
					
				sbuffer.append(
					");\n" +
					"                break;\n" +
					"            }\n"
					);
			}
		}

		/*   */
		sbuffer.append(
			"           default :\n" +
			"           {\n" +
			"           }\n" +
			"        }\n" +
			"        \n" +
			"        return value;\n" +
			"    }\n" +
			"\n" +
			"\n"
			);
		
		return sbuffer.toString();
	}


	/**
	 *
	 */
	private String generateExpression(
		JRExpression expression,
		byte evaluationType
		) throws JRException
	{
		Map parametersMap = jasperDesign.getParametersMap();
		Map fieldsMap = jasperDesign.getFieldsMap();
		Map variablesMap = jasperDesign.getVariablesMap();

		JRParameter jrParameter = null;
		JRField jrField = null;
		JRVariable jrVariable = null;

		StringBuffer sbuffer = new StringBuffer();

		JRExpressionChunk[] chunks = expression.getChunks();
		JRExpressionChunk chunk = null;
		String chunkText = null;
		if (chunks != null && chunks.length > 0)
		{
			for(int i = 0; i < chunks.length; i++)
			{
				chunk = chunks[i];

				chunkText = chunk.getText();
				if (chunkText == null)
				{
					chunkText = "";
				}
				
				switch (chunk.getType())
				{
					case JRExpressionChunk.TYPE_TEXT :
					{
						sbuffer.append(chunkText);
						break;
					}
					case JRExpressionChunk.TYPE_PARAMETER :
					{
						jrParameter = (JRParameter)parametersMap.get(chunkText);
	
						sbuffer.append(
							"(" +
								"(" +
									jrParameter.getValueClassName() +
								")" +
								"super.parameter_" + chunkText + ".getValue()" +
							")"
							);
	
						break;
					}
					case JRExpressionChunk.TYPE_FIELD :
					{
						jrField = (JRField)fieldsMap.get(chunkText);
	
						sbuffer.append(
							"(" +
								"(" +
									jrField.getValueClassName() +
								")" +
								"super.field_" + chunkText + ".get" + (String)fieldPrefixMap.get(new Byte(evaluationType)) + "Value()" +
							")"
							);
	
						break;
					}
					case JRExpressionChunk.TYPE_VARIABLE :
					{
						jrVariable = (JRVariable)variablesMap.get(chunkText);
	
						sbuffer.append(
							"(" +
								"(" +
									jrVariable.getValueClassName() +
								")" +
								"super.variable_" + chunkText + ".get" + (String)variablePrefixMap.get(new Byte(evaluationType)) + "Value()" +
							")"
							);
	
						break;
					}
				}
			}
		}
		
		if (sbuffer.length() == 0)
		{
			sbuffer.append("null");
		}

		return sbuffer.toString();
	}


}
