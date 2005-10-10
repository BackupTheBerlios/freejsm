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
package com.develog.utils.report.engine.fill;

import com.develog.utils.report.engine.JRException;
import com.develog.utils.report.engine.JRVariable;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRComparableIncrementerFactory.java,v 1.1 2005/10/10 14:18:15 nahuel Exp $
 */
public class JRComparableIncrementerFactory implements JRIncrementerFactory
{


	/**
	 *
	 */
	private static JRComparableIncrementerFactory mainInstance = new JRComparableIncrementerFactory();


	/**
	 *
	 */
	private JRComparableIncrementerFactory()
	{
	}


	/**
	 *
	 */
	public static JRComparableIncrementerFactory getInstance()
	{
		return mainInstance;
	}


	/**
	 *
	 */
	public JRIncrementer getIncrementer(byte calculation)
	{
		JRIncrementer incrementer = null;

		switch (calculation)
		{
			case JRVariable.CALCULATION_LOWEST :
			{
				incrementer = JRComparableLowestIncrementer.getInstance();
				break;
			}
			case JRVariable.CALCULATION_HIGHEST :
			{
				incrementer = JRComparableHighestIncrementer.getInstance();
				break;
			}
			case JRVariable.CALCULATION_SYSTEM :
			case JRVariable.CALCULATION_NOTHING :
			case JRVariable.CALCULATION_COUNT :
			case JRVariable.CALCULATION_SUM :
			case JRVariable.CALCULATION_AVERAGE :
			case JRVariable.CALCULATION_STANDARD_DEVIATION :
			case JRVariable.CALCULATION_VARIANCE :
			default :
			{
				incrementer = JRDefaultIncrementerFactory.getInstance().getIncrementer(calculation);
				break;
			}
		}
		
		return incrementer;
	}


}


/**
 *
 */
class JRComparableLowestIncrementer implements JRIncrementer
{
	/**
	 *
	 */
	private static JRComparableLowestIncrementer mainInstance = new JRComparableLowestIncrementer();

	/**
	 *
	 */
	private JRComparableLowestIncrementer()
	{
	}

	/**
	 *
	 */
	public static JRComparableLowestIncrementer getInstance()
	{
		return mainInstance;
	}

	/**
	 *
	 */
	public Object increment(
		JRFillVariable variable, 
		Object expressionValue,
		AbstractValueProvider valueProvider
		) throws JRException
	{
		Comparable value = (Comparable)variable.getValue();
		Comparable newValue = (Comparable)expressionValue;

		if (
			value != null && !variable.isInitialized() &&
			(newValue == null || value.compareTo(newValue) < 0)
			)
		{
			newValue = value;
		}
				
		return newValue;
	}
}


/**
 *
 */
class JRComparableHighestIncrementer implements JRIncrementer
{
	/**
	 *
	 */
	private static JRComparableHighestIncrementer mainInstance = new JRComparableHighestIncrementer();

	/**
	 *
	 */
	private JRComparableHighestIncrementer()
	{
	}

	/**
	 *
	 */
	public static JRComparableHighestIncrementer getInstance()
	{
		return mainInstance;
	}

	/**
	 *
	 */
	public Object increment(
		JRFillVariable variable, 
		Object expressionValue,
		AbstractValueProvider valueProvider
		) throws JRException
	{
		Comparable value = (Comparable)variable.getValue();
		Comparable newValue = (Comparable)expressionValue;

		if (
			value != null && !variable.isInitialized() &&
			(newValue == null || value.compareTo(newValue) > 0)
			)
		{
			newValue = value;
		}
				
		return newValue;
	}
}
