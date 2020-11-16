package com.example.communityweb.repository;

import com.example.communityweb.domain.Board;
import com.example.communityweb.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {
    Board findByUser(User user);
}
