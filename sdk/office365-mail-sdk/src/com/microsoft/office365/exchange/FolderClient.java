package com.microsoft.office365.exchange;

import java.util.List;

import com.google.common.util.concurrent.ListenableFuture;
import com.microsoft.office365.Credentials;
import com.microsoft.office365.mail.entities.Folder;

public class FolderClient extends BaseClient<Folder>{

	public FolderClient(Credentials credentials) {
		super(credentials);
	}

	public ListenableFuture<List<Folder>> getFolders() {

		String url = Constants.BASE_URL + Constants.ROOTFOLDER_URL + Constants.CHILDFOLDERS_URL;

		return getList(url, null, Folder[].class);
	}
}