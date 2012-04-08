/**
 * Semantic Seriation based on Hamiltonian Path
 *  Copyright (C) 2012 Peter Wittek
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package sg.edu.nus.comp.sseriation.sdistance;

import edu.mit.jwi.item.ISynsetID;

public class PathInfo {

	private byte edgeLength;
	private ISynsetID sid;

	public PathInfo() {

	}

	public byte getEdgeLength() {
		return edgeLength;
	}

	public void setEdgeLength(byte edgeLength) {
		this.edgeLength = edgeLength;
	}

	public ISynsetID getSid() {
		return sid;
	}

	public void setSid(ISynsetID sid) {
		this.sid = sid;
	}

}
