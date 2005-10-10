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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import com.develog.utils.report.engine.JRException;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRJikesCompiler.java,v 1.1 2005/10/10 14:18:12 nahuel Exp $
 */
public class JRJikesCompiler extends JRAbstractJavaCompiler
{


	/**
	 *
	 */
	public String compileClass(File sourceFile, String classpath) throws JRException 
	{
		String[] source = new String[4];
		source[0] = "jikes";
		source[1] = "-classpath";
		source[2] = classpath;
		source[3] = sourceFile.getPath();
		
		try 
		{
			// Compile the source file and arrange to read the errors if any.
			Process compile = Runtime.getRuntime().exec(source);
			InputStream errFile = compile.getErrorStream();
			
			// Read the error messages (if any) into the ByteArrayOutputStream
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int count = 0;
			do 
			{
				count = errFile.read(buffer);
				if (count > 0)
				{
					baos.write(buffer, 0, count);
				}
			} while (count >= 0);
			
			if( baos.toString().indexOf("error") != -1 ) 
			{
				return baos.toString();
			}
			
			return null;
		}
		catch (Exception e) 
		{
			throw new JRException("Error compiling report java source file : " + sourceFile, e);
		}
	}


}
