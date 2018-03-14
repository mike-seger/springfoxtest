package com.net128.app.sft.service;

import com.net128.app.sft.model.Attachment;
import com.net128.app.sft.model.Message;
import com.net128.app.sft.model.UserContext;
import com.net128.app.sft.repository.AttachmentRepository;
import com.net128.app.sft.repository.MessageRepository;

import com.net128.app.sft.util.MimeUtil;
import org.apache.commons.io.IOUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@Service
@Transactional
public class MessageService {
    @Inject
    private MessageRepository repository;

    @Inject
    private AttachmentRepository attachmentRepository;

    private @Value("${sft.messages.repository.maxresults}") int maxResults;

    public List<Message> findUserMessages(UserContext context, String userId, String aroundMessageId, Integer maxResults) {
        boolean sentBefore=false;
        if(maxResults==null) {
            maxResults=this.maxResults;
        } else if(maxResults < 0) {
            maxResults=-maxResults;
            sentBefore=true;
        }
        Page<Message> messagePage=repository.findByUserIdAroundMessageId(userId, aroundMessageId, sentBefore, maxResults);
        return messagePage.getContent();
    }

    @Transactional(readOnly = true)
    public Message getMessage(String messageId) {
        Message message = repository.getOne(messageId);
        return message;
    }

    public Message create(UserContext context, Message message) {
        repository.save(message);
        repository.flush();
        return getMessage(message.getId());
    }

    public void attach(UserContext context, String messageId, Attachment attachment) {
        attach(context, repository.getOne(messageId), attachment);
    }

    @Transactional(readOnly = true)
    public void streamAttachment(UserContext context, String messageId, OutputStream outputStream) throws IOException {
        Pageable singleResult = new PageRequest(0, 1);
        List<Attachment> attachments=attachmentRepository.findByMessage(repository.getOne(messageId), singleResult).getContent();
        if(attachments.size()>0) {
            try (InputStream is = new ByteArrayInputStream(attachments.get(0).getData())) {
                IOUtils.copy(is, outputStream);
            }
        }
    }

    private void attach(UserContext context, Message message, Attachment attachment) {
        message.setMimeType(MimeUtil.mimeType(attachment.getData()));
        message.setLength(attachment.getData().length);
        repository.save(message);
        repository.flush();

        attachmentRepository.deleteByMessage(message);
        attachmentRepository.flush();
        attachment.setMessage(message);
        attachmentRepository.save(attachment);
    }
}
