package graphql.schema

sealed class Character(
    val id: String,
    val name: String,
    val friends: List<String>,
    val appearsIn: List<Int>,
    val secretBackstory: String?,
)
