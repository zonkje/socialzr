package com.szymek.socializr.common;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
public class ApplicationResponse {

    private final List<String> messages;
    private final String timeStamp;

}
