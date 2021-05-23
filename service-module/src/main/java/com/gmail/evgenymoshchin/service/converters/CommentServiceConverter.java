package com.gmail.evgenymoshchin.service.converters;

import com.gmail.evgenymoshchin.repository.model.Comment;
import com.gmail.evgenymoshchin.service.model.CommentDTO;

public interface CommentServiceConverter {
    CommentDTO convertCommentToDTO(Comment comment);
}
