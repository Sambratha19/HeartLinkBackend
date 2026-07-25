package com.example.heart_link_backend.Controller;

import com.example.heart_link_backend.Entity.Avatar;
import com.example.heart_link_backend.Repository.AvatarRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import  java.util.*;

@RestController
@RequestMapping("/avatars")
@CrossOrigin(origins = "http://localhost:3000")
public class AvatarController {

    @Autowired
    private AvatarRepository avatarRepository;

    @GetMapping
    public  List<Avatar> getAll(){
        return avatarRepository.findAll();
    }

    @GetMapping("/search")
    public List<Avatar> search(@RequestParam String q){
        return avatarRepository.searchAvatars(q);
    }

    @Transactional
    @PostMapping("/add")
    public  Avatar createAvatar(@RequestBody Avatar avatar){
        Avatar saved = avatarRepository.save(avatar);
        System.out.println("SAVED: " + saved);
        return saved;
    }
}
