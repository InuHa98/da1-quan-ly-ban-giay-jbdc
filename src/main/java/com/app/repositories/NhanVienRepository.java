package com.app.repositories;

import com.app.entities.NhanVien;
import com.app.models.responseDTO.INhanVienDTO;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Integer> {

    @Query(value = """
	SELECT
	    nv.id,
	    nv.username,
	    nv.password,
	    nv.ho_ten,
	    nv.email,
	    nv.so_dien_thoai,
	    nv.avatar,
	    nv.chuc_vu
	FROM NhanVien AS nv
	WHERE nv.id = :id
    """, nativeQuery = true)
    Optional<INhanVienDTO> findNhanVienById(@Param("id") int id);

	@Query(value = """
	SELECT
	    nv.id,
	    nv.username,
	    nv.password,
	    nv.ho_ten,
	    nv.email,
	    nv.so_dien_thoai,
	    nv.avatar,
	    nv.chuc_vu
	FROM NhanVien AS nv
	WHERE nv.email LIKE :email
    """, nativeQuery = true)
	Optional<INhanVienDTO> findNhanVienByEmail(@Param("email") String email);

	@Query(value = """
	SELECT
	    nv.id,
	    nv.username,
	    nv.password,
	    nv.ho_ten,
	    nv.email,
	    nv.so_dien_thoai,
	    nv.avatar,
	    nv.chuc_vu
	FROM NhanVien AS nv
	WHERE nv.username LIKE :username
    """, nativeQuery = true)
	Optional<INhanVienDTO> findNhanVienByUsername(@Param("username") String username);

    @Modifying
    @Query(value = """
    UPDATE NhanVien SET otp = :otp WHERE id = :id
    """, nativeQuery = true)
    Integer updateOTPById(@Param("id") int id, @Param("otp") String otp);

	@Query(value = """
    SELECT 
    	CASE WHEN COUNT(*) > 0 
			THEN 'true'
			ELSE 'false'
    	END 
    FROM NhanVien 
    WHERE email LIKE :email AND otp = :otp
    """, nativeQuery = true)
	boolean checkOtp(@Param("email") String email, @Param("otp") String otp);

	@Modifying
	@Query(value = """
    UPDATE NhanVien SET password = :password, otp = NULL WHERE email LIKE :email AND otp = :otp
    """, nativeQuery = true)
	Integer updateForgotPassword(@Param("password") String password, @Param("email") String email, @Param("otp") String otp);

	@Modifying
	@Query(value = """
    UPDATE NhanVien SET avatar = :avatar WHERE id = :id
    """, nativeQuery = true)
	Integer updateAvatar(@Param("id") int id, @Param("avatar") String avatar);

}
