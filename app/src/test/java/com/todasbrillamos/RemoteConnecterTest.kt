package com.todasbrillamos

import android.util.Log
import com.todasbrillamos.model.RemoteConnecter
import com.todasbrillamos.model.data.*
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class RemoteConnecterTest {
    private val remoteConnecter = RemoteConnecter()

    @Test
    fun testGetUserByEmail_Success() = runTest {
        val email = "crocsvin@example.com"
        remoteConnecter.setEmail(email)

        val userInfo = UserInfo(
            "crocsvin@example.com", "kevin", "torres", "5578430107", emptyList(), emptyList()
        )

        val result = remoteConnecter.getUserbyEmail(email)
        assertEquals(userInfo, result)
    }

    @Test
    fun testGetUserByEmail_NotFound() = runTest {
        val email = "not.found@example.com"
        remoteConnecter.setEmail(email)

        val result = remoteConnecter.getUserbyEmail(email)
        assertEquals(UserInfo("", "", "", "", emptyList(), emptyList()), result)
    }

    @Test
    fun testGetProduct_Success() = runTest {
        val productId = 5
        val productInfo = ProductInfo(
            "Book Talk",
            "Concern edge agent music.",
            20.85f,
            "Muebles",
            "https://picsum.photos/500/500",
            1, 5
        )

        val result = remoteConnecter.getProductById(productId)
        assertEquals(productInfo, result)
    }

    @Test
    fun testGetProduct_NotFound() = runTest {
        mockkStatic(Log::class)

        every { Log.e(any(), any()) } returns 0

        val connecter = RemoteConnecter()
        val result = connecter.getProductById(0)

        assertNull(result)

        verify { Log.e("RemoteConnecter", "Product not found") }

        unmockkStatic(Log::class)
    }

    /* This test could only be run once
    @Test
    fun testSignupUser_Success() = runTest {
        val email = "hola@example.com"
        val name = "string"
        val lastName = "string"
        val phone = "5555555555"
        val password = "hola"

        val result = remoteConnecter.signupUser(email, name, lastName, phone, password)
        assertEquals("User created successfully", result)
    }


     */

    @Test
    fun testUserSignup_Failed() = runTest {
        mockkStatic(Log::class)

        every { Log.e(any(), any()) } returns 0

        val connecter = RemoteConnecter()
        val result = connecter.signupUser(
            "bad.@example.com",
            "string",
            "string",
            "5555555555",
            "hola"
        )

        assertNull(result)

        verify { Log.e("RemoteConnecter", "Unprocessable Entity") }

        unmockkStatic(Log::class)
    }


    @Test
    fun testAddToCart_Success() = runTest {
        val email = "crocsvin@example.com"
        remoteConnecter.setEmail(email)

        val cartItem = CartItem(
            ProductInfo(
                "Book Talk",
                "Concern edge agent music.",
                20.85f,
                "Muebles",
                "https://picsum.photos/500/500",
                1, 5
            ), 1
        )

        val result = remoteConnecter.addToCart(cartItem)
        assertTrue(result)

    }

    @Test
    fun testErase_Cart_Success() = runTest {
        val email = "crocsvin@example.com"
        remoteConnecter.setEmail(email)

        val result = remoteConnecter.deleteCart(email)
        assertFalse(result)
    }


    

    @Test
    fun addProductToCart_Failure() = runTest {
        val email = "crocsvin@example.com"
        remoteConnecter.setEmail(email)

        val cartItem = CartItem(
            ProductInfo(
                "Book Talk",
                "Concern edge agent music.",
                20.85f,
                "Muebles",
                "https://picsum.photos/500/500",
                1, 0
            ), 1
        )

        val result = remoteConnecter.addToCart(cartItem)
        assertFalse(result)
    }

    @Test
    fun testRemoveFromCart_Success() = runTest {
        val email = "crocsvin@example.com"
        remoteConnecter.setEmail(email)

        val cartItem = CartItem(
            ProductInfo(
                "Book Talk",
                "Concern edge agent music.",
                20.85f,
                "Muebles",
                "https://picsum.photos/500/500",
                1, 5
            ), 1
        )

        val result = remoteConnecter.removeFromCart(cartItem)
        assertTrue(result)
    }




}