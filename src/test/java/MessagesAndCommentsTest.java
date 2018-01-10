import com.kray.messenger.service.CommentService;

public class MessagesAndCommentsTest {

    public static void main(String[] args) {
        CommentService dao = new CommentService();
        System.out.println(dao.getAllComments(1));
    }

}
