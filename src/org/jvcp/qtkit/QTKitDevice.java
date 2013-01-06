/*
 * Copyright 2010, Lionel Jeanson
 * 
 * This file is part of JVCP, a Java library to get video capture preview.
 * 
 * JVCP is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * JVCP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JVCP.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jvcp.qtkit;

import org.jvcp.VideoDevice;

public class QTKitDevice extends VideoDevice {
	private String UID;
	
	public QTKitDevice(String desc, String uid) {
		super(desc);
		UID = uid;
	}

	public String getUID() {
		return UID;
	}
}