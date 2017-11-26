package com.okhrymovych_kalandiak.repo;

import com.okhrymovych_kalandiak.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    User findOneByUserName(String userName);

}
