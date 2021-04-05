package ai.zira.jdbi

import ai.zira.config.DbConfig
import ai.zira.entity.Account
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import javax.inject.Singleton
import javax.validation.constraints.Email
import org.jdbi.v3.sqlobject.kotlin.onDemand

interface AccountRepository {
    @SqlUpdate("insert into accounts (email, password) values(:email,:password)")
    @GetGeneratedKeys
    fun signup(@BindBean account: Account): Account

    @SqlQuery("select * from accounts")
    fun findAll(): List<Account>

    @SqlQuery("select * from accounts where email=:email and password=:password")
    fun login(@Bind("email") email: String, @Bind("password") password: String): Account?

    @SqlUpdate("delete from accounts where email=:email")
    fun deleteUser(@Bind("email") email: Email)
}

@Singleton
class AccountService {
    private val db = DbConfig.getInstance()

    fun signup(email: String, password: String) : Account? {
        return try {
            return Account(0, email, password).also {
                db.onDemand<AccountRepository>().signup(it)
            }
        } catch (e: Exception) {
            null
        }
    }

    fun findAll(): List<Account> {
        return try {
            db.onDemand<AccountRepository>().findAll()
        } catch (e: Exception) {
            emptyList<Account>()
        }
    }

    fun login(email: String, password: String) : Account? {
        return try {
            db.onDemand<AccountRepository>().login(email, password)
        } catch (e: Exception) {
            null
        }
    }

    fun deleteUser(email: Email) : Boolean {
        return try {
            db.onDemand<AccountRepository>().deleteUser(email)
            true
        } catch (e: Exception) {
            false
        }
    }
}