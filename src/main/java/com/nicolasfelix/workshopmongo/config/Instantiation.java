package com.nicolasfelix.workshopmongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.nicolasfelix.workshopmongo.domain.Post;
import com.nicolasfelix.workshopmongo.domain.User;
import com.nicolasfelix.workshopmongo.dto.AuthorDTO;
import com.nicolasfelix.workshopmongo.dto.CommentDTO;
import com.nicolasfelix.workshopmongo.repository.PostRepository;
import com.nicolasfelix.workshopmongo.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		userRepository.deleteAll();
		postRepository.deleteAll();
		
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");
		
		userRepository.saveAll(Arrays.asList(maria, alex, bob));
		
		Post post1 = new Post(null, sdf.parse("23/02/2018"), "Vou Viajar", "Vou pra Ubt", new AuthorDTO(maria));	
		Post post2 = new Post(null, sdf.parse("23/02/2018"), "Hoje eu acordei feliz", "Salve UBT", new AuthorDTO(maria));
		
		CommentDTO c1 = new CommentDTO("Boa Viagem", sdf.parse("24/01/2018"), new AuthorDTO (alex));
		CommentDTO c2 = new CommentDTO("Curte Aí", sdf.parse("24/01/2018"), new AuthorDTO(bob));
		CommentDTO c3 = new CommentDTO("Show de Bola", sdf.parse("24/01/2018"), new AuthorDTO(alex));
		
		post1.getComments().addAll(Arrays.asList(c1, c2));
		post2.getComments().addAll(Arrays.asList(c3));
		
		postRepository.saveAll(Arrays.asList(post1, post2));
		
		maria.getPosts().addAll(Arrays.asList(post1, post2));
		userRepository.save(maria);
	}

}
