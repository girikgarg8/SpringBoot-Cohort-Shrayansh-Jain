package com.security.controller;

import com.security.model.Comment;
import com.security.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/xss")
public class XssController {

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/comments")
    public String showComments(Model model) {
        List<Comment> comments = commentRepository.findAll();
        model.addAttribute("comments", comments);
        System.out.println("=== Displaying " + comments.size() + " comments ===");
        return "xss-comments";
    }

    @PostMapping("/comment")
    public String addComment(@RequestParam String comment) {
        System.out.println("=== New Comment Received ===");
        System.out.println("Comment content: " + comment);
        
        // VULNERABLE: Storing raw comment without sanitization
        Comment newComment = new Comment();
        newComment.setContent(comment);
        commentRepository.save(newComment);
        
        System.out.println("✓ Comment stored in database (without sanitization)");
        System.out.println("⚠️  If this contains script tags, XSS attack is possible!");
        
        return "redirect:/xss/comments";
    }
}

