/*
 * Copyright 2007, 2008 Duncan McGregor
 * 
 * This file is part of Rococoa, a library to allow Java to talk to Cocoa.
 * 
 * Rococoa is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Rococoa is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Rococoa.  If not, see <http://www.gnu.org/licenses/>.
 */
 
package org.rococoa.cocoa.qtkit;

import com.sun.jna.Structure;

//long   long         timeValue;   long            timeScale;   long            flags;
public class QTTime extends QTTimeByReference implements Structure.ByValue {

    public QTTime() {
        super();
    }

    public QTTime(long timeValue, int timeScale, int flags) {
        super(timeValue, timeScale, flags);
    }

    public QTTime(long timeValue, int timeScale) {
        super(timeValue, timeScale);
    }
    
}
