package com.newrise.applicanttrackingsystem.servicesimpl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newrise.applicanttrackingsystem.entities.Users;
import com.newrise.applicanttrackingsystem.repository.UsersRepository;
import com.newrise.applicanttrackingsystem.services.OtpService;
import com.newrise.applicanttrackingsystem.utils.ColorPrinter;

@Service
public class OtpServiceImpl implements OtpService
{

    @Autowired
    private UsersRepository usersRepository;

    private static final int OTP_EXPIRY_MINUTES = 15;	
	
	@Override
	public boolean generateOtp(String email) 
	{
        Optional<Users> optionalUser = usersRepository.findByEmail(email);
        if (optionalUser.isPresent()) 
        {
            Users user = optionalUser.get();
            String otp = String.format("%06d", new Random().nextInt(999999));
            user.setOtpCode(otp);
            user.setOtpExpiry(LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES));
            usersRepository.save(user);
            // ######################## Integration of SMS/Email Service ########################
            
            
            
            
            // ######################## Integration of SMS/Email Service ########################
            ColorPrinter.printlnYellow("Generated OTP for "+ user.getEmail()+" : " + otp);
            return true;
        }
        return false;
	}

	@Override
	public boolean verifyOtp(String email, String otp) 
	{
        Optional<Users> optionalUser = usersRepository.findByEmail(email);
        if (optionalUser.isPresent()) 
        {
            Users user = optionalUser.get();
            if (user.getOtpCode() != null && user.getOtpExpiry() != null) 
            {
                if (user.getOtpCode().equals(otp) && LocalDateTime.now().isBefore(user.getOtpExpiry())) 
                {
                    user.setVerified(true);
                    // Clear OTP after successful verification
                    user.setOtpCode(null);
                    user.setOtpExpiry(null);
                    usersRepository.save(user);
                    return true;
                }
            }
        }
        return false;
	}

}
