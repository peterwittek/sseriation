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
