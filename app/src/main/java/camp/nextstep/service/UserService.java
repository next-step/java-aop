package camp.nextstep.service;

import camp.nextstep.dao.UserDao;
import camp.nextstep.dao.UserHistoryDao;
import camp.nextstep.domain.User;
import camp.nextstep.domain.UserHistory;
import com.interface21.beans.factory.annotation.Autowired;
import com.interface21.context.stereotype.Service;
import com.interface21.tx.annotation.Transactional;

@Service
public class UserService {

    private final UserDao userDao;
    private final UserHistoryDao userHistoryDao;

    @Autowired
    public UserService(UserDao userDao, UserHistoryDao userHistoryDao) {
        this.userDao = userDao;
        this.userHistoryDao = userHistoryDao;
    }

    public User findByAccount(final String account) {
        return userDao.findByAccount(account);
    }

    // XXX: 여기 @Transactional 달면 에러남
    public User findById(final long id) {
        return userDao.findById(id);
    }

    @Transactional
    public void save(final User user) {
        userDao.insert(user);
    }

    @Transactional
    public void changePassword(final long id, final String newPassword, final String createBy) {
        final var user = findById(id);
        user.changePassword(newPassword);
        userDao.update(user);
        userHistoryDao.log(new UserHistory(user, createBy));
    }
}
