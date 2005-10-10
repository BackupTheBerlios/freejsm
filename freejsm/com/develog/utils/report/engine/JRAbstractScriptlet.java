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
package com.develog.utils.report.engine;

import java.util.Map;

import com.develog.utils.report.engine.fill.JRFillField;
import com.develog.utils.report.engine.fill.JRFillGroup;
import com.develog.utils.report.engine.fill.JRFillParameter;
import com.develog.utils.report.engine.fill.JRFillVariable;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRAbstractScriptlet.java,v 1.1 2005/10/10 14:18:04 nahuel Exp $
 */
public abstract class JRAbstractScriptlet
{


	/**
	 *
	 */
	protected Map parametersMap = null;
	protected Map fieldsMap = null;
	protected Map variablesMap = null;
	protected JRFillGroup[] groups = null;


	/**
	 *
	 */
	public JRAbstractScriptlet()
	{
	}


	/**
	 *
	 */
	public void setData(
		Map parsm,
		Map fldsm,
		Map varsm,
		JRFillGroup[] grps
		)
	{
		parametersMap = parsm;
		fieldsMap = fldsm;
		variablesMap = varsm;
		groups = grps;
	}


	/**
	 *
	 */
	public Object getParameterValue(String parameterName) throws JRScriptletException
	{
		JRFillParameter parameter = (JRFillParameter)this.parametersMap.get(parameterName);
		if (parameter == null)
		{
			throw new JRScriptletException("Parameter not found : " + parameterName);
		}
		return parameter.getValue();
	}


	/**
	 *
	 */
	public Object getFieldValue(String fieldName) throws JRScriptletException
	{
		JRFillField field = (JRFillField)this.fieldsMap.get(fieldName);
		if (field == null)
		{
			throw new JRScriptletException("Field not found : " + fieldName);
		}
		return field.getValue();
	}


	/**
	 *
	 */
	public Object getVariableValue(String variableName) throws JRScriptletException
	{
		JRFillVariable variable = (JRFillVariable)this.variablesMap.get(variableName);
		if (variable == null)
		{
			throw new JRScriptletException("Variable not found : " + variableName);
		}
		return variable.getValue();
	}


	/**
	 *
	 */
	public void setVariableValue(String variableName, Object value) throws JRScriptletException
	{
		JRFillVariable variable = (JRFillVariable)this.variablesMap.get(variableName);
		if (variable == null)
		{
			throw new JRScriptletException("Variable not found : " + variableName);
		}
		
		if (value != null && !variable.getValueClass().isInstance(value) )
		{
			throw new JRScriptletException("Incompatible value assigned to variable " + variableName + ". Expected " + variable.getValueClassName() + ".");
		}
		
		variable.setValue(value);
	}


	/**
	 *
	 */
	public void callBeforeReportInit() throws JRScriptletException
	{
		this.beforeReportInit();
		this.beforePageInit();
		this.beforeColumnInit();

		if(groups != null && groups.length > 0)
		{
			for(int i = 0; i < groups.length; i++)
			{
				this.beforeGroupInit( groups[i].getName() );
			}
		}
	}


	/**
	 *
	 */
	public void callAfterReportInit() throws JRScriptletException
	{
		if(groups != null && groups.length > 0)
		{
			for(int i = groups.length - 1; i >= 0; i--)
			{
				this.afterGroupInit( groups[i].getName() );
			}
		}

		this.afterColumnInit();
		this.afterPageInit();
		this.afterReportInit();
	}


	/**
	 *
	 */
	public void callBeforePageInit() throws JRScriptletException
	{
		this.beforePageInit();
		this.beforeColumnInit();
	}


	/**
	 *
	 */
	public void callAfterPageInit() throws JRScriptletException
	{
		this.afterColumnInit();
		this.afterPageInit();
	}


	/**
	 *
	 */
	public void callBeforeColumnInit() throws JRScriptletException
	{
		this.beforeColumnInit();
	}


	/**
	 *
	 */
	public void callAfterColumnInit() throws JRScriptletException
	{
		this.afterColumnInit();
	}


	/**
	 *
	 */
	public void callBeforeGroupInit() throws JRScriptletException
	{
		if(groups != null && groups.length > 0)
		{
			JRFillGroup group = null;
			for(int i = 0; i < groups.length; i++)
			{
				group = (JRFillGroup)groups[i];
				if (group.hasChanged())
				{
					this.beforeGroupInit(group.getName());
				}
			}
		}
	}


	/**
	 *
	 */
	public void callAfterGroupInit() throws JRScriptletException
	{
		if(groups != null && groups.length > 0)
		{
			JRFillGroup group = null;
			for(int i = groups.length - 1; i >= 0; i--)
			{
				group = (JRFillGroup)groups[i];
				if (group.hasChanged())
				{
					this.afterGroupInit(group.getName());
				}
			}
		}
	}


	/**
	 *
	 */
	public void callBeforeDetailEval() throws JRScriptletException
	{
		this.beforeDetailEval();
	}


	/**
	 *
	 */
	public void callAfterDetailEval() throws JRScriptletException
	{
		this.afterDetailEval();
	}


	/**
	 *
	 */
	public abstract void beforeReportInit() throws JRScriptletException;


	/**
	 *
	 */
	public abstract void afterReportInit() throws JRScriptletException;


	/**
	 *
	 */
	public abstract void beforePageInit() throws JRScriptletException;


	/**
	 *
	 */
	public abstract void afterPageInit() throws JRScriptletException;


	/**
	 *
	 */
	public abstract void beforeColumnInit() throws JRScriptletException;


	/**
	 *
	 */
	public abstract void afterColumnInit() throws JRScriptletException;


	/**
	 *
	 */
	public abstract void beforeGroupInit(String groupName) throws JRScriptletException;


	/**
	 *
	 */
	public abstract void afterGroupInit(String groupName) throws JRScriptletException;


	/**
	 *
	 */
	public abstract void beforeDetailEval() throws JRScriptletException;


	/**
	 *
	 */
	public abstract void afterDetailEval() throws JRScriptletException;


}
