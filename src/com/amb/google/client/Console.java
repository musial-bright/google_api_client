package com.amb.google.client;


import com.amb.google.api.DocsClient;
import com.amb.google.helper.ProcessingIndicatorThread;

public class Console {
	
	public static void main(String[] args) {
		DocsClient dc;
		try {
			dc = new DocsClient();
		} catch (Exception e) {
			System.out.println(e);
			return;
		}
		if (args.length == 0) {
			System.out.println(
				"Usage : DocsClient " +
				"<read|write> " + 
				"[<\"search text\">] " +
				"[<url> | default: " + dc.url + "]"
			);
			return;
		}
		if (args.length > 1) {
			dc.searchText = args[1];
		}
		if (args.length > 2) {
			dc.url = args[2];
		}
		String action = args[0];
		System.out.println(action + " \"" + dc.searchText + "\" " + dc.domain + dc.url);
		ProcessingIndicatorThread indicator = new ProcessingIndicatorThread();
		indicator.start();
		
		try {
			if (action.equals("read")) {
				dc.print(dc.getDocumentListFeed());
			} else if (action.equals("create")) {
				dc.createNewDocument(args[1], "document");
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			indicator.interrupt();
		}

	}
	
}
