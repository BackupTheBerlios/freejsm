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

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.develog.utils.report.engine.util.JRLoader;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRAbstractExporter.java,v 1.1 2005/10/10 14:18:04 nahuel Exp $
 */
public abstract class JRAbstractExporter implements JRExporter
{


	/**
	 *
	 */
	protected Map parameters = new HashMap();

	/**
	 *
	 */
	protected JasperPrint jasperPrint = null;
	protected int startPageIndex = 0;
	protected int endPageIndex = 0;


	/**
	 *
	 */
	public void setParameter(JRExporterParameter parameter, Object value)
	{
		parameters.put(parameter, value);
	}


	/**
	 *
	 */
	public Object getParameter(JRExporterParameter parameter)
	{
		return parameters.get(parameter);
	}


	/**
	 *
	 */
	public void setParameters(Map parameters)
	{
		this.parameters = parameters;
	}
	

	/**
	 *
	 */
	public Map getParameters()
	{
		return parameters;
	}
	

	/**
	 *
	 */
	public abstract void exportReport() throws JRException;


	/**
	 *
	 */
	protected void setInput() throws JRException
	{
		jasperPrint = (JasperPrint)parameters.get(JRExporterParameter.JASPER_PRINT);
		if (jasperPrint == null)
		{
			InputStream is = (InputStream)parameters.get(JRExporterParameter.INPUT_STREAM);
			if (is != null)
			{
				jasperPrint = (JasperPrint)JRLoader.loadObject(is);
			}
			else
			{
				URL url = (URL)parameters.get(JRExporterParameter.INPUT_URL);
				if (url != null)
				{
					jasperPrint = (JasperPrint)JRLoader.loadObject(url);
				}
				else
				{
					File file = (File)parameters.get(JRExporterParameter.INPUT_FILE);
					if (file != null)
					{
						jasperPrint = (JasperPrint)JRLoader.loadObject(file);
					}
					else
					{
						String fileName = (String)parameters.get(JRExporterParameter.INPUT_FILE_NAME);
						if (fileName != null)
						{
							jasperPrint = (JasperPrint)JRLoader.loadObject(fileName);
						}
						else
						{
							throw new JRException("No input source supplied to the exporter.");
						}
					}
				}
			}
		}
	}
	

	/**
	 *
	 */
	protected void setPageRange() throws JRException
	{
		int lastPageIndex = -1;
		if (jasperPrint.getPages() != null)
		{
			lastPageIndex = jasperPrint.getPages().size() - 1;
		}

		Integer start = (Integer)parameters.get(JRExporterParameter.START_PAGE_INDEX);
		if (start == null)
		{
			startPageIndex = 0;
		}
		else
		{
			startPageIndex = start.intValue();
			if (startPageIndex < 0 || startPageIndex > lastPageIndex)
			{
				throw new JRException("Start page index out of range : " + startPageIndex + " of " + lastPageIndex);
			}
		}

		Integer end = (Integer)parameters.get(JRExporterParameter.END_PAGE_INDEX);
		if (end == null)
		{
			endPageIndex = lastPageIndex;
		}
		else
		{
			endPageIndex = end.intValue();
			if (endPageIndex < 0 || endPageIndex > lastPageIndex)
			{
				throw new JRException("End page index out of range : " + endPageIndex + " of " + lastPageIndex);
			}
		}

		Integer index = (Integer)parameters.get(JRExporterParameter.PAGE_INDEX);
		if (index != null)
		{
			int pageIndex = index.intValue();
			if (pageIndex < 0 || pageIndex > lastPageIndex)
			{
				throw new JRException("Page index out of range : " + pageIndex + " of " + lastPageIndex);
			}
			else
			{
				startPageIndex = pageIndex;
				endPageIndex = pageIndex;
			}
		}
	}
	

	/**
	 *
	 */
	protected void setOutput()
	{
	}
	

}
