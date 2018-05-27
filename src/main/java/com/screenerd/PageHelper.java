package com.screenerd;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.screenerd.domain.Post;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by telly on 27/05/18.
 */
public class PageHelper extends PageImpl<Post> {

    @JsonCreator
    public PageHelper(@JsonProperty("content") List<Post> content, @JsonProperty("pageable") Pageable pageable,
                      @JsonProperty("total") long total) {
        super(content, pageable, total);
    }

}

