package com.example.communityweb.domain;

import com.example.communityweb.domain.enums.BoardType;
import com.example.communityweb.repository.BoardRepository;
import com.example.communityweb.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserTest {


    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;

    @BeforeEach
    public void init(){
        User user = userRepository.save(User.builder().name("test").password("test").email("test@naver.com").createdDate(LocalDateTime.now()).build());
        Board board = boardRepository.save(Board.builder().title("title").content("content").boardType(BoardType.notice).createdDate(LocalDateTime.now())
        .updatedDate(LocalDateTime.now()).user(user).build());
    }

    @Test
    public void createTest(){
        User user = userRepository.findByEmail("test@naver.com");
        assertThat(user.getName()).isEqualTo("test");

        Board board = boardRepository.findByUser(user);
        assertThat(board.getTitle()).isEqualTo("title");
    }
}