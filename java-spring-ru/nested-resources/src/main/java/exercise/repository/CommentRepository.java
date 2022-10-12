package exercise.repository;

import exercise.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

    // BEGIN
    @Autowired
    Iterable<Comment> getAllCommentByPostId(long id);
    Optional<Comment> getCommentByIdAndPostId(long commentId, long postId);
    // END
}
