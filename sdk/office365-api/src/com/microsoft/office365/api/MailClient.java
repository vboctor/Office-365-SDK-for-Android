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

/**
 * The Class MailClient.
 */
public class MailClient extends BaseOfficeClient {

	Builder mBuilder;

	protected MailClient(MailClient.Builder builder) {
		super(builder);
		mBuilder = builder;
	}

	/**
	 * New message.
	 *
	 * @return the i message
	 */
	public IMessage newMessage() {
		IMessage message = Messages.newMessage();
		return message;
	}

	/**
	 * New message.
	 *
	 * @param defaultFolder the default folder
	 * @return the i message
	 */
	public IMessage newMessage(DefaultFolder defaultFolder) {
		IMessage message = Messages.newMessage(defaultFolder);
		// flush here?
		return message;
	}

	/**
	 * Save.
	 *
	 * @param message the message
	 */
	public void save(IMessage message) {

		if (message == null) {
			throw new IllegalArgumentException("message cannot be null");
		}
		// TODO: Flush?
	}

	/**
	 * Send.
	 *
	 * @param message the message
	 */
	public void send(IMessage message) {
		if (message == null) {
			throw new IllegalArgumentException("message cannot be null");
		}
		message.send(); // flushes automatically
	}

	/**
	 * Creates the reply.
	 *
	 * @param message the message
	 * @return the i message
	 */
	public IMessage createReply(IMessage message) {

		if (message == null) {
			throw new IllegalArgumentException("message cannot be null");
		}
		message.createReply();
		return message;
	}

	// TODO:Is this one necessary or createReply is enough??
	/**
	 * Creates the reply all.
	 *
	 * @param message the message
	 * @return the i message
	 */
	public IMessage createReplyAll(IMessage message) {
		return null;
	}

	/**
	 * Reply.
	 *
	 * @param messageId the message id
	 * @param comment the comment
	 */
	public void reply(String messageId, String comment) {
		IMessage message = Me.getMessages().get(messageId);
		if (message != null) {
			message.reply(comment);
			Me.flush();
		}
	}

	/**
	 * Reply.
	 *
	 * @param message the message
	 * @param comment the comment
	 */
	public void reply(IMessage message, String comment) {

		if (message == null) {
			throw new IllegalArgumentException("message cannot be null");
		}

		message.reply(comment);
		Me.flush();
	}

	/**
	 * Reply all.
	 *
	 * @param messageId the message id
	 * @param comment the comment
	 */
	public void replyAll(String messageId, String comment) {

		IMessage message = Me.getMessages().get(messageId);

		if (message != null) {
			message.replyAll(comment);
			Me.flush();
		}
	}

	/**
	 * Reply all.
	 *
	 * @param message the message
	 * @param comment the comment
	 */
	public void replyAll(IMessage message, String comment) {

		if (message == null) {
			throw new IllegalArgumentException("message cannot be null");
		}

		message.replyAll(comment);
		Me.flush();
	}

	/**
	 * Forward.
	 *
	 * @param message the message
	 * @param comment the comment
	 * @param recipients the recipients
	 */
	public void forward(IMessage message, String comment, Recipient... recipients) {

	}

	/**
	 * Creates the file attachment.
	 *
	 * @param message the message
	 * @return the file attachment
	 */
	public IFileAttachment createFileAttachment(IMessage message) {

		if (message == null) {
			throw new IllegalArgumentException("message cannot be null");
		}

		IFileAttachment attachment = message.getAttachments().newFileAttachment();
		return attachment;
	}

	/**
	 * Creates the item attachment.
	 *
	 * @param message the message
	 * @return the item attachment
	 */
	public IItemAttachment createItemAttachment(IMessage message) {

		if (message == null) {
			throw new IllegalArgumentException("message cannot be null");
		}

		IItemAttachment attachment = message.getAttachments().newItemAttachment();
		return attachment;
	}

	/**
	 * Move.
	 *
	 * @param message the message
	 * @param destinationFolder the destination folder
	 */
	public void move(IMessage message, String destinationFolder) {
		IFolder folder = Me.getFolders().get(destinationFolder);

		if (folder != null) {
			message.move(folder.getId());
		}
	}

	/**
	 * Move.
	 *
	 * @param message the message
	 * @param destinationFolder the destination folder
	 */
	public void move(IMessage message, IFolder destinationFolder) {

		if (message == null) {
			throw new IllegalArgumentException("message cannot be null");
		}

		if (destinationFolder == null) {
			throw new IllegalArgumentException("destinationFolder cannot be null");
		}

		message.move(destinationFolder.getId());
	}

	/**
	 * Delete.
	 *
	 * @param message the message
	 */
	public void delete(IMessage message) {

		if (message == null) {
			throw new IllegalArgumentException("message cannot be null");
		}

		Me.getMessages().delete(message.getId());
		Me.flush();
	}

	// this could be the same as delete but only applies to drafts
	/**
	 * Discard.
	 *
	 * @param message the message
	 */
	public void discard(IMessage message) {

	}

	/**
	 * Mark as read.
	 *
	 * @param message the message
	 */
	public void markAsRead(IMessage message) {
		if (message == null) {
			throw new IllegalArgumentException("message cannot be null");
		}
		message.setIsRead(true);
	}

	/**
	 * Mark as unread.
	 *
	 * @param message the message
	 */
	public void markAsUnread(IMessage message) {
		if (message == null) {
			throw new IllegalArgumentException("message cannot be null");
		}
		message.setIsRead(false);
	}

	/**
	 * Gets the child folders.
	 *
	 * @return the child folders
	 */
	public List<IFolder> getChildFolders() {
		List<IFolder> childFolders = new ArrayList<IFolder>(Me.getRootFolder().getChildFolders());
		return childFolders;
	}

	/**
	 * Gets the messages.
	 *
	 * @param folderId the folder id
	 * @param from the from
	 * @return the messages
	 */
	public List<IMessage> getMessages(String folderId, int from) {

		Query<IMessage, IMessageCollection> query = Me.getFolders().get(folderId).getMessages().createQuery();
		query.setMaxResults(mBuilder.getMaxRsults());
		query.setFirstResult(from);

		List<IMessage> messages = new ArrayList<IMessage>(query.getResult());
		return messages;
	}

	/**
	 * Gets the messages.
	 *
	 * @param folderId the folder id
	 * @return the messages
	 */
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

	/**
	 * The Class Builder.
	 */
	public static final class Builder extends BaseOfficeClient.Builder {

		private int mMaxResults;

		/**
		 * Instantiates a new builder.
		 */
		public Builder() {
			super();
		}

		/**
		 * Instantiates a new builder.
		 *
		 * @param credentials the credentials
		 * @param resourceId the resource id
		 * @param odataEndpoint the odata endpoint
		 */
		public Builder(OAuthCredentials credentials, String resourceId, String odataEndpoint) {
			super(credentials, resourceId, odataEndpoint);
		}

		/* (non-Javadoc)
		 * @see com.microsoft.office365.api.BaseOfficeClient.Builder#build()
		 */
		@Override
		public MailClient build() {
			return new MailClient(this);
		}

		/* (non-Javadoc)
		 * @see com.microsoft.office365.api.BaseOfficeClient.Builder#setCredentials(com.microsoft.office365.http.OAuthCredentials)
		 */
		@Override
		public Builder setCredentials(OAuthCredentials credentials) {
			return (Builder) super.setCredentials(credentials);
		}

		/* (non-Javadoc)
		 * @see com.microsoft.office365.api.BaseOfficeClient.Builder#setOdataEndpoint(java.lang.String)
		 */
		@Override
		public Builder setOdataEndpoint(String odataEndpoint) {
			return (Builder) super.setOdataEndpoint(odataEndpoint);
		}

		/* (non-Javadoc)
		 * @see com.microsoft.office365.api.BaseOfficeClient.Builder#setResourceId(java.lang.String)
		 */
		@Override
		public Builder setResourceId(String resourceId) {
			return (Builder) super.setResourceId(resourceId);
		}

		/**
		 * Sets the max results.
		 *
		 * @param maxResults the max results
		 * @return the builder
		 */
		public Builder setMaxResults(int maxResults) {
			mMaxResults = maxResults;
			return this;
		}

		/**
		 * Gets the max rsults.
		 *
		 * @return the max rsults
		 */
		public int getMaxRsults() {
			return mMaxResults;
		}
	}
}
