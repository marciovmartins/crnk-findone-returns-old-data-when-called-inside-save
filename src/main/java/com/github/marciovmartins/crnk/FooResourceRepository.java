package com.github.marciovmartins.crnk;

import io.crnk.core.queryspec.QuerySpec;
import io.crnk.data.jpa.JpaEntityRepositoryBase;
import org.springframework.stereotype.Controller;

@Controller
public class FooResourceRepository extends JpaEntityRepositoryBase<Foo, Long> {

    public FooResourceRepository() {
        super(Foo.class);
    }

    @Override
    public <S extends Foo> S save(S resource) {
        var originalResource = findOne(resource.getId(), new QuerySpec(Foo.class));
        resource.setOldValue(originalResource.getNewValue());
        return super.save(resource);
    }

}