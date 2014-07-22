package com.microsoft.office365.api;

import java.util.ArrayList;
import java.util.List;

import com.microsoft.exchange.services.odata.model.DefaultFolder;
import com.microsoft.exchange.services.odata.model.Me;
import com.microsoft.exchange.services.odata.model.Messages;
import com.microsoft.exchange.services.odata.model.types.IFileAttachment;
import com.microsoft.exchange.services.odata.model.types.IFolder;
import com.microsoft.exchange.services.odata.model.types.IItemAttachment;
import com.microsoft.exchange.services.odata.model.types.IMessage;
import com.microsoft.exchange.services.odata.model.types.IMessageCollection;
import com.microsoft.exchange.services.odata.model.types.Recipient;
import com.microsoft.office365.http.OAuthCredentials;
import com.msopentech.odatajclient.proxy.api.Query;

public class MailClient extends AbstractOfficeClient {

	Builder mBuilder;

	protected MailClient(MailClient.Builder builder) {
		super(builder);
		mBuilder = builder;
	}

	public IMessage newMessage() {
		IMessage message = Messages.newMessage();
		return message;
	}

	public IMessage newMessage(DefaultFolder defaultFolder) {
		IMessage message = Messages.newMessage(defaultFolder);
		// flush here?
		return message;
	}

	public void save(IMessage message) {

		if (message == null) {
			throw new IllegalArgumentException("message cannot be null");
		}
		// TODO: Flush?
	}

	public void send(IMessage message) {
		if (message == null) {
			throw new IllegalArgumentException("message cannot be null");
		}
		message.send(); // flushes automatically
	}

	public IMessage createReply(IMessage message) {

		if (message == null) {
			throw new IllegalArgumentException("message cannot be null");
		}
		message.createReply();
		return message;
	}

	// TODO:Is this one necessary or createReply is enough??
	public IMessage createReplyAll(IMessage message) {
		return null;
	}

	public void reply(String messageId, String comment) {
		IMessage message = Me.getMessages().get(messageId);
		if (message != null) {
			message.reply(comment);
			Me.flush();
		}
	}

	public void reply(IMessage message, String comment) {

		if (message == null) {
			throw new IllegalArgumentException("message cannot be null");
		}

		message.reply(comment);
		Me.flush();
	}

	public void replyAll(String messageId, String comment) {

		IMessage message = Me.getMessages().get(messageId);

		if (message != null) {
			message.replyAll(comment);
			Me.flush();
		}
	}

	public void replyAll(IMessage message, String comment) {

		if (message == null) {
			throw new IllegalArgumentException("message cannot be null");
		}

		message.replyAll(comment);
		Me.flush();
	}

	public void forward(IMessage message, String comment, Recipient... recipients) {

	}

	public IFileAttachment createFileAttachment(IMessage message) {

		if (message == null) {
			throw new IllegalArgumentException("message cannot be null");
		}

		IFileAttachment attachment = message.getAttachments().newFileAttachment();
		return attachment;
	}

	public IItemAttachment createItemAttachment(IMessage message) {

		if (message == null) {
			throw new IllegalArgumentException("message cannot be null");
		}

		IItemAttachment attachment = message.getAttachments().newItemAttachment();
		return attachment;
	}

	public void move(IMessage message, String destinationFolder) {
		IFolder folder = Me.getFolders().get(destinationFolder);

		if (folder != null) {
			message.move(folder.getId());
		}
	}

	public void move(IMessage message, IFolder destinationFolder) {

		if (message == null) {
			throw new IllegalArgumentException("message cannot be null");
		}

		if (destinationFolder == null) {
			throw new IllegalArgumentException("destinationFolder cannot be null");
		}

		message.move(destinationFolder.getId());
	}

	public void delete(IMessage message) {

		if (message == null) {
			throw new IllegalArgumentException("message cannot be null");
		}

		Me.getMessages().delete(message.getId());
		Me.flush();
	}

	// this could be the same as delete but only applies to drafts
	public void discard(IMessage message) {

	}

	public void markAsRead(IMessage message) {
		if (message == null) {
			throw new IllegalArgumentException("message cannot be null");
		}
		message.setIsRead(true);
	}

	public void markAsUnread(IMessage message) {
		if (message == null) {
			throw new IllegalArgumentException("message cannot be null");
		}
		message.setIsRead(false);
	}

	public List<IFolder> getChildFolders() {
		List<IFolder> childFolders = new ArrayList<IFolder>(Me.getRootFolder().getChildFolders());
		return childFolders;
	}

	public List<IMessage> getMessages(String folderId, int from) {

		Query<IMessage, IMessageCollection> query = Me.getFolders().get(folderId).getMessages().createQuery();
		query.setMaxResults(mBuilder.getMaxRsults());
		query.setFirstResult(from);

		List<IMessage> messages = new ArrayList<IMessage>(query.getResult());
		return messages;
	}

	public List<IMessage> getMessages(String folderId) {

		List<IMessage> messages = null;
		IFolder folder = Me.getFolders().get(folderId);
		if (folder != null) {
			Query<IMessage, IMessageCollection> query = folder.getMessages().createQuery();
			query.setMaxResults(mBuilder.getMaxRsults());
			messages = new ArrayList<IMessage>(query.getResult());
		}
		return messages;
	}

	public static final class Builder extends AbstractOfficeClient.Builder {

		private int mMaxResults;

		public Builder() {
			super();
		}

		public Builder(OAuthCredentials credentials, String resourceId, String odataEndpoint) {
			super(credentials, resourceId, odataEndpoint);
		}

		@Override
		public MailClient build() {
			return new MailClient(this);
		}

		@Override
		public Builder setCredentials(OAuthCredentials credentials) {
			return (Builder) super.setCredentials(credentials);
		}

		@Override
		public Builder setOdataEndpoint(String odataEndpoint) {
			return (Builder) super.setOdataEndpoint(odataEndpoint);
		}

		@Override
		public Builder setResourceId(String resourceId) {
			return (Builder) super.setResourceId(resourceId);
		}

		public Builder setMaxResults(int maxResults) {
			mMaxResults = maxResults;
			return this;
		}

		public int getMaxRsults() {
			return mMaxResults;
		}
	}
}
