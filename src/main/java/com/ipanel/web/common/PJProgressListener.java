package com.ipanel.web.common;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;

public class PJProgressListener implements ProgressListener {
	private HttpSession session;

	public PJProgressListener() {
	}

	public PJProgressListener(HttpSession _session) {
		// session=_session;
		// ProgressEntity ps = new ProgressEntity();
		// session.setAttribute("upload_ps", ps);
	}

	@Override
	public void update(long pBytesRead, long pContentLength, int pItems) {
		// ProgressEntity ps = (ProgressEntity)
		// session.getAttribute("upload_ps");
		// ps.setpBytesRead(pBytesRead);
		// ps.setpContentLength(pContentLength);
		// ps.setpItems(pItems);
		// //更新
		// session.setAttribute("upload_ps", ps);
	}

}
