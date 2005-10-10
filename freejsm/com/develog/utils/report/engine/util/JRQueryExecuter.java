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

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.develog.utils.report.engine.JRException;
import com.develog.utils.report.engine.JRParameter;
import com.develog.utils.report.engine.JRQuery;
import com.develog.utils.report.engine.JRQueryChunk;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRQueryExecuter.java,v 1.1 2005/10/10 14:18:20 nahuel Exp $
 */
public class JRQueryExecuter
{


	/**
	 *
	 */
	private JRQuery query = null;
	private Map parametersMap = new HashMap();
	private Map parameterValues = null;
	private String queryString = "";
	private List parameterNames = new ArrayList();


	/**
	 *
	 */
	protected JRQueryExecuter(JRQuery query, Map parameters, Map values)
	{
		this.query = query;
		this.parametersMap = parameters;
		this.parameterValues = values;
		
		this.parseQuery();
	}


	/**
	 *
	 */
	public static PreparedStatement getStatement(
		JRQuery query,
		Map parameters,
		Map values,
		Connection conn
		) throws JRException
	{
		PreparedStatement pstmt = null;

		if (conn != null)
		{
			JRQueryExecuter queryExecuter = 
				new JRQueryExecuter(
					query, 
					parameters,
					values
					);
	
			pstmt = queryExecuter.getStatement(conn);
		}

		return pstmt;
	}


	/**
	 *
	 */
	private void parseQuery()
	{
		queryString = "";
		parameterNames = new ArrayList();

		if (query != null)
		{
			JRQueryChunk[] chunks = query.getChunks();
			if (chunks != null && chunks.length > 0)
			{
				StringBuffer sbuffer = new StringBuffer();
				JRQueryChunk chunk = null;
				for(int i = 0; i < chunks.length; i++)
				{
					chunk = chunks[i];
					switch (chunk.getType())
					{
						case JRQueryChunk.TYPE_PARAMETER_CLAUSE :
						{
							String parameterName = chunk.getText();
							Object parameterValue = parameterValues.get(parameterName);
							sbuffer.append(String.valueOf(parameterValue));
							//parameterNames.add(parameterName);
							break;
						}
						case JRQueryChunk.TYPE_PARAMETER :
						{
							sbuffer.append("?");
							parameterNames.add(chunk.getText());
							break;
						}
						case JRQueryChunk.TYPE_TEXT :
						default :
						{
							sbuffer.append(chunk.getText());
							break;
						}
					}
				}
				
				queryString = sbuffer.toString();
			}
		}
	}

	
	/**
	 *
	 */
	private PreparedStatement getStatement(Connection conn) throws JRException
	{
		PreparedStatement pstmt = null;

		if (queryString != null && queryString.trim().length() > 0)
		{
			try
			{
				pstmt = conn.prepareStatement(queryString);
				
				if (parameterNames != null && parameterNames.size() > 0)
				{
					JRParameter parameter = null;
					String parameterName = null;
					Class clazz = null;
					Object parameterValue = null;
					for(int i = 0; i < parameterNames.size(); i++)
					{
						parameterName = (String)parameterNames.get(i);
						parameter = (JRParameter)parametersMap.get(parameterName);
						clazz = parameter.getValueClass();
						//FIXMEparameterValue = jrParameter.getValue();
						parameterValue = parameterValues.get(parameterName);
	
						if ( clazz.equals(java.lang.Object.class) )
						{
							if (parameterValue == null)
							{
								pstmt.setNull(i + 1, Types.JAVA_OBJECT);
							}
							else
							{
								pstmt.setObject(i + 1, parameterValue);
							}
						}
						else if ( clazz.equals(java.lang.Boolean.class) )
						{
							if (parameterValue == null)
							{
								pstmt.setNull(i + 1, Types.BIT);
							}
							else
							{
								pstmt.setBoolean(i + 1, ((Boolean)parameterValue).booleanValue());
							}
						}
						else if ( clazz.equals(java.lang.Byte.class) )
						{
							if (parameterValue == null)
							{
								pstmt.setNull(i + 1, Types.TINYINT);
							}
							else
							{
								pstmt.setByte(i + 1, ((Byte)parameterValue).byteValue());
							}
						}
						else if ( clazz.equals(java.lang.Double.class) )
						{
							if (parameterValue == null)
							{
								pstmt.setNull(i + 1, Types.DOUBLE);
							}
							else
							{
								pstmt.setDouble(i + 1, ((Double)parameterValue).doubleValue());
							}
						}
						else if ( clazz.equals(java.lang.Float.class) )
						{
							if (parameterValue == null)
							{
								pstmt.setNull(i + 1, Types.FLOAT);
							}
							else
							{
								pstmt.setFloat(i + 1, ((Float)parameterValue).floatValue());
							}
						}
						else if ( clazz.equals(java.lang.Integer.class) )
						{
							if (parameterValue == null)
							{
								pstmt.setNull(i + 1, Types.INTEGER);
							}
							else
							{
								pstmt.setInt(i + 1, ((Integer)parameterValue).intValue());
							}
						}
						else if ( clazz.equals(java.lang.Long.class) )
						{
							if (parameterValue == null)
							{
								pstmt.setNull(i + 1, Types.BIGINT);
							}
							else
							{
								pstmt.setLong(i + 1, ((Long)parameterValue).longValue());
							}
						}
						else if ( clazz.equals(java.lang.Short.class) )
						{
							if (parameterValue == null)
							{
								pstmt.setNull(i + 1, Types.SMALLINT);
							}
							else
							{
								pstmt.setShort(i + 1, ((Short)parameterValue).shortValue());
							}
						}
						else if ( clazz.equals(java.math.BigDecimal.class) )
						{
							if (parameterValue == null)
							{
								pstmt.setNull(i + 1, Types.DECIMAL);
							}
							else
							{
								pstmt.setBigDecimal(i + 1, (BigDecimal)parameterValue);
							}
						}
						else if ( clazz.equals(java.lang.String.class) )
						{
							if (parameterValue == null)
							{
								pstmt.setNull(i + 1, Types.VARCHAR);
							}
							else
							{
								pstmt.setString(i + 1, parameterValue.toString());
							}
						}
						else if ( clazz.equals(java.util.Date.class) )
						{
							if (parameterValue == null)
							{
								pstmt.setNull(i + 1, Types.DATE);
							}
							else
							{
								pstmt.setDate( i + 1, new java.sql.Date( ((java.util.Date)parameterValue).getTime() ) );
							}
						}
						else if ( clazz.equals(java.sql.Timestamp.class) )
						{
							if (parameterValue == null)
							{
								pstmt.setNull(i + 1, Types.TIMESTAMP);
							}
							else
							{
								pstmt.setTimestamp( i + 1, (java.sql.Timestamp)parameterValue );
							}
						}
						else if ( clazz.equals(java.sql.Time.class) )
						{
							if (parameterValue == null)
							{
								pstmt.setNull(i + 1, Types.TIME);
							}
							else
							{
								pstmt.setTime( i + 1, (java.sql.Time)parameterValue );
							}
						}
						else
						{
							throw new JRException("Parameter type not supported in query : " + parameterName + " class " + clazz.getName());
						}
					}
				}
			}
			catch (SQLException e)
			{
				throw new JRException("Error preparing statement for executing the report query : " + "\n\n" + queryString + "\n\n", e);
			}
		}
		
		return pstmt;
	}


}
