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

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import com.develog.utils.report.engine.JRException;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRImageLoader.java,v 1.1 2005/10/10 14:18:20 nahuel Exp $
 */
public class JRImageLoader
{


	/**
	 *
	 */
	public static final byte NO_IMAGE = 1;
	public static final byte SUBREPORT_IMAGE = 2;

	private static final String str_NO_IMAGE = "com.develog.utils.report/engine/images/noimage.GIF";
	private static final String str_SUBREPORT_IMAGE = "com.develog.utils.report/engine/images/subreport.GIF";
	private static Image img_NO_IMAGE = null;
	private static Image img_SUBREPORT_IMAGE = null;

	/**
	 *
	 */
	//private static boolean wasWarning = false;


	/**
	 *
	 */
	public static byte[] loadImageDataFromFile(File file) throws JRException
	{
		try
		{
			return JRLoader.loadBytes(file);
		}
		catch (JRException e)
		{
			throw new JRException("Error loading image data : " + file, e);
		}
	}


	/**
	 *
	 */
	public static byte[] loadImageDataFromURL(URL url) throws JRException
	{
		try
		{
			return JRLoader.loadBytes(url);
		}
		catch (JRException e)
		{
			throw new JRException("Error loading image data : " + url, e);
		}
	}


	/**
	 *
	 */
	public static byte[] loadImageDataFromInputStream(InputStream is) throws JRException
	{
		try
		{
			return JRLoader.loadBytes(is);
		}
		catch (JRException e)
		{
			throw new JRException("Error loading image data from input stream.", e);
		}
	}


	/**
	 *
	 */
	public static byte[] loadImageDataFromLocation(String location) throws JRException
	{
		try
		{
			URL url = new URL(location);
			return loadImageDataFromURL(url);
		}
		catch(MalformedURLException e)
		{
		}

		File file = new File(location);
		if (file.exists() && file.isFile())
		{
			return loadImageDataFromFile(file);
		}

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource(location);
		if (url == null)
		{
			//if (!wasWarning)
			//{
			//	if (log.isWarnEnabled())
			//		log.warn("Failure using Thread.currentThread().getContextClassLoader() in JRImageLoader class. Using JRImageLoader.class.getClassLoader() instead.");
			//	wasWarning = true;
			//}
			classLoader = JRImageLoader.class.getClassLoader();
			url = classLoader.getResource(location);
		}

		if (url != null)
		{
			return loadImageDataFromURL(url);
		}

		throw new JRException("Image not found : " + location);
	}


	/**
	 *
	 */
	public static byte[] loadImageDataFromAWTImage(Image img) throws JRException
	{
		BufferedImage bi =
			new BufferedImage(
				img.getWidth(null),
				img.getHeight(null),
				BufferedImage.TYPE_INT_RGB
				);

		Graphics g = bi.createGraphics();
		g.drawImage(img, 0, 0, null);
		g.dispose();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	
		try
		{
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(baos);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bi);
			param.setQuality(1f, true);//1f = JPG_QUALITY
			encoder.encode(bi, param);
		}
		catch (IOException e)
		{
			throw new JRException("Error trying to load image data from AWT image.", e);
		}
		
		return baos.toByteArray();
	}


	/**
	 *
	 */
	public static Image getImage(byte index) throws JRException
	{
		Image image = null;

		switch(index)
		{
			case NO_IMAGE:
			{
				if (img_NO_IMAGE == null)
				{
					ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
					URL url = classLoader.getResource(str_NO_IMAGE);
					if (url == null)
					{
						//if (!wasWarning)
						//{
						//	if (log.isWarnEnabled())
						//		log.warn("Failure using Thread.currentThread().getContextClassLoader() in JRImageLoader class. Using JRImageLoader.class.getClassLoader() instead.");
						//	wasWarning = true;
						//}
						classLoader = JRImageLoader.class.getClassLoader();
					}
					InputStream is = classLoader.getResourceAsStream(str_NO_IMAGE);
					img_NO_IMAGE = loadImage(
						loadImageDataFromInputStream(is)
						);
				}
				image = img_NO_IMAGE;
				break;
			}
			case SUBREPORT_IMAGE:
			{
				if (img_SUBREPORT_IMAGE == null)
				{
					ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
					URL url = classLoader.getResource(str_SUBREPORT_IMAGE);
					if (url == null)
					{
						//if (!wasWarning)
						//{
						//	if (log.isWarnEnabled())
						//		log.warn("Failure using Thread.currentThread().getContextClassLoader() in JRImageLoader class. Using JRImageLoader.class.getClassLoader() instead.");
						//	wasWarning = true;
						//}
						classLoader = JRImageLoader.class.getClassLoader();
					}
					InputStream is = classLoader.getResourceAsStream(str_SUBREPORT_IMAGE);
					img_SUBREPORT_IMAGE = loadImage(
						loadImageDataFromInputStream(is)
						);
				}
				image = img_SUBREPORT_IMAGE;
				break;
			}
		}
		
		return image;
	}


	/**
	 *
	 */
	public static Image loadImage(byte[] bytes)
	{
		Image image = Toolkit.getDefaultToolkit().createImage( bytes );

		MediaTracker traker = new MediaTracker(new Panel());
		traker.addImage(image, 0);
		try
		{
			traker.waitForID(0);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			image = null;
		}
		
		return image;
	}


}
