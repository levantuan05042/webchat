package org.example.chatservice.client;


import org.example.chatservice.config.FeignClientConfig;
import org.example.chatservice.model.dto.user.UserRequest;
import org.example.chatservice.model.dto.user.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "auth-service", configuration = FeignClientConfig.class)
public interface UserClient {
    @GetMapping("/api/users/check/{username}")
    boolean exists(@PathVariable String username);

    @PostMapping("/api/users/search")
    List<UserResponse> search(@RequestBody UserRequest request);
}


