package com.github.marciovmartins.crnk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.crnk.client.CrnkClient;
import io.crnk.spring.client.RestTemplateAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

public class FooResourceRepositoryTest {

    @Test
    void test() {
        var client = getCrnkClient();
        var fooResourceRepository = client.getRepositoryForType(Foo.class);

        var foo = new Foo();
        foo.setNewValue("foo");
        foo = fooResourceRepository.create(foo);
        assertNull(foo.getOldValue());
        assertEquals("foo", foo.getNewValue());

        foo.setNewValue("bar");
        foo = fooResourceRepository.save(foo);
        assertEquals("foo", foo.getOldValue());
        assertEquals("bar", foo.getNewValue());
    }

    private CrnkClient getCrnkClient() {
        var client = new CrnkClient("http://localhost:8080");
        client.findModules();
        var template = ((RestTemplateAdapter) client.getHttpAdapter()).getImplementation();
        template.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        return client;
    }

}