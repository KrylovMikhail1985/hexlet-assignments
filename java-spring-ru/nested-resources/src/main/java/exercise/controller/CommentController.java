package exercise.controller;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;


@RestController
@RequestMapping("/posts")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    // BEGIN
    @GetMapping("/{postId}/comments")
    public Iterable<Comment> getAllCommentsByPostId(@PathVariable(name = "postId") long id) {
        return commentRepository.getAllCommentByPostId(id);
    }
    @GetMapping("/{postId}/comments/{commentId}")
    public Comment getCommentByIdAndPostId(@PathVariable(name = "postId") long postId,
                                           @PathVariable(name = "commentId") long commentId) {
        return commentRepository.getCommentByIdAndPostId(commentId, postId).orElseThrow(()->
                new ResourceNotFoundException("There is no Comment with postId=" + postId
                    + " and commentId=" + commentId));
    }
    @PostMapping("/{postId}/comments")
    public Iterable<Comment> createNewCommentForPost(@PathVariable(name = "postId") long postId,
                                                     @RequestBody Comment comment) {
        comment.setPost(postRepository.findById(postId).orElseThrow());
    commentRepository.save(comment);
    return commentRepository.getAllCommentByPostId(postId);
    }
    @PatchMapping("/{postId}/comments/{commentId}")
    public Comment updateComment(@PathVariable(name = "postId") long postId,
                                 @PathVariable(name = "commentId") long commentId,
                                 @RequestBody Comment c) {
        commentRepository.getCommentByIdAndPostId(commentId, postId).orElseThrow(
                ()-> new ResourceNotFoundException("There is no Comment with postId=" + postId
                + " and commentId=" + commentId));
        c.setId(commentId);
        c.setPost(postRepository.findById(postId).orElseThrow());
        return commentRepository.save(c);
    }
    @DeleteMapping("/{postId}/comments/{commentId}")
    public void deleteComment(@PathVariable(name = "postId") long postId,
                              @PathVariable(name = "commentId") long commentId) {
        Comment comment = commentRepository.getCommentByIdAndPostId(commentId, postId)
                .orElseThrow(()-> new ResourceNotFoundException("There is no Comment with postId=" + postId
                + " and commentId=" + commentId));
        commentRepository.delete(comment);
    }
    // END
}
