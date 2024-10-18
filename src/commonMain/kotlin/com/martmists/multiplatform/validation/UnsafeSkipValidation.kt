package com.martmists.multiplatform.validation

/**
 * Marks the annotated function as unsafe as it skips validation.
 * Only opt-in to this annotation if the validation has been done elsewhere.
 *
 * This only exists in order to create a "validated" data type from already validated data.
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
@RequiresOptIn(
    message = "Skipping validation is not recommended, unless it has been validated elsewhere.",
    level = RequiresOptIn.Level.ERROR
)
annotation class UnsafeSkipValidation
