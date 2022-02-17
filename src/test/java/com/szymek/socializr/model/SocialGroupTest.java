package com.szymek.socializr.model;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SocialGroupTest {

    SocialGroup socialGroup;

    @BeforeEach
    public void setUp(){
        socialGroup = new SocialGroup();
    }

    @Test
    void getName() {
        String name = "TestName";
        socialGroup.setName(name);

        assertEquals(name, socialGroup.getName());
    }

    @Test
    void getDescription() {
    }

    @Test
    void getMembers() {
    }
}