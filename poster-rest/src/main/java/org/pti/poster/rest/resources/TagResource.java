package org.pti.poster.rest.resources;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.pti.poster.model.Post;
import org.pti.poster.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;

@Api(value = "Tag resource", position = 1)
@RequestMapping("/rest/posts/{id}/tags")
@RestController
public class TagResource {

    public static final String POSTS_ROOT = "/rest/posts/";
    @Autowired
    PostService postService;

    @ApiOperation(value = "Adds new tag to existing post.")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void updatePost(@RequestBody Post.Tag tag, @PathVariable BigInteger id, HttpServletResponse response) {
        Post initial = postService.getById(id);
        ArrayList<Post.Tag> tags = new ArrayList<>(initial.getPostTags());
        tags.add(tag);
        Post post = new Post(initial.getText(), tags);
        response.setHeader(HttpHeaders.LOCATION, POSTS_ROOT + String.valueOf(postService.addPost(post)));
    }

    @ApiOperation(value = "Deletes tag from post")
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public void deletePost(@RequestBody Post.Tag tag, @PathVariable BigInteger id, HttpServletResponse response) {
        Post initial = postService.getById(id);
        ArrayList<Post.Tag> tags = new ArrayList<>(initial.getPostTags());
        tags.remove(tag);
        Post post = new Post(initial.getText(), tags);
        response.setHeader(HttpHeaders.LOCATION, POSTS_ROOT + String.valueOf(postService.addPost(post)));
    }

    @ApiOperation(value = "Get All post tags.")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Collection<Post.Tag> getTags(@PathVariable BigInteger id) {
        return postService.getById(id).getPostTags();
    }
}