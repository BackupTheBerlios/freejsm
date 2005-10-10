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

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

import com.develog.utils.report.engine.JRException;
import com.develog.utils.report.engine.JasperReport;
import com.develog.utils.report.engine.fill.JRCalculator;
import com.develog.utils.report.engine.util.JRClassLoader;
import com.develog.utils.report.engine.util.JRLoader;
import com.develog.utils.report.engine.util.JRSaver;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRAbstractJavaCompiler.java,v 1.1 2005/10/10 14:18:10 nahuel Exp $
 */
public abstract class JRAbstractJavaCompiler implements JRCompiler, JRClassCompiler
{


	/**
	 *
	 */
	public JasperReport compileReport(JasperDesign jasperDesign) throws JRException
	{
		JasperReport jasperReport = null;
		
		Collection brokenRules = JRVerifier.verifyDesign(jasperDesign);
		if (brokenRules != null && brokenRules.size() > 0)
		{
			StringBuffer sbuffer = new StringBuffer();
			sbuffer.append("Report design not valid : ");
			int i = 1;
			for(Iterator it = brokenRules.iterator(); it.hasNext(); i++)
			{
				sbuffer.append("\n\t " + i + ". " + (String)it.next());
			}
			throw new JRException(sbuffer.toString());
		}
		else
		{
			//Report design OK

			String tempDirStr = System.getProperty("jasper.reports.compile.temp");
			if (tempDirStr == null || tempDirStr.length() == 0)
			{
				tempDirStr = System.getProperty("user.dir");
			}

			File tempDirFile = new File(tempDirStr);
			if (!tempDirFile.exists() || !tempDirFile.isDirectory())
			{
				throw new JRException("Temporary directory not found : " + tempDirStr);
			}
		
			boolean isKeepJavaFile = 
				Boolean.valueOf(
					System.getProperty("jasper.reports.compile.keep.java.file")
					).booleanValue();

			File javaFile = new File(tempDirFile, jasperDesign.getName() + ".java");
			File classFile = new File(tempDirFile, jasperDesign.getName() + ".class");

			//Generating expressions class source code
			String sourceCode = JRClassGenerator.generateClass(jasperDesign);

			//Creating expression class source file
			JRSaver.saveClassSource(sourceCode, javaFile);
	
			String classpath = System.getProperty("jasper.reports.compile.class.path");
			if (classpath == null || classpath.length() == 0)
			{
				classpath = System.getProperty("java.class.path");
			}
	
			try
			{
				//Compiling expression class source file
				String compileErrors = compileClass(javaFile, classpath);
				if (compileErrors != null)
				{
					throw new JRException("Errors were encountered when compiling report expressions class file:\n" + compileErrors);
				}
	
				//Reading class byte codes from compiled class file
				jasperReport = 
					new JasperReport(
						jasperDesign,
						getClass().getName(),
						JRLoader.loadBytes(classFile)
						);
			}
			catch (JRException e)
			{
				throw e;
			}
			catch (Exception e)
			{
				throw new JRException("Error compiling report design.", e);
			}
			finally
			{
				if (!isKeepJavaFile)
				{
					javaFile.delete();
				}
				classFile.delete();
			}
		}

		return jasperReport;
	}

	
	/**
	 *
	 */
	public JRCalculator loadCalculator(JasperReport jasperReport) throws JRException
	{
		JRCalculator calculator = null;

		try
		{
			Class clazz = 
				JRClassLoader.loadClassFromBytes(
					jasperReport.getName(), 
					(byte[])jasperReport.getCompileData()
					);
					
			calculator = (JRCalculator)clazz.newInstance();
		}
		catch (Exception e)
		{
			throw new JRException("Error loading expression class : " + jasperReport.getName(), e);
		}
		
		return calculator;
	}


}
