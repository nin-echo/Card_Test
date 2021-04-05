package ai.zira.jdbi

import ai.zira.config.DbConfig
import ai.zira.entity.Card
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.kotlin.onDemand
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import javax.inject.Singleton

interface CardRepository {
    @SqlUpdate("insert into cards (account_id, title, description) values(:account_id,:title,:description)")
    @GetGeneratedKeys
    fun save(@BindBean card: Card): Card

    @SqlQuery("select * from cards where account_id=:account_id")
    fun findAll(@Bind("account_id") account_id: Long): List<Card>

    @SqlQuery("select * from cards where id=:id")
    fun findById(@Bind("id") id: Long): Card?

    @SqlUpdate("delete from cards where id=:id")
    fun delete(@Bind("id") id: Long)
}

@Singleton
class CardService {
    private val db = DbConfig.getInstance()
    private val publishSubject = PublishSubject.create<Card>()

    fun save(
        account_id: Long,
        title: String,
        description: String
    ): Card? {
        return try {
            return Card(0, account_id, title, description).also {
                db.onDemand<CardRepository>().save(it)
                publishSubject.onNext(it)
            }
        } catch (e: Exception) {
            null
        }
    }

    fun findAll(account_id: Long): List<Card> {
        return try {
            db.onDemand<CardRepository>().findAll(account_id)
        } catch (e: Exception) {
            emptyList<Card>()
        }
    }

    fun findById(id: Long): Card? {
        return try {
            db.onDemand<CardRepository>().findById(id)
        } catch (e: Exception) {
            null
        }
    }

    fun delete(id: Long): Boolean {
        return try {
            db.onDemand<CardRepository>().delete(id)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getLatestCard() : Flowable<Card> = publishSubject.toFlowable(BackpressureStrategy.BUFFER)
}