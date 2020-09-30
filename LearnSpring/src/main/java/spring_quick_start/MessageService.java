package spring_quick_start;

import org.springframework.stereotype.Component;

/**
 * 执行简单的打印输出
 */

@Component
public class MessageService {

    //Control + O 插入无参构造方法
    public MessageService() {
        super();
        System.out.println("MessageService..");
    }

    /**
     * 执行打印功能
     * @return 返回要打印的字符串
     */
    public String getMessage() {
        return "Hello world";
    }
}
