package com.example.communityweb;

import com.example.communityweb.config.SecurityConfig;
import com.example.communityweb.domain.Board;
import com.example.communityweb.domain.User;
import com.example.communityweb.domain.enums.BoardType;
import com.example.communityweb.repository.BoardRepository;
import com.example.communityweb.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootApplication
public class CommunityWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommunityWebApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(UserRepository userRepository, BoardRepository boardRepository) throws Exception{
		return (args) -> {
			User user = userRepository.save(User.builder()
					.name("test").password("test").email("test@naver.com").createdDate(LocalDateTime.now()).build());

			IntStream.rangeClosed(1,200).forEach(index -> boardRepository.save(
					Board.builder().title("title"+index)
					.content("content"+index)
					.boardType(BoardType.free)
					.createdDate(LocalDateTime.now())
					.updatedDate(LocalDateTime.now())
					.user(user).build()
			));
		};
	}
}
