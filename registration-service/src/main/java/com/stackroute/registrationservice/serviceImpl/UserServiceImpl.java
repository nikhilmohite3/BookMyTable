package com.stackroute.registrationservice.serviceImpl;
import com.stackroute.registrationservice.entity.User;
import com.stackroute.registrationservice.execption.UserAllReadyExist;
import com.stackroute.registrationservice.execption.UserNotFoundException;
import com.stackroute.registrationservice.repository.UserRepo;
import com.stackroute.registrationservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
//@Slf4j
public class UserServiceImpl implements UserService
{
	@Autowired
	private UserRepo userRepo;



	//As a user i should be able to edit my profile details so that i can update them
	// when user will register and want to change profile details than user can easily edit profile
	@Override
	 public User updateUser(User user, String emailId) throws UserNotFoundException {

		Optional<User> user1 = Optional.ofNullable(this.userRepo.findById(emailId).orElseThrow(() -> new UserNotFoundException()));
		user.setEmailId(user1.get().getEmailId());
		return userRepo.save(user);
	}
	// As a user, i should be able to register so that i can have an account with the system
	// when user will register to details than register with database
	@Override
	public User registerUser(User user) throws UserAllReadyExist {
//			log.info(user.toString());

		if (userRepo.existsById(user.getEmailId()))
		{
			throw new UserAllReadyExist();
		}
		User savedUser = userRepo.save(user);
		return savedUser;
	}

	// user want to delete profile than user can easily delete

	@Override
	public String deleteUser(String emailId) throws UserNotFoundException {
		if(userRepo.existsById(emailId))
		{
			userRepo.deleteById(emailId);
			return "deleted successfully";

		}
		throw new UserNotFoundException();
	}


}
