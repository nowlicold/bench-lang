/*
 * Copyright 2008 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */

package com.bench.lang.base.system.environment;

/**
 * Linux implementation of the SystemEnvironment class.
 */
class LinuxSystemEnvironment extends SystemEnvironment {
	LinuxSystemEnvironment() {
		setHostId(getLinuxHostId());
		setSystemModel(getCommandOutput("/bin/uname", "-i"));
		setSystemManufacturer(getLinuxSystemManufacturer());
		setCpuManufacturer(getLinuxCpuManufacturer());
		setSerialNumber(getLinuxSN());
	}

	private String getLinuxHostId() {
		String output = getCommandOutput("/usr/bin/hostid");
		// trim off the leading 0x
		if (output.startsWith("0x")) {
			output = output.substring(2);
		}
		return output;
	}

	private String getLinuxCpuManufacturer() {
		String contents = getFileContent("/proc/cpuinfo");
		for (String line : contents.split("\n")) {
			if (line.contains("vendor_id")) {
				String[] ss = line.split(":", 2);
				if (ss.length > 1) {
					return ss[1].trim();
				}
			}
		}
		return null;
	}

	/**
	 * Tries to obtain and return the system manufacturer.
	 * 
	 * @return The system manufacturer (an empty string if not found or an error occurred)
	 */
	private String getLinuxSystemManufacturer() {
		return getFileContent("/sys/devices/virtual/dmi/id/product_name");
	}

	/**
	 * Tries to obtain and return the serial number of the system.
	 * 
	 * @return The serial number (an empty string if not found or an error occurred)
	 */
	private String getLinuxSN() {
		return getFileContent("/sys/devices/virtual/dmi/id/product_serial");
	}
}
