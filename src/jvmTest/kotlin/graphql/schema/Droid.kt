package graphql.schema

class Droid(
    id: String,
    name: String,
    friends: List<String>,
    appearsIn: List<Int>,
    secretBackstory: String?,
    val primaryFunction: String?,
) : Character(
    id,
    name,
    friends,
    appearsIn,
    secretBackstory
)
