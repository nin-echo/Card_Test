package ai.zira.web.graphql

import ai.zira.entity.Account
import ai.zira.entity.Card
import ai.zira.jdbi.AccountService
import ai.zira.jdbi.CardService
import com.fasterxml.jackson.databind.ObjectMapper
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import org.reactivestreams.Publisher
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
class AccountsFetcher(
    private val accountService: AccountService
) : DataFetcher<List<Account>> {
    override fun get(environment: DataFetchingEnvironment?): List<Account> = accountService.findAll()
}

@Singleton
class AccountSignUpFetcher(
    private val accountService: AccountService
) : DataFetcher<Account> {
    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun get(environment: DataFetchingEnvironment): Account? {
        log.info("Try to create a user")

        val email = environment.getArgument<String>("email")
        val password = environment.getArgument<String>("password")

        return accountService.signup(email = email, password = password)
    }
}

@Singleton
class AccountLoginFetcher(
    private val accountService: AccountService
) : DataFetcher<Account> {
    override fun get(environment: DataFetchingEnvironment): Account? {
        val email = environment.getArgument<String>("email")
        val password = environment.getArgument<String>("password")

        return accountService.login(email = email, password = password)
    }
}

@Singleton
class CardsFetcher(
    private val cardService: CardService
) : DataFetcher<List<Card>> {
    override fun get(environment: DataFetchingEnvironment): List<Card> {
        val accountId = environment.getArgument<String>("account_id").toLong()
        return cardService.findAll(account_id = accountId)
    }
}

@Singleton
class CardByIdFetcher(
    private val cardService: CardService
) : DataFetcher<Card> {
    override fun get(environment: DataFetchingEnvironment): Card? {
        val id = environment.getArgument<String>("id").toLong()
        return id.let { cardService.findById(it) }
    }
}

@Singleton
class CreateCardFetcher(
    private val cardService: CardService
) : DataFetcher<Card> {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun get(environment: DataFetchingEnvironment): Card? {
        log.info("Try to create a card")

        val accountId = environment.getArgument<String>("account_id").toLong()
        val title = environment.getArgument<String>("title")
        val description = environment.getArgument<String>("description")

        return cardService.save(
            account_id = accountId,
            title = title,
            description = description
        )
    }
}

@Singleton
class DeleteCardFetcher(
    private val cardService: CardService
) : DataFetcher<Boolean> {
    override fun get(environment: DataFetchingEnvironment): Boolean {
        val id = environment.getArgument<String>("id").toLong()
        return try {
            cardService.delete(id = id)
            true
        } catch (e: Exception) {
            false
        }
    }
}

@Singleton
class LatestCardFetcher(
    private val cardService: CardService
) : DataFetcher<Publisher<Card>> {
    override fun get(environment: DataFetchingEnvironment): Publisher<Card> = cardService.getLatestCard()
}