package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappings.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;
    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
        encryptionService = new EncryptionService();
    }

    public List<Credential> getCredentials(Integer userId){

        return credentialMapper.getCredentials(userId);
    }

    public int addCredential(Credential credential){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        return credentialMapper.insertCredential(new Credential(null, credential.getUrl(), credential.getUsername(), encodedKey, encryptedPassword, credential.getUserId()));
    }

    public int deleteCredential(Integer credentialId){
        return credentialMapper.deleteCredential(credentialId);
    }

    public int updateCredential(Credential credential){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        return credentialMapper.updateCredential(new Credential(credential.getCredentialId(),credential.getUrl(), credential.getUsername(), encodedKey, encryptedPassword, credential.getUserId()));
    }
    public Credential getCredential(Integer credentialId){
        return credentialMapper.getCredential(credentialId);
    }

    public String decodePassword(Credential credential){
        return encryptionService.decryptValue(credential.getPassword(), credential.getKey());
    }
}
