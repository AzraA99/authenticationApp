package com.springapp.auth.controllers

import com.springapp.auth.dtos.LoginDTO
import com.springapp.auth.dtos.Message
import com.springapp.auth.dtos.RegisterDTO
import com.springapp.auth.models.User
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import javax.servlet.http.HttpServletResponse

@RunWith(MockitoJUnitRunner::class)
class AuthenticationControllerTest() {

    //Mock class
    private val authenticationController: AuthenticationController = mock(AuthenticationController::class.java)
    private val response: HttpServletResponse = mock(HttpServletResponse::class.java)

    private val registerBody = RegisterDTO(
        name = "Test",
        email = "test@test.com",
        password = "test"
    )
    private val loginBody = LoginDTO(
        email = "test@test.com",
        password = "1234"
    )

    @Test
    fun testForAuthenticationController() {
        // Model Data
        val user = User()
        user.name = "TestName"
        user.email = "TestEmail"
        user.password = "1234"

        // Learn Test Method What To Do
        `when`(authenticationController.register(registerBody)).thenReturn(ResponseEntity.ok().body(user))

        // Test Logic
        val registerBody = authenticationController.register(registerBody)

        // Test Results
        assertEquals("TestName", registerBody.body?.name)
        assertEquals("TestEmail", registerBody.body?.email)
    }

    @Test
    fun `testForRegisteredUser returns bad request message "user not found!"`() {
        `when`(authenticationController.login(loginBody, response)).thenReturn(ResponseEntity<Any>(HttpStatus.NOT_FOUND))
        val loginRequest = authenticationController.login(loginBody, response)
        assertEquals(HttpStatus.NOT_FOUND, loginRequest.statusCode)
    }

    @Test
    fun `testForRegisteredUser returns bad request"`() {
        `when`(authenticationController.login(loginBody, response)).thenReturn(ResponseEntity<Any>(HttpStatus.BAD_REQUEST))
        val loginRequest = authenticationController.login(loginBody, response)
        assertEquals(HttpStatus.BAD_REQUEST, loginRequest.statusCode)
    }

    @Test
    fun `testForRegisteredUser returns ok message "success"`() {
        `when`(authenticationController.login(loginBody, response)).thenReturn(ResponseEntity<Any>(HttpStatus.OK))
        val loginRequest = authenticationController.login(loginBody, response)
        assertEquals(HttpStatus.OK, loginRequest.statusCode)
    }

    @Test
    fun testForLogout() {
        val message = Message(message = "success")
        `when`(authenticationController.logout(response)).thenReturn(ResponseEntity.ok().body(message))
        val loginRequest = authenticationController.logout(response)
        assertEquals("success", loginRequest.body?.message)
    }
}
