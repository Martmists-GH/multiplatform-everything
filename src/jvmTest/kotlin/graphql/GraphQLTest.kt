package graphql

import com.martmists.multiplatform.graphql.GraphQL
import com.martmists.multiplatform.graphql.fullInterfaceType
import com.martmists.multiplatform.graphql.fullType
import graphql.schema.Character
import graphql.schema.Droid
import graphql.schema.Episode
import graphql.schema.Human
import kotlinx.coroutines.runBlocking
import kotlin.reflect.typeOf
import kotlin.test.Test

class GraphQLTest {
    val luke = Human("1000", "Luke Skywalker", listOf("1002", "1003", "2000", "2001"), listOf(4, 5, 6), null, "Tatooine")
    val vader = Human("1001", "Darth Vader", listOf("1004"), listOf(4), null, "Tatooine")
    val han = Human("1002", "Han Solo", listOf("1000", "1003", "2001"), listOf(4, 5, 6), null, null)
    val leia = Human("1003", "Leia Organa", listOf("1000", "1002", "2001"), listOf(4, 5, 6), null, "Alderaan")
    val tarkin = Human("1004", "Wilhuff Tarkin", listOf("1001"), listOf(4), null, null)
    val humans = listOf(
        luke,
        vader,
        han,
        leia,
        tarkin
    )

    val threepio = Droid("2000", "C-3PO", listOf("1000", "1002", "1003", "2001"), listOf(4, 5, 6), null, "Protocol")
    val artoo = Droid("2001", "R2-D2", listOf("1000", "1002", "1003"), listOf(4, 5, 6), null, "Astromech")

    val droids = listOf(
        threepio,
        artoo
    )
    val characters = humans + droids

    val gql: GraphQL
        get() = GraphQL().apply {
            schema {
                enum(Episode.entries)
                fullInterfaceType<Character> {
                    resolver {
                        when (this) {
                            is Human -> typeOf<Human>()
                            is Droid -> typeOf<Droid>()
                        }
                    }

                    property("friends") {
                        resolver {
                            friends.map { characters.first { c -> c.id == it } }
                        }
                    }

                    property("appearsIn") {
                        resolver {
                            appearsIn.map { Episode.entries.first { e -> e.number == it } }
                        }
                    }
                }
                fullType<Human> {
                    usesInterface<Character>()

                    property("friends") {
                        resolver {
                            friends.map { characters.first { c -> c.id == it } }
                        }
                    }

                    property("appearsIn") {
                        resolver {
                            appearsIn.map { Episode.entries.first { e -> e.number == it } }
                        }
                    }
                }
                fullType<Droid> {
                    usesInterface<Character>()

                    property("friends") {
                        resolver {
                            friends.map { characters.first { c -> c.id == it } }
                        }
                    }

                    property("appearsIn") {
                        resolver {
                            appearsIn.map { Episode.entries.first { e -> e.number == it } }
                        }
                    }
                }

                query("hero") {
                    val episode = argument<Episode?>("episode")

                    resolver { ctx ->
                        when (ctx.episode()) {
                            Episode.EMPIRE -> luke
                            else -> artoo
                        }
                    }
                }

                query("human") {
                    val id = argument<String>("id")

                    resolver { ctx ->
                        humans.first { it.id == ctx.id() }
                    }
                }

                query("droid") {
                    val id = argument<String>("id")

                    resolver { ctx ->
                        droids.first { it.id == ctx.id() }
                    }
                }
            }
        }

    @Test
    fun testFragments() {
        runBlocking {
            val res = gql.execute(
                """
query DroidFieldInFragment {
  emp: hero(episode: EMPIRE) {
    name
    ...DroidFields
  }
  all: hero {
    name
    ...DroidFields
  }
}

fragment DroidFields on Droid {
  primaryFunction
}
            """.trimIndent(), "DroidFieldInFragment", emptyMap())

            println(res)
        }
    }

    @Test
    fun testIntrospection() {
        runBlocking {
            val res = gql.execute(
                """
{
  __schema {
    queryType {
      fields {
        name
        args {
          name
          description
          type {
            name
            kind
            ofType {
              name
              kind
            }
          }
          defaultValue
        }
      }
    }
  }
}
            """.trimIndent(), null, emptyMap())

            println(res)
        }
    }
}
