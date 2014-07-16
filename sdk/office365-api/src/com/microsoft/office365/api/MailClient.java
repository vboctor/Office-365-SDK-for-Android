package com.microsoft.office365.api;

import com.microsoft.exchange.services.odata.model.DefaultFolder;
import com.microsoft.exchange.services.odata.model.Me;
import com.microsoft.exchange.services.odata.model.Messages;
import com.microsoft.exchange.services.odata.model.types.IFileAttachment;
import com.microsoft.exchange.services.odata.model.types.IFolder;
import com.microsoft.exchange.services.odata.model.types.IItemAttachment;
import com.microsoft.exchange.services.odata.model.types.IMessage;
import com.microsoft.exchange.services.odata.model.types.Recipient;

public class MailClient {

	public static Me Service;

	public MailClient() {
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

		//TODO: Flush?
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
			Service.flush();
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

	// public IMessage insertAttachment(IMessage message, IAttachment
	// attachment) {
	//
	// if (message == null) {
	// throw new IllegalArgumentException("message cannot be null");
	// }
	//
	// if (attachment == null) {
	// throw new IllegalArgumentException("attachment cannot be null");
	// }
	//
	// return null;
	// }

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

}
