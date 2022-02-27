package com.hong_hoan.iuheducation.resolvers;

import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

@Component
public class Query  implements GraphQLQueryResolver {

    public String test() {
        return "hello world";
    }

}
