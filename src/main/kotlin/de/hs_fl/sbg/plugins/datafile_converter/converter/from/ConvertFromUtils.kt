package de.hs_fl.sbg.plugins.datafile_converter.converter.from

class ConvertFromUtils {
    companion object {
        /**
         * Converts [String] to [Boolean], [Int], [Long] or [Double] if possible.
         * Otherwise, it returns the [String]
         **/
        fun toTypeOrDefault(string: String): Any? {
            when {
                string.toBooleanStrictOrNull() != null -> {
                    return string.toBooleanStrict()
                }
                string.toIntOrNull() != null -> {
                    return string.toInt()
                }
                string.toLongOrNull() != null -> {
                    return string.toLong()
                }
                string.toDoubleOrNull() != null -> {
                    return string.toDouble()
                }
                // Special null indicator for XML
                string == "_Null" -> {
                    return null
                }
                else -> {
                    return string
                }
            }
        }
    }
}