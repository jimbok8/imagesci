/**
 *       Java Image Science Toolkit
 *                  --- 
 *     Multi-Object Image Segmentation
 *
 * Center for Computer-Integrated Surgical Systems and Technology &
 * Johns Hopkins Applied Physics Laboratory &
 * The Johns Hopkins University
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or (at
 * your option) any later version.  The license is available for reading at:
 * http://www.gnu.org/copyleft/lgpl.html
 *
 * @author Blake Lucas (blake@cs.jhu.edu)
 */
package edu.jhu.ece.iacl.jist.utility;

// TODO: Auto-generated Javadoc
/**
 * The Class VersionUtil.
 */
public class VersionUtil {
	
	/**
	 * Parses the revision number.
	 *
	 * @param cvsversion the cvsversion
	 * @return the string
	 */
	public static String parseRevisionNumber(String cvsversion) {
		return cvsversion.replace("Revision: ", "").replace("$", "")
				.replace(" ", "");
	}
}
