package ai.zira.entity

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @get:NotBlank(message = "Email is required")
    @get:Size(min = 3, max = 2)
    val email: String,
    @get:NotBlank(message = "Password is required")
    @get:Size(min = 3, max = 25)
    val password: String)