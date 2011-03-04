package com.amb.google.api;
import java.io.IOException;
import java.net.URL;

import com.amb.google.helper.UserData;
import com.google.gdata.client.DocumentQuery;
import com.google.gdata.client.docs.DocsService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.docs.DocumentEntry;
import com.google.gdata.data.docs.DocumentListEntry;
import com.google.gdata.data.docs.DocumentListFeed;
import com.google.gdata.data.docs.PresentationEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

/**
 * Simple Doogle Docs client. 
 * @author adam.musial-bright@infopark.de
 */
public class DocsClient {

	private DocsService docsService = new DocsService("Document List Demo");
	private UserData userData = new UserData("user.properties");
	
	public String domain = "https://docs.google.com";
	public String url = "/feeds/default/private/full";
	public String searchText;
	
	public DocsClient() throws AuthenticationException {
		docsService.setUserCredentials(userData.getUser(), userData.getPassword());
	}
	
	public DocumentListFeed getDocumentListFeed() throws Exception {
		
		URL feedUrl = new URL(domain + url);
		DocumentQuery query = new DocumentQuery(feedUrl);
		if (searchText != null) {
			query.setFullTextQuery(searchText);
		}
		return docsService.getFeed(query, DocumentListFeed.class);
	}
	
	public DocumentListEntry createNewDocument(String title, String type)
			throws IOException, ServiceException {
		
		DocumentListEntry newEntry = null;
		if (type.equals("document")) {
			newEntry = new DocumentEntry();
		} else if (type.equals("presentation")) {
			newEntry = new PresentationEntry();
		} //else if (type.equals("spreadsheet")) {
			//newEntry = new SpreadsheetEntry();
		//}
		newEntry.setTitle(new PlainTextConstruct(title));

		// Prevent collaborators from sharing the document with others?
		// newEntry.setWritersCanInvite(false);

		// You can also hide the document on creation
		// newEntry.setHidden(true);

		return docsService.insert(new URL(domain + url), newEntry);
	}
	
	public void print(DocumentListFeed feed) {
		System.out.println("\n");
		for (DocumentListEntry entry : feed.getEntries()) {
			System.out.println(entry.getDocumentLink().getHref());
			System.out.println(entry.getType());
			System.out.println(entry.getTitle().getPlainText() + ", " +entry.getLastViewed());
			System.out.println();
		}
		System.out.println("Size : " + feed.getEntries().size());
	}
	
}
