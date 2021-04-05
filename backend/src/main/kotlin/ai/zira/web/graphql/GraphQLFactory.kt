package ai.zira.web.graphql

import ai.zira.entity.Account
import ai.zira.entity.Card
import ai.zira.extensions.getResourceAsReader
import ai.zira.jdbi.CardService
import graphql.GraphQL
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.SchemaGenerator
import graphql.schema.idl.SchemaParser
import graphql.schema.idl.TypeDefinitionRegistry
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.core.io.ResourceResolver
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Singleton

@Factory
class GraphQLFactory(
    private val accountsFetcher: AccountsFetcher,
    private val accountLoginFetcher: AccountLoginFetcher,
    private val accountSignUpFetcher: AccountSignUpFetcher,
    private val cardsFetcher: CardsFetcher,
    private val cardFetcher: CardByIdFetcher,
    private val createCardFetcher: CreateCardFetcher,
    private val deleteCardFetcher: DeleteCardFetcher,
    private val latestCardFetcher: LatestCardFetcher
) {

    @Bean
    @Singleton
    fun graphQL(resolver: ResourceResolver): GraphQL {
        val parser = SchemaParser()
        val generator = SchemaGenerator()

        val typeRegistry = TypeDefinitionRegistry()
        typeRegistry.merge(parser.parse(
            resolver.getResourceAsReader("classpath:schema.graphqls")
        ))

        val runtimeWiring = RuntimeWiring.newRuntimeWiring()
            .type("Query") { typeWiring ->
                typeWiring.dataFetcher("accounts", accountsFetcher)
                typeWiring.dataFetcher("login", accountLoginFetcher)
                typeWiring.dataFetcher("cards", cardsFetcher)
                typeWiring.dataFetcher("card", cardFetcher)
            }
            .type("Mutation") { typeWiring ->
                typeWiring.dataFetcher("signup", accountSignUpFetcher)
                typeWiring.dataFetcher("createCard", createCardFetcher)
                typeWiring.dataFetcher("deleteCard", deleteCardFetcher)
            }
            .type("Subscription") { typeWiring ->
                typeWiring.dataFetcher("latestCard", latestCardFetcher)
            }
            .build()

        val schema = generator.makeExecutableSchema(typeRegistry, runtimeWiring)

        return GraphQL.newGraphQL(schema).build()
    }
}
