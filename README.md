# Multiplatform Everything

I got annoyed that a lot of stuff sucked and/or wasn't multiplatform so I made a repo with everything I need and also multiplatform for everything.

Everything is licensed BSD 3-Clause (NON-AI), PRs are welcome.

## Stuff you can find here

- Multiplatform WeakRef/WeakMap/WeakValueMap
- Binary editing tools (ByteArray only)
- Kotlinx.coroutines GraphQL implementation
  - Only server is multiplatform
  - Subscriptions use the [`graphql-transport-ws` protocol](https://github.com/enisdenjo/graphql-ws/blob/master/PROTOCOL.md).
  - JVM-only codegen is provided in the `graphql-client-codegen` directory.
- Validation library
  - Email
  - URI
  - Domain
- Logger
  - Uses SLF4J on JVM/Android

## Maven artifacts

```kotlin
repositories {
    maven("https://maven.martmists.com/releases")
}

dependencies {
    implementation("com.martmists.multiplatform-everything:multiplatform-everything:1.1.5")
}
```

Alternatively you can just lift the code you need as-is, provided you respect the LICENSE file.
