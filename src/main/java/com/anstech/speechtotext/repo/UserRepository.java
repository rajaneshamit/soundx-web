package com.anstech.speechtotext.repo;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.anstech.speechtotext.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	// boolean existsByUserEmail(String str);
	Optional<User> findByEmail(String email);

	Optional<User> findByMobileOrEmail(String mobile, String email);

	List<User> findByIdIn(List<Long> userIds);

	Boolean existsByEmail(String email);

	Boolean existsByMobile(String mobile);

	User getUserById(long id);
	
	User findByMobile(String mobile);

	User getUserByEmail(String username);

	List<User> findAll();
	
	@Transactional
	@Modifying
	@Query("update User u set u.active = 'INACTIVE' where u.id = :id")
	Integer disableCustomerById(@Param("id") long id);

	@Transactional
	@Modifying
	@Query("update User u set u.password =:password where u.email = :email")
	Integer updatePassword(@Param("password") String password , @Param("email") String email);

	
	@Query("SELECT u.id FROM User u WHERE u.email = :email")
	long isEmailExists(@Param("email") String email);
}
