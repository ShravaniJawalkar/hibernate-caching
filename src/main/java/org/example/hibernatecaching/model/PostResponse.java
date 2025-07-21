package org.example.hibernatecaching.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private Long id;
    private String content;
    private String author;
    private String email;
}
