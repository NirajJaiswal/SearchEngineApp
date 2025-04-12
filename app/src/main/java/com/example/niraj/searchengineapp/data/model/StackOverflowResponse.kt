package com.example.niraj.searchengineapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * @created 12/04/2025
 * @author Niraj Kumar
 *
 * Data class representing a response from the Stack Overflow API.
 * @property items A list of [Question] objects representing the questions returned by the API.
 *                 This list may be empty if no questions match the search criteria or if
 *                 an error occurred.
 */
data class StackOverflowResponse(
    @SerializedName("items") val items: List<Question>,
)

/**
 * Data class representing a Stack Overflow question.
 *
 * @property title The title of the question.
 * @property owner An [Owner] object representing the user who posted the question.
 * @property creationDate The timestamp representing the creation date of the question (in seconds since epoch).
 * @property link A URL string pointing to the Stack Overflow question.
 *
 */
data class Question(
    @SerializedName("title") val title: String,
    @SerializedName("owner") val owner: Owner,
    @SerializedName("creation_date") val creationDate: Long,
    @SerializedName("link") val link: String,
)

/**
 * Data class representing the owner of a resource, typically identified by a display name.
 *
 * @property displayName The display name of the owner
 */
data class Owner(
    @SerializedName("display_name") val displayName: String
)