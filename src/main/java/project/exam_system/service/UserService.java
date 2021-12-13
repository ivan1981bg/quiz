package project.exam_system.service;

import project.exam_system.model.service.QuestionServiceModel;
import project.exam_system.model.service.UserRegisterServiceModel;
import project.exam_system.model.service.UserServiceModel;

import java.util.List;

public interface UserService {
    void seedRootUser();
    boolean userNameExists(String username);

    void registerAndLoginUser(UserRegisterServiceModel userRegisterServiceModel);

    UserServiceModel getByUsername(String username);
    List<UserServiceModel> getAll();

    void storeUserAnswer(String username,Long examId, Integer questionIndex, String answer);

    UserServiceModel makeAdmin(Long id);
}
