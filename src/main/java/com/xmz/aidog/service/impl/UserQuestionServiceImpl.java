package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.UserQuestion;
import generator.service.UserQuestionService;
import generator.mapper.UserQuestionMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【user_question】的数据库操作Service实现
* @createDate 2025-04-17 00:39:47
*/
@Service
public class UserQuestionServiceImpl extends ServiceImpl<UserQuestionMapper, UserQuestion>
    implements UserQuestionService{

}




