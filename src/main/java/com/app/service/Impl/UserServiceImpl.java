package com.app.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dto.PassengerDTO;
import com.app.dto.UserDTO;
import com.app.entities.Passenger;
import com.app.entities.PassengerTypeEnum;
import com.app.entities.User;
import com.app.repository.PassengerRepository;
import com.app.repository.UserRepository;
import com.app.service.PassengerService;
import com.app.service.UserService;

@SuppressWarnings("null")
@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	UserRepository userRepository;

	@Autowired
	PassengerRepository passengerRepository;

	@Autowired
	PassengerService passengerService;

	@Override
	public UserDTO createUser(UserDTO userdto) {
		User user = this.modelMapper.map(userdto, User.class);
		User createdUser = userRepository.save(user);
		PassengerDTO passengerDTO = new PassengerDTO();
		passengerDTO.setPassengerName(createdUser.getFirstName() + " " + createdUser.getLastName());
		passengerDTO.setPassengerType(PassengerTypeEnum.DEFAULT);
		passengerDTO.setBalance(0);
		passengerDTO.setUserId(userdto);
		passengerService.createPassenger(passengerDTO);
		return this.modelMapper.map(createdUser, UserDTO.class);
	}

	@Override
	public UserDTO updateUser(UserDTO userdto, Long userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "userid", userId));
		Passenger passenger = this.passengerRepository.findByPassengerId(user.getPassenger().getPassengerId());
		user.setEmail(userdto.getEmail());
		user.setPassword(userdto.getPassword());
		user.setFirstName(userdto.getFirstName());
		user.setLastName(userdto.getLastName());
		passenger.setPassengerName(userdto.getFirstName() + userdto.getLastName());
		User updatedUser = this.userRepository.save(user);

		return this.modelMapper.map(updatedUser, UserDTO.class);
	}

	@Override
	public UserDTO getUserById(Long userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "userid", userId));
		return this.modelMapper.map(user, UserDTO.class);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users = this.userRepository.findAll();
		List<UserDTO> allUserDto = users.stream().map((user) -> this.modelMapper.map(user, UserDTO.class))
				.collect(Collectors.toList());
		return allUserDto;
	}

	@Override
	public void deleteUserById(Long userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "userid", userId));
		this.userRepository.delete(user);
	}

	@Override
	public UserDTO getUserByEmailAndPassword(String email, String password) {
		User user = this.userRepository.findByEmailAndPassword(email, password)
				.orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

		return this.modelMapper.map(user, UserDTO.class);
	}
}
